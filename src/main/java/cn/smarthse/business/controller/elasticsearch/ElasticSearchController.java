package cn.smarthse.business.controller.elasticsearch;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.smarthse.business.model.elasticsearch.SysArea;

@Controller
@RequestMapping("/search")
public class ElasticSearchController {

	@GetMapping()
	public String index(ModelMap m, SysArea queryParam,
			@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		m.put("data", Page.empty());
		return "elastic/searchResult";
	}

	/**
	 * 搜索结果数据
	 */
	@GetMapping("data")
	@ResponseBody
	public List<SysArea> list(SysArea queryParam, @RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		Page<SysArea> page = null; // SysAreaService.pageQuery(pageIndex, pageSize, queryParam);
		return page.getContent();
	}

}
