package test.thread.learn;

import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 **/
public class t0_TheadCommunicate {

	private final static int threadCount = 200;

	public static void main(String[] arg) {
		thread_sync();
	}

	/**
	 * 多线程同步
	 */
	public static void thread_sync() {
		Goods g = new Goods();
		new Thread(new Producer(g)).start();
		new Thread(new Consumer(g)).start();
	}

}

class Goods {
	private String name;
	private String price;
	private Boolean isimpty = Boolean.TRUE;// 内存区为空！

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String sex) {
		this.price = sex;
	}

	public void setGoods(String name, String sex) {
		synchronized (this) {
			// 生产的产品不为空，还没被消费，就不生产，放弃监视器进入等待
			while (isimpty.equals(Boolean.FALSE)) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.name = name;// 为空的话生产者创造！
			this.price = sex;
			isimpty = Boolean.FALSE;// 创造结束后修改属性！
			System.out.println("生产 +++ 产品：" + getName() + ",  " + "价格：" + getPrice());
			this.notifyAll();
		}
	}

	public void getGoods() {
		synchronized (this) {
			// 生产的产品为空，没有产品可以被消费，就不消费，放弃监视器进入等待
			while (isimpty.equals(Boolean.TRUE)) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("消费 --- 产品：" + getName() + ",  " + "价格：" + getPrice());
			isimpty = Boolean.TRUE;
			this.notifyAll();
		}
	}
}

class Producer implements Runnable {
	private Goods pgs;

	public Producer(Goods p) {
		super();
		this.pgs = p;
	}

	@Override
	public void run() {
		for (int i = 0; i < 12; i++) {
			pgs.setGoods("产品编号为：" + i, i + 10 + ".00");

		}
	}
}

class Consumer implements Runnable {
	private Goods cgs;

	public Consumer(Goods p) {
		super();
		this.cgs = p;
	}

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			cgs.getGoods();
		}
	}
}