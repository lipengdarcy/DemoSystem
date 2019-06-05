package cn.smarthse.business.service.mongo.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.smarthse.business.collection.system.SystemLog;
import cn.smarthse.business.collection.system.SystemRole;
import cn.smarthse.business.service.mongo.system.SystemLogService;
import cn.smarthse.framework.generic.GenericServiceImpl;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;
import cn.smarthse.framework.util.StringUtil;

/**
 * 系统日志Service实现类
 */
@Service
public class SystemLogServiceImpl extends GenericServiceImpl<SystemLog> implements SystemLogService {

	@Autowired
	MongoTemplate MongoTemplate;

	private Query getQuery(SystemLog dataParam) {
		Query query = new Query();
		Criteria c = new Criteria();
		c.and("isValid").is(true);

		if (dataParam != null) {
			// 姓名
			if (!StringUtil.isEmpty(dataParam.getUser().getRealName()))
				c.and("user.realName").regex(dataParam.getUser().getRealName());
			// 日志类型
			if (dataParam.getLogType() != null)
				c.and("logType").is(dataParam.getLogType());
		}
		query.addCriteria(c);
		// 排序
		query.with(new Sort(Direction.ASC, "_id"));
		return query;
	}

	@Override
	public List<SystemLog> getData(SystemLog dataParam) {
		return MongoTemplate.find(getQuery(dataParam), SystemLog.class);
	}

	@Override
	public JqGridData<SystemLog> getGridData(JqGridParam param, SystemLog dataParam) {
		PageRequest pageRequest = PageRequest.of((int) param.getPage() - 1, (int) param.getRows());
		Query query = getQuery(dataParam);
		long count = MongoTemplate.count(query, SystemRole.class);
		query.with(pageRequest);
		List<SystemLog> list = MongoTemplate.find(query, SystemLog.class);
		Page<SystemLog> page = new PageImpl<SystemLog>(list, pageRequest, count);
		JqGridData<SystemLog> data = new JqGridData<SystemLog>(page);
		return data;
	}

}
