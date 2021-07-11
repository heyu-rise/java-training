package org.heyu.gateway.filter;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @author heyu
 */
public class HeaderHttpResponseFilter implements HttpResponseFilter {

    @Override
    public void filter(FullHttpResponse response) {
        response.headers().set("heyu", "gateway");
    }
}
