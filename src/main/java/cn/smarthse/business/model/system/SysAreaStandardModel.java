/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */


package cn.smarthse.business.model.system;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


 /**
 * 行政区域数据模型
 *
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8>
 * @author <开发者>
 * @since 2019-02-21 01:25
 */
public @Data class SysAreaStandardModel implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
    @ApiModelProperty(value="ID")
	private Long id;
	/**
	 * 父编号
	 */
    @ApiModelProperty(value="父编号")
	private Long pid;
	/**
	 * 邮政编码
	 */
    @ApiModelProperty(value="邮政编码")
	private String postCode;
	/**
	 * 简称
	 */
    @ApiModelProperty(value="简称")
	private String shortName;
	/**
	 * 名称
	 */
    @ApiModelProperty(value="名称")
	private String name;
	/**
	 * 省简称
	 */
    @ApiModelProperty(value="省简称")
	private String markName;
	/**
	 * 简拼
	 */
    @ApiModelProperty(value="简拼")
	private String py;
	/**
	 * 全拼
	 */
    @ApiModelProperty(value="全拼")
	private String pyFull;
	/**
	 * 经度
	 */
    @ApiModelProperty(value="经度")
	private String lng;
	/**
	 * 纬度
	 */
    @ApiModelProperty(value="纬度")
	private String lat;
	/**
	 * 级别(0-国家,1-省,2-市,3-区、县,4-乡、镇、街道,5-村、居委会)
	 */
    @ApiModelProperty(value="级别(0-国家,1-省,2-市,3-区、县,4-乡、镇、街道,5-村、居委会)")
	private Integer level;
	/**
	 * 热度
	 */
    @ApiModelProperty(value="热度")
	private Integer sort;
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
	/**
	 * 市编号
	 */
    @ApiModelProperty(value="市编号")
	private Long cityId;
	/**
	 * 市名称
	 */
    @ApiModelProperty(value="市名称")
	private String cityName;
	/**
	 * 区域ID
	 */
    @ApiModelProperty(value="区域ID")
	private Long areaId;
	/**
	 * 区域名称
	 */
    @ApiModelProperty(value="区域名称")
	private String areaName;
	/**
	 * 乡镇街道编号
	 */
    @ApiModelProperty(value="乡镇街道编号")
	private Long streetId;
	/**
	 * 乡镇街道名称
	 */
    @ApiModelProperty(value="乡镇街道名称")
	private String streetName;
	/**
	 * 创建时间
	 */
    @ApiModelProperty(value="创建时间")
	private java.util.Date createDate;
	/**
	 * 更新时间
	 */
    @ApiModelProperty(value="更新时间")
	private java.util.Date updateDate;


}
