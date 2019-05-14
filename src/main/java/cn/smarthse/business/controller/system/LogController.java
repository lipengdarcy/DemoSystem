package cn.smarthse.business.controller.system;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import cn.smarthse.business.model.system.SysLogModel;
import cn.smarthse.business.model.system.SysLogSearchModel;
import cn.smarthse.business.service.system.ISysLogService;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;
import cn.smarthse.framework.util.StringUtils;

@Controller
@RequestMapping("system/log")
public class LogController {

	private static final String prefix = "[系统日志管理]";
	private static final String basePath = "system/log/";
	/**
	 * 日志对象
	 */
	protected final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	ISysLogService logService;

	@GetMapping
	public String index(ModelMap model) {
		logger.info("{}-系统日志列表", prefix);

		return basePath + "logList";
	}

	@ResponseBody
	@GetMapping(value = "/listData")
	public JqGridData<SysLogModel> listData(HttpSession session, JqGridParam param, SysLogSearchModel model) {
		logger.info("{}-获取:系统日志列表数据,SysLogSearchModel={}", prefix, model);
		if (StringUtils.isNotEmpty(model.getOptTime())) {
			String[] datetime = model.getOptTime().split("至");
			model.setOptStartTime(datetime[0]);
			model.setOptEndTime(datetime[1]);
		}

		JqGridData<SysLogModel> data = null;
		PageInfo<SysLogModel> roleList = logService.getLogList(param, model);
		data = new JqGridData<SysLogModel>(roleList, param);
		return data;
	}
}
