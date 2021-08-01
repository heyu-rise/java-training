# 第六周作业说明

#### **6**、基于电商交易场景（用户、商品、订单），设计一套简单的表结构

```http
https://github.com/heyu-rise/java-training/blob/main/week06/week06.sql
```

```sql
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
CREATE TABLE `goods` (
  `id` bigint(20) NOT NULL,
  `good_code` varchar(50) DEFAULT NULL COMMENT '商品编码',
  `good_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `price` decimal(20,2) DEFAULT NULL COMMENT '单价',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_good_code` (`good_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- ----------------------------
-- Table structure for order
-- ----------------------------
CREATE TABLE `order` (
  `id` bigint(20) NOT NULL,
  `order_code` varchar(50) DEFAULT NULL COMMENT '订单编号',
  `user_id` bigint(20) DEFAULT NULL COMMENT '订单用户id',
  `order_time` datetime DEFAULT NULL COMMENT '下单日期',
  `cost` decimal(20,2) DEFAULT NULL COMMENT '价款',
  `address` varchar(500) DEFAULT NULL COMMENT '地址',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_order_code` (`order_code`) USING BTREE,
  KEY `index_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- ----------------------------
-- Table structure for order_good
-- ----------------------------
CREATE TABLE `order_good` (
  `id` bigint(20) NOT NULL,
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
  `good_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `quantity` decimal(20,2) DEFAULT NULL COMMENT '数量',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `index_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单商品关系表';

-- ----------------------------
-- Table structure for user
-- ----------------------------
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `gender` int(11) DEFAULT NULL COMMENT '性别，1：男，2：女，3：其他',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

```

