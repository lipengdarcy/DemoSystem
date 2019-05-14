
package cn.smarthse.business.service.wechat.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.smarthse.business.dao.WxUserMapper;
import cn.smarthse.business.entity.WxUser;
import cn.smarthse.business.model.wechat.WxUserModel;
import cn.smarthse.business.service.wechat.IWxUserService;
import cn.smarthse.framework.generic.GenericServiceImpl;
import cn.smarthse.framework.util.WeChatUtil;

@Service
public class WxUserServiceImpl extends GenericServiceImpl<WxUser> implements IWxUserService {
	
	@Autowired
	WxUserMapper WxUserMapper;

	@Override
	public int syncData(Integer cid) {
		// 已有数据，则不再同步
		List<WxUser> old_list = super.selectList();
		if (!old_list.isEmpty()) {
			return 0;
		}
		String token = WeChatUtil.getAccessToken();
		List<WxUserModel> list = WeChatUtil.getUserList(token, "1");
		for (WxUserModel a : list) {
			super.insert(a.getEntity());
		}
		return list.size();

	}

	@Override
	public List<WxUserModel> query(WxUserModel model) {
		List<WxUserModel> list = WxUserMapper.query(model);
		return list;
	}

}
