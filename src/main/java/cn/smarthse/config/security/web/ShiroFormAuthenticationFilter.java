package cn.smarthse.config.security.web;

import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

import cn.smarthse.framework.Constant;
import cn.smarthse.framework.model.ResponseData;
import cn.smarthse.framework.model.ResponseStateEnum;
import cn.smarthse.framework.util.CookieUtil;
import cn.smarthse.framework.util.HttpUtil;
import cn.smarthse.framework.util.JsonMapper;
import cn.smarthse.framework.util.StringUtils;

/**
 * 表单验证（包含验证码）过滤类
 * 
 * 1.过滤所有非静态资源的请求;
 * 
 * 2.踢出重复登录的用户
 */
@Service
public class ShiroFormAuthenticationFilter extends FormAuthenticationFilter {

	private SessionDAO sessionDAO;

	protected final Logger logger = LogManager.getLogger(this.getClass());
	
	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
	
	// 消息
	public static final String DEFAULT_MESSAGE_PARAM = "message";
	
	// 登录带图形验证码
	public static final String DEFAULT_CAPTCHA_LOGIN_PARAM = "captchaLogin";

	// 登录图形验证码
	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	//
	private String messageParam = DEFAULT_MESSAGE_PARAM;

	/** COOKIE保存的用户名 **/
	public static final String COOKIE_LOGIN_USERNAME = "cookie_username";

	/**
	 * @return the sessionDAO
	 */
	public SessionDAO getSessionDAO() {
		return sessionDAO;
	}

	/**
	 * @param sessionDAO
	 *            the sessionDAO to set
	 */
	public void setSessionDAO(SessionDAO sessionDAO) {
		this.sessionDAO = sessionDAO;
	}

	public ShiroFormAuthenticationFilter(SessionDAO dao) {
		this.setSessionDAO(dao);
	}

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {

		String username = getUsername(request);
		String password = getPassword(request);
		if (password == null) {
			password = "";
		}
		boolean rememberMe = isRememberMe(request);
		String host = StringUtils.getRemoteAddr((HttpServletRequest) request);
		String captcha = getCaptcha(request);
		boolean captchaLogin = isCaptchaLogin(request);

		logger.info("表单登录过滤器：：：username:{}, password:{}, host:{}, captcha:{}, rememberMe:{},captchaLogin:{}", username,
				password, host, captcha, rememberMe, captchaLogin);
		// TODO 手机端登录时，可切换为ShiroUsernamePasswordToken(手机+短信验证码）
		ShiroUsernamePasswordToken token = new ShiroUsernamePasswordToken(username, password.toCharArray(), rememberMe,
				host);
		token.setCaptcha(captcha);
		token.setCaptchaLogin(captchaLogin);
		return token;
	}

	public String getCaptchaParam() {
		return captchaParam;
	}

	/**
	 * 读取图形验证码
	 * 
	 * @param request
	 * @return
	 */
	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	public String getMessageParam() {
		return messageParam;
	}

	/**
	 * 
	 * @Comments: <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2017-7-15-上午9:32:30
	 * @param request
	 * @return
	 */
	private boolean isCaptchaLogin(ServletRequest request) {
		return WebUtils.isTrue(request, DEFAULT_CAPTCHA_LOGIN_PARAM);
	}

	/**
	 * 登录成功之后跳转URL
	 */
	public String getSuccessUrl() {
		// TODO 也有可能返回json
		return super.getSuccessUrl();
	}

	/*
	 * 主要是针对登入成功的处理方法。对于请求头是AJAX的之间返回JSON字符串。
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		ShiroUsernamePasswordToken r_token = (ShiroUsernamePasswordToken) token;

		// 保存用户名cookie
		try {
			CookieUtil.setCookie(httpServletResponse, COOKIE_LOGIN_USERNAME,
					URLEncoder.encode(r_token.getUsername(), Constant.characterEncoding));
		} catch (Exception e) {
		}
		//
		// 登录用户编号
		httpServletRequest.getSession().setAttribute(Constant.ACCOUNT_SESSION_UID, ShiroUtil.getLoginUserId());
		// 登录用户所在企业
		httpServletRequest.getSession().setAttribute(Constant.ACCOUNT_COMPANYID, ShiroUtil.getLoginCid());
		// 登入用户名称
		httpServletRequest.getSession().setAttribute(Constant.ACCOUNT_SESSION_UNAME,
				ShiroUtil.getUserModel().getRealName());
		// 登入用户头像
		// httpServletRequest.getSession().setAttribute(Constant.ACCOUNT_USER_ICON,
		// ShiroUtil.getUserModel().get);

		// 保存登录日志

		if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With"))) {
			// 页面submit形式，登录成功进入跳转
			SavedRequest savedRequest = WebUtils.getSavedRequest(request);
			if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase(AccessControlFilter.GET_METHOD)) {
				String successUrl = savedRequest.getRequestUrl();
				WebUtils.issueRedirect(request, response, successUrl, null, true);
			} else {
				issueSuccessRedirect(request, response);
			}
		} else {
			// 窗口登录，ajax形式返回成功的信息
			ResponseData<Map<String, Object>> r = new ResponseData<Map<String, Object>>();
			r.setCode(ResponseStateEnum.success.getCode());

			// 返回前端的数据
			Map<String, Object> userMap = new HashMap<String, Object>();
			// 登录首页地址
			String successUrl = null;
			SavedRequest savedRequest = WebUtils.getSavedRequest(request);
			if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase(AccessControlFilter.GET_METHOD)) {
				successUrl = savedRequest.getRequestUrl();
			}

			if (StringUtils.isEmpty(successUrl)) {
				successUrl = getSuccessUrl();
			}

			userMap.put("home", successUrl);
			r.setData(userMap);
			// 转换为JSON字符串
			httpServletResponse.setCharacterEncoding(Constant.characterEncoding);
			PrintWriter out = httpServletResponse.getWriter();
			out.println(JsonMapper.toJsonString(r));
			out.flush();
			out.close();
		}

		return false;
	}

	/**
	 * 登录失败调用事件
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		// ShiroAuthorizingRealm 根据异常信息，分析登录失败原因，返回给登录页

		Subject subject = getSubject(request, response);
		if (subject.isAuthenticated() || subject.isRemembered()) {
			try { // 如果身份验证成功了 则也重定向到成功页面
				issueSuccessRedirect(request, response);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		String className = e.getClass().getName(), message = "";
		if (IncorrectCredentialsException.class.getName().equals(className)
				|| UnknownAccountException.class.getName().equals(className)) {
			message = "用户或密码错误, 请重试.";
		} else if (AuthenticationException.class.getName().equals(className)) {
			message = StringUtils.replace(e.getMessage(), "msg:", "");
		} else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:")) {
			message = StringUtils.replace(e.getMessage(), "msg:", "");
		} else if (ExcessiveAttemptsException.class.getName().equals(className)) {
			// 尝试次数过多，切换登录方式

		} else if (LockedAccountException.class.getName().equals(className)) {
			// 账号过度尝试,切换带图形验证码登录
			message = "账号过度尝试";
		} else {
			message = "系统出现点问题，请稍后再试！";
			e.printStackTrace(); // 输出到控制台
		}

		// 带着失败消息，回到登录页
		request.setAttribute(getFailureKeyAttribute(), className);
		request.setAttribute(getMessageParam(), message);
		return true;
	}

	@Override
	protected boolean isLoginRequest(ServletRequest request, ServletResponse response) {
		// TODO 确定登录参数以及登录地址
		boolean isloginpath = pathsMatch("/security/login", request);
		return isloginpath;
	}

	@Override
	protected boolean isLoginSubmission(ServletRequest request, ServletResponse response) {
		return (request instanceof HttpServletRequest)
				&& WebUtils.toHttp(request).getMethod().equalsIgnoreCase(POST_METHOD);
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		boolean isAccessAllowed = super.isAccessAllowed(request, response, mappedValue);
		// 不可登录,且非登录页
		if (isAccessAllowed == false && !isLoginRequest(request, response)) {

			logger.info("saveRequest : {}", httpServletRequest.getRequestURI());
			// 保存请求入口
			WebUtils.saveRequest(request);
		}

		String uri = httpServletRequest.getServletPath();
		/**
		 * jdk 1.7编译的情况下，静态资源也会进入此过滤器，因此额外增加静态资源的判断（jdk1.8正常，静态资源不会进入此过滤器， shiro配置有效）
		 * 静态资源和验证码不需要过滤
		 */
		if (HttpUtil.isStaticResource(uri) || uri.startsWith("/m/")) {
			return true;
		}

		Subject subject = getSubject(request, response);
		Object p = subject.getPrincipal();

		// 1.没有登录
		if (p == null && !subject.isRemembered()) {
			return false;
		}
		Serializable thisSessionId = subject.getSession().getId();
		String username = subject.getPrincipal().toString();
		Collection<Session> sessionList = sessionDAO.getActiveSessions();
		for (Session a : sessionList) {
			// 当前session在列表，说明已经登录，需要踢出原来的
			String id = String.valueOf(a.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY));
			if (!a.getId().equals(thisSessionId) && StringUtils.equals(id, username)) {
				logger.info("账号:" + id + "在别处登录，你被踢出系统了……");
				a.setTimeout(0);
			}
		}
		return super.isAccessAllowed(request, response, mappedValue)
				|| (!isLoginRequest(request, response) && isPermissive(mappedValue));

	}
}
