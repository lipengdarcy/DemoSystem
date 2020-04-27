package cn.smarthse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoSystemStart {

	public static void main(String[] args) {
		// System.setProperty("es.set.netty.runtime.available.processors", "false");
		// SpringContextUtil springContextUtil = new SpringContextUtil();
		SpringApplication.run(DemoSystemStart.class, args);
		// springContextUtil.setApplicationContext(applicationContext);
	}

}