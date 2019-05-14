/**
 * 
 */
package cn.smarthse.config;

import java.util.Properties;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import cn.smarthse.config.properties.EmailProperties;
import cn.smarthse.config.properties.OssClientProperties;
import cn.smarthse.config.properties.SmtProperties;
import cn.smarthse.config.properties.SysconfigProperties;
import cn.smarthse.config.properties.TaskExecutorProperties;
import cn.smarthse.framework.context.SpringContextHolder;
import cn.smarthse.framework.context.bean.oss.OssClientBean;
import cn.smarthse.framework.context.mail.FreemarkerEmailServiceImpl;

/**
 * 普通内容配置器
 * 
 */
@Configuration
@EnableAsync // 启动异步调用
@EnableScheduling
@ComponentScan(basePackages = { "cn.smarthse" })
@EnableConfigurationProperties({ OssClientProperties.class, TaskExecutorProperties.class, EmailProperties.class,
		SmtProperties.class, SysconfigProperties.class })
public class ContentConfiguration {

	/**
	 * 日志对象
	 */
	protected final Logger logger = LogManager.getLogger(this.getClass());

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
		logger.info("配置OssClientBean {}", ossClientProperties.getBucketname());
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
	 * 
	 * @Comments: <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2018年4月26日-上午10:48:39
	 * @return
	 */
	@Bean("emailTemplate")
	public JavaMailSenderImpl getJavaMailSenderImpl() {
		logger.info("配置 JavaMailSenderImpl {}", emailProperties);
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
		logger.info("配置AsyncTaskExecutor");
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
		logger.info("配置Email FreemarkerEmailServiceImpl ");
		FreemarkerEmailServiceImpl emailService = new FreemarkerEmailServiceImpl();
		return emailService;
	}

	@Bean
	public SpringContextHolder springContextHolder() {
		return new SpringContextHolder();
	}

}
