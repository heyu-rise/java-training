package org.heyu.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dromara.hmily.annotation.HmilyTCC;
import org.heyu.common.api.good.GoodApi;
import org.heyu.common.api.good.OrderGoodDTO;
import org.heyu.common.api.good.OrderGoodItemDto;
import org.heyu.order.dao.OrderMapper;
import org.heyu.order.model.Order;
import org.heyu.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author heyu
 * @date 2021/8/15
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private GoodApi goodApi;

	@HmilyTCC(confirmMethod = "confirmAddOrder", cancelMethod = "cancelAddOrder")
	@Override
	public void addOrder(Order order, boolean throwEx) {
		String dateStr = "20210815";
		BigDecimal cost = new BigDecimal(1000);
		Long a = 1999999L;
		Date date = new Date();
		// order.setId(a);
		order.setUserId(a);
		order.setAddress("北京");
		order.setOrderCode(dateStr + order.getId());
		order.setCost(cost);
		order.setOrderTime(date);
		order.setCreateTime(date);
		order.setUpdateTime(date);
		orderMapper.insertSelective(order);

		OrderGoodDTO orderGoodDTO = new OrderGoodDTO();
		orderGoodDTO.setOrderId(order.getId());
		OrderGoodItemDto orderGoodItemDto = new OrderGoodItemDto();
		orderGoodItemDto.setGoodId(1L);
		orderGoodItemDto.setQuantity(new BigDecimal(1));
		Date now = new Date();
		orderGoodItemDto.setCreateTime(now);
		orderGoodItemDto.setUpdateTime(now);
		List<OrderGoodItemDto> goodList = new ArrayList<>(1);
		goodList.add(orderGoodItemDto);
		orderGoodDTO.setGoodList(goodList);
		goodApi.addOrderGood(orderGoodDTO);
		if (throwEx) {
			throw new RuntimeException("aaa");
		}
	}

	public void confirmAddOrder(Order order) {
		log.info("confirmAddOrder");
	}

	public void cancelAddOrder(Order order) {
		log.info("cancelAddOrder");

	}
}
