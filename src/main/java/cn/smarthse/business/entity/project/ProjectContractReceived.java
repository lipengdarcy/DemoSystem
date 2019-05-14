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
 * @since 2019-03-07 10:32
 */
@Table(name = "project_contract_received")
public @Data class ProjectContractReceived implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * 项目合同id
	 */
	private Integer projectContractId;
	/**
	 * 回款项名称
	 */
	private String name;
	/**
	 * 回款金额
	 */
	private BigDecimal price;
	/**
	 * 回款备注
	 */
	private String mark;
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


}
