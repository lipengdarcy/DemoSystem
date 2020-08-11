package cn.smarthse.config.kafka;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaListeners {

	@KafkaListener(containerFactory = "kafkaBatchListener6", topics = { "#{'${kafka.listener.topics}'.split(',')[0]}" })
	public void batchListener(List<ConsumerRecord<?, ?>> records, Acknowledgment ack) {

		List<KafkaDemoData> dataList = new ArrayList<>();
		try {
			records.forEach(record -> {
				KafkaDemoData data = JSON.parseObject(record.value().toString(), KafkaDemoData.class);
				data.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				dataList.add(data);
			});
		} catch (Exception e) {
			log.error("Kafka监听异常" + e.getMessage(), e);
		} finally {
			ack.acknowledge();// 手动提交偏移量
		}

	}

}
