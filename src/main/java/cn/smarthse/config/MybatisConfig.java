package cn.smarthse.config;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import com.alibaba.druid.pool.DruidDataSource;
import cn.smarthse.config.properties.DatabaseProperties;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Mybatis配置文件,主数据库
 */
@Configuration
@Order(1)
@EnableConfigurationProperties(DatabaseProperties.class)
@MapperScan(basePackages = "cn.smarthse.business.dao", sqlSessionFactoryRef = "defaultSqlSessionFactory")
public class MybatisConfig {

	/**
	 * 日志对象
	 */
	protected final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private DatabaseProperties dbproperties;

	// <!-- step1：定义数据源，druid数据库连接池 -->
	@Bean(name = "defaultDataSource")
	@Primary
	public DataSource druidDataSource() {
		logger.info("step1：定义数据源，druid数据库连接池: {}", dbproperties.getUrl());
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(dbproperties.getDriverClassName());
		dataSource.setUrl(dbproperties.getUrl());
		dataSource.setUsername(dbproperties.getUsername());
		dataSource.setPassword(dbproperties.getPassword());
		dataSource.setInitialSize(dbproperties.getInitialSize());
		dataSource.setMinIdle(dbproperties.getMinIdle());
		dataSource.setMaxActive(dbproperties.getMaxActive());
		dataSource.setMaxWait(dbproperties.getMaxWait());
		dataSource.setTimeBetweenEvictionRunsMillis(dbproperties.getTimeBetweenEvictionRunsMillis());
		dataSource.setMinEvictableIdleTimeMillis(dbproperties.getMinEvictableIdleTimeMillis());
		dataSource.setPoolPreparedStatements(dbproperties.isPoolPreparedStatements());
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(
				dbproperties.getMaxPoolPreparedStatementPerConnectionSize());

		return dataSource;
	}

	/**
	 * 配置 SqlSessionFactory1: 主数据库
	 */
	@Primary
	@Bean(name = "defaultSqlSessionFactory")
	@DependsOn(value = "defaultDataSource")
	public org.mybatis.spring.SqlSessionFactoryBean defaultSqlSessionFactory(
			@Qualifier("defaultDataSource") DataSource dataSource) throws Exception {
		logger.info("step2：定义SqlSessionFactory");
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setConfigLocation(new ClassPathResource("mybatis/sqlMapConfig.xml"));
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactory.setMapperLocations(resolver.getResources("mapper/main/**/*Mapper.xml"));
		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setTypeAliasesPackage("cn.smarthse.business.model.*");
		return sqlSessionFactory;

	}

	@Bean("transactionManager")
	public DataSourceTransactionManager getDataSourceTransactionManager(
			@Qualifier("defaultDataSource") DataSource dataSource) {
		logger.info("step4：定义事务管理器transactionManager");
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource);
		return transactionManager;
	}

}
