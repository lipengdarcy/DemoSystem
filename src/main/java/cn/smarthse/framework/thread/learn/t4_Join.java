package cn.smarthse.framework.thread.learn;



import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

/**
 * Created with IntelliJ IDEA.
 * User: Blank
 * Date: 14-3-28
 * Time: 下午7:49
 */
public class t4_Join implements Runnable {


    public static void main(String[] sure) throws InterruptedException {
        Thread t = new Thread(new t4_Join());
        long start = System.currentTimeMillis();
        t.start();
        t.join(1000);//等待线程t 1000毫秒
        System.out.println(System.currentTimeMillis()-start);//打印出时间间隔
        System.out.println("Main finished");//打印主线程结束
    }

    @Override
    public void run() {
        synchronized (currentThread()) {
            for (int i = 1; i <= 5; i++) {
                try {
                    sleep(1000);//睡眠5秒，循环是为了方便输出信息
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("睡眠" + i);
            }
            System.out.println("TestJoin finished");//t线程结束
        }
    }
}