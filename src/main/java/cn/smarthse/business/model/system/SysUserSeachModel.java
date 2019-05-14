/**
 * 
 */
package cn.smarthse.business.model.system;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 《用户模块搜索条件模型》
 * 姓名/工号，岗位状态，角色
 * 
 * @Project:  GiianSystem
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8> 
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2019年2月19日-下午1:51:15
 */
public @Data class SysUserSeachModel implements Serializable {
	private static final long serialVersionUID = -87963021897506028L;

	@ApiModelProperty(value="三方企业ID")
	private Integer cid;
	
	/**
	 * 姓名/工号
	 */
	@ApiModelProperty(value="姓名/工号")
	private String keyword;
	
	/**
	 * 岗位状态:1-在岗、2-离岗
	 */
	@ApiModelProperty(value="岗位状态:1-在岗、2-离岗")
	private Integer dutyState;
	
	
	@ApiModelProperty(value="角色编号")
	private Integer roleId;
	
	@ApiModelProperty(value="多个角色编号，按逗号隔开")
	private String roleIds;
	
	@ApiModelProperty(value="部门ID")
	private Integer deptId;
}
