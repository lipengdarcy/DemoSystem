package cn.smarthse.framework.util.excel;

import lombok.Data;
//员工导出vo
public @Data class ExportStaffVo {
	
	private String workno; //工号 
	
	private String realname; //姓名
	
	private String gender; //性别

    private String orgname; //部门
    
    private String workrole; //岗位
    
    private String workstatus; //岗位状态 

}