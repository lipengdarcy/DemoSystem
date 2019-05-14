/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */


package cn.smarthse.business.service.system;

import java.util.List;

import cn.smarthse.business.entity.system.SysUserRole;
import cn.smarthse.framework.generic.GenericService;


 /**
 *
 *
 *
 *
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8>
 * @author <开发者>
 * @since 2019-02-19 04:00
 */
public interface ISysUserRoleService extends GenericService<SysUserRole>{

	/**
	 * 
	* @Function: ISysUserRoleService.java
	* @Description: 按用户Id查出角色
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: cjy
	* @date: 2019年2月20日 下午1:53:22 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2019年2月20日      cjy           v1.0.0               修改原因
	 */
	List<SysUserRole> getListByUserId(Integer userId);
	
	/**
	 * 
	* @Function: ISysUserRoleService.java
	* @Description: 按用户Id和角色查SysUserRole表
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: cjy
	* @date: 2019年2月20日 下午3:22:26 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2019年2月20日      cjy           v1.0.0               修改原因
	 */
	SysUserRole getByUserIdAndRole(Integer userId,Integer roleId);
	
	/**
	 * 
	* @Function: ISysUserRoleService.java
	* @Description: 找出拥有业务类型角色的uid
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: cjy
	* @date: 2019年3月13日 下午2:55:32 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2019年3月13日      cjy           v1.0.0               修改原因
	 */
	List<SysUserRole> getUidListByBusinessType();
	
	/**
	 * 
	* @Function: ISysUserRoleService.java
	* @Description: 根据用户ID批量删除
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: cjy
	* @date: 2019年2月21日 下午4:24:23 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2019年2月21日      cjy           v1.0.0               修改原因
	 */
	void delByUserId(Integer userId,Integer updateBy);
	
	/**
	 * 判断用户是否包含roleIds角色(多个)
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2019年3月18日-下午3:06:09
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	boolean checkUserRole(Integer userId, String roleIds);

}
