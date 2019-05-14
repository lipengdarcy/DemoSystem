/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */

package cn.smarthse.business.service.system.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.smarthse.business.entity.system.SysRole;
import cn.smarthse.business.entity.system.SysUserRole;
import cn.smarthse.business.service.system.ISysRoleService;
import cn.smarthse.business.service.system.ISysUserRoleService;
import cn.smarthse.framework.generic.GenericServiceImpl;
import tk.mybatis.mapper.entity.Example;

/**
 *
 * 用户角色
 */
@Service
@Transactional(readOnly = true)
public class SysUserRoleServiceImpl extends GenericServiceImpl<SysUserRole>
		implements ISysUserRoleService {

	@Autowired
	private ISysRoleService roleService;

	@Override
	public List<SysUserRole> getListByUserId(Integer userId) {
		Example example = new Example(SysUserRole.class);
		example.createCriteria().andEqualTo("isValid", true).andEqualTo("uid", userId);
		return this.getListByExample(example);
	}

	@Override
	public SysUserRole getByUserIdAndRole(Integer userId, Integer roleId) {
		Example example = new Example(SysUserRole.class);
		example.createCriteria().andEqualTo("isValid", true).andEqualTo("uid", userId).andEqualTo("roleId", roleId);
		return this.getOneByExample(example);
	}

	@Transactional(readOnly = false)
	@Override
	public void delByUserId(Integer userId, Integer updateBy) {
		List<SysUserRole> userRoles = this.getListByUserId(userId);
		userRoles.forEach(userRole -> {
			this.delete(userRole);
		});
	}

	@Override
	public List<SysUserRole> getUidListByBusinessType() {
		// 找出业务类型的角色
		List<SysRole> roleList = roleService.getByRoleType(1);

		String roleIds = "";
		for (SysRole role : roleList) {
			roleIds += (role.getId() + ",");
		}

		Example example = new Example(SysUserRole.class);
		example.createCriteria().andEqualTo("isValid", true).andCondition("role_id in",
				roleIds.substring(0, roleIds.length() - 1));
		example.selectProperties("uid").setDistinct(true);

		logger.info(this.getListByExample(example));
		return this.getListByExample(example);
	}

	@Override
	public boolean checkUserRole(Integer userId, String roleIds) {
		List<String> list = Arrays.asList(roleIds.split(","));

		Example example = new Example(SysUserRole.class);
		example.createCriteria().andEqualTo("isValid", true).andEqualTo("uid", userId).andIn("roleId", list);
		// example.selectProperties("uid").setDistinct(true);

		return this.getCountByExample(example) > 0;
	}

}
