package cn.smarthse.business.service.wechat;

import cn.smarthse.business.entity.WxDepartment;
import cn.smarthse.framework.generic.GenericService;

/**
 *
 * 企业微信，部门数据service
 */
public interface IWxDepartmentService extends GenericService<WxDepartment> {

	/**
	 * 同步微信部门数据
	 * 
	 * @param cid
	 *            企业id
	 * @return 同步数量
	 */
	public int syncData(Integer cid);

}
