package cn.smarthse.config.security.jwt;

import java.util.Map;

import org.apache.shiro.authc.AuthenticationToken;

import lombok.Data;

/**
 * jwt类型的Token（无状态）
 * 
 * 签发服务的核心功能是验证客户端是否合法，如果合法则授予其包含特定访问主张的JWT。
 * 
 */
public @Data class JwtToken implements AuthenticationToken {

	private static final long serialVersionUID = 1795833011374373298L;

	// 认证主体
	private JwtPrincipal principal;

	private String client;// 客户标识（可以是用户名、app id等等）
	private String digest;// 消息摘要
	private String timeStamp;// 时间戳
	private Map<String, String[]> parameters;// 访问参数
	private String host;// 客户端IP

	// 构造函数1
	public JwtToken() {
	}

	// 构造函数2
	public JwtToken(String clientKey, String digest, Map<String, String[]> parameters) {
		this.client = clientKey;
		this.digest = digest;
		this.parameters = parameters;
	}

	// 构造函数3
	public JwtToken(String clientKey, String timeStamp, String digest, String host, Map<String, String[]> parameters) {
		this.client = clientKey;
		this.timeStamp = timeStamp;
		this.digest = digest;
		this.host = host;
		this.parameters = parameters;
	}

	// 构造函数3
	public JwtToken(String clientKey, String timeStamp) {
		this.client = clientKey;
		this.timeStamp = timeStamp;
	}

	@Override
	public Object getPrincipal() {
		return client;
	}

	@Override
	public Object getCredentials() {
		return digest;
	}
	
	public JwtPrincipal getJwtPrincipal() {
		return principal;
	}

}
