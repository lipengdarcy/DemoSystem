/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */

package cn.smarthse.business.service.system.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.smarthse.business.entity.system.SysRolePermission;
import cn.smarthse.business.service.system.ISysRolePermissionService;
import cn.smarthse.framework.generic.GenericServiceImpl;

/**
 *
 *
 * 角色权限
 */
@Service
@Transactional(readOnly = true)
public class SysRolePermissionServiceImpl extends GenericServiceImpl<SysRolePermission>
		implements ISysRolePermissionService {

}
