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
 * 行政区域表
 *
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8>
 * @author <开发者>
 * @since 2019-02-21 01:25
 */
@Table(name = "sys_area_standard")
public @Data class SysAreaStandard implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 父编号
	 */
	private Long pid;
	/**
	 * 邮政编码
	 */
	@Column(name = "postCode")
	private String postCode;
	/**
	 * 简称
	 */
	private String shortName;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 省简称
	 */
	private String markName;
	/**
	 * 简拼
	 */
	private String py;
	/**
	 * 全拼
	 */
	private String pyFull;
	/**
	 * 经度
	 */
	private String lng;
	/**
	 * 纬度
	 */
	private String lat;
	/**
	 * 级别(0-国家,1-省,2-市,3-区、县,4-乡、镇、街道,5-村、居委会)
	 */
	private Integer level;
	/**
	 * 热度
	 */
	private Integer sort;
	/**
	 * 省编号
	 */
	private Long provinceId;
	/**
	 * 省名称
	 */
	private String provinceName;
	/**
	 * 市编号
	 */
	private Long cityId;
	/**
	 * 市名称
	 */
	private String cityName;
	/**
	 * 区域ID
	 */
	private Long areaId;
	/**
	 * 区域名称
	 */
	private String areaName;
	/**
	 * 乡镇街道编号
	 */
	private Long streetId;
	/**
	 * 乡镇街道名称
	 */
	private String streetName;
	/**
	 * 是否有效（0-无效，1-有效）
	 */
	private Boolean isValid;
	/**
	 * 创建者
	 */
	private Long createBy;
	/**
	 * 创建时间
	 */
	private java.util.Date createDate;
	/**
	 * 更新者
	 */
	private Long updateBy;
	/**
	 * 更新时间
	 */
	private java.util.Date updateDate;

	/**
	 * 行政区域全名
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2019年3月19日-下午1:05:24
	 * @return
	 */
	public String getAreaDetailName() {
		
		StringBuffer result = new StringBuffer();
		//provinceName cityName areaName streetName
		//省
		if(provinceName != null && provinceName!=""){
			result.append(provinceName); //.append("/");
		}
		//市
		if(cityName != null && cityName!=""){
			result.append(cityName); //.append("/");
		}
		//区县
		if(areaName != null && areaName!=""){
			result.append(areaName); //.append("/");
		}
		//街道
		if(streetName != null && streetName!=""){
			result.append(streetName); //.append("/");
		}
		
		return result.toString();
	}
	
	/**
	 * 省市 全称
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2019年3月19日-下午1:06:05
	 * @return
	 */
	public String getProvinceCityName() {
		
		StringBuffer result = new StringBuffer();
		//provinceName cityName areaName streetName
		//省
		if(provinceName != null && provinceName!=""){
			result.append(provinceName); //.append("/");
		}
		//市
		if(cityName != null && cityName!=""){
			result.append(cityName); //.append("/");
		}
		
		if(result.length() == 0) {
			result.append(name); //.append("/");
		}
		
		return result.toString();
	}
}
