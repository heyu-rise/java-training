package org.heyu.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author heyu
 * @date 2021/7/11
 */
@Slf4j
public class AuthHttpRequestFilter implements HttpRequestFilter{

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        String token = fullRequest.headers().get("token");
        log.info(token);
        if (StringUtil.isNullOrEmpty(token)) {
            log.info("用户未登录");
            // ResponseHandlerUtil.handleErrorResponse(fullRequest, ctx, new RuntimeException("用户未登录"));
        }
    }

}
