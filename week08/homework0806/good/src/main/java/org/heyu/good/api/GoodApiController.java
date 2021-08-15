package org.heyu.good.api;

import org.heyu.common.api.good.GoodApi;
import org.heyu.common.api.good.OrderGoodDTO;
import org.heyu.good.service.OrderGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author heyu
 * @date 2021/8/15
 */
@RestController
public class GoodApiController implements GoodApi {

    @Autowired
    private OrderGoodService orderGoodService;


    @Override
    public void addOrderGood(@RequestBody OrderGoodDTO orderGood) {
        orderGoodService.addOrderGood(orderGood);
    }
}
