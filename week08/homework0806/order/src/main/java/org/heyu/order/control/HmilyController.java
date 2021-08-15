package org.heyu.order.control;

import java.math.BigDecimal;
import java.util.Date;

import org.heyu.order.model.Order;
import org.heyu.order.service.OrderService;
import org.heyu.order.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author heyu
 * @date 2021/8/15
 */
@RestController
public class HmilyController {

    @Autowired
    private OrderService orderService;



    @GetMapping("/test1")
    public void test1(){
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
        orderService.addOrder(order, true);
    }

    @GetMapping("/test2")
    public void test2(){
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
