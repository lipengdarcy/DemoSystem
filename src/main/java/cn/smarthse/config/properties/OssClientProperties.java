package cn.smarthse.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties("oss")
public @Data class OssClientProperties {

	private String endpoint;
	private String accesskey;
	private String accesssecret;
	private String bucketname;

	/**
	 * 直传回调地址
	 */
	private String callback;
}
