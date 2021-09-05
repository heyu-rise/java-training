package org.heyu.week11.service.impl;

import org.heyu.week11.lock.RedisLock;
import org.heyu.week11.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author heyu
 * @date 2021/9/5
 */
@Service
public class RedisServiceImpl implements RedisService {

    private static int a = 0;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RedisLock(key = "redis:lockTest")
    @Override
    public void lockTest() {
        a++;
    }

    @Override
    public void test2() {
        stringRedisTemplate.opsForValue().increment("redis:count");
        stringRedisTemplate.opsForValue().decrement("redis:count");
    }

}
