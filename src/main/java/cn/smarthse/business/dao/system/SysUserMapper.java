/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */

package cn.smarthse.business.dao.system;

import java.util.List;

import cn.smarthse.business.entity.system.SysUser;
import cn.smarthse.business.model.system.SysUserSeachModel;
import cn.smarthse.framework.generic.GenericDao;

 /**
 * 用户Mapper

 */
public interface SysUserMapper extends GenericDao<SysUser> {

	/**
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2019年2月19日-下午2:12:58
	 * @param searchModel	搜索条件
	 * @return
	 */
	List<SysUser> queryBySearchModel(SysUserSeachModel searchModel);


}
