package org.heyu.order.feign;

import org.heyu.common.api.good.GoodApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author heyu
 * @date 2021/8/15
 */
@FeignClient(name = "heyu-good-service")
public interface GoodApiFeign extends GoodApi {

}
