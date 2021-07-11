package org.heyu.gateway.outbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author heyu
 * @date 2021/7/9
 */
public interface OutBoundHandler {


    /**
     * 处理接口
     *
     * @param fullRequest        fullRequest
     * @param ctx                ctx
     * @throws InterruptedException InterruptedException
     */
    void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) throws InterruptedException;

}
