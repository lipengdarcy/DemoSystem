package cn.smarthse.business.service.system.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.smarthse.business.dao.system.SysParamTypeMapper;
import cn.smarthse.business.entity.system.SysParam;
import cn.smarthse.business.entity.system.SysParamType;
import cn.smarthse.business.service.system.ISysParamService;
import cn.smarthse.framework.generic.GenericServiceImpl;
import cn.smarthse.framework.util.StringUtil;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class SysParamServiceImpl extends GenericServiceImpl<SysParam> implements ISysParamService {

	@Autowired
	private SysParamTypeMapper SysParamTypeMapper;

	@Override
	public List<SysParam> getOccupationClassList() {
		Example example = new Example(SysParam.class);
		Criteria c1 = example.createCriteria();
		c1.andEqualTo("isValid", true).andEqualTo("paramType", 1).andLike("paramCode", "1-2_%");
		Criteria c2 = example.createCriteria();
		c2.andEqualTo("isValid", true).andEqualTo("paramType", 1).andLike("paramCode", "1-3_%");
		Criteria c3 = example.createCriteria();
		c3.andEqualTo("isValid", true).andEqualTo("paramType", 1).andLike("paramCode", "1-6_%");
		example.or(c1);
		example.or(c2);
		example.or(c3);
		return this.getListByExample(example);
	}
	@Override
	public SysParam getOccupationClassListByName(String name) {
		Example example = new Example(SysParam.class);
		Criteria c1 = example.createCriteria();
		c1.andEqualTo("isValid", true).andEqualTo("paramType", 1).andLike("paramCode", "1-2_%").andEqualTo("paramValue", name);
		Criteria c2 = example.createCriteria();
		c2.andEqualTo("isValid", true).andEqualTo("paramType", 1).andLike("paramCode", "1-3_%").andEqualTo("paramValue", name);
		Criteria c3 = example.createCriteria();
		c3.andEqualTo("isValid", true).andEqualTo("paramType", 1).andLike("paramCode", "1-6_%").andEqualTo("paramValue", name);
		example.or(c1);
		example.or(c2);
		example.or(c3);
		List<SysParam> l=this.getListByExample(example);
		if(l!=null&&l.size()>0) {
			return l.get(0);
		}
		return null;
	}

	@Override
	public SysParam getDefaultAuditTime() {
		Example example = new Example(SysParam.class);
		example.createCriteria().andEqualTo("isValid", true).andEqualTo("paramType", 32);
		List<SysParam> list = this.getListByExample(example);
		if (list.isEmpty())
			return null;
		return list.get(0);
	}

	@Override
	public List<SysParamType> getParamTypeList(SysParamType sysParam) {
		Example example = new Example(SysParamType.class);
		Criteria c= example.createCriteria();
		c.andEqualTo("isValid", true);

		if (!StringUtil.isEmpty(sysParam.getTypeCode())) {
			c.andLike("typeCode", sysParam.getTypeCode());
			//c.andTypeCodeLike("%" + sysParam.getTypeCode() + "%");
		}
		if (!StringUtil.isEmpty(sysParam.getTypeValue())) {
			c.andLike("typeValue", sysParam.getTypeValue());
			//c.andTypeValueLike("%" + sysParam.getTypeValue() + "%");
		}
		example.setOrderByClause("id desc");
		List<SysParamType> list = SysParamTypeMapper.selectByExample(example);
		return list;
	}
	
	

	@Override
	public SysParam getSystemParamEndwithTypeCode(String typeCode) {
		if(StringUtils.isBlank(typeCode)) {
			return null;
		}
		Example example = new Example(SysParam.class);
		example.createCriteria().andEqualTo("isValid", true).andLike("paramCode", "%-"+typeCode);
		
		return super.getOneByExample(example);
	}

	@Override
	public SysParamType getParamType(Integer id) {
		return SysParamTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<SysParam> getParamList(Integer paramType) {
		Example example = new Example(SysParam.class);
		example.createCriteria().andEqualTo("isValid", true).andEqualTo("paramType", paramType);
		return this.getListByExample(example);
	}
	@Override
	public SysParam getOccupationClassListByCode(String code) {
		if(StringUtil.isEmpty(code)) {
			return null;
		}
		Example example = new Example(SysParam.class);
		example.createCriteria().andEqualTo("isValid", true).andEqualTo("paramCode", code).andEqualTo("paramType", 1);
		List<SysParam>  list= this.getListByExample(example);
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}

}
