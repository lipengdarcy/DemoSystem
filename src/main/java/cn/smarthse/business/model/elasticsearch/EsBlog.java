package cn.smarthse.business.model.elasticsearch;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;
/**
 * 在Elasticsearch 6.X 版本中，不建议使用type，在7.X版本中将会彻底废弃type
 * */
@Document(indexName = "blog", type = "blog")
public @Data class EsBlog implements Serializable {

	private static final long serialVersionUID = 159383211493905568L;

	@Id
	private String id;
	private String title;
	private String summary;
	private String content;

	public EsBlog() {
	}
	
	public EsBlog(String id, String title, String summary) {
		this.id = id;
		this.title = title;
		this.summary = summary;
	}
}