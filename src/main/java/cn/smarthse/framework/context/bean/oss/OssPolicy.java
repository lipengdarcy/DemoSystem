package cn.smarthse.framework.context.bean.oss;

import lombok.Data;

/**
 * 《OSS服务端签名直传并设置上传回调属性定义类》
 * 
 * 
 * @Project:  GIIANTECH CORE
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.7> 
 * @CopyRight CopyRright (c) 2015
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2017-4-26-下午1:25:12
 */
public @Data class OssPolicy {

	//OSSAccessKeyId
	private String accessid;
	//policyBase64
	private String policy;
	//直传签名
	private String signature;
	//上传路径
	private String dir;
	//
	private String host;
	//有效期
	private String expire;
}
