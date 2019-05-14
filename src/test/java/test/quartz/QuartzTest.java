package test.quartz;

//测试main函数
//QuartzTest.java

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class QuartzTest {

	/** */
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Test start.");
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"spring/spring-servlet.xml");
		// 如果配置文件中将startQuertz bean的lazy-init设置为false 则不用实例化
		// context.getBean("startQuertz");
		
		System.out.print("Test end..");

	}
}
