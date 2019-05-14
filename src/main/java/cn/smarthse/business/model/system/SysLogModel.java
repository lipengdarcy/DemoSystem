/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */


package cn.smarthse.business.model.system;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import cn.smarthse.business.entity.system.SysLog;
import cn.smarthse.framework.interceptor.log.LogConstans;
import cn.smarthse.framework.util.DateUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


 /**
 *	系统日志Model
 *
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8>
 * @author <开发者>
 * @since 2019-02-20 08:45
 */
public @Data class SysLogModel implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 日志ID
	 */
	private Integer id;
    
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 日志内容简单说明
	 */
	private String desc;
	/**
	 * 用户账号（冗余）
	 */
	private String userName;
	/**
	 * 用户姓名（冗余）
	 */
	private String userFullname;

	/**
	 * 来源模块
	 */
	private String moduleName;
	/**
	 * 类型(0-异常日志,1-登录日志,2-操作日志）
	 */
	private Integer logType;
	
	/**
	 * 类型名
	 */
	private String logTypeName;

	/**
	 * 请求Ip
	 */
	private String requestIp;
	
	/**
	 * 操作时间
	 */
	private String optDateTime;

	public SysLogModel(SysLog log) {
		this.desc = log.getDescription();
		this.id = log.getId();
		if (log.getLogType()!=null) {
			String prefix = log.getLogType().toString().substring(0, 1);
			switch (prefix) {
			case "1":
				this.logTypeName = "登录日志";
				break;
			case "2":
				this.logTypeName = "操作日志";
				break;
			default:
				break;
			}
		}
		this.optDateTime = DateUtils.format(log.getUpdateTime(), "yyyy-MM-dd HH:mm:ss");
		this.requestIp = log.getRequestIp();
		this.userFullname = log.getUserFullname();
		this.userName = log.getUserName();
		this.userId = log.getUserId();
	} 
}
