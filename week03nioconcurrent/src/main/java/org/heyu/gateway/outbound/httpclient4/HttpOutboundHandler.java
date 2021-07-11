package org.heyu.gateway.outbound.httpclient4;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.util.EntityUtils;
import org.heyu.gateway.filter.HttpResponseFilter;
import org.heyu.gateway.outbound.OutBoundHandler;
import org.heyu.gateway.router.HashHttpEndpointRouter;
import org.heyu.gateway.router.HttpEndpointRouter;
import org.heyu.gateway.router.RouteDefinition;
import org.heyu.gateway.util.MyThreadFactory;
import org.heyu.gateway.util.ResponseHandlerUtil;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author heyu
 */
@Slf4j
public class HttpOutboundHandler implements OutBoundHandler {

    private final CloseableHttpAsyncClient httpclient;
    private final ExecutorService proxyService;

    List<HttpResponseFilter> responseFilterList;
    HttpEndpointRouter router;

    public HttpOutboundHandler(List<RouteDefinition> routeDefinitionList, List<HttpResponseFilter> responseFilterList) {
        this.responseFilterList = responseFilterList;
        router = new HashHttpEndpointRouter(routeDefinitionList);
        int cores = Runtime.getRuntime().availableProcessors();
        long keepAliveTime = 1000;
        int queueSize = 2048;
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        proxyService = new ThreadPoolExecutor(cores, cores * 2,
                keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
                new MyThreadFactory("proxyService"), handler);

        IOReactorConfig ioConfig = IOReactorConfig.custom()
                .setConnectTimeout(1000)
                .setSoTimeout(1000)
                .setIoThreadCount(cores)
                .setRcvBufSize(32 * 1024)
                .build();

        httpclient = HttpAsyncClients.custom().setMaxConnTotal(40)
                .setMaxConnPerRoute(8)
                .setDefaultIOReactorConfig(ioConfig)
                .setKeepAliveStrategy((response, context) -> 6000)
                .build();
        httpclient.start();
    }

    @Override
    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        String url = router.route(fullRequest, ctx);
        proxyService.submit(() -> fetchGet(fullRequest, ctx, url));
    }

    private void fetchGet(final FullHttpRequest inbound, final ChannelHandlerContext ctx, final String url) {
        final HttpRequestBase requestBase = getRequest(inbound, url);
        httpclient.execute(requestBase, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(final HttpResponse endpointResponse) {
                try {
                    handleResponse(inbound, ctx, endpointResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(final Exception ex) {
                try {
                    ResponseHandlerUtil.handleErrorResponse(inbound, ctx, ex);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    requestBase.abort();
                    ex.printStackTrace();
                }
            }

            @Override
            public void cancelled() {
                requestBase.abort();
            }
        });
    }

    private HttpRequestBase getRequest(FullHttpRequest inbound, String url) {
        HttpMethod httpMethod = inbound.method();
        if (httpMethod.equals(HttpMethod.GET)) {
            HttpRequestBase httpRequestBase = new HttpGet(url);
            setHeader(httpRequestBase, inbound, true);
            return httpRequestBase;
        }
        if (httpMethod.equals(HttpMethod.POST)) {
            HttpPost httpRequestBase = new HttpPost(url);
            setHeader(httpRequestBase, inbound, false);
            StringEntity s;
            try {
                s = new StringEntity(inbound.content().toString());
                httpRequestBase.setEntity(s);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return httpRequestBase;
        }
        return null;
    }

    private void setHeader(HttpRequestBase requestBase, FullHttpRequest inbound, boolean contentLength) {
        HttpHeaders headers = inbound.headers();
        for (Map.Entry<String, String> header : headers) {
            if (!contentLength && Objects.equals(header.getKey(), "Content-Length")) {
                continue;
            }
            requestBase.setHeader(header.getKey(), header.getValue());
        }
    }

    private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final HttpResponse endpointResponse) {
        FullHttpResponse response = null;
        try {
            byte[] body = EntityUtils.toByteArray(endpointResponse.getEntity());
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
            for (Header allHeader : endpointResponse.getAllHeaders()) {
                response.headers().set(allHeader.getName(), allHeader.getValue());
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

}
