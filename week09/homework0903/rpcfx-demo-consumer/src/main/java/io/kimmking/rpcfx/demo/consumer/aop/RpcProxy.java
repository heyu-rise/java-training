package io.kimmking.rpcfx.demo.consumer.aop;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.demo.consumer.netty.NettyHttpClient;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author heyu
 * @date 2021/8/22
 */
@Aspect
@Component
public class RpcProxy {

	public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");

	@Pointcut("@annotation(io.kimmking.rpcfx.demo.consumer.aop.RpcAnno)")
	public void rpcTest() {
	}

	@Around("rpcTest()")
	public Object aroundLog(ProceedingJoinPoint point) throws Throwable {
		MethodSignature sign = (MethodSignature) point.getSignature();
		Class<?> clazz = Arrays.stream(sign.getDeclaringType().getInterfaces()).iterator().next();
		Method method = sign.getMethod();
		RpcAnno rpcAnno = method.getAnnotation(RpcAnno.class);
		Object[] params = point.getArgs();
		RpcfxRequest request = new RpcfxRequest();
		request.setServiceClass(clazz.getName());
		request.setMethod(method.getName());
		request.setParams(params);
		RpcfxResponse response = post2(request, rpcAnno.url());
		return JSON.parse(response.getResult().toString());
	}

	private RpcfxResponse post2(RpcfxRequest req, String url) throws URISyntaxException {
		URI uri = new URI(url);
		String reqJson = JSON.toJSONString(req);
		FullHttpRequest httpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.POST,
				uri.toASCIIString(), Unpooled.wrappedBuffer(reqJson.getBytes(StandardCharsets.UTF_8)));
		HttpHeaders headers = httpRequest.headers();
		headers.set(HttpHeaderNames.HOST, url);
		headers.set(HttpHeaderNames.CONTENT_LENGTH, httpRequest.content().readableBytes());
		headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
		headers.set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
		FullHttpResponse fullHttpResponse = NettyHttpClient.send(httpRequest);
		byte[] bytes = new byte[fullHttpResponse.content().readableBytes()];
		fullHttpResponse.content().readBytes(bytes);
		return JSON.parseObject(new String(bytes), RpcfxResponse.class);
	}

	private RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
		String reqJson = JSON.toJSONString(req);
		System.out.println("req json: " + reqJson);

		// 1.可以复用client
		// 2.尝试使用httpclient或者netty client
		OkHttpClient client = new OkHttpClient();
		final Request request = new Request.Builder().url(url).post(RequestBody.create(JSONTYPE, reqJson)).build();
		String respJson = client.newCall(request).execute().body().string();
		System.out.println("resp json: " + respJson);
		return JSON.parseObject(respJson, RpcfxResponse.class);
	}

}
