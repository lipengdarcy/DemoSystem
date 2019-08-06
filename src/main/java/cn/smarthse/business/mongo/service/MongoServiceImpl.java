package cn.smarthse.business.mongo.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.smarthse.business.entity.system.SysAreaStandard;
import cn.smarthse.business.mongo.repository.system.MongoAreaRepository;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;
import cn.smarthse.framework.util.StringUtil;

@Service
public class MongoServiceImpl implements MongoService {

	@Resource
	MongoTemplate mongoTemplate;
	
	@Resource
	MongoAreaRepository MongoAreaDao;

	@Override
	public List<SysAreaStandard> getAllArea() {
		return (List<SysAreaStandard>) MongoAreaDao.findAll();
	}

	/**
	 * 分页查询数据,返回grid格式的数据
	 * 
	 */
	@Override
	public JqGridData<SysAreaStandard> getGridData(JqGridParam param, String name) {
		PageRequest pageRequest = PageRequest.of((int) param.getPage() - 1, (int) param.getRows());
		Query query = new Query();
		if(!StringUtil.isEmpty(name))
			query.addCriteria(Criteria.where("name").regex(name));
		long count = mongoTemplate.count(query, SysAreaStandard.class);
		query.with(pageRequest);
		List<SysAreaStandard> list = mongoTemplate.find(query, SysAreaStandard.class);			
		Page<SysAreaStandard> page =  new PageImpl<SysAreaStandard>(list, pageRequest, count);		
		//Page<SysAreaStandard> page = MongoAreaDao.findAll(pageRequest);
		JqGridData<SysAreaStandard> data = new JqGridData<SysAreaStandard>(page);
		return data;
	}

	@Override
	public void addList(List<SysAreaStandard> list) {
		MongoAreaDao.insert(list);
	}

}
