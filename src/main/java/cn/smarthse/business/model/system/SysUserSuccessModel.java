package cn.smarthse.business.model.system;

import java.io.Serializable;

import cn.smarthse.business.entity.system.SysUser;
import lombok.Data;

public @Data class SysUserSuccessModel extends SysUserModel implements Serializable {

	// @Fields serialVersionUID : TODO
	private static final long serialVersionUID = -4268806832028001472L;
	private Integer id;
	//Excel中与系统重复的数据行编号
	private Integer rowNum;
	private String location;
	
	//是否覆盖了系统数据
	private Boolean isCover;
	
	//部门ID
	private Integer deptId;
	
	//角色ID串ID
	private String roleIds;
	
	public SysUserSuccessModel(SysUser user,Integer rowNum,Boolean isCover) {
		super(user);
		this.id=rowNum;
		this.rowNum = rowNum;
		this.location = rowNum!=null?"第"+rowNum+"行":null;
		this.isCover = isCover;
		this.roleIds = user.getRoleIds();
	}

}
