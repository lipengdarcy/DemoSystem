/**
 * 
 */
package cn.smarthse.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 《短信配置属性》
 * 
 * 
 * @Project:  platform-service
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8> 
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2018年10月24日-上午9:05:35
 */
@ConfigurationProperties("smt")
public @Data class SmtProperties {

	//SMT 通道尝试发送3次,3次之后切换为另一个配置项
	private int trytime=3;
	
	//SMT账号
	private  String CORPID = "giiantech";
	//SMT密码
	private  String PWD = "chenyun";
    private String ENCODING = "UTF-8";
    //统一签名
    private String MSGSIGN = "【智慧职安环保科技】";
    //通用发送接口的http地址
    private String URI_SEND_SMS = "http://114.215.187.19/WS/Send.aspx";
    //群发短信 BatchSend
    private String URI_BATCHSEND_SMS = "http://114.215.187.19/WS/BatchSend.aspx";
    
    //阿里云短信配置
    private final Dysmsapi dysmsapi = new Dysmsapi();
    
    
    public @Data static class Dysmsapi {
    	//产品名称:云通信短信API产品,开发者无需替换
    	private String product = "Dysmsapi";
        //产品域名,开发者无需替换
    	private String domain = "dysmsapi.aliyuncs.com";
        // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    	private String regionid = "cn-hangzhou";
    	private String endpointname = "cn-hangzhou";
    	private String accesskeyid = "LTAIH6Jwysni9i5M";
    	private String accesskeysecret = "B13HsuDlPuQvGacfKAVXH5k5C4dhgI";
    	private String signname = "智慧职安环保科技";
    	private String templatecode="SMS_137671605";
    }
	
}
