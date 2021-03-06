[TOC]

# 第12周作业说明

## 1、配置 redis 的主从复制，sentinel 高可用，Cluster 集群

### 1.1、配置主从复制

- 把`6380.conf`的bind改为 `0.0.0.0`，运行docker命令

```shell
docker run -p 6380:6380 -p 26380:26380 -v d:/conf:/usr/local/etc/redis --name myredis-master redis redis-server /usr/local/etc/redis/redis6380.conf
```

- 用`docker inspect myredis-master2`命令查看镜像`ip`地址为 `172.17.02`
- 把`6380.conf`的bind改为 `0.0.0.0`，把`replicaof`改为 `172.17.02 6380`
- 运行docker命令

```shell
docker run -p 6379:6379 -p 26379:26379 -v d:/conf:/usr/local/etc/redis --name myredis- redis redis-server /usr/local/etc/redis/redis6379.conf
```

### 1.2、配置sentinel 高可用

- 删除sentinel配置文件中的 `Generated by CONFIG REWRITE`配置
- 进入镜像分别执行

```shell
redis-sentinel /usr/local/etc/redis/sentinel0.conf
redis-sentinel /usr/local/etc/redis/sentinel1.conf
```

### 1.3、Cluster 集群

## 2、搭建 ActiveMQ 服务，基于 JMS，写代码分别实现对于 queue 和 topic 的消息生产和消费

> 运行`docker`命令,代码请看homework1206

```shell
docker run -p 61616:61616 -p 8161:8161 rmohr/activemq
```

