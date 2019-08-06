package cn.smarthse.business.mongo.service.system.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.smarthse.business.mongo.collection.system.SystemRole;
import cn.smarthse.business.mongo.repository.system.SystemRoleRepository;
import cn.smarthse.business.mongo.service.system.SystemRoleService;
import cn.smarthse.framework.generic.GenericServiceImpl;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;
import cn.smarthse.framework.util.StringUtil;

/**
 * 系统角色Service实现类
 */
@Service
public class SystemRoleServiceImpl extends GenericServiceImpl<SystemRole> implements SystemRoleService {

	@Autowired
	SystemRoleRepository SystemRoleRepository;

	@Autowired
	MongoTemplate MongoTemplate;

	private Query getQuery(SystemRole dataParam) {
		Query query = new Query();
		Criteria c = new Criteria();
		c.and("isValid").is(true);
		Criteria c2 = new Criteria();
		if (dataParam != null) {
			// 角色名称
			if (!StringUtil.isEmpty(dataParam.getRoleName()))
				c.and("roleName").regex(dataParam.getRoleName());
			// 角色编码
			if (!StringUtil.isEmpty(dataParam.getRoleCode()))
				c2.and("roleCode").regex(dataParam.getRoleCode());
		}

		c.orOperator(c2);
		query.addCriteria(c);
		// 排序
		query.with(new Sort(Direction.ASC, "_id"));
		return query;
	}

	@Override
	public List<SystemRole> getData(SystemRole dataParam) {
		return MongoTemplate.find(getQuery(dataParam), SystemRole.class);
	}

	@Override
	public JqGridData<SystemRole> getGridData(JqGridParam param, SystemRole dataParam) {
		PageRequest pageRequest = PageRequest.of((int) param.getPage() - 1, (int) param.getRows());
		Query query = getQuery(dataParam);
		long count = MongoTemplate.count(query, SystemRole.class);
		query.with(pageRequest);
		List<SystemRole> list = MongoTemplate.find(query, SystemRole.class);
		Page<SystemRole> page = new PageImpl<SystemRole>(list, pageRequest, count);
		JqGridData<SystemRole> data = new JqGridData<SystemRole>(page);
		return data;
	}

	@Override
	public SystemRole get(String keyword) {
		// step 1 : 根据角色名称
		SystemRole queryParam = new SystemRole();
		queryParam.setRoleName(keyword);
		// 创建匹配器，即如何使用查询条件
		ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.EXACT)
				.withIgnoreCase(true).withMatcher("roleName", ExampleMatcher.GenericPropertyMatchers.exact());
		// 创建实例
		Example<SystemRole> example = Example.of(queryParam, matcher);
		Optional<SystemRole> role = SystemRoleRepository.findOne(example);
		if (role.isPresent())
			return role.get();

		// step 2 : 根据角色编码
		queryParam.setRoleCode(keyword);
		matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.EXACT).withIgnoreCase(true)
				.withMatcher("roleCode", ExampleMatcher.GenericPropertyMatchers.exact());
		example = Example.of(queryParam, matcher);
		role = SystemRoleRepository.findOne(example);
		if (role.isPresent())
			return role.get();
		return null;
	}

}
