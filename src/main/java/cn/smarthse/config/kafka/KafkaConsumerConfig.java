package cn.smarthse.config.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties.AckMode;

import lombok.extern.slf4j.Slf4j;

/**
 * Kafka Consumer
 */
@Configuration
@EnableKafka
@Slf4j
public class KafkaConsumerConfig {

	@Value("${kafka.consumer.group-id}")
	private String groupId;
	
	@Value("${kafka.consumer.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${kafka.consumer.enable-auto-commit}")
	private Boolean autoCommit;

	@Value("${kafka.consumer.auto-commit-interval}")
	private Integer autoCommitInterval;

	@Value("${kafka.consumer.max-poll-records}")
	private Integer maxPollRecords;

	@Value("${kafka.consumer.auto-offset-reset}")
	private String autoOffsetReset;

	@Value("${kafka.consumer.session-timeout}")
	private String sessionTimeout;

	@Value("${kafka.consumer.max-poll-interval}")
	private Integer maxPollInterval;

	@Value("${kafka.consumer.max-partition-fetch-bytes}")
	private Integer maxPartitionFetchBytes;
	
	
	
	@Value("${kafka.listener.batch-listener}")
	private Boolean batchListener;
	
	@Value("#{'${kafka.listener.concurrencys}'.split(',')[0]}")
	private Integer concurrency3;

	@Value("#{'${kafka.listener.concurrencys}'.split(',')[1]}")
	private Integer concurrency6;

	@Value("${kafka.listener.poll-timeout}")
	private Long pollTimeout;
	

	/**
	 * 并发数6
	 *
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(name = "kafkaBatchListener6")
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaBatchListener6() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = kafkaListenerContainerFactory();
		factory.setConcurrency(concurrency6);
		return factory;
	}

	/**
	 * 并发数3
	 *
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(name = "kafkaBatchListener3")
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaBatchListener3() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = kafkaListenerContainerFactory();
		factory.setConcurrency(concurrency3);
		return factory;
	}

	/**
	 * 自动提交，在服务启停时，会有重复数据被生产到kafka中，保证吞吐量的同时，降低了kafka的原子性；
	 * 
	 * 手动提交，保证了kafka的原子性，同时降低了kafka的吞吐量，实际开发中，可跟随数据量的大小，自行分析配置。
	 */
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		// 批量消费
		factory.setBatchListener(batchListener);
		// 如果消息队列中没有消息，等待timeout毫秒后，调用poll()方法。
		// 如果队列中有消息，立即消费消息，每次消费的消息的多少可以通过max.poll.records配置。
		// 手动提交无需配置
		factory.getContainerProperties().setPollTimeout(pollTimeout);
		// 设置提交偏移量的方式， MANUAL_IMMEDIATE 表示消费一条提交一次；MANUAL表示批量提交一次
		factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
		return factory;
	}

	private ConsumerFactory<String, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	private Map<String, Object> consumerConfigs() {
		log.info("定义kafka消费者：" + bootstrapServers);
		Map<String, Object> props = new HashMap<>(11);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, autoCommit);
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
		props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollInterval);
		props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, maxPartitionFetchBytes);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return props;
	}

}
