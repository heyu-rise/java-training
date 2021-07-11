package org.heyu.gateway.outbound.netty4;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;
import org.heyu.gateway.filter.HttpResponseFilter;
import org.heyu.gateway.util.ResponseHandlerUtil;

import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author heyu
 */
@Slf4j
public class NettyInboundHandlerAdapter extends ChannelInboundHandlerAdapter {

    private final List<HttpResponseFilter> responseFilterList;

    private final ChannelHandlerContext ctxRequest;

    private final FullHttpRequest fullRequest;

    private final String uri;

    private HttpHeaders headers;

    private HttpResponseStatus status;

    private HttpVersion version;


    public NettyInboundHandlerAdapter(List<HttpResponseFilter> responseFilterList, ChannelHandlerContext ctx, FullHttpRequest fullRequest, String uri) {
        this.responseFilterList = responseFilterList;
        this.ctxRequest = ctx;
        this.fullRequest = fullRequest;
        this.uri = uri;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        fullRequest.setUri(uri);
        ctx.writeAndFlush(fullRequest);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof DefaultHttpResponse) {
            DefaultHttpResponse defaultHttpResponse = (DefaultHttpResponse) msg;
            headers = defaultHttpResponse.headers();
            status = defaultHttpResponse.status();
            version = defaultHttpResponse.protocolVersion();
        }
        if(msg instanceof DefaultLastHttpContent){
            DefaultLastHttpContent response = (DefaultLastHttpContent)msg;
            handleResponse(fullRequest, ctxRequest, response);
        }
    }

    private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, DefaultLastHttpContent defaultLastHttpContent) {
        FullHttpResponse response = null;
        try {
            response = new DefaultFullHttpResponse(version, status, defaultLastHttpContent.content());
            for (Map.Entry<String, String> header : headers) {
                response.headers().set(header.getKey(), header.getValue());
            }
            if (responseFilterList != null) {
                for (HttpResponseFilter httpResponseFilter : responseFilterList) {
                    httpResponseFilter.filter(response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            ResponseHandlerUtil.exceptionCaught(ctx, e);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        log.info(cause.getMessage());
        super.exceptionCaught(ctx, cause);
    }

}