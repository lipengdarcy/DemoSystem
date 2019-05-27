package cn.smarthse.config.security.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.smarthse.business.collection.system.SystemUser;
import lombok.Data;

/**
 * 自定义认证主体
 */
public @Data class ShiroPrincipal implements Serializable {
	private static final long serialVersionUID = 1428196040744555722L;

	// 用户对象
	private SystemUser user;

	// 用户权限列表
	private List<String> permissionList = new ArrayList<String>();
	//用户角色列表
	private List<String> roles = new ArrayList<String>();
		
	// 是否已授权。如果已授权，则不需要再从数据库中获取权限信息，减少数据库访问
	// 这里会导致修改权限时，需要重新登录方可有效
	private boolean isAuthorized = false;
	
	//登入用户的IP地址
	private String loginUserIp;

	/**
	 * 构造函数，参数为User对象 根据User对象属性，赋值给Principal相应的属性上
	 * 
	 * @param user
	 */
	public ShiroPrincipal(SystemUser user) {
		this.user = user;
	}


	/**
	 * 
	 * @return
	 */
	public String getUsername() {
		if (user != null)
			return user.getUserName();

		return null;
	}

	public String getId() {
		return this.user.getId();
	}

	/**
	 * <shiro:principal/>标签显示中文名称
	 * PS:此处只能返回用户名，KickoutSessionControlFilter过滤器根据这个登录名进行验证是否重复登录
	 */
	@Override
	public String toString() {
		return this.user.getRealName();
	}

}
