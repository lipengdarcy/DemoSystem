package cn.smarthse.business.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import cn.smarthse.business.entity.system.SysAreaStandard;

/**
 * mongodb 版本的行政区域
 */

public interface MongoAreaDao extends MongoRepository<SysAreaStandard, String> {

	// 这个方法名不能乱写，findByXXX，那么对于的类中必须有XXX字段。也就是说对应的数据库中一定要存在XXX字段对应的列
	// public Page<SysAreaStandard> findByname(String uid, Pageable pageable);

}
