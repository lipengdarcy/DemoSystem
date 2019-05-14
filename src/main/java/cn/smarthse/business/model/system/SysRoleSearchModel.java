package cn.smarthse.business.model.system;

import java.io.Serializable;

import lombok.Data;

public @Data class SysRoleSearchModel implements Serializable {

	// @Fields serialVersionUID : TODO
	private static final long serialVersionUID = 9137466562667406071L;

	//角色名称
	private String roleName;
	//部门ID
	private Integer deptId;
}
