package cn.smarthse.backup.model.hotel;

import java.util.Date;

import cn.smarthse.backup.entity.hotel.HotelData;
import lombok.Data;

public @Data class HotelDataModel extends HotelData{


	/**
	 * 入住开始时间
	 */
	private Date begin;
	
	/**
	 * 入住结束时间
	 */
	private Date end;

}