package org.heyu.week11.lock;

import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author heyu
 * @date 2021/9/5
 */
@Aspect
@Component
public class RedisLockAop {

    private static final long OK = 0L;

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
