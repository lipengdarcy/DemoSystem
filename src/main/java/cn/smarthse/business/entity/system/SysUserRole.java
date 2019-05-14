/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */


package cn.smarthse.business.entity.system;

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
 * @since 2019-02-19 04:00
 */
@Table(name = "sys_user_role")
public @Data class SysUserRole implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 用户ID
	 */
	private Integer uid;
	/**
	 * 角色说明
            0：管理员
            1：检测主管
            2：检测人员
            3：原始记录评审人员
            4：仪器管理人员
            5：报告管理人员
            6：报告编制人员
	 */
	private Integer roleId;
	/**
	 * 角色名称
	 */
	private String roleName;
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
	 * 角色代码（冗余）
	 */
	private String roleCode;

}
