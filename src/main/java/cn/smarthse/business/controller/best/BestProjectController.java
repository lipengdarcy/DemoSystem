package cn.smarthse.business.controller.best;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.smarthse.business.controller.ControllerSupport;
import cn.smarthse.business.entity.best.BestContract;
import cn.smarthse.business.entity.best.BestProject;
import cn.smarthse.business.entity.best.TnetOrder;
import cn.smarthse.business.entity.best.YouOrder;
import cn.smarthse.business.service.best.IBestProjectService;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 查询百世订单、产品、合同等信息
 */
@Controller
@Api(value = "查询百世订单、产品、合同等信息", tags = "百世订单、产品、合同")
@RequestMapping(value = "/best")
public class BestProjectController extends ControllerSupport {

	private final String basePath = "best/";

	/**
	 * Service
	 */
	@Autowired
	private IBestProjectService BestProjectService;

	/**
	 * 默认页面： 产品信息表
	 */
	@RequestMapping()
	public String index() {
		return basePath + "BestProjectList";
	}

	/**
	 * 代理协议,页面
	 */
	@GetMapping("BestContract")
	public String BestContract() {
		return basePath + "BestContractList";
	}

	/**
	 * 代理协议,数据
	 * 
	 */
	@ApiOperation(value = "代理协议数据接口")
	@ResponseBody
	@GetMapping("BestContractData")
	public JqGridData<BestContract> BestContractData(JqGridParam params, BestContract model) {
		PageHelper.startPage((int) params.getPage(), (int) params.getRows());
		Page<BestContract> list = (Page<BestContract>) BestProjectService.query(model);
		JqGridData<BestContract> data = new JqGridData<BestContract>(list, params);
		return data;
	}

	/**
	 * 产品信息表,页面
	 */
	@GetMapping("BestProject")
	public String BestProject(HttpSession session, ModelMap m) {
		return basePath + "BestProjectList";
	}

	/**
	 * 产品信息,数据
	 * 
	 */
	@ApiOperation(value = "产品信息数据接口")
	@ResponseBody
	@GetMapping(value = "/BestProjectData")
	public JqGridData<BestProject> BestProjectData(JqGridParam params, BestProject model) {
		PageHelper.startPage((int) params.getPage(), (int) params.getRows());
		Page<BestProject> list = (Page<BestProject>) BestProjectService.query(model);
		JqGridData<BestProject> data = new JqGridData<BestProject>(list, params);
		return data;
	}

	/**
	 * tnet周单量统计报表,页面
	 */
	@GetMapping("TnetOrder")
	public String TnetOrder(HttpSession session, ModelMap m) {
		return basePath + "TnetOrderList";
	}

	/**
	 * tnet周单量统计报表,数据
	 * 
	 */
	@ApiOperation(value = "tnet周单量统计报表数据接口")
	@ResponseBody
	@GetMapping("TnetOrderData")
	public JqGridData<TnetOrder> TnetOrderData(JqGridParam params, TnetOrder model) {
		PageHelper.startPage((int) params.getPage(), (int) params.getRows());
		Page<TnetOrder> list = (Page<TnetOrder>) BestProjectService.query(model);
		JqGridData<TnetOrder> data = new JqGridData<TnetOrder>(list, params);
		return data;
	}

	/**
	 * 优赢日单量表,页面
	 */
	@GetMapping("YouOrder")
	public String YouOrder(HttpSession session, ModelMap m) {
		return basePath + "YouOrderList";
	}

	/**
	 * 优赢日单量表,数据
	 * 
	 */
	@ApiOperation(value = "优赢日单量表数据接口")
	@ResponseBody
	@GetMapping("YouOrderData")
	public JqGridData<YouOrder> YouOrderData(JqGridParam params, YouOrder model) {
		PageHelper.startPage((int) params.getPage(), (int) params.getRows());
		Page<YouOrder> list = (Page<YouOrder>) BestProjectService.query(model);
		JqGridData<YouOrder> data = new JqGridData<YouOrder>(list, params);
		return data;
	}

}
