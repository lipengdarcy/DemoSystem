package cn.smarthse.config.security.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.smarthse.business.entity.system.UserThirdlogin;
import cn.smarthse.framework.util.StringUtil;
import cn.smarthse.framework.util.encode.Base64;

/**
 * app端权限过滤器：无状态权限
 * 
 * 支持跨域请求
 * 
 */
@Service
public class JwtAuthFilter extends AccessControlFilter {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

//	@Autowired
//	UserThirdloginMapper userThirdloginMapper;

	private UserThirdlogin getThirdloginByToken(int uid, String token) {
		
		return new UserThirdlogin();
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getServletPath();
		if (uri.startsWith("/m")) {
			String jsonWebToken = req.getHeader("Authorization");
			if (!StringUtil.isEmpty(jsonWebToken)) {
				String retToken = new String(Base64.decode(jsonWebToken.toCharArray()));

				String[] userLogin = retToken.split("&");
				int uid = Integer.parseInt(userLogin[0]);
				if (userLogin.length == 2) {
					UserThirdlogin thirdloginByToken = getThirdloginByToken(uid, userLogin[1]);
					return thirdloginByToken != null;
				}

			}
		}
		return false;
	}

	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// 1、客户端生成的消息摘要
		String digest = request.getParameter("digest");
		// 2、客户端传入的用户身份
		String username = request.getParameter("client");
		// 3、客户端请求的参数列表
		Map<String, String[]> params = new HashMap<String, String[]>(request.getParameterMap());
		params.remove("digest");
		// 4、生成无状态Token
		JwtToken token = new JwtToken(username, digest, params);
		try {
			// 5、委托给Realm进行登录
			getSubject(request, response).login(token);
		} catch (Exception e) {
			e.printStackTrace();
			onLoginFail(response); // 6、登录失败
			return false;
		}
		return true;
	}

	// 登录失败时默认返回401状态码
	private void onLoginFail(ServletResponse response) throws IOException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		httpResponse.getWriter().write("login error");
	}

	/**
	 * 判断用户是否想要登入。 检测header里面是否包含Authorization字段即可
	 */
	protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
		HttpServletRequest req = (HttpServletRequest) request;
		String authorization = req.getHeader("Authorization");
		return authorization != null;
	}

	/**
	 * 对跨域提供支持
	 */
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
		httpServletResponse.setHeader("Access-Control-Allow-Headers",
				httpServletRequest.getHeader("Access-Control-Request-Headers"));
		// 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
		if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
			httpServletResponse.setStatus(HttpStatus.OK.value());
			return false;
		}
		return super.preHandle(request, response);
	}

	/**
	 * 将非法请求跳转到 /401
	 */
	private void response401(ServletRequest req, ServletResponse resp) {
		try {
			HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
			httpServletResponse.sendRedirect("/401");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

}
