/**
 * 
 */
package cn.smarthse.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 数据库链接
 * 
 */
@ConfigurationProperties("spring.datasource")
public @Data class DatabaseProperties {

	private String driverClassName;

	private String url;

	private String username;

	private String password;

	private int initialSize;

	private int minIdle;

	private int maxActive;
	
	private int maxWait;
	
	private int timeBetweenEvictionRunsMillis;
	
	private int minEvictableIdleTimeMillis;
		
	private boolean poolPreparedStatements;
	
	private int maxPoolPreparedStatementPerConnectionSize;
}
