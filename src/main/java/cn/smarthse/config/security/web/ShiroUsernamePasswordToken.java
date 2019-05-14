package cn.smarthse.config.security.web;

import org.apache.shiro.authc.UsernamePasswordToken;

import lombok.Data;

/**
 * 用户名、密码类型的Token（session认证）
 * 
 */
public @Data class ShiroUsernamePasswordToken extends UsernamePasswordToken {
	private static final long serialVersionUID = 1795833011374373298L;

	//图形验证码
	private String captcha;
	
	//动态验证码
	private String vcode;
	
	//第三方平台TokenKey
	private String token;
	
	//带验证码登录(默认false)
	private boolean captchaLogin = false;
	
	
	public ShiroUsernamePasswordToken(){}
	
	/**
	 * 构造函数
	 * @param username	用户名
	 * @param password	密码
	 * @param rememberMe	记住我
	 * @param host	登录IP
	 */
	public ShiroUsernamePasswordToken(String username, char[] password,
			boolean rememberMe, String host){
		super(username, password, rememberMe, host);
	}
	

}
