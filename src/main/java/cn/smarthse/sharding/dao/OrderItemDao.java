package cn.smarthse.sharding.dao;

import java.util.List;
import cn.smarthse.sharding.entity.OrderItem;

public interface OrderItemDao {
    
    void createIfNotExistsTable();
    
    void truncateTable();
    
    Long insert(OrderItem model);
    
    void delete(Long orderItemId);
    
    List<OrderItem> selectAll();
    
    void dropTable();
}
