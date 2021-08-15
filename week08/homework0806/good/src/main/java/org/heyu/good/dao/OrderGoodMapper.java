package org.heyu.good.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.heyu.good.model.OrderGood;

/**
 * @author shy19
 */
@Mapper
public interface OrderGoodMapper extends BaseMapper<OrderGood>{

    /**
     * 获取订单商品
     * @param orderId
     * @return
     */
    List<OrderGood> getByOrderId(@Param("orderId") Long orderId);
}