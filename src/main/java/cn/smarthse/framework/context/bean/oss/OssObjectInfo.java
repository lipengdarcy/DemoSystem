package cn.smarthse.framework.context.bean.oss;

import lombok.Data;

/**
 * 《Oss Object定义》
 * 
 * 
 * @Project:  GIIANTECH CORE
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.7> 
 * @CopyRight CopyRright (c) 2015
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2017-4-26-上午9:01:59
 */
public @Data class OssObjectInfo {

	//文件长度
	private long length;
	//文件路径地址
	private String path;
	//文件md5
	private String MD5;
	
	public OssObjectInfo(String path, long length, String MD5){
		this.length = length;
		this.path = path;
		this.MD5 = MD5;
	}
	
	public OssObjectInfo(String path){
		this.path = path;
	}
	
	public OssObjectInfo(){
		
	}
}
