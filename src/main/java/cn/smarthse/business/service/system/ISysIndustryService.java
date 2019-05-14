/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */

package cn.smarthse.business.service.system;

import java.util.List;
import java.util.Map;

import cn.smarthse.business.entity.system.SysIndustry;
import cn.smarthse.business.model.system.SysIndustryModel;
import cn.smarthse.framework.generic.GenericService;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;

/**
 *
 * 系统参数_行业分类
 */
public interface ISysIndustryService extends GenericService<SysIndustry> {
	
	/**
	 * 行业分类分页查询
	 *
	 * @param queryParam
	 *            查询参数
	 */
	JqGridData<SysIndustryModel> getGridData(JqGridParam param, SysIndustry queryParam);
	/**
	 * 获取行业分类的下级分类
	 * 
	 * @param typeId
	 *            分类id
	 * 
	 * @return 下级分类列表
	 */
	public List<SysIndustry> getChildren(Long typeId);

	/**
	 * 根据行业分类id获取对应的一级分类列表、二级分类列表、三级分类列表
	 *
	 * @param typeId
	 *            分类id
	 */
	public Map getTypeList(Long typeId);
	
	
	/**
	 * 
	 * @Comments:  <获取行业所有名称>
	 * @author BinXu(徐斌) [784514607@qq.com]
	 * @since 2019年3月26日-下午2:46:26
	 * @param typeId
	 * @return
	 */
	public String getFullIndustryName(Long typeId);
	

}
