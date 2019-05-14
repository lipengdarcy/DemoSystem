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
import javax.persistence.Transient;

import lombok.Data;


 /**
 * 用户表
 *
 */
@Table(name = "sys_user")
public @Data class SysUser implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * 企业ID
	 */
	private Integer cid;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String passWord;
	/**
	 * 盐值（系统生成）
	 */
	private String salt;
	/**
	 * 姓名
	 */
	private String realName;
	/**
	 * 工号
	 */
	private String staffNo;
	/**
	 * 联系电话（手机号码）
	 */
	private String tel;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
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
	 * 身份证号
	 */
	private String idCard;
	/**
	 * 部门Id
	 */
	private Integer deptId;
	/**
	 * 进入部门时间
	 */
	private java.util.Date deptJoinDate;
	/**
	 * 岗位状态:1-在岗、2-离岗
	 */
	private Integer dutyState;
	
	/**
	 * 角色ID串
	 */
	@Transient
	private String roleIds;


}
