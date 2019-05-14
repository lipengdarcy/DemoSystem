package cn.smarthse.business.model.elasticsearch.common;

import lombok.Data;

/**
 * Elasticsearch查询返回对象
 */

@Data
public class ElasticsearchResponseData {
	
	private String title;

	private String content;

}
