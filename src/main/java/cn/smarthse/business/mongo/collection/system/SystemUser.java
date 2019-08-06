package cn.smarthse.business.mongo.collection.system;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * 用户， 包含基础信息，角色信息，工作信息等
 *
 */
@Document(value = "SystemUser")
public @Data class SystemUser implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

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
	 * 昵称
	 */
	private String nickName;

	/**
	 * 联系电话（手机号码）
	 */
	private String tel;

	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 身份证号
	 */
	private String idCard;

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

	/**
	 * 角色信息
	 */
	private List<SystemRole> role;
	
	/**
	 * 工作信息
	 */
	private UserJob job;

}
