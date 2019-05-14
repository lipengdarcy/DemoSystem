/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */


package cn.smarthse.business.model.message;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



/**
 * 《》
 * 
 * 
 * @Project:  GiianSystem
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8> 
 * @author XiaoYi（肖奕) xy@smarthse.cn
 * @since 2019年3月11日-下午2:08:07
 */
public @Data class MessageListModel   implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 企业ID
	 */
 
	private Integer cid;
    
    /**
	 * 项目ID
	 */
	private Integer projectId;
    
    /**
     * 消息详情id
     */
    private Integer id;
	/**
	 * 消息id
	 */
	private Integer messageId;
    
   
    /**
     * 接收人
     */
    private Integer reciever;
    
    
    /**
     * 模块
     */
    private Integer model;
    
   
    /**
     * 消息类型
     */
    private Integer messageType;
    
   
    /**
     * 状态
     */
    private Integer status;
    
  
  	/**
  	 * 提醒时间
  	 */
  	private java.util.Date remindTime;
    
  	/**
  	 * 自定义消息第一次提醒时间
  	 */
  	private java.util.Date firstRemindTime;
  	
  	 /**
     * 自定义消息重复状态
     */
    private Integer repeatState;
    
    /**
     * 自定义消息重复周期
     */
    private Integer repeatCycle;
 
	/**
	 * 委托批号
	 */
	private Integer entrustedNo;

	
	/**
	 * 委托期限
	 */
	private Date entrustedTerm;
	
	
	/**
	 * 周期
	 */
	private String cycle;
}
