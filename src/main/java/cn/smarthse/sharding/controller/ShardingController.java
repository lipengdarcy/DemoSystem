package cn.smarthse.sharding.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.smarthse.business.controller.ControllerSupport;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;
import cn.smarthse.sharding.entity.OrderItem;
import cn.smarthse.sharding.service.ShardingDemoService;

/**
 * 分库分表测试页面
 */
@Controller
@RequestMapping("/sharding")
public class ShardingController extends ControllerSupport {

	@Autowired
	private ShardingDemoService ShardingDemoService;

	/**
	 * 首页
	 */
	@GetMapping
	public String list(ModelMap m) {
		//ShardingDemoService.createData();
		return "sharding/index";
	}

	/**
	 * 订单数据，来自多个库、多个表
	 * 
	 */
	@ResponseBody
	@GetMapping(value = "/listData")
	public JqGridData<OrderItem> listData(HttpSession session, JqGridParam param) {
		JqGridData<OrderItem> data = ShardingDemoService.getData(param);
		return data;
	}

}
