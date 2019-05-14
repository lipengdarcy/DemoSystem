package cn.smarthse.business.dao.elasticsearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import cn.smarthse.business.model.elasticsearch.EsBlog;

public interface BlogRepository extends ElasticsearchRepository<EsBlog, String> {

	/**
	 * 分页查询博客去重
	 * 
	 * @param title
	 * @param summary
	 * @param content
	 * @param pageable
	 * @return Page<EsBlog>
	 */
	Page<EsBlog> findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining(String title, String summary,
			String content, Pageable pageable);
}
