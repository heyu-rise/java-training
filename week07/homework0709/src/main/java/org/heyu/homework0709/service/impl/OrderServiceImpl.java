package org.heyu.homework0709.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.heyu.homework0709.dao.IOrderMapper;
import org.heyu.homework0709.database.SqlType;
import org.heyu.homework0709.database.SqlTypeEnum;
import org.heyu.homework0709.database.SqlTypeUtils;
import org.heyu.homework0709.model.Order;
import org.heyu.homework0709.service.IOrderService;
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

	@SqlType(SqlTypeEnum.MODIFY)
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int addOrder() {
		IOrderMapper orderMapper = SqlTypeUtils.getValue();
		String dateStr = "20210804";
		BigDecimal cost = new BigDecimal(1000);
		Long a = 1999999L;
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

	@SqlType(SqlTypeEnum.READ_ONLY)
	@Override
	public Order getOrder(long id) {
        IOrderMapper orderMapper = SqlTypeUtils.getValue();
		return orderMapper.selectByPrimaryKey(id);
	}
}
