package cn.smarthse.framework.util.excel;

import java.util.Date;

import lombok.Data;
//员工岗位变动导出vo
public @Data class ExportStaffChangeVo {
	
	private String workno; //工号 
	
	private String realname; //姓名
	
	private String workrole; //岗位
	
	private String change; //岗位变动情况

    private Date changeTime; //变动时间
       
    private String health; //体检情况
    private String train; //教育培训情况
    private String inform; //合同告知情况

}