package cn.smarthse.business.collection.system;

import java.io.Serializable;

import lombok.Data;


 /**
 * 用户工作信息
 *
 */
public @Data class UserJob implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	/**
	 * 企业ID
	 */
	private Integer cid;
	
	/**
	 * 工号
	 */
	private String staffNo;
	
	/**
	 * 部门Id
	 */
	private Integer deptId;
	
	/**
	 * 进入部门时间
	 */
	private java.util.Date deptJoinDate;
	
	/**
	 * 岗位状态:1-在岗、2-离岗
	 */
	private Integer dutyState;
	
}
