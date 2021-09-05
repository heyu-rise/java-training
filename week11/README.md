[TOC]

# 第11周作业说明

## 基于 Redis 封装分布式数据操作

### 1、在 Java 中实现一个简单的分布式锁

```http
https://github.com/heyu-rise/java-training/blob/main/week11/src/main/java/org/heyu/week11/lock/RedisLockAop.java
```

> 使用redis的原子自增方法，模拟信号量的方法实现

```java
@Aspect
@Component
public class RedisLockAop {

    private static final long READY = 1L;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Pointcut("@annotation(org.heyu.week11.lock.RedisLock)")
    public void redisLock() {
    }

    @Before("@annotation(redisLock)")
    public void before(JoinPoint point, RedisLock redisLock) throws InterruptedException {
        String key = redisLock.key();
        Long a = stringRedisTemplate.opsForValue().increment(key);
        if (!Objects.equals(a, READY)) {
            while (Objects.equals(a, READY)) {
                stringRedisTemplate.opsForValue().decrement(key);
                Thread.sleep(50);
                a = stringRedisTemplate.opsForValue().increment(key);
            }
        }
    }

    @After("@annotation(redisLock)")
    public void after(JoinPoint point, RedisLock redisLock) {
        stringRedisTemplate.opsForValue().decrement(redisLock.key());
    }

}
```

### 2、在 Java 中实现一个分布式计数器，模拟减库存

```http
https://github.com/heyu-rise/java-training/blob/main/week11/src/main/java/org/heyu/week11/service/impl/RedisServiceImpl.java
```

  > 使用redis的原子自增方法

```java
stringRedisTemplate.opsForValue().increment("redis:count");
stringRedisTemplate.opsForValue().decrement("redis:count");
```

