package cn.smarthse.business.dao.best;

import java.util.List;

import cn.smarthse.business.entity.best.BestContract;
import cn.smarthse.framework.generic.GenericDao;

public interface BestContractMapper extends GenericDao<BestContract> {
	List<BestContract> query(BestContract model);

}
