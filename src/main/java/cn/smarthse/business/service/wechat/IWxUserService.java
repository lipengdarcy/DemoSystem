package cn.smarthse.business.service.wechat;

import java.util.List;

import cn.smarthse.business.entity.WxUser;
import cn.smarthse.business.model.wechat.WxUserModel;
import cn.smarthse.framework.generic.GenericService;

/**
 *
 *
 * 企业微信，用户service
 */
public interface IWxUserService extends GenericService<WxUser> {
	/**
	 * 同步微信用户数据
	 * 
	 * @param cid
	 *            企业id
	 * @return 同步数量
	 */
	public int syncData(Integer cid);
	
	/**
	 * 综合查询
	 */
	public List<WxUserModel> query(WxUserModel model);

}
