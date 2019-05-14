package cn.smarthse.business.model.system;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;
import lombok.Data;

public @Data class SysUserImportModel implements Serializable {
	// @Fields serialVersionUID : TODO
	private static final long serialVersionUID = -8189521756615732373L;

	//导入错误的提示List
	List<SysUserExcelErrorModel> errorList;
	
	//有覆盖内容的list
	List<SysUserSuccessModel> coverList;
	
	//导入成功的数据List
	List<SysUserSuccessModel> successList;
	
}
