package cn.smarthse.business.enums.project;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ProjectStatusEnum {
	
	editing("编辑中",0),	
	finish("已完成",1); 
	
	// 成员变量
	private String name;
	private Integer code;
	// 构造方法

	private ProjectStatusEnum(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	// 普通方法
	public static String getName(Integer code) {
		if (code == null) {
			return "";
		}
		for (ProjectStatusEnum c : ProjectStatusEnum.values()) {
			if (c.getCode()==code) {
				return c.name;
			}
		}
		return "";
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
}
