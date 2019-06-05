package cn.smarthse.business.collection.system;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * 系统日志
 */
@Document(value = "SystemLog")
public @Data class SystemLog implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	
	/**
	 * 企业ID
	 */
	private String cid;

	/**
	 * 用户
	 */
	private SystemUser user;

	/**
	 * 日志内容简单说明
	 */
	private String description;

	/**
	 * 用户代理信息
	 */
	private String userAgent;

	/**
	 * 请求URI
	 */
	private String requestUri;

	/**
	 * 操作方式：post/get/push等resetful
	 */
	private String requestMethod;

	/**
	 * 来源模块
	 */
	private String moduleName;

	/**
	 * 类型(0-异常日志,1-登录日志,2-操作日志）
	 */
	private Integer logType;

	/**
	 * 操作提交的数据内容(json)或者异常信息
	 */
	private String logParams;

	/**
	 * 请求Ip
	 */
	private String requestIp;

	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 更新人
	 */
	private String updateBy;
	/**
	 * 是否有效
	 */
	private Boolean isValid;

}
