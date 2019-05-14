/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */


package cn.smarthse.business.entity.project;

import java.io.Serializable;
import java.math.BigDecimal;

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
 * @since 2019-03-07 10:31
 */
@Table(name = "project_contract")
public @Data class ProjectContract implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * 项目id
	 */
	private Integer projectId;
	/**
	 * 委托开始时间
	 */
	private java.util.Date entrustBeginTime;
	/**
	 * 委托结束时间
	 */
	private java.util.Date entrustEndTime;
	/**
	 * 检测周期
	 */
	private Integer checkCycle;
	/**
	 * 射线类型（x：x射线，y：y射线，xy：xy射线）
	 */
	private String rayType;
	/**
	 * 本期费用
	 */
	private BigDecimal currentExpenses;
	/**
	 * 费用备注
	 */
	private String currentExpensesMark;
	/**
	 * 往期费用
	 */
	private BigDecimal lastExpenses;
	/**
	 * 备注
	 */
	private String mark;
	/**
	 * 到款（1、到款 2、未到）
	 */
	private String incomeInfo;
	/**
	 * 合同收回日期
	 */
	private java.util.Date contractBack;
	/**
	 * 发票日期
	 */
	private java.util.Date invoiceDate;
	/**
	 * 企业id
	 */
	private Integer cid;
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
	 * 已收到的费用
	 */
	private BigDecimal receivedExpenses;
	
	/**
	 *监测人数 
	 */
	private Integer checkNums;
	
	/**
	 * 监测年限（1：1周期 2：1年）,见枚举：ProjectCheckCycleTypeEnum
	 */
	private Integer checkCycleType;

}
