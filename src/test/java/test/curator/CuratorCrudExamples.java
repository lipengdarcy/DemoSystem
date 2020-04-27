package test.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;

/**
 * @author: WangSaiChao
 * @date: 2018/9/12
 * @description: 节点操作例子
 */
public class CuratorCrudExamples {

	public static void main(String[] args) throws Exception {

		String connectionString = "127.0.0.1:2181";
		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3, Integer.MAX_VALUE);
		CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
		curatorFramework.start();

		// =========================创建节点=============================

		/**
		 * 创建一个 允许所有人访问的 持久节点
		 */
		curatorFramework.create().creatingParentsIfNeeded()// 递归创建,如果没有父节点,自动创建父节点
				.withMode(CreateMode.PERSISTENT)// 节点类型,持久节点
				.withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)// 设置ACL,和原生API相同
				.forPath("/node10/child_01", "123456".getBytes());

		/**
		 * 创建一个容器节点
		 */
		curatorFramework.create().creatingParentContainersIfNeeded()// 递归创建，如果没有父节点,自动创建父节点
				.withMode(CreateMode.CONTAINER)// 节点类型,容器节点，当最后一个子节点删除的时候，服务器会在未来的一段时间将该容器节点删除(不是立刻删除)
				.withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)// 设置ACL,和原生API相同
				.forPath("/node11", "123".getBytes());

		/**
		 * 创建一个定时节点 临时节点存在时间为3秒 ACL为 digest:wangsaichao:123456:cdrwa
		 */
		curatorFramework.create().withTtl(3000)// 设置临时节点的有效期，单位是毫秒
				.creatingParentContainersIfNeeded()// 递归创建，如果没有父节点,自动创建父节点
				.withMode(CreateMode.PERSISTENT_SEQUENTIAL_WITH_TTL)// 节点类型,定时节点，在一定的时间内,如果没有操作该节点,并且该节点没有子节点,那么服务器将自动删除该节点
				.withACL(Collections.singletonList(
						new ACL(ZooDefs.Perms.ALL, new Id("digest", "wangsaichao:G2RdrM8e0u0f1vNCj/TI99ebRMw="))))// ACL
				.forPath("/node12", "123".getBytes());

		// =========================获取节点=============================

		/**
		 * 获取节点 /node10/child_01 数据 和stat信息
		 */
		Stat node10Stat = new Stat();
		byte[] node10 = curatorFramework.getData().storingStatIn(node10Stat)// 获取stat信息存储到stat对象
				.forPath("/node10/child_01");
		System.out.println("=====>该节点信息为：" + new String(node10));
		System.out.println("=====>该节点的数据版本号为：" + node10Stat.getVersion());

		/**
		 * 获取节点信息 并且留下 Watcher事件 该Watcher只能触发一次
		 */
		byte[] bytes = curatorFramework.getData().usingWatcher(new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				System.out.println("=====>wathcer触发了。。。。");
				System.out.println(event);
			}
		}).forPath("/node10/child_01");
		System.out.println("=====>获取到的节点数据为：" + new String(bytes));
		Thread.sleep(1000000);

		// =========================设置节点=============================

		Stat stat = curatorFramework.setData().withVersion(-1).forPath("/node10/child_01", "I love you".getBytes());
		System.out.println("=====>修改之后的版本为：" + stat.getVersion());

		// =========================删除节点=============================

		/**
		 * 删除node节点 不递归 如果有子节点,将报异常
		 */
		Void aVoid = curatorFramework.delete().forPath("/node10");
		System.out.println("=====>" + aVoid);

		/**
		 * 递归删除,如果有子节点 先删除子节点
		 */
		curatorFramework.delete().deletingChildrenIfNeeded().forPath("/node10");

		curatorFramework.close();

		/**
		 * 删除
		 */
		curatorFramework.delete().guaranteed().forPath("/node11");

		// =========================判断节点是否存在=============================

		/**
		 * CuratorFramework类有一个判断节点是否存在的接口checkExists()，该接口返回一个org.apache.zookeeper.data.Stat对象，对象中有一个ephemeralOwner属性。
		 * 如果该节点是持久化节点，ephemeralOwner的值为0 如果该节点是临时节点，ephemeralOwner的值大于0
		 */

		Stat existsNodeStat = curatorFramework.checkExists().forPath("/node10");
		if (existsNodeStat == null) {
			System.out.println("=====>节点不存在");
		}
		if (existsNodeStat.getEphemeralOwner() > 0) {
			System.out.println("=====>临时节点");
		} else {
			System.out.println("=====>持久节点");
		}

	}
}
