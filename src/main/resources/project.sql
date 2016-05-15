/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50548
Source Host           : localhost:3306
Source Database       : project

Target Server Type    : MYSQL
Target Server Version : 50548
File Encoding         : 65001

Date: 2016-05-15 10:56:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(11) NOT NULL COMMENT '主键',
  `userid` varchar(50) NOT NULL DEFAULT '' COMMENT '用户id,登陆名',
  `username` varchar(100) DEFAULT '' COMMENT '用户名称',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `cellphone` varchar(50) DEFAULT NULL COMMENT '手机号码',
  `gender` varchar(1) DEFAULT '' COMMENT '性别 M : 男性  F : 女性',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `location_address` varchar(200) DEFAULT NULL COMMENT '用户所在地',
  `passwd` varchar(50) DEFAULT NULL COMMENT '密码',
  `is_valid` varchar(1) DEFAULT NULL COMMENT '是否有效 ：  Y 有效  N 无效',
  `is_confirm` varchar(1) DEFAULT NULL COMMENT '是否验证过 Y 已验证 N 未验证',
  `confirm_code` varchar(50) DEFAULT NULL COMMENT '验证字符串',
  `sessionid` varchar(50) DEFAULT NULL COMMENT '登陆时的sessionid',
  `sessionid_expire_time` timestamp NULL DEFAULT NULL COMMENT 'session 过期时间',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) DEFAULT NULL COMMENT '最后登陆的ip地址',
  `login_error_cnt` int(11) DEFAULT NULL COMMENT '失败登陆次数',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `active_time` timestamp NULL DEFAULT NULL COMMENT '激活时间',
  `mod_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `question1` varchar(100) DEFAULT NULL COMMENT '密保问题1',
  `answer1` varchar(100) DEFAULT NULL COMMENT '密保答案1',
  `question2` varchar(100) DEFAULT NULL COMMENT '密保问题2',
  `answer2` varchar(100) DEFAULT NULL COMMENT '密保答案2',
  `question3` varchar(100) DEFAULT NULL COMMENT '密保问题3',
  `answer3` varchar(100) DEFAULT NULL COMMENT '密保答案3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
