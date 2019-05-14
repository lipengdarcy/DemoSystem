package cn.smarthse.framework.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * Memcached Cache 定义
 * 
 */
public class MemcachedCache implements Cache {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private MemcachedClient memcachedClient;

	/**
	 * 缓存名字
	 */
	public String name;

	/**
	 * 缓存时效 永久
	 */
	public static final int CACHE_EXP_FOREVER = 0;

	/**
	 * 有效时间
	 */
	private int expiretime = CACHE_EXP_FOREVER;

	/**
	 * 构造方法(默认)
	 */

	public MemcachedCache() {
	}

	/**
	 * 构造方法(带参数)
	 */

	public MemcachedCache(String name, MemcachedClient client) {
		this.name = name;
		this.memcachedClient = client;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Object getNativeCache() {
		return memcachedClient;
	}

	/**
	 * @return the expiretime
	 */
	public int getExpiretime() {
		return expiretime;
	}

	/**
	 * @param expiretime
	 *            the expiretime to set
	 */
	public void setExpiretime(int expiretime) {
		this.expiretime = expiretime;
	}

	@Override
	public ValueWrapper get(Object key) {
		Object object = null;
		try {
			logger.debug("获取缓存:" + key);
			object = memcachedClient.get((String) key);
		} catch (InterruptedException e) {
			logger.warn("获取 Memcached 缓存被中断", e);
			e.printStackTrace();
		} catch (TimeoutException e) {
			logger.warn("获取 Memcached 缓存超时", e);
		} catch (MemcachedException e) {
			logger.warn("获取 Memcached 失败，不晓得什么原因", e);
			e.printStackTrace();
		}
		return (object != null ? new SimpleValueWrapper(object) : null);
	}

	@Override
	public void put(Object key, Object value) {
		if (value == null) {
			return;
		}
		try {
			logger.debug("更新缓存:" + key);
			memcachedClient.set((String) key, expiretime, value);
		} catch (InterruptedException e) {
			logger.warn("更新 Memcached 缓存被中断", e);
			e.printStackTrace();
		} catch (TimeoutException e) {
			logger.warn("更新 Memcached 缓存超时", e);
		} catch (MemcachedException e) {
			logger.warn("更新 Memcached 失败，不晓得什么原因", e);
			e.printStackTrace();
		}
	}

	@Override
	public void evict(Object key) {
		try {
			logger.debug("删除缓存:" + key);
			memcachedClient.delete((String) key);
		} catch (InterruptedException e) {
			logger.warn("删除 Memcached 缓存被中断", e);
			e.printStackTrace();
		} catch (TimeoutException e) {
			logger.warn("删除 Memcached 缓存超时", e);
		} catch (MemcachedException e) {
			logger.warn("删除 Memcached 失败，不晓得什么原因", e);
			e.printStackTrace();
		}
	}

	@Override
	public void clear() {
		try {
			logger.debug("清空缓存");
			memcachedClient.flushAll();
		} catch (InterruptedException e) {
			logger.warn("删除 Memcached 缓存被中断", e);
			e.printStackTrace();
		} catch (TimeoutException e) {
			logger.warn("删除 Memcached 缓存超时", e);
		} catch (MemcachedException e) {
			logger.warn("删除 Memcached 失败，不晓得什么原因", e);
			e.printStackTrace();
		}
	}


	@Deprecated
	@Override
	public ValueWrapper putIfAbsent(Object arg0, Object arg1) {
		return null;
	}

	//@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public <T> T get(Object key, Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

}
