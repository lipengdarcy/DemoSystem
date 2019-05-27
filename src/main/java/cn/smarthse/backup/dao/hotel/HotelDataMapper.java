package cn.smarthse.backup.dao.hotel;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.smarthse.backup.entity.hotel.HotelData;
import cn.smarthse.framework.generic.GenericDao;

public interface HotelDataMapper extends GenericDao<HotelData> {
	/**
	 * 分页查询
	 * 
	 * @param index
	 *            页码
	 * @param count
	 *            每页显示条目
	 */
	List<HotelData> getPage(@Param(value = "index") Integer index, @Param(value = "count") Integer count);
}