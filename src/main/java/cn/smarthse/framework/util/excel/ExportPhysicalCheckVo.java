package cn.smarthse.framework.util.excel;

import java.util.Date;

import lombok.Data;

//职业健康监护清单导出
public @Data class ExportPhysicalCheckVo {

	private String checkNo; // 体检编号
	//private String confirmNo; // 职业病诊断编号
	private Date reportTime; // 上报时间
	private String manager; // 编制人
	private Date updateTime; // 更新时间
}