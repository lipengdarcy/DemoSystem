package cn.smarthse.business.mongo.service.system;

import java.util.List;

import cn.smarthse.business.mongo.collection.system.SystemUser;
import cn.smarthse.framework.generic.GenericService;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;

/**
 * 系统用户Service接口定义
 *
 */
public interface SystemUserService extends GenericService<SystemUser> {

	/**
	 * 综合查询
	 * 
	 */
	public List<SystemUser> getData(SystemUser data);

	/**
	 * 分页查询数据,返回grid格式的数据
	 * 
	 */
	public JqGridData<SystemUser> getGridData(JqGridParam param, SystemUser dataParam);

	/**
	 * 根据 用户名/电话/身份证 读取用户
	 * 
	 * @param keyword
	 *            用户名/电话/身份证
	 * @return
	 */
	SystemUser getUser(String keyword);

	/**
	 * 
	 * 用户管理-新增用户
	 */
	SystemUser addUser(SystemUser user);

	/**
	 * 
	 * 用户管理-编辑用户
	 * 
	 */
	void editUser(SystemUser user);

	/**
	 * 用户管理-删除用户
	 */
	void delUser(String id);

	/**
	 * 用户管理-修改密码
	 */
	public int changePassword(SystemUser user, String oldPassword);

	/**
	 * 重置密码，密码为：123456
	 */
	public int resetPassword(String uid);

}
