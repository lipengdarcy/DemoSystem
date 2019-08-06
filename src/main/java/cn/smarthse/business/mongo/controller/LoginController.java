package cn.smarthse.business.mongo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.smarthse.business.entity.system.SysUser;
import cn.smarthse.business.mongo.collection.system.SystemUser;
import cn.smarthse.business.mongo.collection.system.UserJob;
import cn.smarthse.business.mongo.service.system.SystemUserService;
import cn.smarthse.config.security.web.ShiroFormAuthenticationFilter;
import cn.smarthse.config.security.web.ShiroUtil;
import cn.smarthse.framework.interceptor.log.Log;
import cn.smarthse.framework.interceptor.log.LogConstans;
import cn.smarthse.framework.model.ResponseData;
import cn.smarthse.framework.model.ResponseStateEnum;
import cn.smarthse.framework.util.CookieUtil;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 用户登录Controller
 */
@ApiIgnore
@Controller
@RequestMapping(value = "/security")
public class LoginController {

	private final String basePath = "security/";

	private final String info_prefix = "[登录模块]";

	private Logger log = LogManager.getLogger(this.getClass());

	@Autowired
	SystemUserService SystemUserService;

	@GetMapping("login")
	public String login(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("{}-登录页", info_prefix);

		if (ShiroUtil.getUserModel() != null) {
			WebUtils.redirectToSavedRequest(request, response, "/");
			return null;
		}
		String usernameCookie = CookieUtil.getCookie(request, ShiroFormAuthenticationFilter.COOKIE_LOGIN_USERNAME);
		model.addAttribute("cookie_username", usernameCookie);
		return basePath + "login";
	}

	private void addUser() {
		SystemUser user = new SystemUser();
		user.setUserName("admin");
		UserJob job = new UserJob();
		SystemUserService.addUser(user);
	}

	/**
	 * 登录失败时，会返回此页，失败页处理失败原因，并返回给前端 登录成功时，自动进入配置successUrl页
	 * 
	 */
	@ResponseBody
	@PostMapping("login")
	public ResponseData<Map<String, Object>> login(HttpServletRequest request, HttpServletResponse response) {
		ResponseData<Map<String, Object>> r = new ResponseData<Map<String, Object>>();
		Subject subject = SecurityUtils.getSubject();
		// 用户名、记住我、是否手机端登录、异常、失败原因
		String username = WebUtils.getCleanParam(request, ShiroFormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
		boolean rememberMe = WebUtils.isTrue(request, ShiroFormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
		String exception = (String) request
				.getAttribute(ShiroFormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String) request.getAttribute(ShiroFormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
		if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")) {
			message = "用户或密码错误, 请重试.";
		}
		r.setCode(ResponseStateEnum.fail.getCode());
		r.setMessage(message);
		return r;
	}

	/**
	 * 退出登录
	 */
	@GetMapping("logout")
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
		}
		return "redirect: /security/login";
	}

	/**
	 * 注册
	 */
	@GetMapping("register")
	public String register() {
		return basePath + "register";
	}

	@Log(module = LogConstans.SYSTEM_USER, description = "用户注册", type = LogConstans.type_opt_add)
	@ResponseBody
	@PostMapping("register")
	public ResponseData<String> register(SysUser user) {
		ResponseData<String> data = new ResponseData<String>();
		data.setMessage("注册成功!");
		return data;
	}

	/**
	 * 忘记密码
	 */
	@GetMapping("forget")
	public String forget() {
		return basePath + "forget";
	}

	@Log(module = LogConstans.SYSTEM_USER, description = "忘记密码", type = LogConstans.type_opt_edit)
	@ResponseBody
	@PostMapping(value = "/forget")
	public ResponseData<String> forget(SysUser user) {
		ResponseData<String> data = new ResponseData<String>();
		data.setMessage("密码修改成功!");
		return data;
	}

}
