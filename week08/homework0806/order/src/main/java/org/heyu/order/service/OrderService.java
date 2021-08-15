package org.heyu.order.service;

import org.heyu.order.model.Order;

/**
 * @author heyu
 * @date 2021/8/15
 */
public interface OrderService {

    /**
     * 添加订单
     * @param order
     */
    void addOrder(Order order, boolean throwEx);

}
