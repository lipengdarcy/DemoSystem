/**
 * 
 */
package cn.smarthse.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 《》
 * 
 * 
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8> 
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2018年9月12日-下午3:45:13
 */
@ConfigurationProperties("oss")
public @Data class OssClientProperties {

//	#===============================#
//	#===== oss  settings =====#
//	#===============================#
	private String endpoint;
	private String accesskey;
	private String accesssecret;
	private String bucketname;
	
	/**
	 * 直传回调地址
	 */
	private String callback;
}
