package cn.smarthse.business.controller.work;

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
 * 待办任务
 */
@Controller
@RequestMapping("work/task")
public class TaskController {
	
	private static final String basePath = "work/task/";

	protected final Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	ISysRoleService roleService;

	@GetMapping
	public String list(ModelMap model) {
		return basePath + "taskList";
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
		m.addAttribute("model", model);
		return basePath + "view";
	}

}
