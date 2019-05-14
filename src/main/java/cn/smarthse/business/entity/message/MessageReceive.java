

package cn.smarthse.business.entity.message;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
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
 * @since 2019年3月11日-上午9:24:35
 */
@Table(name = "message_receive")
public @Data class MessageReceive implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * 消息id
	 */
	private Integer messageId;
	/**
	 * 接收者
	 */
	private Integer reciever;
	/**
	 * 消息提醒时间
	 */
	private Date remindTime;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
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
	 * 企业ID
	 */
	private Integer cid;
	/**
	 * 消息状态：1.已读、2.未读、3.延期提醒
	 */
	private Integer status;
	
	/**
	 * 重复状态（自定义消息用）：1.不重复，2。按日重复，3.按月重复
	 */
	private Integer repeatState;

	/**
	 * 重复周期
	 */
	private Integer repeatCycle;
	

	/**
	 * 第一次提醒时间（自定义消息用）
	 */
	private Date firstRemindTime;



}
