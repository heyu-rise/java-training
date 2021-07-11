package org.heyu.gateway.inbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.heyu.gateway.router.RouteDefinition;

import java.util.List;

/**
 * @author shy19
 */
public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {
	
	private final List<RouteDefinition> routeDefinitionList;
	
	public HttpInboundInitializer(List<RouteDefinition> routeDefinitionList) {
		this.routeDefinitionList = routeDefinitionList;
	}
	
	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new HttpServerCodec());
		p.addLast(new HttpObjectAggregator(1024 * 1024));
		p.addLast(new HttpInboundHandler(this.routeDefinitionList));
	}
}
