package org.heyu.gateway.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import org.heyu.gateway.filter.*;
import org.heyu.gateway.outbound.OutBoundHandler;
import org.heyu.gateway.outbound.okhttp.OkhttpOutboundHandler;
import org.heyu.gateway.router.RouteDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author heyu
 */
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private final OutBoundHandler handler;

    private final List<HttpRequestFilter> requestFilterList;

    public HttpInboundHandler(List<RouteDefinition> routeDefinitionList) {
        requestFilterList = new ArrayList<>(5);
        requestFilterList.add(new HeaderHttpRequestFilter());
        requestFilterList.add(new AuthHttpRequestFilter());
        List<HttpResponseFilter> responseFilterList = new ArrayList<>(5);
        responseFilterList.add(new HeaderHttpResponseFilter());
        // handler = new HttpOutboundHandler(routeDefinitionList, responseFilterList);
        handler = new OkhttpOutboundHandler(routeDefinitionList, responseFilterList);
        // handler = new NettyHttpOutBoundHandler(routeDefinitionList, responseFilterList);
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            if (requestFilterList != null) {
                for (HttpRequestFilter httpRequestFilter : requestFilterList) {
                    httpRequestFilter.filter(fullRequest, ctx);
                }
            }
            handler.handle(fullRequest, ctx);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            // ReferenceCountUtil.release(msg);
        }
    }

}
