package cn.smarthse.business.service.wechat.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.smarthse.business.entity.WxDepartment;
import cn.smarthse.business.model.wechat.WxDepartmentModel;
import cn.smarthse.business.service.wechat.IWxDepartmentService;
import cn.smarthse.framework.generic.GenericServiceImpl;
import cn.smarthse.framework.util.WeChatUtil;

@Service
public class WxDepartmentServiceImpl extends GenericServiceImpl<WxDepartment> implements IWxDepartmentService {

	@Override
	public int syncData(Integer cid) {
		// 已有数据，则不再同步
		List<WxDepartment> old_list = super.selectList();
		if (!old_list.isEmpty()) {
			return 0;
		}
		String token = WeChatUtil.getAccessToken();
		List<WxDepartmentModel> list = WeChatUtil.getDeptList(token);
		for (WxDepartmentModel a : list) {
			super.insert(a.getEntity());
		}
		return list.size();
	}

}
