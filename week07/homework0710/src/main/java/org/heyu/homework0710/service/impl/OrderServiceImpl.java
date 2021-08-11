package org.heyu.homework0710.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.heyu.homework0710.dao.OrderMapper;
import org.heyu.homework0710.model.Order;
import org.heyu.homework0710.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 * @author heyu
 * @date 2021/8/10
 */
@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private OrderMapper orderMapper;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int addOrder() {
		String dateStr = "20210804";
		BigDecimal cost = new BigDecimal(1000);
		Long a = 1999998L;
		Date date = new Date();
		Order order = new Order();
		order.setId(a);
		order.setUserId(a);
		order.setAddress("北京");
		order.setOrderCode(dateStr + a);
		order.setCost(cost);
		order.setOrderTime(date);
		order.setCreateTime(date);
		order.setUpdateTime(date);
		return orderMapper.insert(order);
	}

	@Override
	public Order getOrder(long id) {

		return orderMapper.selectByPrimaryKey(id);
	}
}
