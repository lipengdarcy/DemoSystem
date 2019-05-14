package cn.smarthse.config.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//该类为数据源上下文配置，用于切换数据源

public class DynamicDataSourceContextHolder {

	private static final Log log = LogFactory.getLog(DynamicDataSourceContextHolder.class);

	/**
	 * 用于在切换数据源时保证不会被其他线程修改
	 */
	private static Lock lock = new ReentrantLock();

	/**
	 * 用于轮循的计数器
	 */
	private static int counter = 0;

	/**
	 * Maintain variable for every thread, to avoid effect other thread
	 */
	private static final ThreadLocal<String> CONTEXT_HOLDER = ThreadLocal.withInitial(DataSourceKey.master::name);

	/**
	 * All DataSource List
	 */
	public static List<Object> dataSourceKeys = new ArrayList<>();

	/**
	 * The constant slaveDataSourceKeys.
	 */
	public static List<Object> slaveDataSourceKeys = new ArrayList<>();

	/**
	 * To switch DataSource
	 *
	 * @param key
	 *            the key
	 */
	public static void setDataSourceKey(String key) {
		CONTEXT_HOLDER.set(key);
	}

	/**
	 * Use master data source.
	 */
	public static void useMasterDataSource() {
		CONTEXT_HOLDER.set(DataSourceKey.master.name());
	}

	/**
	 * 当使用只读数据源时通过轮循方式选择要使用的数据源
	 */
	public static void useSlaveDataSource() {
		lock.lock();

		try {
			int datasourceKeyIndex = counter % slaveDataSourceKeys.size();
			CONTEXT_HOLDER.set(String.valueOf(slaveDataSourceKeys.get(datasourceKeyIndex)));
			counter++;
		} catch (Exception e) {
			log.error("Switch slave datasource failed, error message is " + e.getMessage());
			useMasterDataSource();
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Get current DataSource
	 *
	 * @return data source key
	 */
	public static String getDataSourceKey() {
		return CONTEXT_HOLDER.get();
	}

	/**
	 * To set DataSource as default
	 */
	public static void clearDataSourceKey() {
		CONTEXT_HOLDER.remove();
	}

	/**
	 * Check if give DataSource is in current DataSource list
	 *
	 * @param key
	 *            the key
	 * @return boolean boolean
	 */
	public static boolean containDataSourceKey(String key) {
		return dataSourceKeys.contains(key);
	}
}