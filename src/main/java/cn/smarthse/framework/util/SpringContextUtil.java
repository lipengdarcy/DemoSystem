package cn.smarthse.framework.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring的Cacheable注解是基于Spring AOP实现的，但是类内部方法互相调用时不会被Spring AOP拦截
 * 
 * 因此采用此工具，使得内部方法也走aop代理
 */
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static Object getBean(@SuppressWarnings("rawtypes") Class var1) throws BeansException {
		return applicationContext.getBean(var1);
	}
}