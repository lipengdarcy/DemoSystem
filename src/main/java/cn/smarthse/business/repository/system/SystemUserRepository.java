package cn.smarthse.business.repository.system;

import org.springframework.data.mongodb.repository.MongoRepository;

import cn.smarthse.business.collection.system.SystemUser;

/**
 * 系统用户
 */

public interface SystemUserRepository extends MongoRepository<SystemUser, String> {

	// 这个方法名不能乱写，findByXXX，那么对于的类中必须有XXX字段。也就是说对应的数据库中一定要存在XXX字段对应的列
	// public Page<SystemUser> findByname(String uid, Pageable pageable);

}
