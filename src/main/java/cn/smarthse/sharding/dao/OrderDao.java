package cn.smarthse.sharding.dao;

import cn.smarthse.sharding.entity.Order;

public interface OrderDao {
    
    void createIfNotExistsTable();
    
    void truncateTable();
    
    Long insert(Order model);
    
    void delete(Long orderId);
    
    void dropTable();
}
