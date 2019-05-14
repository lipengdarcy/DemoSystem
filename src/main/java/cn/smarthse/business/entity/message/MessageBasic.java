/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */


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
 * @since 2019年3月11日-上午9:23:14
 */
@Table(name = "message_basic")
public @Data class MessageBasic implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 发送时间
	 */
	private Date sendTime;
	/**
	 * 发送人
	 */
	private Integer sendUser;
	/**
	 * 消息类型
	 */
	private Integer messageType;
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
	 * 项目ID
	 */
	private Integer projectId;
	/**
	 * 消息所属模块：1.业务模块、2.技术模块
	 */
	private Integer model;


}
