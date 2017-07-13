/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.26_3306
Source Server Version : 50717
Source Host           : 192.168.1.26:3306
Source Database       : eshop_mall

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-07-12 09:23:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for comment_image
-- ----------------------------
DROP TABLE IF EXISTS `comment_image`;
CREATE TABLE `comment_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `img_url` varchar(200) NOT NULL COMMENT '图片路径',
  `comment_id` bigint(20) NOT NULL COMMENT '评价id',
  `type` tinyint(2) NOT NULL COMMENT '评论类型 1：商家2：商品',
  `status` tinyint(1) NOT NULL COMMENT '0：删除1：正常',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for comment_merchant
-- ----------------------------
DROP TABLE IF EXISTS `comment_merchant`;
CREATE TABLE `comment_merchant` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content` varchar(500) DEFAULT NULL COMMENT '评价内容',
  `pay_order_id` bigint(20) DEFAULT NULL COMMENT '买单ID',
  `reply_content` varchar(500) DEFAULT NULL COMMENT '商家回复内容',
  `grade` tinyint(2) NOT NULL COMMENT '评分',
  `member_id` bigint(20) NOT NULL COMMENT '用户',
  `merchant_id` bigint(20) NOT NULL COMMENT '商家',
  `is_anonymous` tinyint(1) NOT NULL COMMENT '是否匿名（0：否1：是）',
  `avg_spend` decimal(10,2) NOT NULL COMMENT '人均消费',
  `status` tinyint(2) NOT NULL COMMENT '状态（1：有效0：无效）',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_reply` datetime DEFAULT NULL COMMENT '回复时间',
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for comment_product
-- ----------------------------
DROP TABLE IF EXISTS `comment_product`;
CREATE TABLE `comment_product` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content` varchar(500) DEFAULT NULL COMMENT '评价内容',
  `reply_content` varchar(500) DEFAULT NULL COMMENT '商家回复内容',
  `grade` tinyint(2) NOT NULL COMMENT '评分1~5星',
  `member_id` bigint(20) NOT NULL COMMENT '用户',
  `merchant_id` bigint(20) NOT NULL COMMENT '商家ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品',
  `order_item_id` bigint(20) NOT NULL COMMENT '订单项ID',
  `product_model_id` bigint(20) NOT NULL COMMENT '商品型号id',
  `is_anonymous` tinyint(1) NOT NULL COMMENT '是否匿名（0：否1：是）',
  `status` tinyint(2) NOT NULL COMMENT '状态（1：有效0：无效）',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_reply` datetime DEFAULT NULL COMMENT '回复时间',
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for discount_package
-- ----------------------------
DROP TABLE IF EXISTS `discount_package`;
CREATE TABLE `discount_package` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchant_id` bigint(20) unsigned NOT NULL COMMENT '商家id',
  `merchant_store_id` bigint(20) NOT NULL COMMENT '实体门店id',
  `name` varchar(25) NOT NULL COMMENT '套餐名称',
  `cover_image` varchar(255) NOT NULL COMMENT '封面图片',
  `price` decimal(7,2) unsigned NOT NULL COMMENT '套餐价格',
  `original_price` decimal(7,2) unsigned NOT NULL COMMENT '原价',
  `other_instructions` varchar(250) DEFAULT NULL COMMENT '其他说明',
  `validity_period_begin` datetime NOT NULL COMMENT '有效期-开始(yyyy-MM-dd)',
  `validity_period_end` datetime NOT NULL COMMENT '有效期-结束(yyyy-MM-dd)',
  `use_time_week` varchar(15) NOT NULL COMMENT '使用时间-周一到周日(用1-7表示,并用逗号分隔)',
  `use_time_begin` time NOT NULL COMMENT '使用时间-开始(HH:mm)',
  `use_time_end` time NOT NULL COMMENT '使用时间-结束(HH:mm)',
  `status` tinyint(2) unsigned NOT NULL COMMENT '状态(0-删除|1-上架|2-下架)',
  `is_reservation` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否需要预约(0-免预约|1-需要预约)',
  `use_rules` varchar(550) DEFAULT NULL COMMENT '使用规则',
  `gmt_up` datetime NOT NULL COMMENT '上架时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for discount_package_content
-- ----------------------------
DROP TABLE IF EXISTS `discount_package_content`;
CREATE TABLE `discount_package_content` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `discount_package_id` bigint(20) unsigned NOT NULL COMMENT '优惠套餐id',
  `name` varchar(20) NOT NULL COMMENT '内容名称',
  `unit_price` decimal(5,2) unsigned NOT NULL COMMENT '单价',
  `quantity` int(11) unsigned NOT NULL COMMENT '数量',
  `unit` varchar(5) NOT NULL COMMENT '单位',
  `subtotal` decimal(5,2) unsigned NOT NULL COMMENT '小计',
  `status` tinyint(2) unsigned NOT NULL COMMENT '状态(0-删除|1-正常)',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ;

-- ----------------------------
-- Table structure for discount_package_image
-- ----------------------------
DROP TABLE IF EXISTS `discount_package_image`;
CREATE TABLE `discount_package_image` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `discount_package_id` bigint(20) unsigned NOT NULL COMMENT '优惠套餐id',
  `description` varchar(100) NOT NULL COMMENT '文字描述',
  `image` varchar(255) NOT NULL COMMENT '图片',
  `status` tinyint(2) unsigned NOT NULL COMMENT '状态(0-删除|1-正常)',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for express_company
-- ----------------------------
DROP TABLE IF EXISTS `express_company`;
CREATE TABLE `express_company` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(10) NOT NULL COMMENT '编码',
  `name` varchar(25) NOT NULL COMMENT '名称',
  `homepage` varchar(75)  NOT NULL COMMENT '网址',
  `tel` varchar(15)  NOT NULL COMMENT '电话',
  `ordinal` int(11) unsigned NOT NULL COMMENT '排序(对应name首字母ASCII编码的十进制',
  `is_show` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否显示在列表',
  `status` tinyint(3) unsigned NOT NULL COMMENT '状态',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ;


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
-- Table structure for industry_type
-- ----------------------------
DROP TABLE IF EXISTS `industry_type`;
CREATE TABLE `industry_type` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` smallint(5) unsigned NOT NULL COMMENT '父编号',
  `path` varchar(25)  COMMENT '行业路径',
  `name` varchar(50)  COMMENT '行业名称',
  `image_url` varchar(120) NOT NULL DEFAULT '' COMMENT '图片路径',
  `ordinal` int(10) unsigned NOT NULL COMMENT '序号',
  `status` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '1为启用,0为停用',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for merchant_favored
-- ----------------------------
DROP TABLE IF EXISTS `merchant_favored`;
CREATE TABLE `merchant_favored` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchant_id` bigint(20) NOT NULL COMMENT '商家ID',
  `type` tinyint(2) NOT NULL COMMENT '1:每满、2:满减、3:全单折扣',
  `reach_amount` decimal(10,2) DEFAULT NULL COMMENT '满额',
  `favored_amount` decimal(10,2) DEFAULT NULL COMMENT '优惠金额',
  `discount_rate` decimal(10,2) DEFAULT NULL COMMENT '折扣率',
  `valid_week_time` varchar(50) DEFAULT NULL COMMENT '每周有效时间段',
  `valid_day_begin_time` varchar(20) DEFAULT NULL COMMENT '每日有效开始时间',
  `valid_day_end_time` varchar(20) DEFAULT NULL COMMENT '每日有效结束时间',
  `entire_begin_time` date NOT NULL COMMENT '总有效期：开始时间',
  `entire_end_time` date NOT NULL COMMENT '总有效期：结束时间',
  `status` tinyint(2) NOT NULL COMMENT '状态（1：有效0：无效）',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_num` varchar(19) NOT NULL COMMENT '用户编号',
  `relate_id` bigint(20) DEFAULT NULL COMMENT '关联主键',
  `type` tinyint(3) NOT NULL COMMENT '消息类型 1:推荐店铺2:平台通知3:充值4:提现5:收益6:发货通知7:派送通知8:签收通知',
  `title` varchar(50) DEFAULT NULL COMMENT '标题',
  `content` varchar(500) NOT NULL COMMENT '消息内容',
  `status` tinyint(2) unsigned NOT NULL COMMENT '状态：0未读，1已读，2删除',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for message_template
-- ----------------------------
DROP TABLE IF EXISTS `message_template`;
CREATE TABLE `message_template` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(50) NOT NULL COMMENT '标题',
  `content` varchar(500) NOT NULL COMMENT '模板内容',
  `type` tinyint(3) unsigned DEFAULT NULL COMMENT '1邀请粉丝--会员2订单付款3活动消息4订单已发货5订单正在派件6提现申请7提现成功8提现失败9余额充值10积分充值11买单成功12同意退款13拒绝退款14退款成功15付款成功16推荐E友获取现金17推荐E友获取积分18推荐商家获取现金19推荐商家获取积分20ad审核通过21ad审核不通过22门店审核通过23门店审核不通过24保证金审核通过25新增订单26商家发货提醒27商家退货提醒28用户已发货29运营平台通知30现金红包31ad自动下架32邀请粉丝--商家33订单已签收34看广告获取金额35抢赞获取金额36保证金审核未通过37ad强制下架38退款申请39消费积分投放广告40消费积分红包41订单成功--商家42买单成功--商家',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ;

-- ----------------------------
-- Table structure for property
-- ----------------------------
DROP TABLE IF EXISTS `property`;
CREATE TABLE `property` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '键',
  `value` varchar(20) NOT NULL COMMENT '值',
  `remark` varchar(50) DEFAULT NULL,
  `gmt_create` datetime NOT NULL,
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for region
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region` (
  `id` int(8) unsigned NOT NULL COMMENT '区域ID',
  `parent_id` int(8) unsigned NOT NULL COMMENT '父级区域',
  `path` varchar(25) NOT NULL DEFAULT '' COMMENT '路径',
  `level` tinyint(3) unsigned NOT NULL COMMENT '层级',
  `name` varchar(50) NOT NULL COMMENT '区域名称',
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for search_word
-- ----------------------------
DROP TABLE IF EXISTS `search_word`;
CREATE TABLE `search_word` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `word` varchar(20) NOT NULL COMMENT '搜索词条',
  `type` tinyint(3) unsigned NOT NULL COMMENT '词条类型：1--门店，2--商品',
  `gmt_create` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ;

-- ----------------------------
-- Table structure for sms_record
-- ----------------------------
DROP TABLE IF EXISTS `sms_record`;
CREATE TABLE `sms_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mobile` varchar(15) NOT NULL DEFAULT '' COMMENT '手机号码',
  `content` varchar(500) NOT NULL COMMENT '短信内容',
  `ip` varchar(20) DEFAULT NULL COMMENT '地址',
  `type` tinyint(2) unsigned DEFAULT NULL COMMENT '类型 1--注册，2--找回登录密码，2--找回支付密码',
  `is_success` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '1成功，0失败',
  `fail_reason` varchar(500) DEFAULT NULL COMMENT '失败原因',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ;

-- ----------------------------
-- Table structure for suggestion
-- ----------------------------
DROP TABLE IF EXISTS `suggestion`;
CREATE TABLE `suggestion` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_num` varchar(19) NOT NULL DEFAULT '' COMMENT '用户编号',
  `content` varchar(1000) NOT NULL DEFAULT '' COMMENT '建议内容',
  `user_type` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '用户类型，1是商家，2是会员',
  `client_type` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '客户端类型，1是android，2是ios',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ;

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
) ;

-- ----------------------------
-- Table structure for verify_code
-- ----------------------------
DROP TABLE IF EXISTS `verify_code`;
CREATE TABLE `verify_code` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mobile` varchar(15) NOT NULL DEFAULT '' COMMENT '手机号码',
  `code` varchar(10) NOT NULL COMMENT '验证码',
  `purpose` tinyint(3) unsigned DEFAULT NULL COMMENT '用途',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ;
