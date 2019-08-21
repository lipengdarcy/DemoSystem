package cn.smarthse.framework.thread;

/**
 * 该方法用来在jvm中增加一个关闭的钩子。当程序正常退出,系统调用
 * System.exit方法或虚拟机被关闭时才会执行添加的shutdownHook线程。
 * 其中shutdownHook是一个已初始化但并不有启动的线程，当jvm关闭的时候，
 * 会执行系统中已经设置的所有通过方法addShutdownHook添加的钩子，
 * 当系统执行完这些钩子后，jvm才会关闭。
 * 所以可通过这些钩子在jvm关闭的时候进行内存清理、资源回收等工作。
 */
public class AddShutdownHookTest {
	public static void main(String[] args) {
		Object o = new Object() {
			@Override
			protected void finalize() throws Throwable {
				// 一旦垃圾收集器准备好释放对象占用的存储空间，它首先调用finalize()
				System.out.println("running finalize......");
			}
		};
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("running shutdown hook....");
			}
		});

		o = null;
		System.gc();

		System.out.println("Calling system exit");
		System.exit(0);
	}
}
