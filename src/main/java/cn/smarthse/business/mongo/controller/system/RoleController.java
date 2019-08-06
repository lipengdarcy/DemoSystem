package cn.smarthse.business.mongo.controller.system;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.smarthse.business.mongo.collection.system.SystemRole;
import cn.smarthse.business.mongo.service.system.SystemRoleService;
import cn.smarthse.framework.interceptor.log.Log;
import cn.smarthse.framework.interceptor.log.LogConstans;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;
import cn.smarthse.framework.model.ResponseData;

/**
 * 
 * 角色管理
 */
@Controller
@RequestMapping("system/role")
public class RoleController {

	private static final String basePath = "system/role/";

	protected final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private SystemRoleService SystemRoleService;

	@GetMapping
	public String list(ModelMap model) {
		return basePath + "roleList";
	}

	@ResponseBody
	@GetMapping(value = "/listData")
	public JqGridData<SystemRole> listData(HttpSession session, JqGridParam param, SystemRole dataParam) {
		JqGridData<SystemRole> data = SystemRoleService.getGridData(param, dataParam);
		return data;
	}

	@GetMapping(value = "view")
	public String view(SystemRole model, ModelMap m) {
		SystemRole role = SystemRoleService.getById(model.getId());
		m.put("role", role);
		return basePath + "viewRole";
	}

	@GetMapping(value = "/add")
	public String add(ModelMap m) {
		return basePath + "addRole";
	}

	@Log(module = LogConstans.SYSTEM_ROLE, description = "角色新增", type = LogConstans.type_opt_add)
	@PostMapping(value = "/add")
	@ResponseBody
	public ResponseData<String> add(SystemRole role) {
		ResponseData<String> data = new ResponseData<String>();
		SystemRoleService.insert(role);
		data.setData(role.getId());
		data.setMessage("新增成功!");
		return data;
	}

	@GetMapping(value = "/edit")
	public String edit(@RequestParam(value = "id", required = false) String id, ModelMap m) {
		SystemRole role = SystemRoleService.getById(id);
		m.put("role", role);
		return basePath + "editRole";
	}

	@Log(module = LogConstans.SYSTEM_ROLE, description = "角色编辑", type = LogConstans.type_opt_edit)
	@PostMapping(value = "/edit")
	@ResponseBody
	public ResponseData<String> edit(SystemRole role) {
		ResponseData<String> data = new ResponseData<String>();
		// 先取值，然后更新，不然属性就全部覆盖了，mongodb没有类似mybatis的updateByPrimaryKeySelective
		SystemRole oldValue = SystemRoleService.getById(role.getId());
		oldValue.setRoleName(role.getRoleName());
		oldValue.setRoleCode(role.getRoleCode());
		oldValue.setRoleDesc(role.getRoleDesc());
		SystemRoleService.update(oldValue);
		data.setMessage("编辑成功!");
		return data;
	}

	@Log(module = LogConstans.SYSTEM_ROLE, description = "角色删除", type = LogConstans.type_opt_del)
	@PostMapping(value = "/del")
	@ResponseBody
	public ResponseData<String> del(String id) {
		ResponseData<String> data = new ResponseData<String>();
		SystemRole role = SystemRoleService.getById(id);
		SystemRoleService.delete(role);
		data.setMessage("删除成功!");
		return data;
	}

}
