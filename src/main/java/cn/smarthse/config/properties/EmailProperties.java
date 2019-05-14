/**
 * 
 */
package cn.smarthse.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 《email 配置》
 * email:
    host: hwhzsmtp.qiye.163.com
    port: 994
    username: zyj@smarthse.cn
    password: tR8hkLwsgkAbUZAh
    smtp_auth: true
    defaultEncoding: utf-8
    customer: zuoyj@smarthse.cn    #客户通知邮箱
 * 
 * @Project:  platform-service
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8> 
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2018年10月10日-下午2:44:29
 */
@ConfigurationProperties("email")
public @Data class EmailProperties {
//	#===============================#
//	#===== email  settings =====#
//	#===============================#
	private String host;
	private int port;
	private String username;
	private String password;
	private String defaultEncoding;
	private boolean smtp_auth;
	private String customer;
	
}
