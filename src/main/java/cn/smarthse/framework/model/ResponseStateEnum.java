package cn.smarthse.framework.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResponseStateEnum {
	success("操作成功",0),
	
	fail("操作失败",1),
	
	warning("警告",2),
	
	BAD_REQUEST("400参数不匹配",400),
	UNAUTHORIZED("401登录失效",401),
//	LOGIN_FAIL("402登录失败",402),
	Unauthorized403("403无权限",403),
	INTERNAL_SERVER_ERROR("500服务器出错",500),
	; 
	
	// 成员变量
	private String name;
	private Integer code;
	// 构造方法

	private ResponseStateEnum(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	// 普通方法
	public static String getName(Integer code) {
		if (code == null) {
			return null;
		}
		for (ResponseStateEnum c : values()) {
			if (c.getCode().equals(code)) {
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
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	
	public boolean equals(ResponseStateEnum state){
		if(state == null){
			return false;
		}
		if(this.code.equals(state.getCode())){
			return true;
		}

		return false;
	}
	
	public boolean notEquals(ResponseStateEnum state){
		return !equals(state);
	}
}
