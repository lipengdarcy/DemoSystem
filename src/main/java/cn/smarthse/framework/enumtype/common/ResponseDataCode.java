package cn.smarthse.framework.enumtype.common;

/**
 * 异步调用返回码 枚举类
 * @author lipeng
 * */
public enum ResponseDataCode {
	fail("操作失败", -1), success("操作成功", 0) ;

	// 成员变量
	private String name;
	private int value;

	// 构造方法
	private ResponseDataCode(String name, int value) {
		this.name = name;
		this.value = value;
	}

	// 普通方法
	public static String getName(int index) {
		for (ResponseDataCode c : ResponseDataCode.values()) {
			if (c.getValue() == index) {
				return c.name;
			}
		}
		return null;
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

}
