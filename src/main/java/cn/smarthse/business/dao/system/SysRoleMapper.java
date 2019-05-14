package cn.smarthse.business.dao.system;

import java.util.List;

import cn.smarthse.business.entity.system.SysRole;
import cn.smarthse.framework.generic.GenericDao;

/**
 *
 * 系统角色
 */
public interface SysRoleMapper extends GenericDao<SysRole> {

	/**
	 *
	 * 根据用户Id 读取用户角色编码列表
	 * 
	 * @param id
	 *            用户id
	 */
	List<String> getUserRoleCodeList(int id);

}
