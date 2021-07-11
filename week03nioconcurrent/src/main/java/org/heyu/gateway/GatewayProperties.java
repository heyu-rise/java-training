package org.heyu.gateway;

import lombok.Data;
import org.heyu.gateway.router.RouteDefinition;

import java.util.List;

/**
 * @author heyu
 * @date 2021/7/8
 */
@Data
public class GatewayProperties {

    /**
     * 端口
     */
    private Integer port = 8888;

    /**
     * 路由
     */
    private List<RouteDefinition> routes;

}
