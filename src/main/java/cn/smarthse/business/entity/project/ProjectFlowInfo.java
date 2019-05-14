/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */


package cn.smarthse.business.entity.project;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;


 /**
 *
 *
 *
 *
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8>
 * @author <开发者>
 * @since 2019-04-03 09:49
 */
@Table(name = "project_flow_info")
public @Data class ProjectFlowInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * 企业id
	 */
	private Integer cid;
	/**
	 * 项目id
	 */
	private Integer projectId;
	/**
	 * 最新阶段（1、审核中 2、被驳回 3、审核通过/待流转 4、进行中 5、已完成 6、已停止 7、暂停）
	 */
	private Integer phase;
	/**
	 * 是否有效
	 */
	private Boolean isValid;
	/**
	 * 创建人
	 */
	private Integer createBy;
	/**
	 * 创建日期
	 */
	private Date createTime;
	/**
	 * 最后修改人
	 */
	private Integer updateBy;
	/**
	 * 最后修改日期
	 */
	private Date updateTime;


}
