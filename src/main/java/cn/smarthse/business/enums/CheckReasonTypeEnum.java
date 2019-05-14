/**
 * 
 */
package cn.smarthse.business.enums;

/**
 * 《依据类型》
 * 
 * @Project:  platform-facade
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8> 
 * @author Xuyd(徐颖东) [xyd.900919@qq.com]
 * @since 2018年6月13日-上午9:05:50
 */
public enum CheckReasonTypeEnum {

	/**
	 * 检测依据
	 */
	testing("检测依据", (byte) 1),
	
	/**
	 * 评价依据
	 */
	evaluate("评价依据", (byte) 2);

	// 成员变量
	private String name;
	private Byte value;
	// 构造方法

	private CheckReasonTypeEnum(String name, Byte value) {
		this.name = name;
		this.value = value;
	}
	// 普通方法
	public static String getName(Byte value) {
		if (value == null) {
			return null;
		}
		for (CheckReasonTypeEnum c : CheckReasonTypeEnum.values()) {
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
	public void setValue(Byte value) {
		this.value = value;
	}
}

