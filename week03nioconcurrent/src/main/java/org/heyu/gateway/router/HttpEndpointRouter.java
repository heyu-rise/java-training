package org.heyu.gateway.router;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author shy19
 */
public interface HttpEndpointRouter {

    /**
     * 获取转发路由
     *
     * @param fullRequest 请求对象
     * @param ctx         请求对象
     * @return 处理后转发地址
     */
    String route(FullHttpRequest fullRequest, ChannelHandlerContext ctx);

    /**
     * 获取netty转发路由
     *
     * @param fullRequest 请求对象
     * @param ctx         请求对象
     * @return netty转发路由
     */
    RequestModel routeNetty(FullHttpRequest fullRequest, ChannelHandlerContext ctx);

}
