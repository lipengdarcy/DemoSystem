package cn.smarthse.business.service.best;

import java.util.List;

import cn.smarthse.business.entity.best.BestContract;
import cn.smarthse.business.entity.best.BestProject;
import cn.smarthse.business.entity.best.TnetOrder;
import cn.smarthse.business.entity.best.YouOrder;
import cn.smarthse.framework.generic.GenericService;

/**
 * 产品信息表
 */
public interface IBestProjectService extends GenericService<BestProject> {
	/**
	 * 代理协议,综合查询
	 */
	public List<BestContract> query(BestContract model);

	/**
	 * 产品信息表，包含客户、合同、项目, 综合查询
	 */
	public List<BestProject> query(BestProject model);

	/**
	 * tnet周单量统计报表, 综合查询
	 */
	public List<TnetOrder> query(TnetOrder model);

	/**
	 * 优赢日单量表, 综合查询
	 */
	public List<YouOrder> query(YouOrder model);
}
