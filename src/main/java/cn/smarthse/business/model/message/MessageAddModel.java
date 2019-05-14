


package cn.smarthse.business.model.message;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
 * @since 2019年3月12日-上午11:36:52
 */
public @Data class MessageAddModel   implements Serializable,Cloneable{
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
     * 自定义消息日重复周期
     */
    private Integer dayRepeatCycle;
    /**
     * 自定义消息月重复周期
     */
    private Integer monthRepeatCycle;
    
    /**
     * 接收人id列表
     */
    List<Integer>  recieves;
 

}
