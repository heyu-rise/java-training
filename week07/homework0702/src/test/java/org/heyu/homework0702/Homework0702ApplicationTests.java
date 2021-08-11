package org.heyu.homework0702;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import org.heyu.homework0702.dao.OrderMapper;
import org.heyu.homework0702.model.Order;
import org.heyu.homework0702.thread.MyRejectExecutionHandler;
import org.heyu.homework0702.thread.MyThreadFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Homework0702ApplicationTests {

	@Autowired
	private OrderMapper orderMapper;

	@Test
	void insert1() {
		String dateStr = "20210804";
		BigDecimal cost = new BigDecimal(1000);
		Date date = new Date();
		IntStream.range(0, 100).forEach(ab -> {
			List<Order> orderList = new ArrayList<>(10000);
			IntStream.range(ab * 10000, (ab + 1) * 10000).forEach(a -> {
				Order order = new Order();
				order.setId((long) a);
				order.setUserId((long) a);
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

	@Test
	void insert2() {
		String dateStr = "20210804";
		BigDecimal cost = new BigDecimal(1000);
		Date date = new Date();
		IntStream.range(0, 1000).parallel().forEach(ab -> {
			List<Order> orderList = new ArrayList<>(1000);
			IntStream.range(ab * 1000, (ab + 1) * 1000).forEach(a -> {
				Order order = new Order();
				order.setId((long) a);
				order.setUserId((long) a);
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

	@Test
	void insert3() throws InterruptedException {
		BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
		MyThreadFactory threadFactory = new MyThreadFactory("apply");
		MyRejectExecutionHandler rejectExecutionHandler = new MyRejectExecutionHandler();
		ExecutorService executorService = new ThreadPoolExecutor(50, 50, 2, TimeUnit.MINUTES, queue, threadFactory,
				rejectExecutionHandler);
		String dateStr = "20210804";
		BigDecimal cost = new BigDecimal(1000);
		Date date = new Date();
		CountDownLatch latch = new CountDownLatch(500);
		IntStream.range(0, 500).forEach(ab -> {
			executorService.execute(() -> {
				try {
					List<Order> orderList = new ArrayList<>(2000);
					IntStream.range(ab * 2000, (ab + 1) * 2000).forEach(a -> {
						Order order = new Order();
						order.setId((long) a);
						order.setUserId((long) a);
						order.setAddress("北京");
						order.setOrderCode(dateStr + a);
						order.setCost(cost);
						order.setOrderTime(date);
						order.setCreateTime(date);
						order.setUpdateTime(date);
						orderList.add(order);
					});
					orderMapper.insertBatch(orderList);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					latch.countDown();
				}
			});
		});
		latch.await();
	}

}
