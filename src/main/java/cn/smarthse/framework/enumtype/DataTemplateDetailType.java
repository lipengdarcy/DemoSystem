package cn.smarthse.framework.enumtype;


/**
 * 检测原始记录具体名称(12个原始表单)
 * 
 * @author lipeng
 * */
public enum DataTemplateDetailType {

	type1("医用诊断X射线工作场所放射防护检测原始记录", 1),
	type2("车载式医用X射线诊断工作场所放射防护检测原始记录", 2),
	type3("医用X射线诊断设备（摄影）性能检测原始记录", 3),
	type4("医用X射线诊断设备（透视）性能检测原始记录", 4),
	type5("医用X射线计算机断层摄影装置（CT）性能检测原始记录", 5),
	type6("乳腺数字X射线摄影系统（乳腺DR）质量控制检测原始记录", 6),
	type7("乳腺计算机X射线摄影系统（乳腺CR）质量控制检测原始记录", 7),
	type8("医用X射线诊断设备（乳腺屏片摄影）质量控制检测原始记录", 8),
	type9("牙科X射线机性能检测原始记录", 9),
	type10("医用数字X射线摄影系统（DR）质量控制检测原始记录", 10)	,
	type11("计算机X射线摄影（CR）质量控制检测原始记录", 11),
	type12("近台同室操作X射线设备透视防护区（介入）工作人员位置空气比释动能率检测原始记录", 12),
	;

	// 成员变量
	private String name;
	private int value;

	// 构造方法
	private DataTemplateDetailType(String name, int value) {
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

	public int getValue() {
		return value;
	}

	public void setValue(int index) {
		this.value = index;
	}


	public static String getName(byte index) {
		for (DataTemplateDetailType c : DataTemplateDetailType.values()) {
			if (c.getValue() == index) {
				return c.name;
			}
		}
		return null;
	}
}
