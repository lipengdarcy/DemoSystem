package cn.smarthse.business.model.elasticsearch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import cn.smarthse.business.entity.system.SysAreaStandard;
import lombok.Data;

/**
 * 行政区域表（elasticsearch的index）,indexName必须全部小写字母
 *
 */
@Document(indexName = "sys_area")
public @Data class SysArea implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	@Id
	private Long id;
	/**
	 * 父编号
	 */
	private Long pid;
	/**
	 * 邮政编码
	 */
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
	 * SysAreaStandard 转化为SysArea
	 */
	public SysArea getSysArea(SysAreaStandard b) {
		SysArea a = new SysArea();
		a.setId(b.getId());
		a.setPid(b.getPid());
		a.setPostCode(b.getPostCode());
		a.setShortName(b.getShortName());
		a.setName(b.getName());
		a.setMarkName(b.getMarkName());
		a.setPy(b.getPy());
		a.setPyFull(b.getPyFull());
		a.setLng(b.getLng());
		a.setLat(b.getLat());
		a.setLevel(b.getLevel());
		a.setSort(b.getSort());
		a.setProvinceId(b.getProvinceId());
		a.setProvinceName(b.getProvinceName());
		a.setCityId(b.getCityId());
		a.setCityName(b.getCityName());
		a.setAreaId(b.getAreaId());
		a.setAreaName(b.getAreaName());
		a.setStreetId(b.getStreetId());
		a.setStreetName(b.getStreetName());
		return a;

	}

	/**
	 * List SysAreaStandard 转化为List SysArea
	 */
	public List<SysArea> getSysAreaList(List<SysAreaStandard> list) {
		List<SysArea> result = new ArrayList<SysArea>();
		SysArea a = new SysArea();
		for (SysAreaStandard b : list) {
			result.add(a.getSysArea(b));
		}
		return result;
	}
}
