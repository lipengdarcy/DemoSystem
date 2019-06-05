
package cn.smarthse.config;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import cn.smarthse.business.interceptor.LogInterceptor;
import cn.smarthse.framework.context.interceptor.ShiroAuthInterceptor;

/**
 * Web配置器
 * 
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurationSupport {

	private Logger log = LogManager.getLogger(this.getClass());

	/**
	 * 日志拦截器
	 */
	@Bean
	public LogInterceptor getLogInterceptor() {
		return new LogInterceptor();
	}

	public void addInterceptors(InterceptorRegistry registry) {
		log.info("配置 日志拦截器");
		registry.addInterceptor(getLogInterceptor()).addPathPatterns("/**");
		log.info("配置 权限拦截器");
		registry.addInterceptor(new ShiroAuthInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}

	// 这个是springboot运行的静态资源配置
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		log.info("配置 静态资源访问");
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/").setCachePeriod(3600);
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		super.addResourceHandlers(registry);
	}

	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		log.info("配置 JSON转换器: FastJson");
		converters.add(new FastJsonHttpDateConverter());
		super.configureMessageConverters(converters);
	}

}
