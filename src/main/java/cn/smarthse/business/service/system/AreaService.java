package cn.smarthse.business.service.system;

import java.util.List;
import java.util.Map;

import cn.smarthse.business.entity.system.SysAreaStandard;
import cn.smarthse.framework.generic.GenericService;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;

public interface AreaService extends GenericService<SysAreaStandard> {

	/**
	 * 行政区域分页查询
	 *
	 * @param queryParam
	 *            查询参数
	 */
	JqGridData<SysAreaStandard> getGridData(JqGridParam param, SysAreaStandard queryParam);

	/**
	 * 获取行政区域的下级单位
	 * 
	 * @param sysareaId
	 *            行政区域id
	 * 
	 * @return 下级单位列表
	 */
	public List<SysAreaStandard> getChildren(Long sysareaId);

	/**
	 * 根据行政区域id获取对应的省列表、市列表、区列表
	 *
	 * @param areaId
	 */
	public Map getAreaList(Long areaId);

	/**
	 * 级联根据level获得区域
	 * 
	 * @param areaId
	 * @param level
	 * @return
	 */
	List<SysAreaStandard> getCascadeByLevel(Long areaId, int level);

	/**
	 * 省市区级联接口
	 * 
	 * @param pid
	 * @return
	 */
	List<SysAreaStandard> getSysAreaListByPid(Long pid);

	/**
	 * 获取对象by id
	 * 
	 * @param id
	 * @return
	 */
	SysAreaStandard get(Long id);

	/**
	 * 获取省份
	 */
	List<SysAreaStandard> getProvince();

	/**
	 * 获取所有的市
	 * 
	 * @Comments: <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2019年3月18日-上午9:54:00
	 * @return
	 */
	public List<SysAreaStandard> getCitys();

	/**
	 * 
	 * @Comments: <获取区域所有名称>
	 * @author BinXu(徐斌) [784514607@qq.com]
	 * @since 2019年3月26日-下午2:46:26
	 * @param typeId
	 * @return
	 */
	public String getFullName(Long areaId);


	/**
	 * @Comments:  <获取 区域详细信息  省 市 区>
	 * @author zhoulj(周利军) [1217102780@qq.com]
	 * @since 2019/4/9-16:55
	 * @param areaId
	 * @param splitStr
	 * @return java.lang.String
	*/
	public String getFullName2(Long areaId, String splitStr);
	
	public List<SysAreaStandard> getProvinces();
	
	
	/**
	 * 根据省份名称获取id
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author  XiaoYi（肖奕) xy@smarthse.cn
	 * @since 2019年4月12日-上午9:33:54
	 * @param shortName
	 * @return
	 */
	Long getProvinceAreaIdByName(String name);
}
