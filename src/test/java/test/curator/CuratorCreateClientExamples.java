package test.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author: WangSaiChao
 * @date: 2018/9/12
 * @description: 创建会话的例子
 */
public class CuratorCreateClientExamples {

	public static void main(String[] args) {

		String connectionString = "127.0.0.1";
		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3, Integer.MAX_VALUE);

		/**
		 * connectionString zk地址
		 * 
		 * retryPolicy 重试策略
		 * 
		 * 默认的sessionTimeoutMs为60000
		 * 
		 * 默认的connectionTimeoutMs为15000
		 */
		CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
		curatorFramework.start();

		/**
		 * connectionString zk地址
		 * 
		 * sessionTimeoutMs 会话超时时间
		 * 
		 * connectionTimeoutMs 连接超时时间
		 * 
		 * retryPolicy 重试策略
		 */
		CuratorFramework curatorFramework1 = CuratorFrameworkFactory.newClient(connectionString, 60000, 15000,
				retryPolicy);
		curatorFramework1.start();

		/**
		 * connectionString zk地址 sessionTimeoutMs 会话超时时间 connectionTimeoutMs 连接超时时间
		 * namespace 每个curatorFramework 可以设置一个独立的命名空间,之后操作都是基于该命名空间，比如操作 /app1/message
		 * 其实操作的是/node1/app1/message retryPolicy 重试策略
		 */
		CuratorFramework curatorFramework2 = CuratorFrameworkFactory.builder().connectString(connectionString)
				.sessionTimeoutMs(60000).connectionTimeoutMs(15000).namespace("/node1").retryPolicy(retryPolicy)
				.build();
		curatorFramework2.start();

	}
}
