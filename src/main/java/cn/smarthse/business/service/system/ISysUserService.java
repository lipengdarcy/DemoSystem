package cn.smarthse.business.service.system;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.smarthse.business.entity.system.SysUser;
import cn.smarthse.business.model.system.SysUserModel;
import cn.smarthse.business.model.system.SysUserSeachModel;
import cn.smarthse.framework.generic.GenericService;
import cn.smarthse.framework.model.JqGridParam;


 /**
 * 系统用户Service接口定义
 *
 */
public interface ISysUserService extends GenericService<SysUser>{

	/**
	 * 根据username/tel/model 读取用户Id
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2019年2月18日-下午2:53:35
	 * @param username
	 * @return
	 */
	Integer getUserId(String username);
	
	/**
	 * 根据username/tel 读取用户实体
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2019年2月18日-下午2:54:18
	 * @param username	用户名/tel
	 * @return
	 */
	SysUser getUser(String username);
	
	/**
	 * 根据username 读取用户实体
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2019年2月18日-下午2:54:18
	 * @param username
	 * @return
	 */
	SysUser getUserByUsername(String username);
	
	/**
	 * 根据tel 读取用户实体
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2019年2月18日-下午2:54:18
	 * @param tel	联系电话（手机号码）
	 * @return
	 */
	SysUser getUserByTel(String tel);
	
	/**
	 * 
	* @Function: ISysUserService.java
	* @Description: 根据工号 读取用户实体
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: cjy
	* @date: 2019年3月26日 下午2:22:24 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2019年3月26日      cjy           v1.0.0               修改原因
	 */
	SysUser getUserByStaffNo(String staffNo);

	/**
	 * 根据用户Id 读取用户角色编码列表
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2019年2月18日-下午3:27:28
	 * @param userId
	 * @return
	 */
	List<String> getUserRoleCodeList(Integer userId);

	/**
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2019年2月19日-下午2:00:04
	 * @param params
	 * @param searchModel
	 * @return
	 */
	PageInfo<SysUserModel> getUserList(JqGridParam params, SysUserSeachModel searchModel);

	/**
	 * searchModel搜索用户列表
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2019年3月18日-下午3:37:09
	 * @param searchModel
	 * @return
	 */
	List<SysUser> getUserListBySearchModel(SysUserSeachModel searchModel);
	
	
	/**
	 * TODO 蔡剑英 : 危险接口
	* @Function: ISysUserService.java
	* @Description: 仅做测试用例使用
	 */
	@Deprecated
	List<SysUser> getList();
	
	/**
	 * 
	* @Function: ISysUserService.java
	* @Description: 查找某公司某部门的全部用户
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: cjy
	* @date: 2019年2月20日 下午4:06:24 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2019年2月20日      cjy           v1.0.0               修改原因
	 */
	List<SysUser> getListByDeptIdAndCid(Integer deptId,Integer cid);
	
	
	
	/**
	 * 
	* @Function: ISysUserService.java
	* @Description: 按身份证获取信息,若有多个user的有效记录，抛出异常
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: cjy
	* @date: 2019年3月13日 上午8:46:36 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2019年3月13日      cjy           v1.0.0               修改原因
	 */
	SysUser getByIdCard(String idCard);
	
	/**
	 * 
	* @Function: ISysUserService.java
	* @Description: 校验系统中用户A的手机号是否被其他用户使用
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: cjy
	* @date: 2019年3月5日 下午2:29:28 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2019年3月5日      cjy           v1.0.0               修改原因
	 */
	Boolean hasOthersByTel(String tel,Integer userId);
	
	/**
	 * 
	* @Function: ISysUserService.java
	* @Description: 校验系统中用户A的身份证是否被其他用户使用
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: cjy
	* @date: 2019年3月5日 下午2:29:28 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2019年3月5日      cjy           v1.0.0               修改原因
	 */
	Boolean hasOthersByIdCard(String idCard,Integer userId);
	
	/**
	 * 
	* @Function: ISysUserService.java
	* @Description:校验工号是否被其他人使用
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: cjy
	* @date: 2019年3月27日 上午10:19:39 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2019年3月27日      cjy           v1.0.0               修改原因
	 */
	Boolean hasOthersByStaffNo(String staffNo, Integer userId);
	
	/**
	 * 
	* @Function: ISysUserService.java
	* @Description: 校验用户名是否存在
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: cjy
	* @date: 2019年2月22日 上午11:04:47 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2019年2月22日      cjy           v1.0.0               修改原因
	 */
	Boolean isUsernameExsists(String username);
	
	/**
	 * 
	* @Function: ISysUserService.java
	* @Description: 校验身份证号在系统中是否存在
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: cjy
	* @date: 2019年2月22日 上午11:04:47 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2019年2月22日      cjy           v1.0.0               修改原因
	 */
	Boolean isIdCardExsists(String idCard);
	
	/**
	 * 
	* @Function: ISysUserService.java
	* @Description: 校验手机号在系统中是否存在
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: cjy
	* @date: 2019年3月5日 下午2:43:52 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2019年3月5日      cjy           v1.0.0               修改原因
	 */
	Boolean isTelExsists(String tel);
	
	/**
	 * 
	* @Function: ISysUserService.java
	* @Description: 验证系统中工号是否重复
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: cjy
	* @date: 2019年3月26日 上午11:27:39 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2019年3月26日      cjy           v1.0.0               修改原因
	 */
	Boolean isStaffNoExsists(String staffNo); 
	
	/**
	 * 
	* @Function: ISysUserService.java
	* @Description: 系统管理-用户管理-导入
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: cjy
	* @date: 2019年2月25日 上午10:21:54 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2019年2月25日      cjy           v1.0.0               修改原因
	 */
	void addByBatch(Integer createBy,List<SysUser> userList);
	
	/**
	 * 
	* @Function: ISysUserService.java
	* @Description: 系统管理-用户管理-新增用户
	*
	* @param:描述1描述
	* @return：返回新增的userId
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: cjy
	* @date: 2019年3月4日 下午4:15:42 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2019年3月4日      cjy           v1.0.0               修改原因
	 */
	SysUser addUser(SysUser user,Integer createBy);
	
	/**
	 * 
	* @Function: ISysUserService.java
	* @Description: 系统管理-用户管理-编辑提交
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: cjy
	* @date: 2019年3月5日 上午10:39:13 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2019年3月5日      cjy           v1.0.0               修改原因
	 */
	void editUser(SysUser user,Integer updateBy);
	
	/**
	 * 
	* @Function: ISysUserService.java
	* @Description: 
	* 删除用户:
	*	1.删除角色用户关系表记录
	*	2.删除用户主表
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: cjy
	* @date: 2019年3月1日 下午4:09:27 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2019年3月1日      cjy           v1.0.0               修改原因
	 */
	void delSysUser(Integer id,Integer updateBy);

	/**
	 * 
	* @Function: ISysUserService.java
	* @Description: 批量删除用户 
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: cjy
	* @date: 2019年3月1日 下午3:58:53 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2019年3月1日      cjy           v1.0.0               修改原因
	 */
	void delByBatch(String userIds,Integer updateBy);
	
	/**
	 * 
	* @Function: ISysUserService.java
	* @Description: 批量更新
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: cjy
	* @date: 2019年3月12日 下午3:53:47 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2019年3月12日      cjy           v1.0.0               修改原因
	 */
	void updateByBatch(SysUser[] userList,Integer updateBy);
	
	/**
	 * 
	* @Function: ISysUserService.java
	* @Description: 导入最后一步 插入或更新数据
	*
	* @param:
	* 		coverList 需覆盖系统中数据的list 走update方法
	* 		successList 通过导入验证的有效数据 走add方法
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: cjy
	* @date: 2019年3月12日 下午4:52:18 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2019年3月12日      cjy           v1.0.0               修改原因
	 */
	void processImportUserList(SysUser[] coverList,List<SysUser> successList,Integer updateBy);
	
	
	/**
	 * 修改密码
	 * */
	public int changePassword(SysUser user, String oldPassword) ;
	
	/**
	 * 重置密码，密码为：giian123456
	 * */
	public int resetPassword(Integer uid) ;
	

	// 单表查询结果转化成model
	public SysUserModel toUserModel(SysUser user) ;
}
