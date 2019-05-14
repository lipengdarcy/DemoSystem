package cn.smarthse.config;

import net.sf.ehcache.config.CacheConfiguration;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.transcoders.SerializingTranscoder;
import net.sf.ehcache.CacheManager;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import cn.smarthse.config.properties.CacheProperties;

/**
 * Ehcache 缓存配置，shiro配置需要用到
 * */
@Configuration
@EnableCaching
@EnableConfigurationProperties(CacheProperties.class)
public class EhcacheConfig{
	
	/**
	 * 日志对象
	 */
	protected final Logger logger = LogManager.getLogger(this.getClass());
	
	// 缓存配置文件,来源:application.yml
	@Autowired
	private CacheProperties properties;
	
	
	@Bean(name="HSECacheManager")
	public CacheManager cacheManager() {
		logger.info("CacheManager 创建");
		//默认cache
		CacheConfiguration defaultCacheConfiguration = new CacheConfiguration();
		defaultCacheConfiguration.setMemoryStoreEvictionPolicy("LRU");
		defaultCacheConfiguration.setMaxEntriesLocalHeap(1000);
		
		//shiro cache
		CacheConfiguration shiroCacheConfig = new CacheConfiguration();
		shiroCacheConfig.setName("tp-activeSessionCache");
		shiroCacheConfig.setMemoryStoreEvictionPolicy("LRU");
		shiroCacheConfig.setMaxEntriesLocalHeap(1000);
		//密码错误缓存
		CacheConfiguration passwordRetryCache = new CacheConfiguration();
		passwordRetryCache.setName("tp-passwordRetryCache");
		passwordRetryCache.setMemoryStoreEvictionPolicy("LRU");
		passwordRetryCache.setMaxEntriesLocalHeap(1000);
		passwordRetryCache.setTimeToIdleSeconds(600);

		net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
		config.setDefaultCacheConfiguration(defaultCacheConfiguration);
		config.addCache(shiroCacheConfig);
		config.addCache(passwordRetryCache);
		//不要自动更新
		//config.setUpdateCheck(false);
		return net.sf.ehcache.CacheManager.newInstance(config);
	}
	
//	@Bean("memcachedClient")
//	public MemcachedClient getMemcachedClientBuilder() throws IOException {
//		logger.info("init Memcache Configuration host:{} port:{}", properties.getMemcachedServer1Host(),
//				properties.getMemcachedServer1Port());
//		InetSocketAddress inetSocketAddress = new InetSocketAddress(properties.getMemcachedServer1Host(), properties.getMemcachedServer1Port());
//		// InetSocketAddress inetSocketAddress2 = new InetSocketAddress(
//		// memcached_server2_host, memcached_server2_port);
//		//
//		XMemcachedClientBuilder xcb = new XMemcachedClientBuilder(Lists.newArrayList(inetSocketAddress));
//		xcb.setFailureMode(properties.isMemcachedFailureMode());
//		xcb.setConnectionPoolSize(properties.getMemcachedConnectionPoolSize());
//		xcb.setCommandFactory(new BinaryCommandFactory());
//		xcb.setSessionLocator(new KetamaMemcachedSessionLocator());
//		xcb.setTranscoder(new SerializingTranscoder());
//		// xcb.setBufferAllocator(new SimpleBufferAllocator());
//		return xcb.build();
//	}

}