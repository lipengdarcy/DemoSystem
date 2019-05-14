/**
 * 
 */
package cn.smarthse.business.enums;

/**
 * 《设备出入库状态》
 * (0-备用,1-入库,2-出库)
 * 
 * @Project:  GiianSystem
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8> 
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2019年3月7日-上午11:14:40
 */
public enum DeviceWarehouseStateEnum {
	WarehouseState("标记", 0),
	/** 入库 **/
	Warehouse_in("入库", 1),
	/** 出库 **/
	Warehouse_out("出库", 2),
	;
	// 成员变量
	private String name;
	private Integer warehouseState;
	// 构造方法

	private DeviceWarehouseStateEnum(String name, Integer warehouseState) {
		this.name = name;
		this.warehouseState = warehouseState;
	}
	
	// 普通方法
	public static String getName(Integer state) {
		if (state == null) {
			return null;
		}
		for (DeviceWarehouseStateEnum c : values()) {
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
		for (DeviceWarehouseStateEnum c : values()) {
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
