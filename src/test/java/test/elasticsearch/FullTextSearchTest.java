package test.elasticsearch;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetAction;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

/**
 * 全文检索测试类
 */
public class FullTextSearchTest {

	public static void main(String[] args) throws Exception {
		test();
		
	}

	public static void test() throws IOException {
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("localhost", 9200, "http")));

		// darcyjob: 索引; 1: 文档ID
		GetRequest getRequest = new GetRequest("darcyjob", "5c6a15d71cadb4186c303a1d3b1dae");
		RequestOptions options = RequestOptions.DEFAULT;

		// ===============================可选参数start====================================
		// 禁用_source检索，默认为启用
		getRequest.fetchSourceContext(new FetchSourceContext(true));

		// 配置指定stored_fields的检索（要求字段在映射中单独存储）
		getRequest.storedFields("content");
		GetResponse getResponse = client.get(getRequest, options);
		// 检索message 存储字段（要求将字段分开存储在映射中）
		//String message = getResponse.getField("content").getValue();
		System.out.println(getResponse);

		getRequest.routing("routing");// 设置routing值
		getRequest.preference("preference");// 设置preference值
		getRequest.realtime(false);// 设置realtime为false，默认是true
		getRequest.refresh(true);// 在检索文档之前执行刷新（默认为false）
		getRequest.version(2);// 设置版本
		getRequest.versionType(VersionType.EXTERNAL);// 设置版本类型
		// ===============================可选参数end====================================

		// 同步执行
		GetResponse getResponse1 = client.get(getRequest, options);

		// 异步执行
		// GetResponse 的典型监听器如下所示：
		// 异步方法不会阻塞并立即返回。
		ActionListener<GetResponse> listener = new ActionListener<GetResponse>() {
			@Override
			public void onResponse(GetResponse getResponse) {
				// 执行成功时调用。 Response以参数方式提供
			}

			@Override
			public void onFailure(Exception e) {
				// 在失败的情况下调用。 引发的异常以参数方式提供
			}
		};
		// 异步执行获取索引请求需要将GetRequest 实例和ActionListener实例传递给异步方法：
		client.getAsync(getRequest, options, listener);

		// Get Response
		// 返回的GetResponse允许检索请求的文档及其元数据和最终存储的字段。
		String index = getResponse.getIndex();
		String type = getResponse.getType();
		String id = getResponse.getId();
		if (getResponse.isExists()) {
			long version = getResponse.getVersion();
			String sourceAsString = getResponse.getSourceAsString();// 检索文档(String形式)
			Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();// 检索文档(Map<String, Object>形式)
			byte[] sourceAsBytes = getResponse.getSourceAsBytes();// 检索文档（byte[]形式）
		} else {
			/*
			 * 处理找不到文档的情况。 请注意，尽管返回404状态码， 但返回的是有效的GetResponse，而不是抛出的异常。
			 * 此类Response不包含任何源文档，并且其isExists方法返回false。
			 */
		}

		// 当针对不存在的索引执行获取请求时，响应404状态码，将引发ElasticsearchException，需要按如下方式处理：
		GetRequest request = new GetRequest("does_not_exist", "1");
		try {
			GetResponse getResponse2 = client.get(request, options);
		} catch (ElasticsearchException e) {
			if (e.status() == RestStatus.NOT_FOUND) {
				// 处理因为索引不存在而抛出的异常情况
			}
		}

		// 如果请求了特定的文档版本，并且现有文档具有不同的版本号，则会引发版本冲突：
		try {
			GetRequest request1 = new GetRequest("posts", "1").version(2);
			GetResponse getResponse3 = client.get(request, options);
		} catch (ElasticsearchException exception) {
			if (exception.status() == RestStatus.CONFLICT) {
				// 引发的异常表示返回了版本冲突错误
			}
		}

		client.close();
	}
	
	public static void fullTextSearch() throws IOException {
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("localhost", 9200, "http")));

		// darcyjob: 索引; 1: 文档ID
		MultiGetRequest getRequest = new MultiGetRequest();
		RequestOptions options = RequestOptions.DEFAULT;
		
		

		client.close();
	}

}
