package cn.smarthse.business.controller.system;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.smarthse.business.controller.ControllerSupport;
import cn.smarthse.business.entity.system.SysParam;
import cn.smarthse.business.entity.system.SysParamType;
import cn.smarthse.business.service.system.ISysParamService;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;
import cn.smarthse.framework.model.ResponseData;
import io.swagger.annotations.Api;

/**
 * 系统参数设置
 * 
 * 目前只有业务管理部审核时间设置
 */
@Api(value = "系统参数", tags = "系统参数")
@Controller
@RequestMapping("system/param")
public class ParamController extends ControllerSupport {

	private Logger log = LogManager.getLogger(this.getClass());

	private final String basePath = "system/param/";

	@Autowired
	private ISysParamService ISysParamService;

	/**
	 * 系统参数类型页面
	 */
	@RequestMapping()
	public String list(HttpSession session, ModelMap m) {
		return basePath + "paramList";
	}

	/**
	 * 系统参数类型数据
	 */
	@ResponseBody
	@GetMapping(value = "/listData")
	public JqGridData<SysParamType> listData(JqGridParam params, SysParamType sysParam) {
		PageHelper.startPage((int) params.getPage(), (int) params.getRows());
		Page<SysParamType> list = (Page<SysParamType>) ISysParamService.getParamTypeList(sysParam);
		JqGridData<SysParamType> data = new JqGridData<SysParamType>(list, params);
		return data;
	}

	/**
	 * 系统参数类型详情查看
	 */
	@GetMapping(value = "/viewParamType/{id}")
	public String viewParamType(@PathVariable("id") Integer id, ModelMap m) {
		SysParamType record = ISysParamService.getParamType(id);
		List<SysParam> list = ISysParamService.getParamList(id);
		m.put("record", record);
		m.put("list", list);
		return basePath + "viewParamType";
	}

	/**
	 * 系统参数编辑
	 */
	@ResponseBody
	@PostMapping(value = "/editParam")
	public ResponseData<String> editParam(SysParam param) {
		log.info("系统参数编辑, 参数值：" + param.getParamValue());
		ResponseData<String> data = new ResponseData<>();
		SysParam record = ISysParamService.getDefaultAuditTime();
		record.setParamValue(param.getParamValue());
		ISysParamService.update(record);
		data.setMessage("系统参数编辑成功!");
		return data;
	}

}
