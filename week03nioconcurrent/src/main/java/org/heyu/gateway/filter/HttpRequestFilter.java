package org.heyu.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public interface HttpRequestFilter {

    /**
     * 请求过滤器
     *
     * @param fullRequest
     * @param ctx
     */
    void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx);
    
}
