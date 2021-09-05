package org.heyu.week11.controller;

import org.heyu.week11.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author heyu
 * @date 2021/9/5
 */
@RestController
@RequestMapping("/week11")
public class TestController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/testRedis")
    public void testRedis(){
        redisService.lockTest();
    }

    @GetMapping("/testRedis2")
    public void testRedis2(){
        redisService.test2();
    }

}
