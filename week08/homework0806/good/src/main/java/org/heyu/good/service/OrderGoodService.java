package org.heyu.good.service;

import org.heyu.common.api.good.OrderGoodDTO;

/**
 * @author heyu
 * @date 2021/8/15
 */
public interface OrderGoodService {

    /**
     * 添加订单商品
     * @param orderGoodDTO
     */
    void addOrderGood(OrderGoodDTO orderGoodDTO);

}
