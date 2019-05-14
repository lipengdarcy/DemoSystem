package cn.smarthse.business.model.elasticsearch;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * 在Elasticsearch 6.X 版本中，不建议使用type，在7.X版本中将会彻底废弃type
 */
@Data
@Document(indexName = "commodity")
public class Commodity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String skuId;

	private String name;

	private String category;

	private Integer price;

	private String brand;

	private Integer stock;

}