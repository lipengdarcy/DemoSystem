/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */

package cn.smarthse.business.dao.system;

import java.util.List;
import java.util.Map;

import cn.smarthse.business.entity.system.SysFile;
import cn.smarthse.framework.generic.GenericDao;

/**
 *
 * 系统文件
 */
public interface SysFileMapper extends GenericDao<SysFile> {

	/**
	 * 根据id批量查询文件
	 * 
	 * @param fileIds
	 * @return
	 */
	List<SysFile> selectByFileIds(Map<String, Object> map);

}
