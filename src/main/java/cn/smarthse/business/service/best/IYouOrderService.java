
package cn.smarthse.business.service.best;

import java.util.List;

import cn.smarthse.business.entity.best.YouOrder;
import cn.smarthse.framework.generic.GenericService;

/**
 *
 * 优赢日单量表
 */
public interface IYouOrderService extends GenericService<YouOrder> {

	/**
	 * 综合查询
	 */
	public List<YouOrder> query(YouOrder model);

}
