package cn.smarthse.business.enums.skill;

/**
 * 
 * 《剂量读数状态枚举》
 * 
 * 
 * @Project:  GiianSystem
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.7> 
 * @author horsy(何世壹) [hsy@smarthse.cn]
 * @since 2019年3月14日-上午10:16:20
 */
public enum InstrumentReadingsStatusEnum {


	/**
	 * 检测依据
	 */
	invalid("失效",  0),
	
	/**
	 * 评价依据
	 */
	valid("有效",  1);

	// 成员变量
	private String name;
	private Integer value;
	// 构造方法

	private InstrumentReadingsStatusEnum(String name, Integer value) {
		this.name = name;
		this.value = value;
	}
	// 普通方法
	public static String getName(Integer value) {
		if (value == null) {
			return null;
		}
		for (InstrumentReadingsStatusEnum c : InstrumentReadingsStatusEnum.values()) {
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
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}

}
