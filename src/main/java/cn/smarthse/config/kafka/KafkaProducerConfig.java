package cn.smarthse.config.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * kafka producer
 */
@Configuration
@EnableKafka
@Slf4j
public class KafkaProducerConfig {

	@Value("${kafka.producer.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${kafka.producer.retries}")
	private Integer retries;

	@Value("${kafka.producer.batch-size}")
	private Integer batchSize;

	@Value("${kafka.producer.buffer-memory}")
	private Integer bufferMemory;

	@Value("${kafka.producer.linger}")
	private Integer linger;

	// Topic的配置开始
	@Bean
	public KafkaAdmin admin() {
		Map<String, Object> configs = new HashMap<String, Object>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		return new KafkaAdmin(configs);
	}

	@Bean
	public NewTopic topic() {
		return new NewTopic("foo", 10, (short) 2);
	}
	// Topic的配置结束

	/*
	 * @Bean public KafkaTransactionManager transactionManager() {
	 * KafkaTransactionManager manager = new
	 * KafkaTransactionManager(producerFactory()); return manager; }
	 */

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		log.info("定义kafka生产者：" + bootstrapServers);
		Map<String, Object> props = new HashMap<>(7);
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.RETRIES_CONFIG, retries);
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
		props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

		DefaultKafkaProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(props);

		// producerFactory.transactionCapable();
		// producerFactory.setTransactionIdPrefix("hous-");

		return new KafkaTemplate<>(producerFactory);
	}
}
