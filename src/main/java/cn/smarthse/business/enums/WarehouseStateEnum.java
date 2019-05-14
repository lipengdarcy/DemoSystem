/**
 * 
 */
package cn.smarthse.business.enums;

/**
 * 《出入库工作完成情况状态》
 * : 0-未开始,1-进行中,2-已完成
 * 
 * @Project:  GiianSystem
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8> 
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2019年3月7日-上午11:14:40
 */
public enum WarehouseStateEnum {
	WarehouseState("未开始", 0),
	Warehouse_ing("进行中", 1),
	Warehouse_complete("已完成", 2),
	;
	// 成员变量
	private String name;
	private Integer warehouseState;
	// 构造方法

	private WarehouseStateEnum(String name, Integer warehouseState) {
		this.name = name;
		this.warehouseState = warehouseState;
	}
	
	// 普通方法
	public static String getName(Integer state) {
		if (state == null) {
			return null;
		}
		for (WarehouseStateEnum c : values()) {
			if (c.getWarehouseState().equals(state)) {
				return c.name;
			}
		}
		return null;
	}
	
	public static Integer getState(String name) {
		if (name == null || name.equals("")) {
			return null;
		}
		for (WarehouseStateEnum c : values()) {
			if (c.getName().equals(name)) {
				return c.getWarehouseState();
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

	public Integer getWarehouseState() {
		return warehouseState;
	}

	public void setWarehouseState(Integer warehouseState) {
		this.warehouseState = warehouseState;
	}

	
}
