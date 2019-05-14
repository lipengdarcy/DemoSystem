package cn.smarthse.framework.enumtype;


/**
 * 检测原始记录模板(12个原始表单)
 * 
 * @author lipeng
 * */
public enum DataTemplateType {

	type1("场所", 1),
	type2("车载式场所", 2),
	type3("摄影", 3),
	type4("透视", 4),
	type5("CT", 5),
	type6("乳腺DR", 6),
	type7("乳腺CR", 7),
	type8("乳腺屏片", 8),
	type9("牙科机", 9),
	type10("DR专项", 10)	,
	type11("CR专项", 11),
	type12("近台同室操作", 12),
	;

	// 成员变量
	private String name;
	private int value;

	// 构造方法
	private DataTemplateType(String name, int value) {
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
		for (DataTemplateType c : DataTemplateType.values()) {
			if (c.getValue() == index) {
				return c.name;
			}
		}
		return null;
	}
}
