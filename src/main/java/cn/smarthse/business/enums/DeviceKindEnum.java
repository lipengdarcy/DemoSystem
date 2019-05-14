/**
 * 
 */
package cn.smarthse.business.enums;

/**
 * 《设备种类》
 * 	 * 1-普通仪器
       2-热释光剂量仪
       3-多功能X辐射剂量仪
       4-X、γ射线巡测仪
 * 
 * @Project:  GiianSystem
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8> 
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2019年3月4日-下午3:21:45
 */
public enum DeviceKindEnum {
	kind1("普通仪器", 1),
	kind2("热释光剂量仪", 2),
	kind3("多功能X辐射剂量仪", 3),
	kind4("X、γ射线巡测仪", 4),
	;
	// 成员变量
	private String name;
	private Integer kind;
	// 构造方法

	private DeviceKindEnum(String name, Integer kind) {
		this.name = name;
		this.kind = kind;
	}
	
	// 普通方法
	public static String getName(Integer state) {
		if (state == null) {
			return null;
		}
		for (DeviceKindEnum c : values()) {
			if (c.getKind().equals(state)) {
				return c.name;
			}
		}
		return null;
	}
	
	public static Integer getState(String name) {
		if (name == null || name.equals("")) {
			return null;
		}
		for (DeviceKindEnum c : values()) {
			if (c.getName().equals(name)) {
				return c.getKind();
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

	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}

	
}
