package cn.smarthse.business.model.system;

import java.io.Serializable;
import java.util.List;

import cn.smarthse.business.entity.system.SysUser;
import lombok.Data;

public @Data class SysUserCoverFormModel implements Serializable {

	// @Fields serialVersionUID : TODO
	private static final long serialVersionUID = 6647503980078973845L;
	
	private SysUser[] users;
	
	
}
