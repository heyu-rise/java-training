[TOC]

# 第七周作业说明

### 3、按自己设计的表结构，插入 100 万订单模拟数据，测试不同方式的插入效率

> 我在本地起了一个`mysql`的`docker`镜像，我本机是`6核12线程`的`cpu`，默频`2.6GHz`,睿频`4.3GHz`。

#### 3.1、测试数据

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
			List<Order> orderList = new ArrayList<>(5000);
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
| 25       | 40000        | 42秒274毫秒 |
| 50       | 20000        | 42秒482毫秒 |
| 100      | 10000        | 42秒791毫秒 |
| 200      | 5000         | 43秒764毫秒 |
| 500      | 2000         | 46秒538毫秒 |
| 1000     | 1000         | 48秒607毫秒 |

- **多条多线程插入**

> 插入速度较快，只能使用`系统线程池`或者`自定义线程池`，不使用线程池会内存溢出。

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
| 25     | 40000        | 25秒103毫秒 |
| 50     | 20000        | 20秒216毫秒 |
| 100    | 10000        | 17秒626毫秒 |
| 200    | 5000         | 15秒480毫秒 |
| 500    | 2000         | 13秒364毫秒 |
| 1000   | 1000         | 13秒240毫秒 |
| 2000   | 500          | 13秒718毫秒 |

#### 3.2、结论

1. 在单线程插入的时候，单次插入数据库能够承受的最大数据量，则插入速度最快。
2. 在多线程插入的时候，需要具体压测得到数据，而我目前得出的结论是每次插入1000条数据的时候，插入的速度最快

### 9、读写分离 - 动态切换数据源版本 1.0

> 代码地址

```http
https://github.com/heyu-rise/java-training/tree/main/week07/homework0710
```

> 配置多数据源,使用`aop`,`ThreadLocal`加`注解`的方式完成数据库切换

```java
public enum SqlTypeEnum {

	READ_ONLY(), MODIFY();

}
```

```java
@Aspect
@Component
public class SqlTypeAop {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderMapper2 orderMapper2;

	@Pointcut("@annotation(org.heyu.homework0709.database.SqlType)")
	public void sqlType() {
	}

	@Before("sqlType()")
	public void before(JoinPoint point) {
		MethodSignature sign = (MethodSignature) point.getSignature();
		Method method = sign.getMethod();
		SqlType sqlType = method.getAnnotation(SqlType.class);
		SqlTypeEnum sqlTypeEnum = sqlType.value();
		if (Objects.equals(sqlTypeEnum, SqlTypeEnum.MODIFY)) {
			SqlTypeUtils.setValue(orderMapper);
		} else {
			SqlTypeUtils.setValue(orderMapper2);
		}
	}

	@After("sqlType()")
	public void after(JoinPoint point) {
		SqlTypeUtils.remove();
	}
}
```

```java
public class SqlTypeUtils {

    private static ThreadLocal<IOrderMapper> threadLocal = new ThreadLocal<>();

    public static void setValue(IOrderMapper iOrderMapper) {
        threadLocal.set(iOrderMapper);
    }

    public static IOrderMapper getValue() {
        return threadLocal.get();
    }

    public static void remove(){
        threadLocal.remove();
    }

}
```

```java
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
```

### 10、读写分离 - 数据库框架版本 2.0

> `shardingsphere`读写分离配置

```yaml
spring:
  shardingsphere:
    datasource:
      names: master,slave0
      master:
        driver-class-name: com.mysql.cj.jdbc.Driver
        jdbc-url: jdbc:mysql://127.0.0.1:3307/week06?useSSL=false
        username: root
        password: mn814561293
        type: com.zaxxer.hikari.HikariDataSource
      slave0:
        driver-class-name: com.mysql.cj.jdbc.Driver
        jdbc-url: jdbc:mysql://127.0.0.1:3308/week06?useSSL=false
        username: root
        password: mn814561293
        type: com.zaxxer.hikari.HikariDataSource
    masterslave:
      load-balance-algorithm-type: round_robin
      master-data-source-name: master
      name: ms
      slave-data-source-names: slave0
    props:
      sql:
        show: true

mybatis:
  mapper-locations: classpath:mapper/*xml
```

