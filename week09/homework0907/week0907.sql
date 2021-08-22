/*
Navicat MySQL Data Transfer

Source Server         : docker
Source Server Version : 50734
Source Host           : localhost:3307
Source Database       : week0901

Target Server Type    : MYSQL
Target Server Version : 50734
File Encoding         : 65001

Date: 2021-08-22 21:50:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dollar
-- ----------------------------
DROP TABLE IF EXISTS `dollar`;
CREATE TABLE `dollar` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `amount` decimal(20,2) DEFAULT NULL COMMENT '人民币',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for freeze
-- ----------------------------
DROP TABLE IF EXISTS `freeze`;
CREATE TABLE `freeze` (
  `id` bigint(20) NOT NULL,
  `amount` decimal(20,2) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `type` int(11) NOT NULL COMMENT '1:美元，2：人名币',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `status` int(11) NOT NULL COMMENT '1:冻结，解冻',
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for rmb
-- ----------------------------
DROP TABLE IF EXISTS `rmb`;
CREATE TABLE `rmb` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `amount` decimal(20,2) DEFAULT NULL COMMENT '人民币',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
