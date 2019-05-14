
package cn.smarthse.business.service.system.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.smarthse.business.entity.system.SysIndustry;
import cn.smarthse.business.model.system.SysIndustryModel;
import cn.smarthse.business.service.system.ISysIndustryService;
import cn.smarthse.framework.generic.GenericServiceImpl;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;
import cn.smarthse.framework.util.StringUtil;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
@Transactional(readOnly = true)
public class SysIndustryServiceImpl extends GenericServiceImpl<SysIndustry> implements ISysIndustryService {

	@Override
	public List<SysIndustry> getChildren(Long typeId) {
		Example e = new Example(SysIndustry.class);
		e.createCriteria().andEqualTo("pid", typeId).andEqualTo("isValid", true);
		List<SysIndustry> list = dao.selectByExample(e);
		return list;
	}

	@Override
	public Map getTypeList(Long typeId) {
		Map map = new HashMap();
		List<SysIndustry> provinceList = this.getChildren((long) 0), cityList = null, areaList = null;
		map.put("provinceList", provinceList);
		SysIndustry record = dao.selectByPrimaryKey(typeId);
		// 没有选择，则返回一级列表
		if (record == null)
			return map;

		switch (record.getLevel()) {
		case 1:
			// 一级
			map.put("provinceId", record.getId());
			break;
		case 2:
			// 二级
			map.put("provinceId", record.getPid());
			cityList = this.getChildren(record.getPid());
			map.put("cityList", cityList);
			map.put("cityId", record.getId());
			break;
		case 3:
			// 三级
			SysIndustry parent = dao.selectByPrimaryKey(record.getPid());
			map.put("provinceId", parent.getPid());
			cityList = this.getChildren(parent.getPid());
			map.put("cityList", cityList);
			map.put("cityId", record.getPid());
			areaList = this.getChildren(record.getPid());
			map.put("areaList", areaList);
			map.put("areaId", record.getId());
			break;
		default:
			break;
		}
		return map;
	}

	@Override
	public String getFullIndustryName(Long typeId) {
		List<String> names = new ArrayList<String>();
		Map map = this.getTypeList(typeId);
		Long level1 = (Long) map.get("provinceId");
		Long level2 = (Long) map.get("cityId");
		Long level3 = (Long) map.get("areaId");
		if (level1 != null) {
			SysIndustry s = this.dao.selectByPrimaryKey(level1);
			if (s != null && !StringUtil.isEmpty(s.getName())) {
				names.add(s.getName());
			}
		}
		if (level2 != null) {
			SysIndustry s = this.dao.selectByPrimaryKey(level2);
			if (s != null && !StringUtil.isEmpty(s.getName())) {
				names.add(s.getName());
			}
		}
		if (level3 != null) {
			SysIndustry s = this.dao.selectByPrimaryKey(level3);
			if (s != null && !StringUtil.isEmpty(s.getName())) {
				names.add(s.getName());
			}
		}
		if (names.size() > 0) {
			return String.join(",", names);
		}
		return "";
	}

	@Override
	public JqGridData<SysIndustryModel> getGridData(JqGridParam param, SysIndustry queryParam) {
		PageHelper.startPage((int) param.getPage(), (int) param.getRows());
		Example example = new Example(SysIndustry.class);
		Criteria c = example.createCriteria();
		c.andGreaterThan("level", 0);
		if (queryParam != null) {
			if (!StringUtil.isEmpty(queryParam.getName())) {
				c.andLike("name", "%" + queryParam.getName() + "%");
			}
			if (queryParam.getLevel() != null) {
				c.andEqualTo("level", queryParam.getLevel());
			}
		}
		Page<SysIndustry> list = (Page<SysIndustry>) this.getListByExample(example);

		Page<SysIndustryModel> result = new Page<SysIndustryModel>();
		for (SysIndustry a : list) {
			SysIndustryModel model = new SysIndustryModel();
			model.setRecord(a);
			model.setExpanded(false);
			if (a.getLevel() == 3)
				model.setIsLeaf(true);
			else
				model.setIsLeaf(false);
			result.add(model);
		}

		JqGridData<SysIndustryModel> data = new JqGridData<SysIndustryModel>(result, param);
		return data;
	}

}
