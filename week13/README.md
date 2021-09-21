[TOC]

# 第13周作业说明

## 1、搭建一个 3 节点 Kafka 集群，测试功能和性能；实现 `spring kafka` 下对 `kafka` 集群的操作

#### 1.1、搭建一个 3 节点 Kafka 集群

- 启动一个`centos`镜像
- 下载`zookeeper`
- 安装`java`

```shell
yum install java
```

- 修改`kafka`配置

```properties
############################# Server Basics #############################
broker.id=1
############################# Socket Server Settings #############################
listeners=PLAINTEXT://127.0.0.1:9091
advertised.listeners=PLAINTEXT://127.0.0.1:9001
broker.list=127.0.0.1:9001,127.0.0.1:9002,127.0.0.1:9003
############################# Log Basics #############################
log.dirs=/tmp/kafka-logs-1
```

```properties
############################# Server Basics #############################
broker.id=2
############################# Socket Server Settings #############################
listeners=PLAINTEXT://127.0.0.1:9092
advertised.listeners=PLAINTEXT://127.0.0.1:9002
broker.list=127.0.0.1:9001,127.0.0.1:9002,127.0.0.1:9003
############################# Log Basics #############################
log.dirs=/tmp/kafka-logs-1
```

```properties
############################# Server Basics #############################
broker.id=3
############################# Socket Server Settings #############################
listeners=PLAINTEXT://127.0.0.1:9093
advertised.listeners=PLAINTEXT://127.0.0.1:9003
broker.list=127.0.0.1:9001,127.0.0.1:9002,127.0.0.1:9003
############################# Log Basics #############################
log.dirs=/tmp/kafka-logs-1
```

- 启动`zookeeper`

```shell
bin/zookeeper-server-start.sh config/zookeeper.properties
```

- 分别启动3个`kafka`

> 启动报错，连不上其他两个节点

#### 1.2、实现 `spring kafka` 下对 `kafka` 集群的操作

```http
https://github.com/heyu-rise/java-training/tree/main/week13/homework1301
```

## 6.思考和设计自定义 `MQ` 第二个版本或第三个版本，写代码实现其中至少一个功能点，把设计思路和实现代码

```http
https://github.com/heyu-rise/java-training/tree/main/week13/homework1306
```

