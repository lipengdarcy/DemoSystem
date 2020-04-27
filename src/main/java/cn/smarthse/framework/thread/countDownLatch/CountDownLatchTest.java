package cn.smarthse.framework.thread.countDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch， 线程同步，多个线程阻塞，直到计数器为0
 */
public class CountDownLatchTest {
    static CountDownLatch c = new CountDownLatch(3);
    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            public void run() {
                System.out.println(1);
                c.countDown();
                System.out.println(2);
                c.countDown();
                c.countDown();
            }
        }).start();

        c.await();//await会一直阻塞到计数器为零，或者等待中的线程中断，或者等待超时
        System.out.println(3);
    }
}
