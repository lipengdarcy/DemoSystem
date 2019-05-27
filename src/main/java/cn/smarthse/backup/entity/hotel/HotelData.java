package cn.smarthse.backup.entity.hotel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 
 * 如家入住记录
 */
@Table(name = "hotel_data")
public @Data class HotelData {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 卡号
	 */
	private String cardno;

	/**
	 */
	private String descriot;

	/**
	 * 证件类型
	 */
	private String ctftp;

	/**
	 * 证件号码
	 */
	private String ctfid;

	/**
	 * 性别
	 */
	private String gender;

	/**
	 * 生日
	 */
	private String birthday;

	/**
	 * 住址
	 */
	private String address;

	/**
	 * 邮编
	 */
	private String zip;

	/**
	 */
	private String dirty;

	/**
	 * 洲
	 */
	private String district1;

	/**
	 * 国家
	 */
	private String district2;

	/**
	 * 省
	 */
	private String district3;

	/**
	 * 市
	 */
	private String district4;

	/**
	 * 区
	 */
	private String district5;

	/**
	 * 
	 */
	private String district6;

	/**
	 * 名
	 */
	private String firstnm;

	/**
	 * 姓
	 */
	private String lastnm;

	/**
	 */
	private String duty;

	/**
	 * 手机
	 */
	private String mobile;

	/**
	 * 电话
	 */
	private String tel;

	/**
	 * 传真
	 */
	private String fax;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 名族
	 */
	private String nation;

	/**
	 */
	private String taste;

	/**
	 */
	private String education;

	/**
	 */
	private String company;

	/**
	 */
	private String ctel;

	/**
	 */
	private String caddress;

	/**
	 */
	private String czip;

	/**
	 */
	private String family;

	/**
	 * 入住时间
	 */
	private Date version;

}