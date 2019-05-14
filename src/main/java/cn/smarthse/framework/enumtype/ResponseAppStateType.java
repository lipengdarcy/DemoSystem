package cn.smarthse.framework.enumtype;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResponseAppStateType {

	
	success("操作成功",(byte)1),
	
	fail("操作失败",(byte)2),
	
	warning("登录失效",(byte)3),
	; 
	
	// 成员变量
	private String name;
	private Byte value;
	// 构造方法

	private ResponseAppStateType(String name, Byte value) {
		this.name = name;
		this.value = value;
	}
	// 普通方法
	public static String getName(Byte value) {
		if (value == null) {
			return null;
		}
		for (ResponseAppStateType c : ResponseAppStateType.values()) {
			if (c.getValue().equals(value)) {
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
	public Byte getValue() {
		return value;
	}
	public void setValue(Byte index) {
		this.value = index;
	}
	
	public boolean equals(ResponseAppStateType state){
		if(state == null){
			return false;
		}
		if(this.value.equals(state.getValue())){
			return true;
		}

		return false;
	}
	
	public boolean notEquals(ResponseAppStateType state){
		return !equals(state);
	}
}
