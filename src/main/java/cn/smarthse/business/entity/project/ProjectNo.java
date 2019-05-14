/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */


package cn.smarthse.business.entity.project;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
 * @since 2019-03-06 03:03
 */
@Table(name = "project_no")
public @Data class ProjectNo implements Serializable{
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
	 * 批号为：委托期限开始年份+25+流水号（例：18年第1个个人放射监测项目的批号为18250001），批号提交后不管通过与否，不再复用
	 */
	private Integer no;
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
	private java.util.Date createTime;
	/**
	 * 最后修改人
	 */
	private Integer updateBy;
	/**
	 * 最后修改日期
	 */
	private java.util.Date updateTime;


}
