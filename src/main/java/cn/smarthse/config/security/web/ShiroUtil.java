/**
 * 
 */
package cn.smarthse.config.security.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import cn.smarthse.business.model.system.SysUserModel;

/**
 * 《读取Shrio 当前 登录者信息》
 * 
 * 
 * @Project:  GiianSystem
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8> 
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2019年2月20日-上午9:15:42
 */
public class ShiroUtil {
	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject(){
		
		return SecurityUtils.getSubject();
		
	}
	/**
	 * 读取当前 登录者用户认证实体
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2019年2月20日-上午9:17:26
	 * @return
	 */
	public static ShiroPrincipal getShiroPrincipal() {
		Subject subject = getSubject();
		ShiroPrincipal principal = (ShiroPrincipal) subject.getPrincipal();
		if (principal != null){
			return principal;
		}
		
		return null;
	}
	
	
	/**
	 * 获取当前登录者对象
	 * <li>
	 */
	public static SysUserModel getUserModel(){
		//根据当前 head里面的值 获取 当前用户管理员验证实体
		ShiroPrincipal principal = getShiroPrincipal();
		if (principal != null){
			return principal.getUser();
		}
		
		return null;
	}
	
	
	/**
	 * 获取当前登录者用户Id
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2018年5月18日-下午1:25:00
	 * @return
	 */
	public static Integer getLoginUserId() {
		SysUserModel user = getUserModel();
		return user!=null?user.getId() : null;
	}
	
	/**
	 * 获取当前登录者企业Id
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2018年5月18日-下午1:25:00
	 * @return
	 */
	public static Integer getLoginCid() {
		SysUserModel user = getUserModel();
		return user!=null?user.getCid() : null;
	}


	public static String getLoginUserName() {
		SysUserModel user = getUserModel();
		return user!=null?user.getRealName() : null;
	}
}
