package cn.smarthse.sharding.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;
import cn.smarthse.sharding.dao.OrderDao;
import cn.smarthse.sharding.dao.OrderItemDao;
import cn.smarthse.sharding.entity.Order;
import cn.smarthse.sharding.entity.OrderItem;

@Service
public class ShardingDemoService {

	@Resource
	private OrderDao orderRepository;

	@Resource
	private OrderItemDao orderItemRepository;

	/**
	 * 初始化建表
	 */
	public void init() {
		orderRepository.createIfNotExistsTable();
		orderItemRepository.createIfNotExistsTable();
		orderRepository.truncateTable();
		orderItemRepository.truncateTable();
	}

	/**
	 * 创建数据
	 */
	public void createData() {
		List<Long> orderIds = new ArrayList<>(10);
		// 用户id=12，为偶数的落在master0
		for (int i = 0; i < 10; i++) {
			Order order = new Order();
			order.setUserId(12);
			order.setStatus("测试主从同步");
			orderRepository.insert(order);
			long orderId = order.getOrderId();
			orderIds.add(orderId);

			OrderItem item = new OrderItem();
			item.setOrderId(orderId);
			item.setUserId(12);
			item.setStatus("测试主从同步");
			orderItemRepository.insert(item);
		}

		// 用户id=13，为奇数的落在master1，
		for (int i = 0; i < 10; i++) {
			Order order = new Order();
			order.setUserId(13);
			order.setStatus("测试主从同步");
			orderRepository.insert(order);
			long orderId = order.getOrderId();
			orderIds.add(orderId);

			OrderItem item = new OrderItem();
			item.setOrderId(orderId);
			item.setUserId(13);
			item.setStatus("测试主从同步");
			orderItemRepository.insert(item);
		}

	}

	/**
	 * 查询数据
	 */
	public JqGridData<OrderItem> getData(JqGridParam param) {
		PageHelper.startPage((int) param.getPage(), (int) param.getRows());
		Page<OrderItem> list = (Page<OrderItem>) orderItemRepository.selectAll();
		JqGridData<OrderItem> data = new JqGridData<OrderItem>(list, param);
		return data;
	}

	/**
	 * 清空数据，删除表
	 */
	public void deleteData() {
		orderItemRepository.dropTable();
		orderRepository.dropTable();
	}
}
