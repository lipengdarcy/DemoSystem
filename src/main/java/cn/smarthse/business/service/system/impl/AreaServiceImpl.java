package cn.smarthse.business.service.system.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.smarthse.business.entity.system.SysAreaStandard;
import cn.smarthse.business.service.system.AreaService;
import cn.smarthse.framework.generic.GenericServiceImpl;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;
import cn.smarthse.framework.util.StringUtil;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class AreaServiceImpl extends GenericServiceImpl<SysAreaStandard>
		implements AreaService {

	public JqGridData<SysAreaStandard> getGridData(JqGridParam param, SysAreaStandard queryParam) {
		PageHelper.startPage((int) param.getPage(), (int) param.getRows());
		Example example = new Example(SysAreaStandard.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("isValid", true);
		if (queryParam != null) {
			if (queryParam.getLevel() != null) {
				c.andEqualTo("level", queryParam.getLevel());
			}
			if (!StringUtil.isEmpty(queryParam.getName())) {
				c.andLike("name", "%"+queryParam.getName()+"%");
			}
			if (queryParam.getProvinceId() != null) {
				c.andEqualTo("provinceId", queryParam.getProvinceId());
			}
			if (queryParam.getCityId() != null) {
				c.andEqualTo("cityId", queryParam.getCityId());
			}
			if (queryParam.getAreaId() != null) {
				c.andEqualTo("areaId", queryParam.getAreaId());
			}
		}
		Page<SysAreaStandard> list = (Page<SysAreaStandard>) this.getListByExample(example);
		JqGridData<SysAreaStandard> data = new JqGridData<SysAreaStandard>(list, param);
		return data;
	}

	/**
	 * 级联根据level获得区域
	 *
	 * @param areaId
	 * @param level
	 * @return
	 */
	@Override
	public List<SysAreaStandard> getCascadeByLevel(Long areaId, int level) {
		// SELECT * FROM sys_area_standard a where
		// <if test="level == 1">
		// a.level =1 ;
		// </if>
		// <if test="level == 2">
		// a.level =2
		// and a.province_id =#{areaId,jdbcType=BIGINT}
		// </if>
		// <if test="level == 3">
		// a.level =3
		// and a.city_id =#{areaId,jdbcType=BIGINT}
		// </if>
		Example example = new Example(SysAreaStandard.class);
		if (level == 1) {
			example.createCriteria().andEqualTo("level", level).andEqualTo("isValid", true);
		} else if (level == 2) {
			example.createCriteria().andEqualTo("provinceId", areaId).andEqualTo("isValid", true);
		} else if (level == 3) {
			example.createCriteria().andEqualTo("cityId", areaId).andEqualTo("isValid", true);
		} else if (level == 4) {
			example.createCriteria().andEqualTo("areaId", areaId).andEqualTo("isValid", true);
		}

		return dao.selectByExample(example);
	}

	@Override
	public List<SysAreaStandard> getSysAreaListByPid(Long pid) {
		// 查询条件
		Example example = new Example(SysAreaStandard.class);
		example.createCriteria().andEqualTo("pid", pid).andEqualTo("isValid", true);
		example.setOrderByClause("id asc");

		return dao.selectByExample(example);
	}

	@Override
	public SysAreaStandard get(Long id) {
		return dao.selectByPrimaryKey(id);
	}

	/**
	 * 获取所有省份
	 */
	@Override
	public List<SysAreaStandard> getProvince() {
		// select * from sys_area_standard group by province_id;
		Example example = new Example(SysAreaStandard.class);
		example.createCriteria().andEqualTo("pid", 0).andEqualTo("isValid", true);
		example.setOrderByClause("id asc");

		return dao.selectByExample(example);
	}

	/**
	 * 获取行政区域的下级单位
	 * 
	 * @param sysareaId
	 *            行政区域id
	 * 
	 * @return 下级单位列表
	 */
	// @Cacheable(key = "'SysAreaStandard-getChildren-' + #sysareaId")
	public List<SysAreaStandard> getChildren(Long sysareaId) {
		Example e = new Example(SysAreaStandard.class);
		e.createCriteria().andEqualTo("pid", sysareaId).andEqualTo("isValid", true);
		List<SysAreaStandard> list = dao.selectByExample(e);
		return list;
	}

	/**
	 * 根据行政区域id获取对应的省列表、市列表、区列表
	 *
	 * @param areaId
	 */
	public Map getAreaList(Long areaId) {
		Map map = new HashMap();
		List<SysAreaStandard> provinceList = this.getChildren((long) 0), cityList = null, areaList = null;
		map.put("provinceList", provinceList);
		SysAreaStandard area = this.get(areaId);
		// 没有选择，则返回省列表
		if (area == null)
			return map;

		switch (area.getLevel()) {
		case 1:
			// 省级
			map.put("provinceId", area.getProvinceId());
			break;
		case 2:
			// 市级
			map.put("provinceId", area.getProvinceId());
			cityList = this.getChildren(area.getProvinceId());
			map.put("cityList", cityList);
			map.put("cityId", area.getCityId());
			break;
		case 3:
			// 区级
			map.put("provinceId", area.getProvinceId());
			cityList = this.getChildren(area.getProvinceId());
			map.put("cityList", cityList);
			map.put("cityId", area.getCityId());
			areaList = this.getChildren(area.getCityId());
			map.put("areaList", areaList);
			map.put("areaId", area.getAreaId());
			break;
		default:
			break;
		}
		return map;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.smarthse.business.service.system.AreaService#getCitys()
	 */
	@Override
	public List<SysAreaStandard> getCitys() {
		Example example = new Example(SysAreaStandard.class);
		example.createCriteria().andEqualTo("level", 2).andNotEqualTo("provinceName", null).andEqualTo("isValid", true);

		example.setOrderByClause(" id asc");

		return dao.selectByExample(example);
	}

	@Override
	public String getFullName(Long areaId) {
		List<String> names = new ArrayList<String>();
		Map map = this.getAreaList(areaId);
		Long level1 = (Long) map.get("provinceId");
		Long level2 = (Long) map.get("cityId");
		Long level3 = (Long) map.get("areaId");
		if (level1 != null) {
			SysAreaStandard s = this.dao.selectByPrimaryKey(level1);
			if (s != null && !StringUtil.isEmpty(s.getName())) {
				names.add(s.getName());
			}
		}
		if (level2 != null) {
			SysAreaStandard s = this.dao.selectByPrimaryKey(level2);
			if (s != null && !StringUtil.isEmpty(s.getName())) {
				names.add(s.getName());
			}
		}
		if (level3 != null) {
			SysAreaStandard s = this.dao.selectByPrimaryKey(level3);
			if (s != null && !StringUtil.isEmpty(s.getName())) {
				names.add(s.getName());
			}
		}
		if (names.size() > 0) {
			return String.join(",", names);
		}
		return "";
	}


	public String getFullName2(Long areaId,String splitStr) {

		SysAreaStandard area = this.get(areaId);
		if (area!=null && StringUtils.isNotBlank(area.getProvinceName())) {
			StringBuffer sb = new StringBuffer("");
			sb.append(area.getProvinceName());
			if (StringUtils.isNotBlank(area.getCityName())) {
				sb.append(splitStr).append(area.getCityName());
			}
			if (StringUtils.isNotBlank(area.getAreaName())) {
				sb.append(splitStr).append(area.getAreaName());
			}
			return sb.toString();
		}
		return "";
	}
	@Override
	public List<SysAreaStandard> getProvinces() {
	
			Example example = new Example(SysAreaStandard.class);
			Criteria c = example.createCriteria();
			c.andEqualTo("level", 1).andIsNotNull("shortName");
			return this.getListByExample(example);
	}

	@Override
	public Long getProvinceAreaIdByName(String name) {
		Example example = new Example(SysAreaStandard.class);
		example.createCriteria().andEqualTo("level", 1).andLike("name", "%" + name + "%").andEqualTo("isValid", true);

		example.setOrderByClause(" id asc");

		List<SysAreaStandard> list= dao.selectByExample(example);
		Long areaId=null;
		if(list!=null&&list.size()>0){
			areaId=list.get(0).getId();
		}
		return areaId;
	}


}
