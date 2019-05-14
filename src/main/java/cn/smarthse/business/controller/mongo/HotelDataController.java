package cn.smarthse.business.controller.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.smarthse.backup.entity.hotel.HotelData;
import cn.smarthse.backup.model.hotel.HotelDataModel;
import cn.smarthse.backup.service.hotel.HotelDataService;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;

@Controller
@RequestMapping("/mongo/hotel")
public class HotelDataController {

	@Autowired
	private HotelDataService HotelDataService;

	@RequestMapping()
	public String init(ModelMap m) {
		return "mongo/hotelList";
	}

	@RequestMapping("/getData")
	@ResponseBody
	public JqGridData<HotelData> getData(JqGridParam param, HotelDataModel dataParam) {
		JqGridData<HotelData> list = HotelDataService.queryData(param, dataParam);
		return list;
	}
	
	@RequestMapping("/getMongoData")
	@ResponseBody
	public JqGridData<HotelData> getMongoData(JqGridParam param, HotelDataModel dataParam) {
		JqGridData<HotelData> list = HotelDataService.getMongoGridData(param, dataParam);
		return list;
	}
}
