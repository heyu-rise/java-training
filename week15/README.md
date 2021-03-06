[TOC]

## 毕业总结

> ​        为期四个月的`Java`训练营已经结束了，感觉自己确实是进步了，又觉得自己更菜了。自己懂得了更多的知识，也感觉自己在整体的知识框架面前还是非常的不足。虽然自己完成了每一期的作业，但是自己又好像在疲于奔命。从`JVM`到最后的分布式架构，自己都学了一些皮毛，虽然也在自己的工作过程中用到了一些知识，但还是缺少练习。
>
> ​        我觉得学习还是需要自觉性和一致性，自己不仅需要知道学习的必要性，也要行动起来并且坚持下去。其实我报这个训练营的初衷还是让自己被迫学习起来，坚持下去，并且有一定的计划，这样也就不会让自己只停留在想的状态。
>
> ​        自己工作也有四年的时间了，从`android`转到了了`Java`，也会一点`python`和前端的知识，但是自己还是免不了焦虑，自己的家人同学都已经稳定了下来，自己还在外面飘着。但是我还是决定给自己一点时间，看看自己导体能不能成为`pro`。

## 第15周作业

### 1、JVM

> `Java` 是一种面向对象、静态类型、编译执行， 有 VM/GC 和运行时、跨平台的高级语言，而`JVM`更是`Java`运行的基础环境，也是`Java`能跨平台的重要原因之一。`Java`语言编译程序只需生成在`JVM`上运行的目标代码，就可以在多种平台上不加修改地运行。`Java`虚拟机主要分为五大模块：类加载器、内存模型、执行引擎、本地方法接口和垃圾收集。

![](https://github.com/heyu-rise/java-training/blob/main/week15/JVM.jpg?raw=true)

### 2、NIO

> 正常`IO`在处理请求时，每个连接都会使用一个线程，直到这个请求结束，但是在`NIO`模型下，程序会分配专用的线程来处理连接请求，之后连接线程会把这个请求放到专门处理业务的线程中处理并且返回。这样做的好处就是在处理大量的并发请求的时候会有更好的性能，目前`Java`网络连接的`nio`实施标准时`Netty`，`Netty`提供异步的、事件驱动的网络应用程序框架和工具，用以快速开发高性能、高可靠性的网络服务器和客户端程序。



### 3、并发编程

> 最近几年，并发编程已经慢慢成为一项必备技能，这主要是硬件的驱动以及国内互联网行业的飞速发展决定的，大型互联网厂商的系统并发量轻松过百万，传统的中间件和数据库已经成了瓶颈。其实并发编程可以总结为三个核心问题：分工、同步、互斥。分工指的是如何高效地拆解任务并分配给线程，而同步指的是线程之间如何协作，互斥则是保证同一时刻只允许一个线程访问共享资源。Java SDK 并发包很大部分内容都是按照这三个维度组织的，例如 `Fork/Join` 框架就是一种分工模式，`CountDownLatch` 就是一种典型的同步方式，而可重入锁则是一种互斥手段。

![](https://github.com/heyu-rise/java-training/blob/main/week15/concurrent.jpg?raw=true)

### 4、Spring 和 ORM 等框架

> `Spring` 是目前主流的 `Java Web` 开发框架，甚至可以说时`Java web`开发的事实标准，它是一个轻量级的控制反转(`IoC`)和面向切面(`AOP`)的`web`容器框架，spring有4大常用模块：1、核心模块（`Bean/Context/AOP`），2、测试模块，3、数据管理模块（`tx/jdbc/orm`），4、`spring mvc` 模块。
>
> 目前主流的`orm`框架有`spring data`，`MyBatis `，`hibernate`，而目前国内用的最多的时`MyBatis `和`MyBatis `的第三方插件，`MyBatis` 消除了几乎所有的`JDBC`代码和参数的手工设置以及结果集的检索。`MyBatis` 使用简单的 XML或注解用于配置和原始映射，将接口和 `Java` 的`POJOs`（`Plain Ordinary Java Objects`，普通的 `Java`对象）映射成数据库中的记录。

![](https://github.com/heyu-rise/java-training/blob/main/week15/Spring.jpg?raw=true)

### 5、MySQL 数据库和 SQL

> `MySQL` 是一个关系型数据库，近十年来，`MySQL` 在中国广泛普及。它于瑞典的 `MySQL AB` 创立于1995年，2008年1月16日 `MySQL AB` 被 `Sun Microsystems` 收购。2009年4月20日，甲骨文（`Oracle`）收购 `Sun Microsystems` 公司。 其后分离成两个版本：`MariaDB` 和 `MySQL`。
>
> `sql` 是一种特殊的编程语言，是一种数据库查询和程序设计语言，用于存取数据以及查询、更新和管理关系数据库系统。

![](https://github.com/heyu-rise/java-training/blob/main/week15/MySQL.jpg?raw=true)

### 6、分库分表

> 单机数据库已经无法适应互联网的发展，传统的将数据集中存储至单一数据节点的解决方案，在容量、性能、可用性和运维成本这三方面已经难于满足互联网 的海量数据场景。我们在单库单表数据量超过一定容量水位的情况下，索引树层级增加，磁盘 `IO` 也很可能出现压力， 会导致很多问题。
>
> 从性能方面来说，由于关系型数据库大多采用 `B+`树类型的索引，在数据量超过阈值的情况下，索引深度的增加也将使 得磁盘访问的 `IO` 次数增加，进而导致查询性能的下降；同时，高并发访问请求也使得集中式数据库成为系统的最大瓶 颈。 
>
> 从可用性的方面来讲，服务化的无状态型，能够达到较小成本的随意扩容，这必然导致系统的最终压力都落在数据库 之上。而单一的数据节点，或者简单的主从架构，已经越来越难以承担。从运维成本方面考虑，当一个数据库实例中 的数据达到阈值以上，数据备份和恢复的时间成本都将随着数据量的大小而愈发不可控。

![](https://github.com/heyu-rise/java-training/blob/main/week15/sub-mysql.jpg?raw=true)

### 7、RPC 和微服务

> `RPC` 是远程过程调用（`Remote Procedure Call`）的缩写形式，`RPC`就是像调用本地方法一样调用远程方法。常见的 RPC 技术有：`Corba/RMI/.NET Remoting` `JSON RPC`, `XML RPC`，`WebService`(`Axis2, CXF`) - `Hessian`, `Thrift`, `Protocol Buffer`, `gRPC`。
>
> 微服务是一种软件开发技术- 面向服务的体系结构（SOA）架构样式的一种变体，它提倡将单一应用程序划分成一组小的服务，服务之间互相协调、互相配合，为用户提供最终价值。每个服务运行在其独立的进程中，服务于服务间采用轻量级的通信机制互相沟通（通常是基于HTTP的RESTful API）。每个服务都围绕着具体业务进行构建，并且能够独立地部署到生产环境、类生产环境等。另外，应尽量避免统一的、集中式的服务管理机制，对具体的一个服务而言，应根据上下文，选着合适的语言、工具对其进行构建。

![](https://github.com/heyu-rise/java-training/blob/main/week15/Microservice.jpg?raw=true)

### 8、分布式缓存

> 随着互联网的发展，用户规模和数据规模越来越大，对系统的性能提出了更高的要求，缓存就是其中一个非常关键的组件，从简单的商品秒杀，到全民投入的双十一，我们都能见到它的身影。
>
> 分布式缓存首先也是缓存，一种性能很好但是相对稀缺的资源，和我们在课本上学习的CPU缓存原理基本相同，CPU是用性能更好的静态RAM来为性能一般的DRAM加速，分布式缓存则是通过内存或者其他高速存储来加速，但是由于用到了分布式环境中，涉及到并发和网络的问题，所以会更加复杂一些，但是有很多方面的共性，比如缓存淘汰策略。计算机行业有一句鼎鼎大名的格言就指出了缓存失效的复杂性

![](https://github.com/heyu-rise/java-training/blob/main/week15/redis.jpg?raw=true)

### 9、分布式消息队列

> 消息队列是最古老的中间件之一，它可以用来实现实现服务的异步处理，也可以后来做流量控制，还可以用来做服务解耦。
>
> 
>
> 目前主流的消息队列有`RabbitMQ`，`RocketMQ`，`Kafka`，还有最老牌的消息队列`ActiveMQ`，以及新一代消息队列`Pulsar`。
>
> `RabbitMQ` 是使用一种比较小众的编程语言：Erlang 语言编写的，它最早是为电信行业系统之间的可靠通信设计的，也是少数几个支持 AMQP 协议的消息队列之一。
>
> `Kafka` 最早是由 LinkedIn 开发，目前是 `Apache` 的顶级项目。`Kafka` 最初的设计目的是用于处理海量的日志。`Kafka` 与周边生态系统的兼容性是最好的没有之一，尤其在大数据和流计算领域，几乎所有的相关开源软件系统都会优先支持 `Kafka`。
>
> `RocketMQ` 是阿里巴巴在 2012 年开源的消息队列产品，它相当于`Kafka`的`JAVA`版本，
>
> `ActiveMQ` 是最老牌的开源消息队列，是十年前唯一可供选择的开源消息队列，目前已进入老年期，社区不活跃。无论是功能还是性能方面，`ActiveMQ` 都与现代的消息队列存在明显的差距，它存在的意义仅限于兼容那些还在用的爷爷辈儿的系统
>
> `Pulsar` 是一个新兴的开源消息队列产品，最早是由 `Yahoo` 开发，目前处于成长期，流行度和成熟度相对没有那么高。与其他消息队列最大的不同是，`Pulsar` 采用存储和计算分离的设计







