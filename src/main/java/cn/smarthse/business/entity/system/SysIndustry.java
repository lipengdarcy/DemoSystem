/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */

package cn.smarthse.business.entity.system;

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
 * 行业分类
 */
@Table(name = "sys_industry")
public @Data class SysIndustry implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 级别
	 */
	private Integer level;

	/**
	 * 父id
	 */
	private Long pid;
	/**
	 * 行业名称
	 */
	private String name;
	/**
	 * 73号令编号
	 */
	@Column(name = "no_73")
	private String no73;

	/**
	 * 73号令名称
	 */
	// 不加这个注解，sql语句直接用name73，而不是name_73
	@Column(name = "name_73")
	private String name73;
	/**
	 * 职业危害程度
	 */
	private String harmLevel;
	/**
	 * 关键字
	 */
	private String keyWord;
	/**
	 * 分类编号
	 */
	private String recommendNumber;
	/**
	 * 行业描述
	 */
	private String describeCount;
	/**
	 * 推荐标准索引
	 */
	private String recommend;
	/**
	 * 是否有效（0-无效，1-有效）
	 */
	private Boolean isValid;
	/**
	 * 创建人
	 */
	private Long createBy;
	/**
	 * 创建时间
	 */
	private java.util.Date createDate;
	/**
	 * 最后修改人ID
	 */
	private Long updateBy;
	/**
	 * 最后修改时间
	 */
	private java.util.Date updateDate;

}
