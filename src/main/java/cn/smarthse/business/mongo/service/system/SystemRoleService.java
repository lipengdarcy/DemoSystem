package cn.smarthse.business.mongo.service.system;

import java.util.List;

import cn.smarthse.business.mongo.collection.system.SystemRole;
import cn.smarthse.framework.generic.GenericService;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;

/**
 * 系统角色Service接口定义
 *
 */
public interface SystemRoleService extends GenericService<SystemRole> {

	/**
	 * 综合查询
	 * 
	 */
	public List<SystemRole> getData(SystemRole data);

	/**
	 * 分页查询数据,返回grid格式的数据
	 * 
	 */
	public JqGridData<SystemRole> getGridData(JqGridParam param, SystemRole dataParam);

	/**
	 * 根据 角色名称/角色编码
	 * 
	 * @param keyword
	 *            角色名称/角色编码
	 * @return
	 */
	SystemRole get(String keyword);

}
