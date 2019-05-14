package cn.smarthse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import cn.smarthse.framework.util.SpringContextUtil;

@SpringBootApplication
public class DemoSystemStart {

	public static void main(String[] args) {
		System.setProperty("es.set.netty.runtime.available.processors", "false");
		SpringContextUtil springContextUtil = new SpringContextUtil();
		ApplicationContext applicationContext = SpringApplication.run(DemoSystemStart.class, args);
		springContextUtil.setApplicationContext(applicationContext);
	}

}