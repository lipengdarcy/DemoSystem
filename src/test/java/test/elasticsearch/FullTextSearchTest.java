package test.elasticsearch;

import java.net.InetAddress;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * 全文检索测试类
 */
public class FullTextSearchTest {

	@SuppressWarnings({ "resource", "unchecked" })
	public static void main(String[] args) throws Exception {
		Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();

		TransportClient client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

		SearchResponse searchResponse = client.prepareSearch("sys_area")
				.setQuery(QueryBuilders.matchQuery("name", "杭州")).get();

		for (SearchHit searchHit : searchResponse.getHits().getHits()) {
			System.out.println(searchHit.getSourceAsString());
		}

		System.out.println("====================================================");

		searchResponse = client.prepareSearch("sys_area")
				.setQuery(QueryBuilders.multiMatchQuery("杭州", "street_anme", "name")).get();

		for (SearchHit searchHit : searchResponse.getHits().getHits()) {
			System.out.println(searchHit.getSourceAsString());
		}

		System.out.println("====================================================");

		searchResponse = client.prepareSearch("sys_area")
				.setQuery(QueryBuilders.termQuery("name.raw", "宝马318")).get();

		for (SearchHit searchHit : searchResponse.getHits().getHits()) {
			System.out.println(searchHit.getSourceAsString());
		}

		System.out.println("====================================================");

		searchResponse = client.prepareSearch("sys_area")
				.setQuery(QueryBuilders.prefixQuery("name", "宝")).get();

		for (SearchHit searchHit : searchResponse.getHits().getHits()) {
			System.out.println(searchHit.getSourceAsString());
		}

		client.close();
	}

}
