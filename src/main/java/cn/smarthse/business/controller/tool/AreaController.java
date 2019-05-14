package cn.smarthse.business.controller.tool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.smarthse.business.entity.system.SysAreaStandard;
import cn.smarthse.business.model.system.SysAreaCityModel;
import cn.smarthse.business.service.system.AreaService;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;
import cn.smarthse.framework.model.ResponseData;
import cn.smarthse.framework.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 行政区域
 */
@Api(value = "组件：接口", tags = "组件：接口")
@Controller
@RequestMapping("/")
public class AreaController {

	@Autowired
	private AreaService AreaService;

	/**
	 * 行政区域页面
	 */
	@GetMapping(value = "/system/param/area")
	public String area(HttpSession session, ModelMap m) {
		return "common/areaList";
	}

	/**
	 * 行政区域数据
	 */
	@ResponseBody
	@GetMapping(value = "/system/param/area/areaData")
	public JqGridData<SysAreaStandard> areaData(JqGridParam param, SysAreaStandard queryParam) {
		JqGridData<SysAreaStandard> list = AreaService.getGridData(param, queryParam);
		return list;
	}

	@RequestMapping("/area/getCascadeByLevel")
	@ResponseBody
	public ResponseData<List<SysAreaStandard>> getCascadeByLevel(Long areaId, int level) {
		ResponseData<List<SysAreaStandard>> data = new ResponseData<>();
		List<SysAreaStandard> list = null;
		try {
			list = AreaService.getCascadeByLevel(areaId, level);
			data.setCode(1);
		} catch (Exception e) {
			data.setCode(0);
			e.printStackTrace();
		}
		data.setData(list);
		return data;
	}

	@RequestMapping("/area/getArea")
	@ResponseBody
	public ResponseData<SysAreaStandard> getArea(Long id) {
		ResponseData<SysAreaStandard> data = new ResponseData<>();
		SysAreaStandard area = null;
		try {
			area = AreaService.get(id);
			data.setCode(1);
		} catch (Exception e) {
			data.setCode(0);
			e.printStackTrace();
		}
		data.setData(area);
		return data;
	}

	/**
	 * 省市联动查询
	 * 
	 * @param areaId
	 * @param level
	 * @return
	 */
	@RequestMapping("/area/getChildren")
	@ResponseBody
	public ResponseData<List<SysAreaStandard>> getChildren(Long id) {
		ResponseData<List<SysAreaStandard>> data = new ResponseData<>();
		List<SysAreaStandard> list = null;
		try {
			list = AreaService.getChildren(id);
			data.setCode(1);
		} catch (Exception e) {
			data.setCode(0);
			e.printStackTrace();
		}
		data.setData(list);
		return data;
	}

	/**
	 * 根据行政区域id获取对应的省列表、市列表、区列表
	 * 
	 * @param areaId
	 * @return
	 */
	@RequestMapping("/area/getAreaList")
	@ResponseBody
	public ResponseData<Map> getAreaList(Long id) {
		ResponseData<Map> data = new ResponseData<Map>();
		Map map = null;
		try {
			map = AreaService.getAreaList(id);
		} catch (Exception e) {
			data.setCode(0);
			e.printStackTrace();
		}
		data.setData(map);
		return data;
	}

	@ApiOperation(value = "行政区域: 读取所有的城市")
	@RequestMapping(value = "/area/getCitys", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseData<Collection<SysAreaCityModel>> getCitys() {
		ResponseData<Collection<SysAreaCityModel>> data = new ResponseData<>();
		List<SysAreaStandard> list = AreaService.getCitys();
		//
		// 重复的Map
		Map<Long, SysAreaCityModel> duplicationMap = new HashMap<>();
		// 将list转换为cityModel
		list.forEach(city -> {
			if (city.getProvinceId() != null && city.getProvinceId() > 0
					&& StringUtils.isNotEmpty(city.getProvinceName())) {
				// 省编号
				Long duplicationKey = city.getProvinceId();

				SysAreaCityModel cityModel = duplicationMap.get(duplicationKey);
				if (cityModel == null) {
					cityModel = new SysAreaCityModel();
					cityModel.setProvinceId(city.getProvinceId());
					cityModel.setProvinceName(city.getProvinceName());

					List<SysAreaStandard> cityList = new ArrayList<>();
					cityList.add(city);
					cityModel.setCityList(cityList);
				} else {
					cityModel.getCityList().add(city);
				}

				duplicationMap.put(duplicationKey, cityModel);
			}
		});
		// 将Map值转换为List
		Collection<SysAreaCityModel> modelList = duplicationMap.values();
		//
		List<SysAreaCityModel> cityModelList = new ArrayList<>();
		cityModelList.addAll(modelList);
		// 排序
		Collections.sort(cityModelList); // 按年龄排序
		//
		data.setData(cityModelList);
		return data;
	}
}
