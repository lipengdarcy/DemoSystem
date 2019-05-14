package cn.smarthse.business.service.elasticsearch;

public interface ElasticSearchService {

	/**
	 * 全文检索
	 * 
	 * @param keyword
	 *            关键字，单个或多个，多个以空格分隔
	 */
	Iterable<Object> getAll(String keyword);

}
