package cn.smarthse.business.mongo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.smarthse.backup.entity.hotel.HotelData;
import cn.smarthse.backup.model.hotel.HotelDataModel;
import cn.smarthse.backup.service.hotel.HotelDataService;
import cn.smarthse.business.mongo.service.BatchInsertTestService;
import cn.smarthse.framework.model.DataTableData;
import cn.smarthse.framework.model.DataTableParam;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;

@Controller
@RequestMapping("/mongo/hotel")
public class HotelDataController {

	@Autowired
	private HotelDataService HotelDataService;

	@Autowired
	private BatchInsertTestService BatchInsertTestService;

	@RequestMapping()
	public String index(ModelMap m) {
		// return "mongo/hotelGridList"; //jqgrid页面
		return "mongo/hotelList";// datatable页面
	}

	@RequestMapping("/init")
	public void init() {
		BatchInsertTestService.batchInsert();
	}

	/**
	 * mysql版本的jqgrid数据格式
	 */
	@RequestMapping("/getData")
	@ResponseBody
	public JqGridData<HotelData> getData(JqGridParam param, HotelDataModel dataParam) {
		JqGridData<HotelData> list = HotelDataService.queryData(param, dataParam);
		return list;
	}

	/**
	 * mongodb版本的jqgrid数据格式
	 */
	@RequestMapping("/getMongoGrid")
	@ResponseBody
	public JqGridData<HotelData> getMongoGrid(JqGridParam param, HotelDataModel dataParam) {
		// 页码超过10000就不予理睬，事实上也不该有这种需求
		if (param.getPage() > 10000) {
			param.setPage(10000);
		}
		JqGridData<HotelData> list = HotelDataService.getMongoGridData(param, dataParam);
		return list;
	}

	/**
	 * mongodb版本的datatable数据格式。没有跳页的选项，解决了mongodb分页的性能问题
	 */
	@RequestMapping("/getMongo")
	@ResponseBody
	public DataTableData<HotelData> getMongo(DataTableParam param, HotelDataModel dataParam) {
		// 页码超过10000就不予理睬，事实上也不该有这种需求
		if (param.getStart() / param.getLength() > 10000) {
			param.setStart(10000 * param.getLength());
		}
		DataTableData<HotelData> list = HotelDataService.getMongoData(param, dataParam);
		return list;
	}
}
