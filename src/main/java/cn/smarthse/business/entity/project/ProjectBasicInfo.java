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
 * @author yanao
 * @since 2019-03-06 01:48
 */
@Table(name = "project_basic_info")
public @Data class ProjectBasicInfo implements Serializable{
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
	private Integer projectNo;
	/**
	 * 项目名称为:单位名称+放射工作人员个人剂量监测
	 */
	private String projectName;
	/**
	 * 项目类型（1、个人剂量 2、放射检测）
	 */
	private Integer type;
	/**
	 * 受检单位
	 */
	private Integer examinatedCompany;
	/**
	 * 委托单位
	 */
	private String entrustCompany;
	/**
	 * 委托单位区域id
	 */
	private String entrustCompanyAreaId;
	/**
	 * 委托单位地址
	 */
	private String entrustCompanyAddress;
	/**
	 * 项目区域id
	 */
	private String projectAreaId;
	/**
	 * 项目地址
	 */
	private String projectAddress;
	/**
	 * 反复修改次数
	 */
	private Integer editTimes;
	/**
	 * 状态（0、编辑中 1、已完成）
	 */
	private Integer status;
	/**
	 * 最新阶段（1、审核中 2、审核不通过 3、审核通过/待流转 4、进行中 5、已完成 6、已停止 7、被驳回）
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
	private java.util.Date createTime;
	/**
	 * 最后修改人
	 */
	private Integer updateBy;
	/**
	 * 最后修改日期
	 */
	private java.util.Date updateTime;

	 /**
	  * 暂停天数
	  */
	private Integer pauseDay;
}
