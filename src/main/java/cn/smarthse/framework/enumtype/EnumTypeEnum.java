package cn.smarthse.framework.enumtype;

import cn.smarthse.framework.util.enumcommons.EnumAble;

/**
 * 存储枚举类的枚举类
 * 	注:
 * 		主要是在自定义freeMark标签中使用
 * 		标签中的enumType属性值为此枚举类的value值
 * @author liaoly
 *
 */
public enum EnumTypeEnum implements EnumAble<String> {
	
	 type1("cn.smarthse.framework.enumtype.checkrecord.ExposureMode", "ExposureMode"),
	 type2("cn.smarthse.framework.enumtype.checkrecord.SignLightType", "SignLightType"),
	 type3("cn.smarthse.framework.enumtype.checkreport.BusinessType", "BusinessType"),
	 type4("cn.smarthse.framework.enumtype.DataTemplateType", "DataTemplateType"),
	 type5("cn.smarthse.framework.enumtype.checkrecord.FilterThrough", "FilterThrough"),
	 type6("cn.smarthse.framework.enumtype.checkrecord.MammaryMachineCompany","MammaryMachineCompany");
	
	
	// 成员变量
	private String name;
	private String value;

	// 构造方法
	private EnumTypeEnum(String name, String value) {
		this.name = name;
		this.value = value;
	}

	// get set 方法
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String index) {
		this.value = index;
	}



	public static String getName(String index) {
		for (EnumTypeEnum c : EnumTypeEnum.values()) {
			if (c.getValue().equals(index)) {
				return c.name;
			}
		}
		return null;
	}

	@Override
	public String getValueByName() {
		return this.value;
	}

	@Override
	public String getNameByValue() {
		return this.name;
	}
}
