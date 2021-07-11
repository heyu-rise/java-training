package org.heyu.gateway.router;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.heyu.gateway.util.AntPathMatcher;
import org.heyu.gateway.util.PathMatcher;
import org.heyu.gateway.util.ResponseHandlerUtil;

import java.util.List;

/**
 * @author heyu
 */
public class HashHttpEndpointRouter implements HttpEndpointRouter {

    private static final String HTTP = "http://";

    private static final String HTTPS = "https://";

    private static final String SYMBOL = ":";

    private static final String HTTP_SYMBOL = "://";

    private static final int HTTP_PORT = 80;

    private static final int HTTPS_PORT = 443;

    /**
     * 转发路由
     */
    private final List<RouteDefinition> routeDefinitionList;

    private final PathMatcher pathMatcher = new AntPathMatcher();

    public HashHttpEndpointRouter(List<RouteDefinition> routeDefinitionList) {
        this.routeDefinitionList = routeDefinitionList;
    }

    @Override
    public String route(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        try {
            String uri = fullRequest.uri();
            for (RouteDefinition routeDefinition : routeDefinitionList) {
                if (!pathMatcher.match(routeDefinition.getPath(), uri)) {
                    continue;
                }
                String url = stripPrefixUri(routeDefinition.getStripPrefix(), uri);
                String host = getHostByHash(ctx.channel().remoteAddress().toString(), routeDefinition.getUri());
                return host + url;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseHandlerUtil.handleErrorResponse(fullRequest, ctx, e);
        }
        return null;
    }

    @Override
    public RequestModel routeNetty(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        try {
            RequestModel requestModel = new RequestModel();
            String uri = fullRequest.uri();
            for (RouteDefinition routeDefinition : routeDefinitionList) {
                if (!pathMatcher.match(routeDefinition.getPath(), uri)) {
                    continue;
                }
                String url = stripPrefixUri(routeDefinition.getStripPrefix(), uri);
                requestModel.setUri(url);
                String host = getHostByHash(ctx.channel().remoteAddress().toString(), routeDefinition.getUri());
                String[] a = host.split(SYMBOL);
                if (a.length == 3) {
                    requestModel.setPort(Integer.parseInt(a[2]));
                    requestModel.setHost(a[1].substring(HTTP_SYMBOL.length()));
                } else {
                    if (host.startsWith(HTTPS)) {
                        requestModel.setPort(HTTPS_PORT);
                        requestModel.setHost(host.substring(HTTPS.length()));
                    } else {
                        requestModel.setPort(HTTP_PORT);
                        requestModel.setHost(host.substring(HTTP.length()));
                    }
                }
                return requestModel;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseHandlerUtil.handleErrorResponse(fullRequest, ctx, e);

        }
        return null;
    }

    private String stripPrefixUri(Integer stripPrefix, String uri) {
        if (stripPrefix == null || stripPrefix == 0) {
            return uri;
        }
        for (int i = 0; i < stripPrefix; i++) {
            uri = uri.substring(uri.indexOf("/", 1));
        }
        return uri;
    }

    private String getHostByHash(String remoteAddress, List<String> hostList) {
        int hashCode = remoteAddress.hashCode();
        int pos = hashCode % hostList.size();
        return hostList.get(pos);
    }
}
