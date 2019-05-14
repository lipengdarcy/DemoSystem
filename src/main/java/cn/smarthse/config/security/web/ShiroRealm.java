package cn.smarthse.config.security.web;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import cn.smarthse.business.entity.system.SysUser;
import cn.smarthse.business.model.system.SysUserModel;
import cn.smarthse.business.service.system.ISysUserService;
import cn.smarthse.framework.util.encode.Encodes;

/**
 * 用户的认证授权域（web基于session）
 */
public class ShiroRealm extends AuthorizingRealm {
	private Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private ISysUserService userService;

	//密码验证器
	@Autowired
	private ShiroAuthorizingCredentialsMatcher credentialsMatcher;
	
	/**
	 * 构造函数，设置安全的初始化信息
	 */
	public ShiroRealm() {
		super();
		setAuthenticationTokenClass(ShiroUsernamePasswordToken.class);
	}

	/**
	 * 根据认证方式（如表单）获取用户名称、密码
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		ShiroUsernamePasswordToken upToken = (ShiroUsernamePasswordToken) token;
		String username = upToken.getUsername();
		if (StringUtils.isEmpty(username)) {
			// 登录账号为空
			throw new AccountException("登录账号不能为空!");
		}
		//根据用户名/tel读取登录实体
		SysUser user = userService.getUser(username);
		if (user == null) {
			//
			throw new UnknownAccountException("账号不存在！");
		}
		
		//TODO ExcessiveAttemptsException 已被禁止登录，登录超过设定次数，请%s后重新尝试！
		//TODO LockedAccountException 登录次数过多，登录锁定
		
		//将user信息，转换为userModel
		SysUserModel userModel = new SysUserModel(user);
		
		byte[] salt = Encodes.hexDecode(user.getSalt());
		ShiroPrincipal subject = new ShiroPrincipal(userModel);
		return new SimpleAuthenticationInfo(subject, user.getPassWord(), ByteSource.Util.bytes(salt), getName());
	}

	/**
	 * 获取当前认证实体的授权信息（授权包括：角色、权限）
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		ShiroPrincipal p = null;
		// 获取当前登录的用户名
		Object obj = super.getAvailablePrincipal(principals);
		if (obj instanceof ShiroPrincipal) {
			p = (ShiroPrincipal) obj;
		} else {
			return info;
		}
		
		SysUserModel user = p.getUser();
		try {
			if (!p.isAuthorized()) {
				List<String> roleList = userService.getUserRoleCodeList(p.getUser().getId());
				p.setAuthorized(true);
				p.setRoles(roleList);
				logger.info("用户【{}】 角色列表为：【{}】" ,user.getRealName(), roleList);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new AuthorizationException("用户【" + user.getUserName() + "】授权失败");
		}
		
		// 给当前用户设置权限/角色
		info.addStringPermissions(p.getPermissionList());
		info.addRoles(p.getRoles());
		return info;
	}

	
	@PostConstruct
	public void initCredentialsMatcher() {
		setCredentialsMatcher(credentialsMatcher);
	}
	
	@Override
	protected void checkPermission(Permission permission, AuthorizationInfo info) {
		authorizationValidate(permission);
		super.checkPermission(permission, info);
	}
	
	@Override
	protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
        		authorizationValidate(permission);
            }
        }
		return super.isPermitted(permissions, info);
	}
	
	@Override
	public boolean isPermitted(PrincipalCollection principals, Permission permission) {
		authorizationValidate(permission);
		return super.isPermitted(principals, permission);
	}
	
	@Override
	protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
            	authorizationValidate(permission);
            }
        }
		return super.isPermittedAll(permissions, info);
	}
	
	/**
	 * 授权验证方法
	 * @param permission
	 */
	private void authorizationValidate(Permission permission){
		// 模块授权预留接口
	}

}
