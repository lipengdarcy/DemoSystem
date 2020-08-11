package cn.smarthse.config.kafka;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class KafkaDemoData {

	private Integer id;

	private String userName;

	private String description;

	private LocalDateTime createTime;
}
