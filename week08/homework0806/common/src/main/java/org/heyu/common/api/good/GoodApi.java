package org.heyu.common.api.good;

import org.dromara.hmily.annotation.Hmily;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author heyu
 * @date 2021/8/15
 */
@RequestMapping("/api/good")
public interface GoodApi {

    /**
     * 添加訂單商品
     * @param orderGood
     */
    @Hmily
    @PostMapping("/add/order-good")
    void addOrderGood(@RequestBody OrderGoodDTO orderGood);

}
