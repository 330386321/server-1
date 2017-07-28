/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.22_3306
Source Server Version : 50717
Source Host           : 192.168.1.22:3306
Source Database       : eshop_order

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-07-12 10:28:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for follow_transaction_record
-- ----------------------------
DROP TABLE IF EXISTS `follow_transaction_record`;
CREATE TABLE `follow_transaction_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `transation_id` bigint(20) unsigned NOT NULL,
  `topic` varchar(30) NOT NULL,
  `gmt_create` datetime NOT NULL,
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for pay_order
-- ----------------------------
DROP TABLE IF EXISTS `pay_order`;
CREATE TABLE `pay_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `member_id` bigint(20) NOT NULL,
  `member_num` varchar(19) NOT NULL DEFAULT '',
  `merchant_id` bigint(20) NOT NULL,
  `merchant_num` varchar(19) NOT NULL,
  `order_num` varchar(30) NOT NULL,
  `third_number` varchar(50) DEFAULT NULL,
  `total_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `actual_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `favored_amount` decimal(10,2) DEFAULT '0.00',
  `not_favored_amount` decimal(10,2) DEFAULT '0.00',
  `pay_type` tinyint(2) DEFAULT NULL,
  `is_evaluation` tinyint(1) DEFAULT '0',
  `status` tinyint(2) NOT NULL,
  `order_status` tinyint(1) NOT NULL DEFAULT '1',
  `commission_status` tinyint(2) NOT NULL DEFAULT '0',
  `comment_time` datetime DEFAULT NULL,
  `gmt_modified` datetime NOT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_commission` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for property
-- ----------------------------
DROP TABLE IF EXISTS `property`;
CREATE TABLE `property` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `value` varchar(20) NOT NULL,
  `remark` varchar(50) DEFAULT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `member_id` bigint(20) unsigned NOT NULL,
  `merchant_id` bigint(20) unsigned NOT NULL,
  `merchant_store_id` bigint(20) unsigned NOT NULL,
  `merchant_name` varchar(100) NOT NULL,
  `product_id` bigint(20) unsigned NOT NULL,
  `product_model_id` bigint(20) unsigned NOT NULL,
  `quantity` int(10) unsigned NOT NULL,
  `sales_price` decimal(12,2) unsigned NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `gmt_create` datetime NOT NULL,
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for shopping_order
-- ----------------------------
DROP TABLE IF EXISTS `shopping_order`;
CREATE TABLE `shopping_order` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `member_id` bigint(20) unsigned NOT NULL,
  `member_num` varchar(19) DEFAULT NULL,
  `merchant_id` bigint(20) NOT NULL,
  `merchant_store_id` bigint(20) unsigned NOT NULL,
  `merchant_num` varchar(19) DEFAULT '',
  `merchant_name` varchar(100) NOT NULL,
  `consignee_name` varchar(20) NOT NULL,
  `consignee_address` varchar(100) NOT NULL,
  `consignee_mobile` varchar(15) NOT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `message` varchar(200) DEFAULT NULL,
  `freight_price` decimal(10,2) unsigned NOT NULL,
  `commodity_total_price` decimal(10,2) unsigned NOT NULL,
  `order_total_price` decimal(10,2) unsigned NOT NULL,
  `actual_amount` decimal(10,2) unsigned DEFAULT NULL,
  `commission_status` tinyint(2) unsigned NOT NULL DEFAULT '0',
  `order_status` tinyint(2) unsigned NOT NULL,
  `status` tinyint(2) NOT NULL,
  `send_time` int(11) unsigned NOT NULL DEFAULT '0',
  `is_fans` tinyint(1) unsigned NOT NULL,
  `is_no_reason_return` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `is_automatic_receipt` tinyint(1) unsigned DEFAULT NULL,
  `is_done` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `shopping_cart_ids_str` varchar(50) DEFAULT NULL,
  `order_num` varchar(20) NOT NULL,
  `payment_method` tinyint(2) unsigned DEFAULT NULL,
  `third_number` varchar(50) DEFAULT NULL,
  `is_needs_logistics` tinyint(1) unsigned DEFAULT '0',
  `waybill_num` varchar(20) DEFAULT NULL,
  `express_company_id` int(11) unsigned DEFAULT NULL,
  `express_company_code` varchar(10) DEFAULT NULL,
  `express_company_name` varchar(25) DEFAULT NULL,
  `gmt_commission` datetime DEFAULT NULL,
  `gmt_payment` datetime DEFAULT NULL,
  `gmt_transport` datetime DEFAULT NULL,
  `gmt_transaction` datetime DEFAULT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for shopping_order_item
-- ----------------------------
DROP TABLE IF EXISTS `shopping_order_item`;
CREATE TABLE `shopping_order_item` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `shopping_order_id` bigint(20) unsigned NOT NULL,
  `product_id` bigint(11) unsigned NOT NULL,
  `product_name` varchar(100) NOT NULL,
  `product_model_id` bigint(20) unsigned NOT NULL,
  `product_model_name` varchar(100) NOT NULL,
  `product_feature_image` varchar(120) NOT NULL,
  `regular_price` decimal(10,2) unsigned NOT NULL,
  `sales_price` decimal(10,2) unsigned NOT NULL,
  `quantity` int(11) unsigned NOT NULL,
  `is_evaluation` tinyint(1) unsigned NOT NULL,
  `is_allow_refund` tinyint(1) unsigned NOT NULL,
  `order_status` tinyint(2) unsigned NOT NULL,
  `refund_status` tinyint(2) unsigned DEFAULT NULL,
  `send_time` int(11) unsigned NOT NULL DEFAULT '0',
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for shopping_refund_detail
-- ----------------------------
DROP TABLE IF EXISTS `shopping_refund_detail`;
CREATE TABLE `shopping_refund_detail` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `shopping_order_item_id` bigint(20) unsigned NOT NULL COMMENT '购物订单项id',
  `type` tinyint(2) unsigned NOT NULL COMMENT '退款类型(0-退款|1-退货退款)',
  `reason` varchar(100) NOT NULL COMMENT '退货原因',
  `description` varchar(200) DEFAULT NULL COMMENT '退款描述',
  `voucher_picture` varchar(150) DEFAULT NULL COMMENT '凭证图片',
  `refuse_images` varchar(500) DEFAULT NULL COMMENT '拒绝退款图片 ',
  `refusal_reasons` varchar(200) DEFAULT NULL COMMENT '拒绝退款理由',
  `amount` decimal(10,2) NOT NULL COMMENT '退款金额',
  `consignee_name` varchar(20) DEFAULT NULL COMMENT '收货人姓名',
  `consignee_address` varchar(100) DEFAULT NULL COMMENT '收货人地址',
  `consignee_mobile` varchar(15) DEFAULT NULL COMMENT '收货人手机号码',
  `express_company_id` int(11) DEFAULT NULL COMMENT '快递公司id',
  `express_company_code` varchar(10) DEFAULT NULL COMMENT '快递公司编码',
  `express_company_name` varchar(25) DEFAULT NULL COMMENT '快递公司名称',
  `waybill_num` varchar(20) DEFAULT NULL COMMENT '物流编号',
  `status` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '状态(0-无效|1-有效)',
  `is_agree` tinyint(1) unsigned DEFAULT NULL COMMENT '商家是否同意退货申请',
  `gmt_refund` datetime DEFAULT NULL COMMENT '退款时间',
  `gmt_confirmed` datetime DEFAULT NULL COMMENT '商家确认时间',
  `gmt_fill` datetime DEFAULT NULL COMMENT '商家填写退货地址',
  `gmt_submit` datetime DEFAULT NULL COMMENT '买家提交退货物流时间',
  `gmt_intervention` datetime DEFAULT NULL COMMENT '平台介入时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for shopping_refund_process
-- ----------------------------
DROP TABLE IF EXISTS `shopping_refund_process`;
CREATE TABLE `shopping_refund_process` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `shopping_refund_detail_id` bigint(20) unsigned NOT NULL,
  `refund_status` tinyint(2) unsigned NOT NULL,
  `gmt_create` datetime NOT NULL,
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for transaction_record
-- ----------------------------
DROP TABLE IF EXISTS `transaction_record`;
CREATE TABLE `transaction_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `relate_id` bigint(20) unsigned NOT NULL,
  `type` tinyint(3) unsigned NOT NULL,
  `is_processed` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `times` bigint(20) unsigned NOT NULL DEFAULT '0',
  `gmt_modified` datetime NOT NULL,
  `gmt_create` datetime NOT NULL,
  PRIMARY KEY (`id`)
);