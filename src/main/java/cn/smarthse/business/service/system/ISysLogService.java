/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */


package cn.smarthse.business.service.system;

import com.github.pagehelper.PageInfo;

import cn.smarthse.business.entity.system.SysLog;
import cn.smarthse.business.model.system.SysLogModel;
import cn.smarthse.business.model.system.SysLogSearchModel;
import cn.smarthse.framework.generic.GenericService;
import cn.smarthse.framework.model.JqGridParam;


 /**
 * 系统日志Service声明
 *
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8>
 * @author <开发者>
 * @since 2019-02-20 08:45
 */
public interface ISysLogService extends GenericService<SysLog>{
	/**
	 * 
	* @Function: ISysLogService.java
	* @Description: 系统日志表
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: cjy
	* @date: 2019年3月6日 下午3:55:58 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2019年3月6日      cjy           v1.0.0               修改原因
	 */
	PageInfo<SysLogModel> getLogList(JqGridParam param, SysLogSearchModel model);



}
