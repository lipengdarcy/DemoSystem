/**
 * 
 */
package cn.smarthse.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 《Dubbo配置项》
 * 
 * 
 * @Project:  platform-im
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8> 
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2018年8月28日-下午2:42:18
 */
@ConfigurationProperties("dubbo")
public @Data class DubboProperties {

	//# 客户端连接数（建议连接在0-30之间，以免过多产生资源浪费）
//	@Value("${zookeeper.application.name}")
	private String application_name;
	
	/**
	 * 运营后台3.x本地服务
	 */
	private String local_zookeeper_address;
	
	/**
	 * 平台3.x服务
	 */
	private String platform_zookeeper_address;
	
	/**
	 * 平台/职业健康/放辐射服务
	 */
	private String common_zookeeper_address;
	
	/**
	 * 健康顾问服务
	 */
	private String  health_advisor_zookeeper_address;
	/**
	 * 代理商服务
	 */
	private String  agent_zookeeper_address;
	/**
	 * 法律法规服务
	 */
	private String  legal_zookeeper_address;
	/**
	 * 体检中心服务zk
	 */
	private String  healthcheck_zookeeper_address;
	
	/**
	 * 消费者超时时间 
	 */
	private int consumer_Timeout=1200000;
	
	/**
	 * 消费者服务检测,默认False
	 */
	private boolean consumer_Check=false;
}
