/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */


package cn.smarthse.business.model.system;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


 /**
 * 单(多)选用户框
 *
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8>
 */
public @Data class SysUserChooseModel implements Serializable , Comparable<SysUserChooseModel>{
	private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value="部门实体")
    private String deptName;
    
    @ApiModelProperty(value="部门Id")
    private Integer deptId;
    
    @ApiModelProperty(value="用户列表")
    private List<SysUserModel> userList;

    @Override
    public int compareTo(SysUserChooseModel model) {           //重写Comparable接口的compareTo方法，
    	//降序修改相减顺序即可
    	return this.deptName.compareTo(model.getDeptName());
	}	
}
