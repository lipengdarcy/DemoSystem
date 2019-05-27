package cn.smarthse.business.repository.system;

import org.springframework.data.mongodb.repository.MongoRepository;

import cn.smarthse.business.collection.system.SystemRole;

/**
 * 系统角色
 */

public interface SystemRoleRepository extends MongoRepository<SystemRole, String> {


}
