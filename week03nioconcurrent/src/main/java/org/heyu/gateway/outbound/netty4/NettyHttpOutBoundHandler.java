package org.heyu.gateway.outbound.netty4;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import lombok.extern.slf4j.Slf4j;
import org.heyu.gateway.filter.HttpResponseFilter;
import org.heyu.gateway.outbound.OutBoundHandler;
import org.heyu.gateway.router.HashHttpEndpointRouter;
import org.heyu.gateway.router.HttpEndpointRouter;
import org.heyu.gateway.router.RequestModel;
import org.heyu.gateway.router.RouteDefinition;

import java.util.List;

/**
 * @author heyu
 */
@Slf4j
public class NettyHttpOutBoundHandler implements OutBoundHandler {

    List<HttpResponseFilter> responseFilterList;
    HttpEndpointRouter router;

    public NettyHttpOutBoundHandler(List<RouteDefinition> routeDefinitionList, List<HttpResponseFilter> responseFilterList) {
        this.responseFilterList = responseFilterList;
        router = new HashHttpEndpointRouter(routeDefinitionList);
    }

    @Override
    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx) throws InterruptedException {
        RequestModel requestModel = router.routeNetty(fullRequest, ctx);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    // 客户端发送的是httpRequest，所以要使用HttpRequestEncoder进行编码
                    ch.pipeline().addLast(new HttpRequestEncoder());
                    // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                    ch.pipeline().addLast(new HttpResponseDecoder());
                    ch.pipeline().addLast(new NettyInboundHandlerAdapter(responseFilterList, ctx, fullRequest, requestModel.getUri()));
                }
            });
            // Start the client.
            ChannelFuture future = bootstrap.connect(requestModel.getHost(), requestModel.getPort()).sync();
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}