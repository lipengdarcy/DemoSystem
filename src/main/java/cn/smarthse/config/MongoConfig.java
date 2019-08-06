package cn.smarthse.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoConfig {

	/**
	 * 除掉多余的_class字段
	 * 
	 * @param factory
	 * @param context
	 * @param beanFactory
	 * @return
	 */
	@Bean
	public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory, MongoMappingContext context,
			BeanFactory beanFactory) {
		DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
		MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
		try {
			mappingConverter.setCustomConversions(beanFactory.getBean(MongoCustomConversions.class));
		} catch (NoSuchBeanDefinitionException ignore) {
		}

		mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));

		return mappingConverter;
	}
}