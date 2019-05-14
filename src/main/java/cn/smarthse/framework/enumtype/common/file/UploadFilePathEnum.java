package cn.smarthse.framework.enumtype.common.file;

/**
 * 文件上传路径枚举类
 * 
 * */

public enum UploadFilePathEnum {
	Default(0, "默认上传路径", "Default"), // 默认路径
	ASystem(1, "系统管理", "ASystem"), BCompany(2, "企业信息", "BCompany"), CStaff(3,
			"人员管理", "CStaff"), DLive(4, "现场管理", "DLive"), EDevice(5, "设备管理",
			"EDevice"), FSameTime(6, "三同时", "FSameTime"), GThirdParty(7,
			"第三方管理", "GThirdParty"), HLaw(8, "法律法规", "HLaw");

	/** 路径名称 */
	private String name;
	
	/** 路径编码 */
	private int value;

	/** 路径值 */
	private String path;

	// get set 方法
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int index) {
		this.value = index;
	}

	UploadFilePathEnum(int value, String name, String path) {
		this.value = value;		
		this.name = name;
		this.path = path;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	public static UploadFilePathEnum getCode(int value) {
		for (UploadFilePathEnum c : UploadFilePathEnum.values()) {
			if (c.getValue() == value) {
				return c;
			}
		}
		return UploadFilePathEnum.Default;
	}

}
