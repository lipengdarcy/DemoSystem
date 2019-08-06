
package cn.smarthse.config;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import cn.smarthse.business.interceptor.LogInterceptor;
import cn.smarthse.config.properties.EmailProperties;
import cn.smarthse.config.properties.OssClientProperties;
import cn.smarthse.config.properties.SmtProperties;
import cn.smarthse.config.properties.SysconfigProperties;
import cn.smarthse.config.properties.TaskExecutorProperties;
import cn.smarthse.framework.context.SpringContextHolder;
import cn.smarthse.framework.context.bean.oss.OssClientBean;
import cn.smarthse.framework.context.interceptor.ShiroAuthInterceptor;
import cn.smarthse.framework.context.mail.FreemarkerEmailServiceImpl;

/**
 * Web配置器
 * 
 */
@Configuration
@EnableConfigurationProperties({ OssClientProperties.class, TaskExecutorProperties.class, EmailProperties.class,
		SmtProperties.class, SysconfigProperties.class })

public class WebConfig extends WebMvcConfigurationSupport {

	private Logger log = LogManager.getLogger(this.getClass());

	public void addInterceptors(InterceptorRegistry registry) {
		log.info("配置 日志拦截器");
		registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**");
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

	// OSS配置
	@Autowired
	private OssClientProperties ossClientProperties;

	// 任务调度配置
	@Autowired
	private TaskExecutorProperties taskExecutorProperties;

	// 邮箱配置
	@Autowired
	private EmailProperties emailProperties;

	@Bean(name = "ossclient")
	public OssClientBean getOssClientBean() {
		log.info("配置OssClientBean {}", ossClientProperties.getBucketname());
		OssClientBean ossClientBean = new OssClientBean();
		ossClientBean.setEndpoint(ossClientProperties.getEndpoint());
		ossClientBean.setAccessKeyId(ossClientProperties.getAccesskey());
		ossClientBean.setAccessKeySecret(ossClientProperties.getAccesssecret());
		ossClientBean.setBucketName(ossClientProperties.getBucketname());
		ossClientBean.setCallbackUrl(ossClientProperties.getCallback());

		return ossClientBean;
	}

	/**
	 * 配置邮箱模板
	 */
	@Bean("emailTemplate")
	public JavaMailSenderImpl getJavaMailSenderImpl() {
		log.info("配置 JavaMailSenderImpl {}", emailProperties);
		JavaMailSenderImpl emailTemplate = new JavaMailSenderImpl();
		emailTemplate.setDefaultEncoding(emailProperties.getDefaultEncoding());
		emailTemplate.setHost(emailProperties.getHost());
		emailTemplate.setPort(emailProperties.getPort());
		emailTemplate.setUsername(emailProperties.getUsername());
		emailTemplate.setPassword(emailProperties.getPassword());

		Properties javaMailProperties = new Properties();
		javaMailProperties.setProperty("mail.smtp.auth", "true");
		javaMailProperties.setProperty("mail.smtp.timeout", "25000");
		javaMailProperties.put("mail.smtp.starttls.enable", true);
		javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		emailTemplate.setJavaMailProperties(javaMailProperties);

		return emailTemplate;
	}

	/**
	 * 自定义异步线程池
	 * 
	 * @return
	 */
	@Bean
	public AsyncTaskExecutor taskExecutor() {
		log.info("配置AsyncTaskExecutor");
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setThreadNamePrefix("PlatformService-Executor");
		executor.setCorePoolSize(taskExecutorProperties.getCore_pool_size());
		executor.setMaxPoolSize(taskExecutorProperties.getMax_pool_size());
		executor.setQueueCapacity(taskExecutorProperties.getQueue_capacity());
		executor.setKeepAliveSeconds(taskExecutorProperties.getKeep_alive_seconds());

		// 设置拒绝策略
		executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				// .....
			}
		});
		// 使用预定义的异常处理类
		// executor.setRejectedExecutionHandler(new
		// ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();
		return executor;
	}

	@Bean
	public FreemarkerEmailServiceImpl getFreemarkerEmailServiceImpl() {
		log.info("配置Email FreemarkerEmailServiceImpl ");
		FreemarkerEmailServiceImpl emailService = new FreemarkerEmailServiceImpl();
		return emailService;
	}

	@Bean
	public SpringContextHolder springContextHolder() {
		return new SpringContextHolder();
	}

}
