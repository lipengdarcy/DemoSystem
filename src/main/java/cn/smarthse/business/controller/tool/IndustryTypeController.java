package cn.smarthse.business.controller.tool;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.smarthse.business.entity.system.SysIndustry;
import cn.smarthse.business.model.system.SysIndustryModel;
import cn.smarthse.business.service.system.ISysIndustryService;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;
import cn.smarthse.framework.model.ResponseData;
import io.swagger.annotations.Api;

/**
 * 国民经济行业分类
 */
@Api(value = "组件：国民经济行业分类", tags = "组件：接口")
@Controller
@RequestMapping("/")
public class IndustryTypeController {

	@Autowired
	private ISysIndustryService ISysIndustryService;

	/**
	 * 行业分类页面
	 */
	@GetMapping(value = "/system/param/industry")
	public String industry(HttpSession session, ModelMap m) {
		return "common/industryTypeList";
	}

	/**
	 * 行业分类数据
	 */
	@ResponseBody
	@GetMapping(value = "/system/param/industry/industryData")
	public JqGridData<SysIndustryModel> industryData(JqGridParam param, SysIndustry queryParam) {
		JqGridData<SysIndustryModel> data = ISysIndustryService.getGridData(param, queryParam);
		return data;
	}

	/**
	 * 省市联动查询
	 * 
	 * @param areaId
	 * @param level
	 * @return
	 */
	@RequestMapping("/industry/getChildren")
	@ResponseBody
	public ResponseData<List<SysIndustry>> getChildren(Long id) {
		ResponseData<List<SysIndustry>> data = new ResponseData<>();
		List<SysIndustry> list = null;
		try {
			list = ISysIndustryService.getChildren(id);
		} catch (Exception e) {
			data.setCode(0);
			e.printStackTrace();
		}
		data.setData(list);
		return data;
	}

	/**
	 * 根据行业分类id获取对应的一级分类列表、二级分类列表、三级分类列表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/industry/getTypeList")
	@ResponseBody
	public ResponseData<Map> getTypeList(Long id) {
		ResponseData<Map> data = new ResponseData<Map>();
		Map map = null;
		try {
			map = ISysIndustryService.getTypeList(id);
		} catch (Exception e) {
			data.setCode(0);
			e.printStackTrace();
		}
		data.setData(map);
		return data;
	}

}
