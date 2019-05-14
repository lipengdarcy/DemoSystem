package cn.smarthse.business.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.smarthse.business.model.elasticsearch.SysArea;
import cn.smarthse.framework.util.StringUtil;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
@RequestMapping("/")
public class HomeController extends ControllerSupport {

	/**
	 * 首页 or 搜索结果页面
	 */
	@GetMapping
	public String index(ModelMap m, String keyword,
			@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		if (StringUtil.isEmpty(keyword))
			return "index";
		SysArea area = new SysArea();
		area.setName(keyword);
		m.put("global_keyword", keyword);
		return "queryResult";
	}

	/**
	 * 空白页，便于快速构建页面
	 */
	@RequestMapping("blank")
	public String blank(ModelMap m) {
		return "common/blank";
	}

}
