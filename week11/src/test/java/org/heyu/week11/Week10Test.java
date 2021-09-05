package org.heyu.week11;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author heyu
 * @date 2021/9/5
 */
@Slf4j
@SpringBootTest
public class Week10Test {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void test1() {
        stringRedisTemplate.opsForValue().set("key:1", "11111");
    }

    @Test
    void test2() {
        String x = stringRedisTemplate.opsForValue().get("key:1");
        log.info(x);
    }

}
