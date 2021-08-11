package org.heyu.homework0709;

import org.heyu.homework0709.model.Order;
import org.heyu.homework0709.service.IOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class Homework0709ApplicationTests {

	@Autowired
	IOrderService orderService;

	@Test
	void sqlType() {
		Order order = orderService.getOrder(1L);
		log.info(order.getAddress());
	}

}
