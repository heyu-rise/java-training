package org.heyu.order;

import java.math.BigDecimal;
import java.util.Date;

import org.heyu.order.model.Order;
import org.heyu.order.service.OrderService;
import org.heyu.order.utils.SnowFlake;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderApplicationTests {

	@Autowired
	private OrderService orderService;

	@Test
	void contextLoads() {
		String dateStr = "2021815";
		BigDecimal cost = new BigDecimal(1000);
		Long a = 1999999L;
		Date date = new Date();
		Order order = new Order();
		order.setId(SnowFlake.getInstance().nextId());
		order.setUserId(a);
		order.setAddress("北京");
		order.setOrderCode(dateStr + a);
		order.setCost(cost);
		order.setOrderTime(date);
		order.setCreateTime(date);
		order.setUpdateTime(date);
		orderService.addOrder(order, false);
	}

}
