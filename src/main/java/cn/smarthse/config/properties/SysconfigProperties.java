/**
 * 
 */
package cn.smarthse.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 《系统配置》
 * 
 * 
 * @Project:  platform-www
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8> 
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2018年10月30日-上午9:37:56
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
