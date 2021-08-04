# 第七周作业说明

#### 3、按自己设计的表结构，插入 100 万订单模拟数据，测试不同方式的插入效率

> 我在本地起了一个`mysql`的`docker`镜像，内存为`1G`，而我本机是`6核12线程`的`cpu`，默频`2.6GHz`,睿频`4.3GHz`

- **一条一条插入，或者用线程池一条一条插入**

> 非常慢，没有等到执行完成

- **多条分多次插入**

> 插入速度较快

```java
	@Test
	void insert1() {
		String dateStr = "20210804";
		BigDecimal cost = new BigDecimal(1000);
		Date date = new Date();
		IntStream.range(0, 200).forEach(ab -> {
			List<Order> orderList = new ArrayList<>(20048);
			IntStream.range(ab * 5000, (ab + 1) * 5000).forEach(a -> {
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
```

| 插入次数 | 每次插入条数 | 耗时        |
| -------- | ------------ | ----------- |
| 50       | 20000        | 42秒482毫秒 |
| 100      | 10000        | 42秒791毫秒 |
| 200      | 5000         | 43秒764毫秒 |

- **多条多线程插入**

> 插入速度较快，只能使用系统线程池或者自定义线程池，否则会内存溢出。

```java
	@Test
	void insert2() {
		String dateStr = "20210804";
		BigDecimal cost = new BigDecimal(1000);
		Date date = new Date();
		IntStream.range(0, 1000).parallel().forEach(ab -> {
			List<Order> orderList = new ArrayList<>(1024);
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
```
| 线程数 | 每次插入条数 | 耗时        |
| ------ | ------------ | ----------- |
| 50     | 20000        | 20秒216毫秒 |
| 100    | 10000        | 17秒626毫秒 |
| 200    | 5000         | 15秒480毫秒 |
| 500    | 2000         | 13秒364毫秒 |
| 1000   | 1000         | 13秒240毫秒 |
| 2000   | 500          | 13秒718毫秒 |
