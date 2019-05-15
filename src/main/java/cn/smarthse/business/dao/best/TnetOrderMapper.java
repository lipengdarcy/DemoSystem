
package cn.smarthse.business.dao.best;

import java.util.List;

import cn.smarthse.business.entity.best.TnetOrder;
import cn.smarthse.framework.generic.GenericDao;

public interface TnetOrderMapper extends GenericDao<TnetOrder> {
	List<TnetOrder> query(TnetOrder model);
}
