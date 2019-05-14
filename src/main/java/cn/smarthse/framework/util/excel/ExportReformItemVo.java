package cn.smarthse.framework.util.excel;

import java.util.Date;

import lombok.Data;

//整改项清单导出
public @Data class ExportReformItemVo {
	private String itemNo;//"整改项编号", 
	private String desc;//"描述", 
	private Date reqTime;//"要求时间",
	private Date feedbackTime;//"反馈时间",
	private String level;//"分级",
	private String source;//"来源",
	private String manager;//"整改人",
	private String phase;//"当前阶段",
	private String status;//"当前状态", 
	private String solutionNo;//"方案编号", 
	private String solutionStatus;//"方案状态" };


}