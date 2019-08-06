package cn.smarthse.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 系统配置
 */
@ConfigurationProperties("sysconfig")
public @Data class SysconfigProperties {

	/**
	 * 上传本地路径
	 */
	private String uploadDir;
	/**
	 * 图片默认扩展名
	 */
	private String uploadDefaultExtension;
	/**
	 * 找不到文件友情提示
	 */
	private String nofile;
}
