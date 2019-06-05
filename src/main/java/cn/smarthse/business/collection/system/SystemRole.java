package cn.smarthse.business.collection.system;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 *
 * 系统角色
 */
@Document(value = "SystemRole")
public @Data class SystemRole implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	
	
	/**
	 * 企业ID
	 */
	private String cid;
	
	/**
	 * 角色编码，示例：user,admin
	 */
	private String roleCode;
	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 角色类型:0:普通用户；1.管理员；2.其他
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
	private String createBy;
	/**
	 * 更新人
	 */
	private String updateBy;
	/**
	 * 是否有效
	 */
	private Boolean isValid;

}
