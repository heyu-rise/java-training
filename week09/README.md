[TOC]

# 第九周作业说明

### 3、改造自定义 RPC 的程序

```http
https://github.com/heyu-rise/java-training/tree/main/week09/homework0903
```

- 尝试将客户端动态代理改成 AOP，添加异常处理

  > 主要代码

  ```java
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
  ```

- 尝试使用 Netty+HTTP 作为 client 端传输方式。

  > 主要代码

  ```java
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
  
  
  public class FullHttpResponseFuture extends DefaultPromise<FullHttpResponse> {
  }
  
  @Slf4j
  public class NettyHttpClient {
      public static FullHttpResponse send(final FullHttpRequest fullHttpRequest){
          HttpVersion version = HttpVersion.HTTP_1_1;
          HttpResponseStatus status = HttpResponseStatus.NO_CONTENT;
          FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(version, status);
          EventLoopGroup workerGroup = new NioEventLoopGroup();
          try{
              FullHttpResponseFuture responseFuture = new FullHttpResponseFuture();
              Bootstrap bootstrap = new Bootstrap();
              bootstrap.group(workerGroup);
              bootstrap.channel(NioSocketChannel.class);
              bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
              bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                  @Override
                  protected void initChannel(SocketChannel socketChannel) throws Exception {
                      socketChannel.pipeline().addLast(new HttpClientCodec());
                      socketChannel.pipeline().addLast(new HttpObjectAggregator(1024 * 1024));
                      socketChannel.pipeline().addLast(new NettyResponseHandler(responseFuture));
                  }
              });
              String host = fullHttpRequest.headers().get("Host");
              URL url = new URL(host);
              ChannelFuture f = bootstrap.connect(url.getHost(), url.getPort()).sync();
              f.channel().writeAndFlush(fullHttpRequest);
  
              fullHttpResponse = responseFuture.get();
  
              f.channel().close().sync();
          } catch (InterruptedException | ExecutionException | MalformedURLException e) {
              log.error("Netty Client Error: ", e);
          } finally {
              workerGroup.shutdownGracefully();
          }
  
          return fullHttpResponse;
      }
  }
  
  @Slf4j
  public class NettyResponseHandler extends ChannelInboundHandlerAdapter {
  
      private final FullHttpResponseFuture httpResponseFuture;
  
      public NettyResponseHandler(FullHttpResponseFuture httpResponseFuture){
          this.httpResponseFuture = httpResponseFuture;
      }
  
      @Override
      public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
          httpResponseFuture.setSuccess((FullHttpResponse) msg);
      }
  
      @Override
      public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
          ctx.flush();
      }
  
      @Override
      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
          ctx.close();
      }
  }
  ```

### 7、结合 dubbo+hmily，实现一个 TCC 外汇交易处理

  > 代码没气来，报log冲突

