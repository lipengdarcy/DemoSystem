/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */


package cn.smarthse.business.model.system;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import cn.smarthse.business.entity.system.SysAreaStandard;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


 /**
 * 行政区域省市数据模型
 *
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8>
 * @author <开发者>
 * @since 2019-02-21 01:25
 */
public @Data class SysAreaCityModel implements Serializable, Comparable<SysAreaCityModel>{
	private static final long serialVersionUID = 1L;
	/**
	 * 省编号
	 */
    @ApiModelProperty(value="省编号")
	private Long provinceId;
	/**
	 * 省名称
	 */
    @ApiModelProperty(value="省名称")
	private String provinceName;
	
    @ApiModelProperty(value="城市列表")
    private List<SysAreaStandard> cityList;


    @Override
    public int compareTo(SysAreaCityModel model) {           //重写Comparable接口的compareTo方法，
    	//降序修改相减顺序即可
//    	return this.provinceId > model.getProvinceId()?-1:0;
    	return this.provinceId > model.getProvinceId()?0:-1;
	}	
}
