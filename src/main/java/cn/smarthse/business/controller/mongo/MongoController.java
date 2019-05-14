package cn.smarthse.business.controller.mongo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.smarthse.business.entity.system.SysAreaStandard;
import cn.smarthse.business.service.mongo.MongoService;
import cn.smarthse.business.service.system.AreaService;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;

@Controller
@RequestMapping("/mongo")
public class MongoController {

	@Autowired
	private AreaService AreaService;

	@Autowired
	private MongoService MongoService;


	@RequestMapping()
	public String init(ModelMap m) {
		// List<SysAreaStandard> areaList = MongoService.getAllArea();
		// m.put("areaList", areaList);
		return "mongo/mongoList";
	}

	
	/**
	 * 行政区域页面
	 */
	@RequestMapping("areaList")
	public String areaList(ModelMap m) {
		return "Mongo/areaList";
	}

	/**
	 * 行政区域数据
	 */
	@ResponseBody
	@RequestMapping("areaData")
	public JqGridData<SysAreaStandard> areaData(JqGridParam param, String name) {
		JqGridData<SysAreaStandard> data = MongoService.getGridData(param, name);
		return data;
	}

	/**
	 * mysql导入mongo 行政区域数据
	 */
	@RequestMapping("import")
	@ResponseBody
	public String inportFromMysql(ModelMap m) {
		List<SysAreaStandard> list = AreaService.selectList();
		// MongoService.addList(list);
		return "mysql数据导入mongo成功，导入记录数：" + list.size();
	}

}
