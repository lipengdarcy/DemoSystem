package test.dubbo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

import test.dubbo.consumer.action.AnnotationAction;

public class AnnotationConsumerBootstrap {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				ConsumerConfiguration.class);
		context.start();
		final AnnotationAction annotationAction = (AnnotationAction) context.getBean("annotationAction");
		System.out.println("hello :" + annotationAction.doSayHello("world"));
	}

	@Configuration
	@EnableDubbo(scanBasePackages = "test.dubbo.consumer.action")
	@ComponentScan(value = {"test.dubbo.consumer.action"})
	@PropertySource("classpath:/application-dev.yml")
	static public class ConsumerConfiguration {

	}

}
