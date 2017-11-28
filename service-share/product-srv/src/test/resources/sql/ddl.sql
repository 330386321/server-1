/*
Navicat MySQL Data Transfer

Source Server         : 开发环境192.168.1.22root_1qazXSW@
Source Server Version : 50717
Source Host           : 192.168.1.22:3306
Source Database       : eshop_product

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-07-12 10:20:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for favorite_product
-- ----------------------------
DROP TABLE IF EXISTS `favorite_product`;
CREATE TABLE `favorite_product` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `member_id` bigint(20) unsigned NOT NULL COMMENT '会员',
  `product_id` bigint(20) unsigned NOT NULL COMMENT '收藏',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
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
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchant_id` bigint(20) unsigned NOT NULL COMMENT '商家ID',
  `merchant_num` varchar(19) NOT NULL DEFAULT '' COMMENT '商家编号',
  `category_id` int(5) unsigned NOT NULL COMMENT '分类ID',
  `num` varchar(25) DEFAULT '' COMMENT '编号',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `content` text  COMMENT '详细描述',
  `keywords` VARCHAR(100) NULL DEFAULT NULL COMMENT '关键词',
  `feature_image` varchar(120) NOT NULL DEFAULT '' COMMENT '特征图片',
  `average_daily_sales` decimal(10,2) unsigned DEFAULT '0.00' COMMENT '平均日销售',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '状态(1-删除2-上架3-下架)',
  `is_allow_refund` tinyint(1) NOT NULL COMMENT '是否支持退换货(0-否1-是)',
  `image_content` text  COMMENT '商品详情图片描述',
  `total_inventory` int(10) NOT NULL DEFAULT '0' COMMENT '商品总库存',
  `total_sales_volume` int(10) NOT NULL DEFAULT '0' COMMENT '商品总销量',
  `total_favorite` int(10) NOT NULL DEFAULT '0' COMMENT '总收藏',
  `min_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '型号最低价',
  `max_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '型号现价最低价对应的原价',
  `remark` VARCHAR(100) COMMENT '备注',
  `gmt_down` datetime DEFAULT NULL COMMENT '下架时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50)  NOT NULL DEFAULT '' COMMENT '商品分类名称',
  `parent_id` int(5) unsigned NOT NULL DEFAULT '0' COMMENT '父类',
  `path` varchar(75)  DEFAULT '' COMMENT '家族图谱',
  `level` tinyint(3) unsigned DEFAULT '0' COMMENT '等级',
  `ordinal` tinyint(3) unsigned NOT NULL DEFAULT '50' COMMENT '顺序排序',
  `statue` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '0隐藏1显示',
  `type` tinyint(3) unsigned DEFAULT '0' COMMENT '1热门分类0默认',
  `is_virtual` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否虚拟商品,0否 1是',
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for product_image
-- ----------------------------
DROP TABLE IF EXISTS `product_image`;
CREATE TABLE `product_image` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_id` bigint(20) unsigned NOT NULL COMMENT '商品ID',
  `image_path` varchar(2000) NOT NULL DEFAULT '' COMMENT '图片路径',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态(0删除1正常)',
  `img_type` tinyint(3) NOT NULL COMMENT '图片类型(1-头部滚动图片2-详情图片)',
  `sortid` int(3) NOT NULL COMMENT '顺序',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for product_model
-- ----------------------------
DROP TABLE IF EXISTS `product_model`;
CREATE TABLE `product_model` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchant_id` bigint(20) unsigned NOT NULL COMMENT '商家ID',
  `product_id` bigint(20) unsigned NOT NULL COMMENT '商品ID',
  `num` varchar(25)  DEFAULT '' COMMENT '编号',
  `name` varchar(100)  NOT NULL COMMENT '名称',
  `original_price` decimal(10,2) unsigned DEFAULT '0.00' COMMENT '原价',
  `price` decimal(10,2) unsigned NOT NULL COMMENT '现价',
  `sales_volume` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '销量',
  `inventory` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '库存',
  `status` tinyint(1) NOT NULL COMMENT '状态(0-删除1-有效)',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for product_model_inventory
-- ----------------------------
DROP TABLE IF EXISTS `product_model_inventory`;
CREATE TABLE `product_model_inventory` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_model_id` bigint(20) unsigned NOT NULL COMMENT '商品型号id',
  `quantity` int(11) unsigned NOT NULL COMMENT '数量',
  `shopping_order_id` bigint(20) unsigned DEFAULT NULL COMMENT '购物订单id(在创建订单和取消订单会保存)',
  `type` tinyint(2) unsigned NOT NULL COMMENT '更新类型(0-加库存|1-减库存|2-创建订单|3-取消订单)',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for recommend_product_category
-- ----------------------------
DROP TABLE IF EXISTS `recommend_product_category`;
CREATE TABLE `recommend_product_category` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category_id` int(5) unsigned NOT NULL COMMENT '分类ID',
  `category_name` varchar(20) NOT NULL COMMENT '名称',
  `isvisible` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可见,0否 1是',
  `ordinal` tinyint(3) unsigned DEFAULT NULL COMMENT '排序，小的排前面',
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


CREATE TABLE `seckill_activity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '活动名称',
  `start_date` datetime NOT NULL COMMENT '开始时间',
  `end_date` datetime NOT NULL COMMENT '结束时间',
  `member_level` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '会员等级',
  `product_valid_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '商家可提交审核的商品数',
  `selling_price` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '活动定价',
  `picture` varchar(200) NOT NULL DEFAULT '' COMMENT '宣传图片',
  `activity_status` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '活动状态(1-未发布|2-未开始|3-进行中|4-已结束)',
  `is_remind` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否提醒',
  `status` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '状态(0-删除|1-正常)',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ;

CREATE TABLE `seckill_activity_product` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `activity_id` bigint(20) unsigned NOT NULL COMMENT '抢购活动id',
  `merchant_id` bigint(20) unsigned NOT NULL COMMENT '商家id',
  `product_id` bigint(20) unsigned NOT NULL COMMENT '商品id',
  `product_picture` varchar(120) NOT NULL COMMENT '商品图片',
  `product_name` varchar(100) NOT NULL COMMENT '商品名称',
  `original_price` decimal(10,2) unsigned NOT NULL COMMENT '商品原价',
  `product_model_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '商品型号总数量',
  `left_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '剩余数量',
  `turnover` decimal(10,2) unsigned NOT NULL DEFAULT '0' COMMENT '成交额',
  `reasons` varchar(200) NOT NULL DEFAULT '' COMMENT '反馈原因',
  `auditor_account` varchar(50) DEFAULT NULL COMMENT '审核人员',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `attention_count` int(10) unsigned NOT NULL COMMENT '关注人数',
  `status` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '状态(1-未审核|2-已审核|3-未通过|)',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ;

CREATE TABLE `seckill_activity_product_model` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `activity_product_id` bigint(20) unsigned NOT NULL COMMENT '抢购活动审核商品id',
  `product_model_id` bigint(20) unsigned NOT NULL COMMENT '商品型号id',
  `count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '商品型号数量',
  `left_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '剩余数量',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
);
