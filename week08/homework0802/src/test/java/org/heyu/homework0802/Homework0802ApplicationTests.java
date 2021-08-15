package org.heyu.homework0802;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import org.heyu.homework0802.dao.OrderMapper;
import org.heyu.homework0802.model.Order;
import org.heyu.homework0802.service.IOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class Homework0802ApplicationTests {

	@Autowired
	private IOrderService orderService;

	@Autowired
	private OrderMapper orderMapper;

	@Test
	void sqlType1() {
		orderService.addOrder();
	}

	@Test
	void sqlType2() {
		Order order = orderService.getOrder(633665885030056126l);
		log.info(order.getAddress());
	}

	@Test
	void sqlType3() {
		log.info(String.valueOf(orderService.modifyOrder()));
	}

	@Test
	void sqlType4() {
		orderService.deleteOrder();
	}

	@Test
	void insert2() {
		String dateStr = "20210815";
		BigDecimal cost = new BigDecimal(1000);
		Date date = new Date();
		IntStream.range(1000, 2000).parallel().forEach(ab -> {
			List<Order> orderList = new ArrayList<>(1000);
			IntStream.range(ab * 1000, (ab + 1) * 1000).forEach(a -> {
				Order order = new Order();
				order.setUserId((long) a + 1);
				order.setAddress("北京");
				order.setOrderCode(dateStr + a);
				order.setCost(cost);
				order.setOrderTime(date);
				order.setCreateTime(date);
				order.setUpdateTime(date);
				orderList.add(order);
			});
			orderMapper.insertBatch(orderList);
		});
	}

}
