package cn.smarthse.framework.thread.learn.advance;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Lock, synchronized都属于Java并发机制的底层基础设施。在实际编程我们应该尽量避免使用以上介绍的较为底层的机制，
 * 
 * 而使用Java类库中提供给我们封装好的较高层次的抽象，如：
 * 
 * BlockingQueue 阻塞队列
 * 
 * Executors 执行器
 * 
 * ThreadPoolExecutor 线程池
 * 
 * ScheduledExecutorService 预定执行
 * 
 * Callable与Future
 * 
 * 并发容器类：
 * 
 * ConcurrentHashMap，这个并发容器是为了取代同步的HashMap；
 * CopyOnWriteArrayList，使用这个类在迭代时进行修改不抛异常； 
 * ConcurrentLinkedQuerue，是一个非阻塞队列；
 * ConcurrentSkipListMap，用于在并发环境下替代SortedMap；
 * ConcurrentSkipSetMap，用于在并发环境下替代SortedSet。
 * 
 * 同步器（Synchronizer）：
 * 
 * CyclicBarrier：它允许线程集等待直至其中预定数目的线程到达某个状态（这个状态叫公共障栅（barrier）），然后可以选择执行一个处理障栅的动作。适用场景：当多个线程都完成某操作，这些线程才能继续执行时，或都完成了某操作后才能执行指定任务时。对CyclicBarrier对象调用await方法即可让相应线程进入barrier状态，等到预定数目的线程都进入了barrier状态后，这些线程就可以继续往下执行了
 * CountDownLatch：允许线程集等待直到计数器减为0。适用场景：当一个或多个线程需要等待直到指定数目的事件发生。举例来说，假如主线程需要等待N个子线程执行完毕才继续执行，就可以使用CountDownLatch来实现，需要用到CountDownLatch的以下方法：
 * Exchanger：允许两个线程在要交换的对象准备好时交换对象。适用场景：当两个线程工作在同一数据结构的两个实例上时，一个向实例中添加数据，另一个从实例中移除数据。
 * Semaphore：允许线程集等待直到被允许继续运行为止。适用场景：限制同一时刻对某一资源并发访问的线程数，初始化Semaphore需要指定许可的数目，线程要访问受限资源时需要获取一个许可，当所有许可都被获取，其他线程就只有等待许可被释放后才能获取。
 * SynchronousQueue：允许一个线程把对象交给另一个线程。适用场景：在没有显式同步的情况下，当两个线程准备好将一个对象从一个线程传递到另一个线程。
 * 
 */
public class BlockingQueueTest {
	private int size = 20;
	private ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(size);

	public static void main(String[] args) {
		BlockingQueueTest test = new BlockingQueueTest();
		Producer producer = test.new Producer();
		Consumer consumer = test.new Consumer();
		producer.start();
		consumer.start();
	}

	class Consumer extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					// 从阻塞队列中取出一个元素
					queue.take();
					System.out.println("Consumer: 队列剩余" + queue.size() + "个元素");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	class Producer extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					// 向阻塞队列中插入一个元素
					queue.put(1);
					System.out.println("Producer: 队列剩余空间：" + (size - queue.size()));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
