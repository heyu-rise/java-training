package org.heyu.good.service.impl;

import java.util.List;

import org.dromara.hmily.annotation.HmilyTCC;
import org.heyu.common.api.good.OrderGoodDTO;
import org.heyu.common.api.good.OrderGoodItemDto;
import org.heyu.good.dao.OrderGoodMapper;
import org.heyu.good.model.OrderGood;
import org.heyu.good.service.OrderGoodService;
import org.heyu.good.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author heyu
 * @date 2021/8/15
 */
@Slf4j
@Service
public class OrderGoodServiceImpl implements OrderGoodService {

    @Autowired
    private OrderGoodMapper orderGoodMapper;

    @HmilyTCC(confirmMethod = "confirmAddOrderGood", cancelMethod = "cancelAddOrderGood")
    @Override
    public void addOrderGood(OrderGoodDTO orderGoodDTO) {
        List<OrderGoodItemDto> orderGoodItemDtoList = orderGoodDTO.getGoodList();
        if (orderGoodItemDtoList == null || orderGoodItemDtoList.size() == 0) {
            return;
        }
        for (OrderGoodItemDto orderGoodItemDto : orderGoodItemDtoList) {
            OrderGood orderGood = new OrderGood();
            orderGood.setId(SnowFlake.getInstance().nextId());
            orderGood.setGoodId(orderGoodItemDto.getGoodId());
            orderGood.setOrderId(orderGoodDTO.getOrderId());
            orderGood.setQuantity(orderGoodItemDto.getQuantity());
            orderGood.setCreateTime(orderGoodItemDto.getCreateTime());
            orderGood.setUpdateTime(orderGoodItemDto.getCreateTime());
            orderGoodMapper.insertSelective(orderGood);
        }
    }

    public void confirmAddOrderGood(OrderGoodDTO orderGoodDTO) {
        log.info("confirmAddOrderGood");
    }

    public void cancelAddOrderGood(OrderGoodDTO orderGoodDTO) {
        log.info("cancelAddOrderGood");
        List<OrderGood> orderGoodList = orderGoodMapper.getByOrderId(orderGoodDTO.getOrderId());
        for (OrderGood orderGood : orderGoodList) {
            orderGoodMapper.deleteByPrimaryKey(orderGood.getId());
        }
    }
}
