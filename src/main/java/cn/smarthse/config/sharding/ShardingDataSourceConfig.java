package cn.smarthse.config.sharding;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.api.yaml.YamlShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.google.common.collect.Lists;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * Mybatis配置文件, 数据分片 + 读写分离
 */
//@Configuration
//@MapperScan(basePackages = "cn.smarthse.sharding.dao", sqlSessionFactoryRef = "shardginSqlSessionFactory")
public class ShardingDataSourceConfig {

	/**
	 * 日志对象
	 */
	protected final Logger logger = LogManager.getLogger(this.getClass());

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
		yaml.setResources(new ClassPathResource("shardingsphere.yml"));
		configurer.setProperties(yaml.getObject());
		return configurer;
	}

	/**
	 * 构建dataSource
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	// @Bean
	public DataSource dataSource_bak() throws SQLException, IOException {
		ClassPathResource resource = new ClassPathResource("shardingsphere.yml");
		DataSource ds = YamlShardingDataSourceFactory.createDataSource(resource.getFile());
		logger.info("shardingsphere 定义数据源，主从分库分表");
		return ds;
	}

	/**
	 * 构建dataSource
	 * 
	 * 数据分片 + 读写分离
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	@Bean
	public DataSource dataSource() throws SQLException {
		ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
		shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
		shardingRuleConfig.getTableRuleConfigs().add(getOrderItemTableRuleConfiguration());
		shardingRuleConfig.getBindingTableGroups().add("t_order, t_order_item");
		shardingRuleConfig.getBroadcastTables().add("t_config");
		// 分库策略，根据用户id
		shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(
				new StandardShardingStrategyConfiguration("user_id", new DefaultDatabaseShardingAlgorithm()));
		// 分表策略，根据订单id
		shardingRuleConfig.setDefaultTableShardingStrategyConfig(
				new StandardShardingStrategyConfiguration("order_id", new DefaultTableShardingAlgorithm()));
		shardingRuleConfig.setMasterSlaveRuleConfigs(getMasterSlaveRuleConfigurations());
		// 显示sql
		Properties p = new Properties();
		p.setProperty("sql.show", "true");
		return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, p);
	}

	/**
	 * 主键生成机制： SNOWFLAKE算法
	 */
	private static KeyGeneratorConfiguration getKeyGeneratorConfiguration() {
		KeyGeneratorConfiguration result = new KeyGeneratorConfiguration("SNOWFLAKE", "order_id");
		return result;
	}

	private TableRuleConfiguration getOrderTableRuleConfiguration() {
		TableRuleConfiguration result = new TableRuleConfiguration("t_order", "ds_${0..1}.t_order_${[0, 1]}");
		result.setKeyGeneratorConfig(getKeyGeneratorConfiguration());
		return result;
	}

	private TableRuleConfiguration getOrderItemTableRuleConfiguration() {
		TableRuleConfiguration result = new TableRuleConfiguration("t_order_item", "ds_${0..1}.t_order_item_${[0, 1]}");
		return result;
	}

	private List<MasterSlaveRuleConfiguration> getMasterSlaveRuleConfigurations() {
		MasterSlaveRuleConfiguration masterSlaveRuleConfig1 = new MasterSlaveRuleConfiguration("ds_0", "master0",
				Arrays.asList("master0slave0", "master0slave1"));
		MasterSlaveRuleConfiguration masterSlaveRuleConfig2 = new MasterSlaveRuleConfiguration("ds_1", "master1",
				Arrays.asList("master1slave0", "master1slave1"));
		return Lists.newArrayList(masterSlaveRuleConfig1, masterSlaveRuleConfig2);
	}

	private Map<String, DataSource> createDataSourceMap() {
		final Map<String, DataSource> result = new HashMap<>();
		result.put("master0", master0());
		result.put("master0slave0", master0slave0());
		result.put("master0slave1", master0slave1());
		result.put("master1", master1());
		result.put("master1slave0", master1slave0());
		result.put("master1slave1", master1slave1());
		return result;
	}

	@Bean("master0")
	@ConfigurationProperties("spring.shardingsphere.datasource.master0")
	public DataSource master0() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean("master0slave0")
	@ConfigurationProperties("spring.shardingsphere.datasource.master0slave0")
	public DataSource master0slave0() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean("master0slave1")
	@ConfigurationProperties("spring.shardingsphere.datasource.master0slave1")
	public DataSource master0slave1() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean("master1")
	@ConfigurationProperties("spring.shardingsphere.datasource.master1")
	public DataSource master1() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean("master1slave0")
	@ConfigurationProperties("spring.shardingsphere.datasource.master1slave0")
	public DataSource master1slave0() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean("master1slave1")
	@ConfigurationProperties("spring.shardingsphere.datasource.master1slave1")
	public DataSource master1slave1() {
		return DruidDataSourceBuilder.create().build();
	}

	private DataSource createDataSource(String name) {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setName(name);
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/" + name
				+ "?serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
		return dataSource;
	}

	/**
	 * 配置 SqlSessionFactory
	 */
	@Bean(name = "shardginSqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		logger.info("step2.2：定义分库分表SqlSessionFactory");
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource());

		// 扫描mybatis mapper文件
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		bean.setTypeAliasesPackage("cn.smarthse.backup.dao");
		bean.setMapperLocations(resolver.getResources("classpath*:mapper/sharding/**/*Mapper.xml"));
		// 配置文件
		logger.debug("读取配置前：" + new Date());
		org.apache.ibatis.session.Configuration mybatisConfig = bean.getObject().getConfiguration();
		logger.debug("读取配置后：" + new Date());
		mybatisConfig.setMapUnderscoreToCamelCase(true);
		mybatisConfig.setJdbcTypeForNull(JdbcType.NULL);
		// 全局的映射器启用或禁用缓存
		mybatisConfig.setCacheEnabled(true);
		// 允许 JDBC 支持生成的键。需要适合的驱动。如果设置为 true 则这个设置强制生成的键被使用，尽管一些驱动拒绝兼容但仍然有效
		mybatisConfig.setUseGeneratedKeys(true);
		// 配置默认的执行器。SIMPLE 执行器没有什么特别之处。REUSE 执行器重用预处理语句。BATCH 执行器重用语句和批量更新
		mybatisConfig.setDefaultExecutorType(ExecutorType.REUSE);
		// 全局启用或禁用延迟加载。当禁用时，所有关联对象都会即时加载。
		mybatisConfig.setLazyLoadingEnabled(true);
		// 设置超时时间，它决定驱动等待一个数据库响应的时间
		mybatisConfig.setDefaultStatementTimeout(25000);

		// 分页插件
		Interceptor pageHelper = new com.github.pagehelper.PageInterceptor();
		Properties p = new Properties();
		p.setProperty("helperDialect", "mysql");
		p.setProperty("offsetAsPageNum", "true");
		p.setProperty("rowBoundsWithCount", "true");
		p.setProperty("autoRuntimeDialect", "true");
		pageHelper.setProperties(p);
		mybatisConfig.addInterceptor(pageHelper);
		return bean.getObject();
	}

	// 定义sqlSession
	@Scope("prototype")
	@Bean(name = "shardingSqlSession")
	public SqlSession sqlSession() throws Exception {
		SqlSessionTemplate a = new SqlSessionTemplate(sqlSessionFactory());
		logger.info("定义shardingSqlSession");
		return a;
	}

}