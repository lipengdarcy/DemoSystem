package cn.smarthse.business.enums.message;



/**
 * 《业务消息类型枚举》
 * 
 * 
 * @Project:  GiianSystem
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8> 
 * @author XiaoYi（肖奕) xy@smarthse.cn
 * @since 2019年3月11日-下午3:22:02
 */
public enum BusinessMessageTypeEnum {
	expireRemind("即将逾期提醒", 1),
	auditRemind("审核提醒", 2),
	beRelatedReming("被相关提醒",3),
	lostPartReming("丢件提醒",4),
	notSampleCollectionRemind("连续未收样提醒",5),
	customRemind("自定义提醒",6),
	staffTurnReming("人员变动提醒",7),
	staffAddRemind("人员添加提醒",8),
	otherRemind("其他提醒",9)
	;
	// 成员变量
	private String name;
	private Integer state;
	// 构造方法

	private BusinessMessageTypeEnum(String name, Integer state) {
		this.name = name;
		this.state = state;
	}
	
	// 普通方法
	public static String getName(Integer state) {
		if (state == null) {
			return null;
		}
		for (BusinessMessageTypeEnum c : values()) {
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