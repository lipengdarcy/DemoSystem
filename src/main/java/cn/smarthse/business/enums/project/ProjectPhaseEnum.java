package cn.smarthse.business.enums.project;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ProjectPhaseEnum {
	//1、审核中 2、被驳回 3、审核通过/待流转 4、进行中 5、已完成 6、终止 7、暂停
	reviewing("审核中",1),	
	reviewFailed("被驳回",2),
	unstart("待流转",3),	
	starting("进行中",4),	
	finished("已完成",5),	
	stoped("终止",6),
	pause("暂停",7);
	
	// 成员变量
	private String name;
	private Integer code;
	// 构造方法

	private ProjectPhaseEnum(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	// 普通方法
	public static String getName(Integer code) {
		if (code == null) {
			return "";
		}
		for (ProjectPhaseEnum c : ProjectPhaseEnum.values()) {
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
