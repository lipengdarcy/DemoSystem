/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */


package cn.smarthse.business.entity.system;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;


 /**
 * 系统日志表
 *
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8>
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2019-02-20 08:45
 */
@Table(name = "sys_log")
public @Data class SysLog implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * 企业ID
	 */
	private Integer cid;
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
	private Integer createBy;
	/**
	 * 更新人
	 */
	private Integer updateBy;
	/**
	 * 是否有效
	 */
	private Boolean isValid;
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 日志内容简单说明
	 */
	private String description;
	/**
	 * 用户账号（冗余）
	 */
	private String userName;
	/**
	 * 用户姓名（冗余）
	 */
	private String userFullname;
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


}
