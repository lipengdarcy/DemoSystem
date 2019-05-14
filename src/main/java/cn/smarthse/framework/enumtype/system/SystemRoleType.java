package cn.smarthse.framework.enumtype.system;

import java.util.List;

import cn.smarthse.business.entity.system.SysUserRole;


/**
 * 系统默认角色 枚举类
 * 
 * @author lipeng
 */
public enum SystemRoleType {

	admin("管理员", 0), checkAdmin("检测主管", 1), checkUser("检测人员", 2), dataAudit("原始记录审核人员", 3), deviceAdmin("仪器管理人员",
			4), reportAdmin("报告管理人员", 5), reportMaker("报告编制人员", 6), businessUser("业务人员", 7), businessAdmin("业务管理人员", 8);

	// 成员变量
	private String name;
	private Integer value;

	private SystemRoleType(String name, Integer value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer index) {
		this.value = index;
	}

	/**
	 * 根据角色id列表获取角色名称
	 * 
	 * @param roleList
	 *            枚举值集合
	 * @return 枚举值名称列表，逗号分隔
	 */
	public static String getRoleName(Integer[] roleList) {
		if (roleList == null || roleList.length == 0)
			return null;
		StringBuffer s = new StringBuffer();
		for (Integer index : roleList) {
			for (SystemRoleType c : SystemRoleType.values()) {
				if (index.equals(c.getValue())) {
					s.append(c.getName());
					s.append(",");
				}
			}
		}
		return s.substring(0, s.length() - 1).toString();
	}

	/**
	 * 根据角色id列表获取角色名称
	 * 
	 * @param roleList
	 *            枚举值集合
	 * @return 枚举值名称列表，逗号分隔
	 */
	public static String getRoleName(List<SysUserRole> roleList) {
		if (roleList == null || roleList.size() == 0)
			return null;
		StringBuffer s = new StringBuffer();
		for (SysUserRole index : roleList) {
			for (SystemRoleType c : SystemRoleType.values()) {
				if (index.getRoleId().equals(c.getValue())) {
					s.append(c.getName());
					s.append(",");
				}
			}
		}
		return s.substring(0, s.length() - 1).toString();
	}

}
