package org.heyu.gateway.outbound.okhttp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import kotlin.Pair;
import okhttp3.*;
import okio.BufferedSink;
import org.heyu.gateway.filter.HttpResponseFilter;
import org.heyu.gateway.outbound.OutBoundHandler;
import org.heyu.gateway.router.HashHttpEndpointRouter;
import org.heyu.gateway.router.HttpEndpointRouter;
import org.heyu.gateway.router.RouteDefinition;
import org.heyu.gateway.util.MyThreadFactory;
import org.heyu.gateway.util.ResponseHandlerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author heyu
 */
public class OkhttpOutboundHandler implements OutBoundHandler {

    List<HttpResponseFilter> responseFilterList;
    HttpEndpointRouter router;
    ExecutorService proxyService;
    OkHttpClient okHttpClient;

    public OkhttpOutboundHandler(List<RouteDefinition> routeDefinitionList, List<HttpResponseFilter> responseFilterList) {
        this.responseFilterList = responseFilterList;
        router = new HashHttpEndpointRouter(routeDefinitionList);
        okHttpClient = new OkHttpClient();
        int cores = Runtime.getRuntime().availableProcessors();
        long keepAliveTime = 1000;
        int queueSize = 2048;
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        proxyService = new ThreadPoolExecutor(cores, cores * 2,
                keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
                new MyThreadFactory("proxyService"), handler);

    }

    @Override
    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        String url = router.route(fullRequest, ctx);
        fetchRemote(fullRequest, ctx, url);
        // proxyService.submit(() -> fetchRemote(fullRequest, ctx, url));
    }

    private void fetchRemote(final FullHttpRequest inbound, final ChannelHandlerContext ctx, final String url) {
        // Request request = new Request.Builder().url(url).get().build();
        Request request = getOkHttpRequest(inbound, url);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                ResponseHandlerUtil.handleErrorResponse(inbound, ctx, e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                handleResponse(inbound, ctx, response);
            }
        });
    }

    private Request getOkHttpRequest(final FullHttpRequest inbound, final String url) {
        Request request = null;
        HttpMethod httpMethod = inbound.method();
        Map<String, String> headerMap = new HashMap<>(inbound.headers().size());
        for (Map.Entry<String, String> header : inbound.headers()) {
            headerMap.put(header.getKey(), header.getValue());
        }
        Headers headers = Headers.of(headerMap);
        if (httpMethod.equals(HttpMethod.GET)) {
            request = new Request.Builder().headers(headers).url(url).get().build();
            return request;
        }
        if (httpMethod.equals(HttpMethod.POST)) {
            RequestBody requestBody = new RequestBody() {
                @Nullable
                @Override
                public MediaType contentType() {
                    return null;
                }

                @Override
                public void writeTo(@NotNull BufferedSink bufferedSink) throws IOException {
                    bufferedSink.write(inbound.content().toString().getBytes());
                }
            };
            request = new Request.Builder().headers(headers).url(url).post(requestBody).build();
            return request;
        }
        return null;
    }

    private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final Response okHttpResponse) {
        FullHttpResponse response = null;
        try {
            ResponseBody responseBody = okHttpResponse.body();
            assert responseBody != null;
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(responseBody.bytes()));
            for (Pair<? extends String, ? extends String> header : okHttpResponse.headers()) {
                response.headers().set(header.getFirst(), header.getSecond());
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
