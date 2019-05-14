/**
 * 
 */
package cn.smarthse.business.enums;

/**
 * 《设备仪器状态》
 * 1：正常； 2：维修；3：停用；4：报废
 * 
 * @Project:  GiianSystem
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8> 
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2019年3月4日-上午10:53:16
 */
public enum DeviceStateEnum {
	normalState("正常", 1),
	repairState("维修", 2),
	disableState("停用", 3),
	scrapState("报废", 4),
	checkingState("检定中", 5),
	;
	// 成员变量
	private String name;
	private Integer state;
	// 构造方法

	private DeviceStateEnum(String name, Integer state) {
		this.name = name;
		this.state = state;
	}
	
	// 普通方法
	public static String getName(Integer state) {
		if (state == null) {
			return null;
		}
		for (DeviceStateEnum c : values()) {
			if (c.getState().equals(state)) {
				return c.name;
			}
		}
		return null;
	}
	
	public static Integer getState(String name) {
		if (name == null || name.equals("")) {
			return null;
		}
		for (DeviceStateEnum c : values()) {
			if (c.getName().equals(name)) {
				return c.getState();
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
