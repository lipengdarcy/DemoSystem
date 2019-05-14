


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
public @Data class MessageSearchModel   implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 企业ID
	 */
    @ApiModelProperty(value="企业ID")
	private Integer cid;
    
    @ApiModelProperty(value="接受人员ID")
    private Integer reciever;
    
    @ApiModelProperty(value="项目ID")
    private Integer projectId;
    
    @ApiModelProperty(value="消息模块")
    private Integer model;
    
    @ApiModelProperty(value="消息类型")
    private Integer messageType;
    
    @ApiModelProperty(value="消息状态（1.已读、2.未读、3.延期提醒）")
    private Integer status;
    
    @ApiModelProperty(value="提醒时间（开始）")
  	private java.util.Date remindSTime;
    
    @ApiModelProperty(value="提醒时间（结束）")
 	private java.util.Date remindETime;
    
    @ApiModelProperty(value="搜索范围")
 	private int searchRange;

	



}
