package cn.smarthse.business.service.elasticsearch.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import cn.smarthse.business.dao.elasticsearch.SysAreaRepository;
import cn.smarthse.business.dao.system.SysAreaStandardMapper;
import cn.smarthse.business.entity.system.SysAreaStandard;
import cn.smarthse.business.model.elasticsearch.SysArea;
import cn.smarthse.business.service.elasticsearch.SysAreaService;

//@Service
public class SysAreaServiceImpl implements SysAreaService {

	@Autowired
	private SysAreaRepository SysAreaRepository;

	@Autowired
	private SysAreaStandardMapper SysAreaStandardMapper;

	@Override
	public Iterable<SysArea> getAll() {
		return SysAreaRepository.findAll();
	}

	@Override
	public Page<SysArea> pageQuery(Integer pageNo, Integer pageSize, SysArea queryParam) {

		NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
		if (queryParam.getId() != null) {
			//matchPhraseQuery 严格匹配
			searchQuery.withQuery(QueryBuilders.matchPhraseQuery("id", queryParam.getId()));
		}
		if (StringUtils.isNotBlank(queryParam.getName())) {
			//matchQuery 部分匹配
			searchQuery.withQuery(QueryBuilders.matchQuery("provinceName", queryParam.getName()));
			searchQuery.withQuery(QueryBuilders.matchQuery("cityName", queryParam.getName()));
			searchQuery.withQuery(QueryBuilders.matchQuery("areaName", queryParam.getName()));
		}
		searchQuery.withPageable(PageRequest.of(pageNo, pageSize)).build();
		return SysAreaRepository.search(searchQuery.build());
	}

	@Override
	public void initData() {
		List<SysAreaStandard> list = SysAreaStandardMapper.selectAll();
		SysArea a = new SysArea();
		SysAreaRepository.saveAll(a.getSysAreaList(list));
	}

}