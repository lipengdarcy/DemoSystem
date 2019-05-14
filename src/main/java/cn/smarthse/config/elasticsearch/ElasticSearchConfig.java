package cn.smarthse.config.elasticsearch;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * ElasticSearch配置
 * 
 **/
@Configuration
public class ElasticSearchConfig {

	/**
	 * 日志对象
	 */
	protected final Logger logger = LogManager.getLogger(this.getClass());

	@Value("${spring.data.elasticsearch.cluster-name}")
	private String clusterName;

	@Value("${spring.data.elasticsearch.cluster-nodes}")
	private String clusterNodes;

	@Bean
	public TransportClient transportClient() throws UnknownHostException {
		// es集群配置（自定义配置） 连接自己安装的集群名称
		Settings settings = Settings.builder().put("cluster.name", clusterName).build();

		// es集群连接
		TransportAddress node = new TransportAddress(InetAddress.getByName("localhost"), 9300);
		// TransportAddress node1 = new
		// TransportAddress(InetAddress.getByName("localhost"), 9301);
		// TransportAddress node2 = new
		// TransportAddress(InetAddress.getByName("localhost"), 9302);

		PreBuiltTransportClient client = new PreBuiltTransportClient(settings);

		client.addTransportAddress(node);
		// client.addTransportAddress(node1);
		// client.addTransportAddress(node2);
		logger.info("配置 ElasticSearchConfig，地址：" + clusterNodes);
		return client;
	}


	@Bean(initMethod = "init", destroyMethod = "close")
	public ESClientSpringFactory getFactory() {
		logger.info("配置 ElasticSearchConfig: HttpHost");
		return ESClientSpringFactory.build(clusterNodes);
	}

	@Bean
	@Scope("singleton")
	public RestClient getRestClient() {
		logger.info("配置 ElasticSearchConfig: RestClient");
		return getFactory().getClient();
	}

	/**
	 * 封装 RestHighLevelClient,
	 * 
	 * 8.0后就不用TransportClient啦 ，统一用RestHighLevelClient
	 */
	@Bean
	@Scope("singleton")
	public RestHighLevelClient getRHLClient() {
		logger.info("配置 ElasticSearchConfig: RestHighLevelClient");
		return getFactory().getRhlClient();
	}

}
