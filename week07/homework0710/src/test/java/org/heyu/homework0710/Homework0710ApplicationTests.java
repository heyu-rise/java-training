package org.heyu.homework0710;

import org.heyu.homework0710.model.Order;
import org.heyu.homework0710.service.IOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class Homework0710ApplicationTests {

	@Autowired
	IOrderService orderService;

	@Test
	void sqlType1() {
		orderService.addOrder();
	}

	@Test
	void sqlType2() {
		Order order = orderService.getOrder(1L);
		log.info(order.getAddress());
	}

}
