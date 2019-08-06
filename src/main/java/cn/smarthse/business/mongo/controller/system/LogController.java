package cn.smarthse.business.mongo.controller.system;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.smarthse.business.mongo.collection.system.SystemLog;
import cn.smarthse.business.mongo.service.system.SystemLogService;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;

@Controller
@RequestMapping("system/log")
public class LogController {

	private static final String basePath = "system/log/";

	@Autowired
	SystemLogService SystemLogService;

	@GetMapping
	public String index(ModelMap model) {
		return basePath + "logList";
	}

	@ResponseBody
	@GetMapping(value = "/listData")
	public JqGridData<SystemLog> listData(HttpSession session, JqGridParam param, SystemLog dataParam) {
		JqGridData<SystemLog> data = SystemLogService.getGridData(param, dataParam);
		return data;
	}
}
