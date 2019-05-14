package cn.smarthse.business.model.system;

import java.io.Serializable;

import lombok.Data;

public @Data class SysLogSearchModel implements Serializable {

	// @Fields serialVersionUID : TODO
	private static final long serialVersionUID = 852863641833080049L;
	
	/**
	 * 搜索条件(内容)
	 */
	private String desc;
	/**
	 * 搜索条件(类型:1-登录日志,2-操作日志)
	 */
	private Integer logType;
	
	/**
	 * 搜索条件 (操作时间)
	 */
	private String optTime;
	/**
	 * 搜索条件 (操作起止时间)
	 */
	private String optStartTime;
	/**
	 * 搜索条件 (操作起止时间)
	 */
	private String optEndTime;

}
