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
 * 系统角色
 */
@Table(name = "sys_role")
public @Data class SysRole implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * 角色编码，示例：user,admin
	 */
	private String roleCode;
	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 角色类型:0:待确定1.业务员2.其他。后续扩展
	 */
	private Integer roleType;

	/**
	 * 角色描述
	 */
	private String roleDesc;
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
	 * 所在部门id
	 */
	private Integer deptId;
	/**
	 * 类型（是否系统）,true:系统角色,false:自定义角色
	 */
	private Boolean isSys;

}
