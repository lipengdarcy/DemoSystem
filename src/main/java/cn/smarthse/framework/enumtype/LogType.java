package cn.smarthse.framework.enumtype;

/**
 * 系统日志类型， 1：登录日志； 2：数据同步； 3：文件上传；4：消息推送；
 * 
 * 
 * @author lipeng
 */
public enum LogType {

	login("登录日志", (byte) 1), 
	dataSync("数据同步", (byte) 2), 
	fileUpload("文件上传", (byte) 3), 
	push("消息推送", (byte) 4),
	update("update操作", (byte)5),
	
	;

	// 成员变量
	private String name;
	private byte value;

	// 构造方法
	private LogType(String name, byte value) {
		this.name = name;
		this.value = value;
	}

	// get set 方法
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getValue() {
		return value;
	}

	public void setValue(byte index) {
		this.value = index;
	}
}
