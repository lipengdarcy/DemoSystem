package cn.smarthse.business.service.best;

import java.util.List;

import cn.smarthse.business.entity.best.BestContract;
import cn.smarthse.framework.generic.GenericService;

/**
 *
 *
 * 代理协议
 */
public interface IBestContractService extends GenericService<BestContract> {

	/**
	 * 综合查询
	 */
	public List<BestContract> query(BestContract model);

}
