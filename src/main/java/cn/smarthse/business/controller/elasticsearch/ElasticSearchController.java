package cn.smarthse.business.controller.elasticsearch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.smarthse.business.model.elasticsearch.Commodity;
import cn.smarthse.business.model.elasticsearch.SysArea;
import cn.smarthse.business.service.elasticsearch.CommodityService;
import cn.smarthse.business.service.elasticsearch.SysAreaService;

@Controller
@RequestMapping("/search")
public class ElasticSearchController {

	//@Autowired
	private CommodityService commodityService;

	//@Autowired
	private SysAreaService SysAreaService;

	/**
	 * 搜索结果数据
	 */
	@GetMapping("data")
	@ResponseBody
	public List<SysArea> list(SysArea queryParam, @RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		Page<SysArea> page = SysAreaService.pageQuery(pageIndex, pageSize, queryParam);
		return page.getContent();
	}

	@GetMapping("init")
	public void initRepositoryData() {
		// SysAreaService.initData();
	}

	@GetMapping("test")
	public void test() {
		System.out.println(commodityService.count());
		testInsert();
		testDelete();
		testGetAll();
		testGetByName();
		testPage();
	}

	public void testInsert() {
		Commodity commodity = new Commodity();
		commodity.setSkuId("1501009001");
		commodity.setName("原味切片面包（10片装）");
		commodity.setCategory("101");
		commodity.setPrice(880);
		commodity.setBrand("良品铺子");
		commodityService.save(commodity);

		commodity = new Commodity();
		commodity.setSkuId("1501009002");
		commodity.setName("原味切片面包（6片装）");
		commodity.setCategory("101");
		commodity.setPrice(680);
		commodity.setBrand("良品铺子");
		commodityService.save(commodity);

		commodity = new Commodity();
		commodity.setSkuId("1501009004");
		commodity.setName("元气吐司850g");
		commodity.setCategory("101");
		commodity.setPrice(120);
		commodity.setBrand("百草味");
		commodityService.save(commodity);

	}

	public void testDelete() {
		Commodity commodity = new Commodity();
		commodity.setSkuId("1501009002");
		commodityService.delete(commodity);
	}

	public void testGetAll() {
		Iterable<Commodity> iterable = commodityService.getAll();
		iterable.forEach(e -> System.out.println(e.toString()));
	}

	public void testGetByName() {
		List<Commodity> list = commodityService.getByName("面包");
		System.out.println(list);
	}

	public void testPage() {
		Page<Commodity> page = commodityService.pageQuery(0, 10, "切片");
		System.out.println(page.getTotalPages());
		System.out.println(page.getNumber());
		System.out.println(page.getContent());
	}

}
