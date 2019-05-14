package cn.smarthse.business.enums.message;



/**
 * 《消息状态》
 * 
 * 
 * @Project:  GiianSystem
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8> 
 * @author XiaoYi（肖奕) xy@smarthse.cn
 * @since 2019年3月11日-下午3:40:02
 */
public enum MessageRepeatTypeEnum {
	noRepeat("不重复",1),
	dayRepeat("按日重复", 2),
	monthRepeat("按月重复", 3)
	;
	// 成员变量
	private String name;
	private Integer state;
	// 构造方法

	private MessageRepeatTypeEnum(String name, Integer state) {
		this.name = name;
		this.state = state;
	}
	
	// 普通方法
	public static String getName(Integer state) {
		if (state == null) {
			return null;
		}
		for (MessageRepeatTypeEnum c : values()) {
			if (c.getState().equals(state)) {
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
}
