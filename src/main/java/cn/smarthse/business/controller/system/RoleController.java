package cn.smarthse.business.controller.system;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.smarthse.business.entity.system.SysRole;
import cn.smarthse.business.model.system.SysRoleModel;
import cn.smarthse.business.model.system.SysRoleSearchModel;
import cn.smarthse.business.service.system.ISysRoleService;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;

/**
 * 
 * 角色管理
 */
@Controller
@RequestMapping("system/role")
public class RoleController {
	private static final String prefix = "[角色管理]";
	private static final String basePath = "system/role/";
	/**
	 * 日志对象
	 */
	protected final Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	ISysRoleService roleService;

	@GetMapping
	public String list(ModelMap model) {
		logger.info("{}-角色列表", prefix);
		return basePath + "roleList";
	}

	@ResponseBody
	@GetMapping(value = "/listData")
	public JqGridData<SysRole> listData(HttpSession session, JqGridParam params, SysRoleSearchModel model) {
		PageHelper.startPage((int) params.getPage(), (int) params.getRows());
		List<SysRole> list = roleService.getRoleList(model);
		PageInfo<SysRole> roleList = new PageInfo<>(list);
		JqGridData<SysRole> data = new JqGridData<>(roleList, params);
		return data;
	}

	@GetMapping(value = "view")
	public String view(SysRoleModel model, ModelMap m) {
		logger.info("{}-查看角色", prefix);
		m.addAttribute("model", model);
		return basePath + "view";
	}

}
