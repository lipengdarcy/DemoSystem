package cn.smarthse.business.dao.best;

import java.util.List;

import cn.smarthse.business.entity.best.YouOrder;
import cn.smarthse.framework.generic.GenericDao;

public interface YouOrderMapper extends GenericDao<YouOrder> {
	List<YouOrder> query(YouOrder model);
}
