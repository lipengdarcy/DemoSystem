package test.dubbo.consumer;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@Configuration
@EnableDubbo(scanBasePackages = "test.dubbo.consumer.action")
@ComponentScan(value = { "test.dubbo.consumer.action;" })
public class ConsumerConfiguration {

}
