package cn.smarthse.business.dao.best;

import java.util.List;

import cn.smarthse.business.entity.best.BestProject;
import cn.smarthse.framework.generic.GenericDao;

public interface BestProjectMapper extends GenericDao<BestProject> {
	List<BestProject> query(BestProject model);
}
