
package cn.smarthse.business.service.system.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.smarthse.business.dao.system.SysRoleMapper;
import cn.smarthse.business.entity.system.SysRole;
import cn.smarthse.business.model.system.SysRoleSearchModel;
import cn.smarthse.business.service.system.ISysRoleService;
import cn.smarthse.framework.generic.GenericServiceImpl;
import cn.smarthse.framework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
@Transactional(readOnly = true)
public class SysRoleServiceImpl extends GenericServiceImpl<SysRole> implements ISysRoleService {

	@Override
	public SysRole getByRoleName(Integer deptId, String roleName) {
		Example example = new Example(SysRole.class);
		Criteria criteria = example.createCriteria().andEqualTo("isValid", true).andEqualTo("roleName", roleName);
		if (deptId != null) {
			criteria.andEqualTo("deptId", deptId);
		}
		return this.getOneByExample(example);
	}

	@Override
	public List<SysRole> getRoleList(SysRoleSearchModel model) {
		Example example = new Example(SysRole.class);
		Criteria c = example.createCriteria().andEqualTo("isValid", true);

		if (model == null) {
			return this.getListByExample(example);
		}

		String roleName = model.getRoleName();
		Integer deptId = model.getDeptId();

		if (StringUtils.isNotEmpty(roleName)) {
			c.andLike("roleName", "%" + roleName + "%");
		}
		if (deptId != null) {
			c.andEqualTo("deptId", deptId);
		}

		return this.getListByExample(example);
	}

	@Override
	public List<SysRole> getByRoleType(Integer roleType) {
		Example example = new Example(SysRole.class);
		example.createCriteria().andEqualTo("isValid", true).andEqualTo("roleType", roleType);
		return this.getListByExample(example);
	}

	@Override
	public List<String> getUserRoleCodeList(Integer id) {
		return ((SysRoleMapper) dao).getUserRoleCodeList(id);
	}

}
