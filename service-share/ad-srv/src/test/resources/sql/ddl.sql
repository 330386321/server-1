/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.22_3306
Source Server Version : 50717
Source Host           : 192.168.1.22:3306
Source Database       : eshop_ad

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-07-06 18:06:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ad
-- ----------------------------
DROP TABLE IF EXISTS `ad`;
CREATE TABLE `ad` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchant_id` bigint(20) unsigned NOT NULL COMMENT '商家ID',
  `merchant_store_id` bigint(20) unsigned NOT NULL COMMENT '商家门店id',
  `merchant_store_name` varchar(100) NOT NULL COMMENT '店铺名称',
  `manage_type` tinyint(2) unsigned NOT NULL COMMENT '经营类型(1-普通商户2-实体店铺)',
  `merchant_num` varchar(19) NOT NULL COMMENT '商家编号',
  `merchant_longitude` decimal(10,7) unsigned DEFAULT NULL COMMENT '经度',
  `merchant_latitude` decimal(10,7) unsigned DEFAULT NULL COMMENT '纬度',
  `title` varchar(100) DEFAULT NULL COMMENT '名称',
   `logo_url` varchar(255) NOT NULL COMMENT '门店logo',
  `media_url` varchar(120) DEFAULT NULL COMMENT '广附件路径',
  `video_img_url` varchar(120) DEFAULT NULL COMMENT '视频封面图片路径',
  `content` varchar(500) DEFAULT NULL COMMENT '广告内容',
  `type` tinyint(2) NOT NULL COMMENT '广告类型(1-平面广告2-视频广告)',
  `put_way` tinyint(2) NOT NULL COMMENT '投放方式(1-区域2-粉丝 3-雷达)',
  `begin_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `areas` varchar(100) DEFAULT NULL COMMENT '投放区域',
  `region_name` varchar(100) DEFAULT NULL COMMENT '区域名称',
  `radius` int(10) unsigned DEFAULT NULL COMMENT '半径，单位米',
  `point` decimal(10,2) unsigned DEFAULT NULL COMMENT '单个积分',
  `total_point` decimal(10,2) unsigned NOT NULL COMMENT '总投放积分',
  `ad_count` int(5) unsigned DEFAULT NULL COMMENT '广告数量',
  `hits` int(5) unsigned DEFAULT NULL COMMENT '已点击次数',
  `viewCount` int(5) unsigned DEFAULT '0' COMMENT '广告浏览次数',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态(0-删除1-上架2-投放中3-投放结束4-下架)',
  `auditor_id` int(11) DEFAULT '0' COMMENT '审核人员ID',
  `remark` varchar(200) DEFAULT NULL COMMENT '审核备注',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for ad_lexicon
-- ----------------------------
DROP TABLE IF EXISTS `ad_lexicon`;
CREATE TABLE `ad_lexicon` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(20) NOT NULL COMMENT '名称',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for ad_platform
-- ----------------------------
DROP TABLE IF EXISTS `ad_platform`;
CREATE TABLE `ad_platform` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_id` bigint(20) unsigned DEFAULT NULL COMMENT '商品ID',
  `title` varchar(100) DEFAULT NULL COMMENT '名称',
  `media_url` varchar(120) DEFAULT NULL COMMENT '广附件路径',
  `link_url` varchar(120) DEFAULT NULL COMMENT '链接地址',
  `merchant_store_id` bigint(20) unsigned DEFAULT NULL COMMENT '门店id',
  `type` tinyint(2) DEFAULT NULL COMMENT '广告类型(1-纯链接2-商品 3-门店)',
  `position` tinyint(2) NOT NULL COMMENT '广告位置(1-人气推荐2-要购物顶部广告 3-要购物今日推荐4-要购物精品5-看广告顶部广告 6-E店必够 7-特色好货 8-实惠单品  9-热门商品)',
  `region_path` varchar(25) DEFAULT NULL COMMENT '地区',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态(0-删除1-上架2-下架)',
  `content` varchar(500) DEFAULT NULL COMMENT '广告内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for ad_region
-- ----------------------------
DROP TABLE IF EXISTS `ad_region`;
CREATE TABLE `ad_region` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchant_id` bigint(20) unsigned NOT NULL COMMENT '商家ID',
  `ad_id` bigint(20) unsigned NOT NULL COMMENT '广告ID',
  `region_id` varchar(20) DEFAULT NULL COMMENT '区域',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_ad_id` (`ad_id`)
);

-- ----------------------------
-- Table structure for favorite_ad
-- ----------------------------
DROP TABLE IF EXISTS `favorite_ad`;
CREATE TABLE `favorite_ad` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ad_id` bigint(20) unsigned NOT NULL COMMENT '广告ID',
  `member_id` bigint(20) unsigned NOT NULL COMMENT '会员ID',
  `member_num` varchar(19) NOT NULL COMMENT '会员编号',
  `is_send`  tinyint(1) DEFAULT '0' NOT NULL COMMENT '是否发送消息',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_member_id` (`member_id`)
);

-- ----------------------------
-- Table structure for follow_transaction_record
-- ----------------------------
DROP TABLE IF EXISTS `follow_transaction_record`;
CREATE TABLE `follow_transaction_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `transation_id` bigint(20) unsigned NOT NULL COMMENT '事务记录id',
  `topic` varchar(30) NOT NULL COMMENT 'MQ消息的topic',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for member_ad_record
-- ----------------------------
DROP TABLE IF EXISTS `member_ad_record`;
CREATE TABLE `member_ad_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `member_id` bigint(20) unsigned NOT NULL COMMENT '会员',
  `member_num` varchar(19) NOT NULL COMMENT '会员编号',
  `ad_id` bigint(20) unsigned NOT NULL COMMENT '广告',
  `point` decimal(10,6) DEFAULT NULL COMMENT '获取积分',
  `original_point` decimal(10,6) DEFAULT NULL COMMENT '原始积分',
  `click_date` date DEFAULT NULL COMMENT '点击日期:年月日',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态(0-没有算提成1-已算提成)',
  `gmt_commission` datetime DEFAULT NULL COMMENT '计算提成时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for point_pool
-- ----------------------------
DROP TABLE IF EXISTS `point_pool`;
CREATE TABLE `point_pool` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchant_id` bigint(20) unsigned NOT NULL COMMENT '商家ID',
  `member_id` bigint(20) unsigned DEFAULT NULL COMMENT '会员ID',
  `member_num` varchar(19) DEFAULT NULL COMMENT '会员编号',
  `ad_id` bigint(20) unsigned NOT NULL COMMENT '广告ID',
  `type` tinyint(2) NOT NULL COMMENT '类型(1-抢赞2-红包)',
  `ordinal` int(8) unsigned NOT NULL COMMENT '序号',
  `point` decimal(10,2) unsigned NOT NULL COMMENT '分配的金额',
  `status` tinyint(2) NOT NULL COMMENT '状态(0-未分配1-已分配2-过期)',
  `taked_time` datetime DEFAULT NULL COMMENT '分配时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for red_packet
-- ----------------------------
DROP TABLE IF EXISTS `red_packet`;
CREATE TABLE `red_packet` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchant_id` bigint(20) unsigned NOT NULL COMMENT '商家ID',
  `merchant_num` varchar(19) NOT NULL COMMENT '商家编号',
  `put_way` tinyint(2) NOT NULL COMMENT '投放方式(1-普通红包2-拼手气红包)',
  `package_count` int(5) unsigned DEFAULT NULL COMMENT '红包数量',
  `totle_point` decimal(10,2) unsigned DEFAULT NULL COMMENT '所需积分',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态(0-删除1-上架3-下架)',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for transaction_record
-- ----------------------------
DROP TABLE IF EXISTS `transaction_record`;
CREATE TABLE `transaction_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `relate_id` bigint(20) unsigned NOT NULL COMMENT '关联ID',
  `type` tinyint(3) unsigned NOT NULL COMMENT '事务类型',
  `is_processed` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '已处理，0否，1是',
  `times` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '执行次数',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
);
