/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */


package cn.smarthse.business.model.system;

import java.io.Serializable;
import java.util.List;

import cn.smarthse.business.entity.system.SysUser;
import cn.smarthse.business.entity.system.SysUserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


 /**
 * 用户Model
 *
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8>
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2019-02-18 01:55
 */
public @Data class SysUserModel implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
    @ApiModelProperty(value="编号")
	private Integer id;
	/**
	 * 三方企业ID
	 */
    @ApiModelProperty(value="三方企业ID")
	private Integer cid;
	/**
	 * 用户名
	 */
    @ApiModelProperty(value="用户名")
	private String userName;
	/**
	 * 密码
	 */
    @ApiModelProperty(value="密码")
	private String passWord;
	/**
	 * 姓名
	 */
    @ApiModelProperty(value="姓名")
	private String realName;
    
	/**
	 * 部门名称
	 */
    @ApiModelProperty(value="部门名称")
	private String deptName;
    
	/**
	 * 工号
	 */
    @ApiModelProperty(value="工号")
	private String staffNo;
	/**
	 * 联系电话（手机号码）
	 */
    @ApiModelProperty(value="联系电话（手机号码）")
	private String tel;
	/**
	 * 创建时间
	 */
    @ApiModelProperty(value="创建时间")
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
    @ApiModelProperty(value="更新时间")
	private java.util.Date updateTime;
    
	/**
	 * 身份证号
	 */
    @ApiModelProperty(value="身份证号")
	private String idCard;

	/**
	 * 进入部门时间
	 */
    @ApiModelProperty(value="进入部门时间")
	private java.util.Date deptJoinDate;
	/**
	 * 岗位状态:1-在岗、2-离岗
	 * @see cn.smarthse.business.enums.DutyStateEnum
	 */
    @ApiModelProperty(value="岗位状态:在岗或者离岗")
	private String dutyState;
    
    /**
	 * 用户角色id列表
	 * 
	 * 角色说明 0：管理员 1：检测主管 2：检测人员 3：原始记录评审人员 4：仪器管理人员 5：报告管理人员 6：报告编制人员
	 */
    @ApiModelProperty(value="用户角色列表")
	private List<SysUserRole> userRoleList;
    
    @ApiModelProperty(value="用户角色名称,仅做显示")
   	private String roleNames;
    
    @ApiModelProperty(value="销售区域ID")
    private String salesAreaIds;
 
    /**
	 * @param user
	 */
	public SysUserModel(SysUser user) {
		if(user==null) return ;
		this.cid = user.getCid();
		this.createTime = user.getCreateTime();
		this.deptJoinDate = user.getDeptJoinDate();
		this.id = user.getId();
		this.idCard = user.getIdCard();
		this.passWord = user.getPassWord();
		this.realName = user.getRealName();
		this.staffNo = user.getStaffNo();
		this.tel = user.getTel();
		this.updateTime = user.getUpdateTime();
		this.userName = user.getUserName();
	}
	/**
	 * 
	 */
	public SysUserModel() {
	}


}
