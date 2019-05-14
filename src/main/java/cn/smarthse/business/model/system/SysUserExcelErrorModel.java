package cn.smarthse.business.model.system;

import java.io.Serializable;

import lombok.Data;

public @Data class SysUserExcelErrorModel implements Serializable {

	// @Fields serialVersionUID : TODO
	private static final long serialVersionUID = 5643326347032373547L;

	//姓名
	private String fullname;
	
	//错误数据所在位置
	private String location;
	
	//错误描述
	private String errorDesc;
	
	public SysUserExcelErrorModel(String fullname,String location,String errorDesc) {
		this.fullname = fullname;
		this.location = location;
		this.errorDesc = errorDesc;
	}
}
