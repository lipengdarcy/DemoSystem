
package cn.smarthse.business.service.best.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.smarthse.business.dao.best.BestContractMapper;
import cn.smarthse.business.dao.best.BestProjectMapper;
import cn.smarthse.business.dao.best.TnetOrderMapper;
import cn.smarthse.business.dao.best.YouOrderMapper;
import cn.smarthse.business.entity.best.BestContract;
import cn.smarthse.business.entity.best.BestProject;
import cn.smarthse.business.entity.best.TnetOrder;
import cn.smarthse.business.entity.best.YouOrder;
import cn.smarthse.business.service.best.IBestProjectService;
import cn.smarthse.framework.generic.GenericServiceImpl;

@Service
public class BestProjectServiceImpl extends GenericServiceImpl<BestProject> implements IBestProjectService {

	@Autowired
	BestContractMapper BestContractMapper;

	@Autowired
	BestProjectMapper BestProjectMapper;

	@Autowired
	TnetOrderMapper TnetOrderMapper;

	@Autowired
	YouOrderMapper YouOrderMapper;

	@Override
	public List<BestContract> query(BestContract model) {
		List<BestContract> list = BestContractMapper.query(model);
		return list;
	}

	@Override
	public List<BestProject> query(BestProject model) {
		List<BestProject> list = BestProjectMapper.query(model);
		return list;
	}

	@Override
	public List<TnetOrder> query(TnetOrder model) {
		List<TnetOrder> list = TnetOrderMapper.query(model);
		return list;
	}

	@Override
	public List<YouOrder> query(YouOrder model) {
		List<YouOrder> list = YouOrderMapper.query(model);
		return list;
	}

}
