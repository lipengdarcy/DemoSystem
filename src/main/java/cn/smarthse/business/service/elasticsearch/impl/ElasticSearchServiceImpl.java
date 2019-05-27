package cn.smarthse.business.service.elasticsearch.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.smarthse.business.model.elasticsearch.SysArea;
import cn.smarthse.business.model.elasticsearch.common.ElasticsearchResponseData;
import cn.smarthse.business.service.elasticsearch.ElasticSearchService;
import cn.smarthse.framework.util.JsonMapper;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

	static final String INDEX_NAME = "my_index_name";

	RequestOptions option;

	@Autowired
	private RestHighLevelClient restClient;

	@Override
	public Iterable<Object> getAll(String keyword) {
		// this.searchDocument(highlightBuilder, query, from, size);
		return null;
	}

	/**
	 * 保存doc
	 * 
	 * @param id
	 *            文档id
	 * @param documentJson
	 *            文档json
	 * @throws IOException
	 *             IO异常
	 */
	private void saveDocument(String id, String documentJson) throws IOException {
		IndexRequest request = new IndexRequest(INDEX_NAME);
		request.source(documentJson, XContentType.JSON);
		restClient.index(request, option);
	}

	/**
	 * 根据id获取文档
	 * 
	 * @param id
	 *            id
	 * @throws IOException
	 *             IO异常
	 */
	private GetResponse getDocument(String id) throws IOException {
		GetRequest getRequest = new GetRequest(INDEX_NAME, id);
		return restClient.get(getRequest, option);
	}

	/**
	 * 删除文档
	 * 
	 * @param id
	 *            id
	 * @return 结果
	 * @throws IOException
	 *             IO异常
	 */
	private DeleteResponse deleteDocument(String id) throws IOException {
		DeleteRequest request = new DeleteRequest(INDEX_NAME, id);
		return restClient.delete(request, option);
	}

	/**
	 * 更新文档内容
	 * 
	 * @param id
	 *            id
	 * @param contentJson
	 *            json内容
	 * @return 结果
	 * @throws IOException
	 *             IO异常
	 */
	private UpdateResponse updateDocument(String id, String contentJson) throws IOException {
		UpdateRequest updateRequest = new UpdateRequest(INDEX_NAME, id);
		updateRequest.doc(contentJson, XContentType.JSON);
		return restClient.update(updateRequest, option);
	}

	/**
	 * 查询所有文档
	 * 
	 * @param highlightBuilder
	 *            高亮封装
	 * @param query
	 *            查询体
	 * @param from
	 *            开始数
	 * @param size
	 *            查询数
	 * @return 查询结果
	 * @throws IOException
	 *             IO异常
	 */
	private SearchResponse searchDocument(HighlightBuilder highlightBuilder, QueryBuilder query, int from, int size)
			throws IOException {
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		if (highlightBuilder != null) {
			searchSourceBuilder.highlighter(highlightBuilder);
		}
		searchSourceBuilder.query(query);
		searchSourceBuilder.from(from);
		searchSourceBuilder.size(size);
		searchRequest.source(searchSourceBuilder);
		return restClient.search(searchRequest, option);
	}

	/**
	 * 封装高亮查询字段
	 * 
	 * @param fieldName
	 *            字段名
	 * @return 高亮字段体
	 */
	private HighlightBuilder.Field makeHighlightContent(String fieldName) {
		HighlightBuilder.Field highlightContent = new HighlightBuilder.Field(fieldName);
		highlightContent.highlighterType("unified");
		highlightContent.fragmentSize(HighlightBuilder.DEFAULT_FRAGMENT_CHAR_SIZE);
		highlightContent.numOfFragments(HighlightBuilder.DEFAULT_NUMBER_OF_FRAGMENTS);
		return highlightContent;
	}

	/**
	 * 高亮分页搜索
	 * 
	 * @param page
	 *            页码（从0开始）
	 * @param size
	 *            每页数量
	 * @param text
	 *            搜索文本
	 * @param fieldNames
	 *            搜索字段名
	 * @return 搜索结果
	 * @throws IOException
	 *             IO异常
	 */
	public SearchResponse searchHighlight(int page, int size, String text, String... fieldNames) throws IOException {
		int from = page * size;
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		for (String fieldName : fieldNames) {
			highlightBuilder.field(makeHighlightContent(fieldName));
		}
		return searchDocument(highlightBuilder,
				QueryBuilders.multiMatchQuery(text, fieldNames).fuzziness(Fuzziness.AUTO), from, size);
	}

	/**
	 * 高亮碎片转为string
	 * 
	 * @param fragments
	 *            碎片数组
	 * @return 字符串
	 */
	private String fragmentsToString(Text[] fragments) {
		return Arrays.stream(fragments).map(Text::string).collect(Collectors.joining("\n"));
	}

	/**
	 * 搜索结果转为 ElasticsearchResponseData 对象
	 * 
	 * @param searchHit
	 *            搜索结果
	 * @return ElasticsearchResponseData
	 */
	private ElasticsearchResponseData fromSearchHit(SearchHit searchHit) {
		String json = searchHit.getSourceAsString();
		ElasticsearchResponseData contentWord = (ElasticsearchResponseData) JsonMapper.fromJsonString(json,
				ElasticsearchResponseData.class);
		Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
		if (!highlightFields.isEmpty()) {
			HighlightField titleField = highlightFields.get("title");
			if (titleField != null) {
				contentWord.setTitle(fragmentsToString(titleField.fragments()));
			}
			HighlightField contentField = highlightFields.get("content");
			if (contentField != null) {
				contentWord.setContent(fragmentsToString(contentField.fragments()));
			}
		}
		return contentWord;
	}

	/**
	 * 搜索内容
	 * 
	 * @param keyWord
	 *            关键字
	 * @param pageable
	 *            分页信息
	 * @return 结果
	 * @throws IOException
	 *             EX
	 */
	public Page<ElasticsearchResponseData> searchContent(String keyWord, Pageable pageable) throws IOException {
		SearchResponse searchResponse = this.searchHighlight(pageable.getPageNumber(), pageable.getPageSize(), keyWord,
				"title", "content");
		Stream<SearchHit> stream = Arrays.stream(searchResponse.getHits().getHits());

		List<ElasticsearchResponseData> list = stream.map(this::fromSearchHit).collect(Collectors.toList());
		return new PageImpl<ElasticsearchResponseData>(list);
	}

	/**
	 * 批量数据插入,测试使用
	 * 
	 * @param list
	 *            批量插入的数据
	 */
	public void batchAdd(List<IndexRequest> list) {
		BulkRequest bulkRequest = new BulkRequest();
		for (IndexRequest indexRequest : list) {
			bulkRequest.add(indexRequest);
		}
		try {
			restClient.bulk(bulkRequest, option);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<IndexRequest> generateRequests() {
		List<IndexRequest> requests = new ArrayList<>();
		requests.add(generateNewsRequest("中印边防军于拉达克举行会晤 强调维护边境和平", "军事", "2018-01-27T08:34:00Z"));
		requests.add(generateNewsRequest("费德勒收郑泫退赛礼 进决赛战西里奇", "体育", "2018-01-26T14:34:00Z"));
		requests.add(generateNewsRequest("欧文否认拿动手术威胁骑士 兴奋全明星联手詹皇", "体育", "2018-01-26T08:34:00Z"));
		requests.add(generateNewsRequest("皇马官方通告拉莫斯伊斯科伤情 将缺阵西甲关键战", "体育", "2018-01-26T20:34:00Z"));
		return requests;
	}

	public IndexRequest generateNewsRequest(String name, String category, String publishTime) {
		IndexRequest indexRequest = new IndexRequest(INDEX_NAME);
		SysArea a = new SysArea();
		a.setName(name);
		String source = JsonMapper.toJsonString(a);
		indexRequest.source(source, XContentType.JSON);
		return indexRequest;
	}

}