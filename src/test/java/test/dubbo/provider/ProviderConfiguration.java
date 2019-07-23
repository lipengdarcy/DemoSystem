package test.dubbo.provider;

import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@Configuration
@EnableDubbo(scanBasePackages = "test.dubbo.provider.service.impl")
public class ProviderConfiguration {

}
