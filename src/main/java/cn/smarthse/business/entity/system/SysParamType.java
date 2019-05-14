package cn.smarthse.business.entity.system;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "sys_param_type")
public class SysParamType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 参数类型值
	 */
	private String typeValue;

	/**
	 * 参数类型编码
	 */
	private String typeCode;

	/**
	 * 类型分组
	 */
	private Integer typeGroup;

	/**
	 * 参数类型上级id
	 */
	private Integer pid;

	/**
	 * 是否可用
	 */
	private Byte isValid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue == null ? null : typeValue.trim();
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode == null ? null : typeCode.trim();
	}

	public Integer getTypeGroup() {
		return typeGroup;
	}

	public void setTypeGroup(Integer typeGroup) {
		this.typeGroup = typeGroup;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Byte getIsValid() {
		return isValid;
	}

	public void setIsValid(Byte isValid) {
		this.isValid = isValid;
	}
}