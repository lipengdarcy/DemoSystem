package cn.smarthse.config.kafka;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import lombok.extern.slf4j.Slf4j;

/**
 * 定时任务：每10秒向kafka发送信息
 */
@Component
@EnableScheduling
@Slf4j
public class DemoDataTask {

	@Value("#{'${kafka.listener.topics}'.split(',')}")
	private List<String> topics;

	private final KafkaSender KAFKA_SENDER;

	@Autowired
	public DemoDataTask(KafkaSender kafkaSender) {
		this.KAFKA_SENDER = kafkaSender;
	}

	@Scheduled(fixedRate = 3600 * 1000)
	public void addDataTask() {
		log.info("定时发送消息到kafka");
		KafkaDemoData user = new KafkaDemoData();
		user.setUserName("HS");
		user.setDescription("text");
		user.setCreateTime(LocalDateTime.now());
		String JSONUser = JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss", // 日期格式化
				SerializerFeature.PrettyFormat);// 格式化json
		for (int i = 0; i < 700; i++) {
			KAFKA_SENDER.sendMessage(topics.get(0), JSONUser);
		}
	}
}
