package org.heyu.week11.service;

/**
 * @author heyu
 * @date 2021/9/5
 */
public interface RedisService {

    /**
     * 锁测试
     */
    void lockTest();

    /**
     * 分布式计数
     */
    void test2();


}
