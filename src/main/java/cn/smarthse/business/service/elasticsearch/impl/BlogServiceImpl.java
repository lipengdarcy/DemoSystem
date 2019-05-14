package cn.smarthse.business.service.elasticsearch.impl;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import cn.smarthse.business.dao.elasticsearch.BlogRepository;
import cn.smarthse.business.model.elasticsearch.EsBlog;
import cn.smarthse.business.service.elasticsearch.BlogService;

//@Service
public class BlogServiceImpl implements BlogService {

	//@Autowired
	private BlogRepository BlogRepository;

	@Override
	public long count() {
		return 0;
		//return BlogRepository.count();
	}

	@Override
	public EsBlog save(EsBlog record) {
		return null;
		//return BlogRepository.save(record);
	}

	@Override
	public void delete(EsBlog record) {
		//BlogRepository.delete(record);
	}

	@Override
	public Iterable<EsBlog> getAll() {
		return null;
		//return BlogRepository.findAll();
	}

	@Override
	public Page<EsBlog> pageQuery(Integer pageNo, Integer pageSize, EsBlog queryParam) {
		NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
		if(StringUtils.isNotBlank(queryParam.getId())) {
			searchQuery.withQuery(QueryBuilders.matchPhraseQuery("id", queryParam.getId()));
		}
		if(StringUtils.isNotBlank(queryParam.getTitle())) {
			searchQuery.withQuery(QueryBuilders.matchPhraseQuery("title", queryParam.getTitle()));
		}
		if(StringUtils.isNotBlank(queryParam.getSummary())) {
			searchQuery.withQuery(QueryBuilders.matchPhraseQuery("summary", queryParam.getSummary()));
		}				
		searchQuery.withPageable(PageRequest.of(pageNo, pageSize)).build();
		return null;
		//return BlogRepository.search(searchQuery.build());
	}

	@Override
	public void deleteAll() {
		//BlogRepository.deleteAll();	
	}

}