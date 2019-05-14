/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */


package cn.smarthse.business.model.system;

import java.io.Serializable;

import cn.smarthse.business.entity.system.SysRole;
import lombok.Data;


/**
 * 
* 
* @ClassName: SysRoleModel.java
* @Description: 角色Model
*
* @version: v1.0.0
* @author: cjy
* @date: 2019年2月28日 下午2:59:13 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2019年2月28日      cjy          v1.0.0               修改原因
 */
public @Data class SysRoleModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//角色名称
	private String roleName;
	//角色ID
	private Integer roleId;
	//部门ID
	private Integer deptId;
	//部门名称
	private String deptName;
	//是否是系统角色
	private Boolean isSys;
	//角色类型名称 系统提供或自定义
	private String roleTypeName;
	//描述
	private String desc;
	
	public SysRoleModel(SysRole role) {
		if(role==null) return ;
		if (role.getIsSys()!=null) this.roleTypeName = (role.getIsSys()==true?"系统提供":"自定义");
		this.roleId = role.getId();
		this.roleName = role.getRoleName();
		this.isSys = role.getIsSys();
		this.desc = role.getRoleDesc();
		this.deptId = role.getDeptId();
		
		
	}


}
