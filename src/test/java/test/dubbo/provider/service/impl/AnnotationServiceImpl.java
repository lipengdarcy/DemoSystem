package test.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;

import test.dubbo.provider.service.AnnotationService;

//需要 2.6.3 及以上版本支持 点此查看完整示例
//服务提供方:Service注解暴露服务
@Service
public class AnnotationServiceImpl implements AnnotationService {
	@Override
	public String sayHello(String name) {
		return "annotation: hello, " + name;
	}
}
