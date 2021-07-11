# 第三周作业说明

> 我在本地启动了两个`httpbin`容器用来做后端请求载体,后端请求`httpclient/okhttp/netty`实现的切换在`HttpInboundHandler` 的构造函数里

```dockerfile
docker run -p 80:80 kennethreitz/httpbin
```

```dockerfile
docker run -p 8080:80 kennethreitz/httpbin
```

### 整合`httpclient/okhttp`

> 整合只支持get和post请求

- 整合`httpclient`

```http
https://github.com/heyu-rise/java-training/blob/main/week03nioconcurrent/src/main/java/org/heyu/gateway/outbound/httpclient4/HttpOutboundHandler.java
```

- 整合`okhttp`

```http
https://github.com/heyu-rise/java-training/blob/main/week03nioconcurrent/src/main/java/org/heyu/gateway/outbound/okhttp/OkhttpOutboundHandler.java
```

### 实现过滤器

> 过滤器注册写在了`HttpInboundHandler`类的构造函数里

- 前置过滤器

> 前置过滤器写在了`HttpInboundHandler `类的`channelRead`方法里

- 后置过滤器

> 后置过滤器写在了`HttpOutboundHandler`和`OkhttpOutboundHandler`的`handleResponse`方法里

### 使用 `netty` 实现后端 `http` 访问

> 使用`netty`客户端的时候，请求总耗时高达2秒，这部分还没有解决

```http
https://github.com/heyu-rise/java-training/blob/main/week03nioconcurrent/src/main/java/org/heyu/gateway/outbound/netty4/NettyHttpOutBoundHandler.java
```

```http
https://github.com/heyu-rise/java-training/blob/main/week03nioconcurrent/src/main/java/org/heyu/gateway/outbound/netty4/NettyInboundHandlerAdapter.java
```

### 实现路由

> 路由模仿了`springcloud gateway`配置，配置文件为`gateway.yml`，在应用启动时加载

```http
https://github.com/heyu-rise/java-training/blob/main/week03nioconcurrent/src/main/java/org/heyu/gateway/router/HashHttpEndpointRouter.java
```
