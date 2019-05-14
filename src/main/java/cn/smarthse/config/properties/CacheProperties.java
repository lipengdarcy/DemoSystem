/**
 * 
 */
package cn.smarthse.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 《缓存配置》
 * 
 * 
 * @Project:  platform-im
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8> 
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2018年8月24日-上午11:05:18
 */
@ConfigurationProperties("cache")
public @Data class CacheProperties {

//	#=====================================#
//	#===== Memcached/Echched sttings =====#
//	#=====================================#
//	#缓存设置
//	ehcache.configFile=ehcache/ehcache.xml
//	#server1    
//	memcached.server1.host=127.0.0.1
//	memcached.server1.port=11211  
//	memcached.server1.weight=1  
	//# 客户端连接数（建议连接在0-30之间，以免过多产生资源浪费）
	private int memcachedConnectionPoolSize;
	//# 是否自动切换Service（当节点失效时） 
	private boolean memcachedFailureMode;
	private String memcachedServer1Host;
	private int memcachedServer1Port;
	private int memcachedServer1Weight;
}
