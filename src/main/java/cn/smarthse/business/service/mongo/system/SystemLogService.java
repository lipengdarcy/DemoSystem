package cn.smarthse.business.service.mongo.system;

import java.util.List;

import cn.smarthse.business.collection.system.SystemLog;
import cn.smarthse.framework.generic.GenericService;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;

/**
 * 系统日志Service接口定义
 *
 */
public interface SystemLogService extends GenericService<SystemLog> {

	/**
	 * 综合查询
	 * 
	 */
	public List<SystemLog> getData(SystemLog data);

	/**
	 * 分页查询数据,返回grid格式的数据
	 * 
	 */
	public JqGridData<SystemLog> getGridData(JqGridParam param, SystemLog dataParam);

}
