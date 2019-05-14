package cn.smarthse.business.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import cn.smarthse.backup.entity.hotel.HotelData;

/**
 * mongodb 版本的hoteldata
 */

public interface MongoHotelDataDao extends MongoRepository<HotelData, String> {

	// 这个方法名不能乱写，findByXXX，那么对于的类中必须有XXX字段。也就是说对应的数据库中一定要存在XXX字段对应的列
	// public Page<SysAreaStandard> findByname(String uid, Pageable pageable);

}
