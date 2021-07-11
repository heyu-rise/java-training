package org.heyu.gateway.filter;

import io.netty.handler.codec.http.FullHttpResponse;

public interface HttpResponseFilter {

    /**
     * 响应过滤器
     *
     * @param response
     */
    void filter(FullHttpResponse response);

}
