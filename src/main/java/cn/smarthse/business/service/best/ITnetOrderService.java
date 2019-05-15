
package cn.smarthse.business.service.best;

import java.util.List;

import cn.smarthse.business.entity.best.TnetOrder;
import cn.smarthse.framework.generic.GenericService;

/**
 * tnet周单量统计报表
 */
public interface ITnetOrderService extends GenericService<TnetOrder> {

	/**
	 * 综合查询
	 */
	public List<TnetOrder> query(TnetOrder model);

}
