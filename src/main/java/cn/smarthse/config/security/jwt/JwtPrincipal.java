package cn.smarthse.config.security.jwt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * jwt认证主体
 */
public @Data class JwtPrincipal implements Serializable {

	private static final long serialVersionUID = 1428196040744555722L;

	private String id;
	private String userName;// 用户名
	private String issuer;// 签发者
	private Date issuedAt;// 签发时间
	private String audience;// 接收方
	private String roles;// 角色
	private String perms;// 权限

	// 用户权限列表
	private List<String> permissionList = new ArrayList<String>();

	// 是否已授权。如果已授权，则不需要再从数据库中获取权限信息，减少数据库访问
	// 这里会导致修改权限时，需要重新登录方可有效
	private boolean isAuthorized = false;

	// 登入用户的IP地址
	private String loginUserIp;

	public JwtPrincipal() {
	}

	/**
	 * <@shiro.principal/>标签显示中文名称
	 */
	@Override
	public String toString() {
		return this.getUserName();
	}

}
