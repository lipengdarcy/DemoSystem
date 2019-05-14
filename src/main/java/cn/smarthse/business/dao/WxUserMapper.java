package cn.smarthse.business.dao;

import java.util.List;

import cn.smarthse.business.entity.WxUser;
import cn.smarthse.business.model.wechat.WxUserModel;
import cn.smarthse.framework.generic.GenericDao;

public interface WxUserMapper extends GenericDao<WxUser> {
	
	List<WxUserModel> query(WxUserModel model);
}
