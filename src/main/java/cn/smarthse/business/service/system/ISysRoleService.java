/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */

package cn.smarthse.business.service.system;

import java.util.List;

import cn.smarthse.business.entity.system.SysRole;
import cn.smarthse.business.model.system.SysRoleSearchModel;
import cn.smarthse.framework.generic.GenericService;

/**
 * 系统角色相关
 */
public interface ISysRoleService extends GenericService<SysRole> {

	List<String> getUserRoleCodeList(Integer id);

	/**
	 * 获取全部角色
	 * 
	 * @param model
	 *            搜索条件，如角色名称
	 *
	 */
	public List<SysRole> getRoleList(SysRoleSearchModel model);

	/**
	 * 
	 * @Function: ISysRoleService.java
	 * @Description:找出业务类型的角色id
	 *
	 */
	List<SysRole> getByRoleType(Integer roleType);

	/**
	 * 
	 * @Function: ISysRoleService.java
	 * @Description: 通过角色名获取角色
	 */
	SysRole getByRoleName(Integer deptId, String roleName);

}
