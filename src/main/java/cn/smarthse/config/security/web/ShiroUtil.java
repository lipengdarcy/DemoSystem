package cn.smarthse.config.security.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import cn.smarthse.business.collection.system.SystemUser;

/**
 * 读取Shrio 当前 登录者信息
 * 
 */
public class ShiroUtil {
	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject() {

		return SecurityUtils.getSubject();

	}

	/**
	 * 读取当前 登录者用户认证实体
	 * 
	 * @Comments: <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2019年2月20日-上午9:17:26
	 * @return
	 */
	public static ShiroPrincipal getShiroPrincipal() {
		Subject subject = getSubject();
		ShiroPrincipal principal = (ShiroPrincipal) subject.getPrincipal();
		if (principal != null) {
			return principal;
		}

		return null;
	}

	/**
	 * 获取当前登录者对象
	 * <li>
	 */
	public static SystemUser getUserModel() {
		// 根据当前 head里面的值 获取 当前用户管理员验证实体
		ShiroPrincipal principal = getShiroPrincipal();
		if (principal != null) {
			return principal.getUser();
		}

		return null;
	}

	/**
	 * 获取当前登录者用户Id
	 *
	 */
	public static String getLoginUserId() {
		SystemUser user = getUserModel();
		return user != null ? user.getId() : null;
	}

	/**
	 * 获取当前登录者企业Id
	 * 
	 */
	public static Integer getLoginCid() {
		SystemUser user = getUserModel();
		if (user == null)
			return 0;
		if (user.getJob() == null)
			return 0;
		return user != null ? user.getJob().getCid() : null;
	}

	public static String getLoginUserName() {
		SystemUser user = getUserModel();
		return user != null ? user.getRealName() : null;
	}
}
