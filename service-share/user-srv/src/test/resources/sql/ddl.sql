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
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
	`user_id` BIGINT(20) UNSIGNED NULL DEFAULT NULL COMMENT '用户ID',
	`user_num` VARCHAR(19) NOT NULL COMMENT '用户编号',
	`is_default` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '默认，0否，1是',
	`name` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '收件人' ,
	`mobile` VARCHAR(15) NOT NULL COMMENT '手机' ,
	`region_path` VARCHAR(25) NOT NULL COMMENT '省市区' ,
	`region_name` VARCHAR(75) NOT NULL COMMENT '收货地址名称',
	`addr` VARCHAR(75) NOT NULL DEFAULT '' COMMENT '地址，除省市县外' ,
	`postcode` CHAR(6) NULL DEFAULT '' COMMENT '邮编' ,
	`remark` VARCHAR(100) NULL DEFAULT '' COMMENT '备注' ,
	`status` TINYINT(2) NOT NULL COMMENT '状态',
	`gmt_modified` DATETIME NOT NULL COMMENT '修改时间',
	`gmt_create` DATETIME NOT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for fans_merchant
-- ----------------------------
DROP TABLE IF EXISTS `fans_merchant`;
CREATE TABLE `fans_merchant` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
	`merchant_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '商家',
	`member_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '会员',
	`gmt_create` DATETIME NOT NULL COMMENT '创建日期',
	`channel` TINYINT(3) NOT NULL COMMENT '成为粉丝的途径(1-注册|2-邀请|3-买单消费|4-订单消费|5-抢红包|6-关注)',
	PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for favorite_merchant
-- ----------------------------
DROP TABLE IF EXISTS `favorite_merchant`;
CREATE TABLE `favorite_merchant` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
	`member_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '会员',
	`merchant_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '收藏',
	`gmt_create` DATETIME NULL DEFAULT NULL COMMENT '创建日期',
	`manage_type` TINYINT(2) NOT NULL COMMENT '1-普通店铺 2-实体店铺',
	PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for follow_transaction_record
-- ----------------------------
DROP TABLE IF EXISTS `follow_transaction_record`;
CREATE TABLE `follow_transaction_record` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
	`transation_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '事务记录id',
	`topic` VARCHAR(30) NOT NULL COMMENT 'MQ消息的topic',
	`gmt_create` DATETIME NOT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for invite_relation
-- ----------------------------
DROP TABLE IF EXISTS `invite_relation`;
CREATE TABLE `invite_relation` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	`user_num` VARCHAR(19) NOT NULL COMMENT '用户编号',
	`invite_user_num` VARCHAR(19) NOT NULL COMMENT '被邀请用户编号',
	`depth` INT(5) NOT NULL COMMENT '深度',
	`type` TINYINT(2) NOT NULL COMMENT '邀请类型1--会员，2--商户',
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
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
	`num` VARCHAR(19) NULL DEFAULT NULL COMMENT '会员编号',
	`account` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '登录账号' ,
	`pwd` CHAR(57) NOT NULL DEFAULT '' COMMENT '登录密码' ,
	`mobile` VARCHAR(15) NOT NULL DEFAULT '' COMMENT '手机号码' ,
	`name` VARCHAR(50) NULL DEFAULT NULL COMMENT '姓名',
	`nickname` VARCHAR(50) NULL DEFAULT NULL COMMENT '昵称',
	`region_path` VARCHAR(25) NULL DEFAULT NULL COMMENT '地区路径',
	`region_name` VARCHAR(100) NULL DEFAULT NULL COMMENT '区域名称',
	`sex` TINYINT(2) NULL DEFAULT NULL COMMENT '性别 (0--男，2--女，1--保密)',
	`birthday` DATE NULL DEFAULT NULL COMMENT '出生年月',
	`headimg` VARCHAR(200) NULL DEFAULT NULL COMMENT '头像',
	`status` TINYINT(2) NOT NULL COMMENT '状态 (0--无效，1--有效)',
	`inviter_id` BIGINT(20) NULL DEFAULT NULL COMMENT '邀请者ID',
	`inviter_type` TINYINT(2) NULL DEFAULT NULL COMMENT '邀请者类型 (0--无推荐，1--会员，2--商户)',
	`level` INT(3) NULL DEFAULT NULL COMMENT '等级',
	`gt_cid` VARCHAR(100) NULL DEFAULT NULL COMMENT '个推CID',
	`ry_token` VARCHAR(200) NULL DEFAULT NULL COMMENT '融云token',
	`gmt_modified` DATETIME NULL DEFAULT NULL COMMENT '修改时间',
	`gmt_create` DATETIME NOT NULL COMMENT '创建时间',
	`property_id` BIGINT(20) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for member_profile
-- ----------------------------
DROP TABLE IF EXISTS `member_profile`;
CREATE TABLE `member_profile` (
	`id` BIGINT(20) UNSIGNED NOT NULL COMMENT '主键，与member主键一致',
	`invite_member_count` INT(11) UNSIGNED NOT NULL DEFAULT '0' COMMENT '邀请会员数(一级)',
	`invite_member_count2` INT(11) UNSIGNED NOT NULL DEFAULT '0' COMMENT '邀请会员数(二级)',
	`invite_member_count3` INT(11) UNSIGNED NOT NULL DEFAULT '0' COMMENT '邀请会员数(三级)',
	`invite_merchant_count` INT(11) UNSIGNED NOT NULL DEFAULT '0' COMMENT '邀请商家数(一级)',
	`invite_merchant_count2` INT(11) UNSIGNED NOT NULL DEFAULT '0' COMMENT '邀请商家数(二级)',
	`invite_merchant_count3` INT(11) UNSIGNED NOT NULL DEFAULT '0' COMMENT '邀请商家数(三级)',
	`gmt_modified` DATETIME NULL DEFAULT NULL COMMENT '修改日期',
	`gmt_create` DATETIME NOT NULL COMMENT '创建日期',
	PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for merchant
-- ----------------------------
DROP TABLE IF EXISTS `merchant`;
CREATE TABLE `merchant` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
	`num` VARCHAR(19) NULL DEFAULT '' COMMENT '商家编号',
	`account` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '账号',
	`pwd` CHAR(57) NOT NULL COMMENT '登录密码',
	`mobile` VARCHAR(15) NOT NULL DEFAULT '' COMMENT '手机号码',
	`headimg` VARCHAR(200) NULL DEFAULT NULL COMMENT '头像',
	`status` TINYINT(2) NOT NULL COMMENT '状态 (0--无效，1--有效)',
	`inviter_id` BIGINT(20) NULL DEFAULT NULL COMMENT '邀请者ID',
	`inviter_type` TINYINT(2) NULL DEFAULT NULL COMMENT '邀请者类型 (1--会员，2--商户)',
	`level` INT(3) NULL DEFAULT NULL COMMENT '等级',
	`gt_cid` VARCHAR(100) NULL DEFAULT NULL COMMENT '个推CID',
	`ry_token` VARCHAR(200) NULL DEFAULT NULL COMMENT '融云token',
	`gmt_modified` DATETIME NULL DEFAULT NULL COMMENT '修改时间',
	`gmt_create` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
	`property_id` BIGINT(20) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for merchant_profile
-- ----------------------------
DROP TABLE IF EXISTS `merchant_profile`;
CREATE TABLE `merchant_profile` (
	`id` BIGINT(20) UNSIGNED NOT NULL COMMENT '主键，与merchant主键一致',
	`invite_member_count` INT(11) UNSIGNED NOT NULL DEFAULT '0' COMMENT '邀请会员数(一级)',
	`invite_member_count2` INT(11) UNSIGNED NOT NULL DEFAULT '0' COMMENT '邀请会员数(二级)',
	`invite_member_count3` INT(11) UNSIGNED NOT NULL DEFAULT '0' COMMENT '邀请会员数(三级)',
	`invite_merchant_count` INT(11) UNSIGNED NOT NULL DEFAULT '0' COMMENT '邀请商家数(一级)',
	`invite_merchant_count2` INT(11) UNSIGNED NOT NULL DEFAULT '0' COMMENT '邀请商家数(二级)',
	`invite_merchant_count3` INT(11) UNSIGNED NOT NULL DEFAULT '0' COMMENT '邀请商家数(三级)',
	`website_url` VARCHAR(200) NULL DEFAULT NULL COMMENT '官网链接',
	`taobao_url` VARCHAR(200) NULL DEFAULT NULL COMMENT '淘宝链接',
	`tmall_url` VARCHAR(200) NULL DEFAULT NULL COMMENT '天猫链接',
	`jd_url` VARCHAR(200) NULL DEFAULT NULL COMMENT '京东链接',
	`gmt_modified` DATETIME NULL DEFAULT NULL COMMENT '修改日期',
	`gmt_create` DATETIME NULL DEFAULT NULL COMMENT '创建日期',
	PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for merchant_store
-- ----------------------------
DROP TABLE IF EXISTS `merchant_store`;
CREATE TABLE `merchant_store` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
	`merchant_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '商家',
	`name` VARCHAR(100) NOT NULL COMMENT '店铺名称' ,
	`region_path` VARCHAR(25) NOT NULL COMMENT '省市区' ,
	`region_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '区域名称',
	`address` VARCHAR(75) NOT NULL COMMENT '店铺地址信息' ,
	`longitude` DECIMAL(10,7) UNSIGNED NOT NULL COMMENT '经度',
	`latitude` DECIMAL(10,7) UNSIGNED NOT NULL COMMENT '纬度',
	`industry_path` VARCHAR(10) NULL DEFAULT NULL COMMENT '主营业务' ,
	`industry_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '主营业务名称' ,
	`intro` VARCHAR(500) NOT NULL COMMENT '店铺介绍' ,
	`status` TINYINT(2) UNSIGNED NOT NULL COMMENT '0：待审核，1：审核通过，2：审核不通过，3：未提交保证金，4：已提交保证金待财务核实，5：财务审核不通过，6：注销',
	`principal_name` VARCHAR(20) NULL DEFAULT NULL COMMENT '负责人名字',
	`principal_mobile` VARCHAR(15) NULL DEFAULT NULL COMMENT '负责人手机',
	`is_no_reason_return` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否支持无理由退货,0否 1是',
	`favorite_number` INT(10) UNSIGNED NULL DEFAULT '0' COMMENT '收藏人数',
	`buy_numbers` INT(10) UNSIGNED NULL DEFAULT '0' COMMENT '已买笔数',
	`comments_count` INT(10) UNSIGNED NULL DEFAULT '0' COMMENT '评价人数',
	`average_consume_amount` DECIMAL(10,2) UNSIGNED NULL DEFAULT '0.00' COMMENT '人均消费',
	`average_score` DECIMAL(10,2) UNSIGNED NULL DEFAULT '0.00' COMMENT '平均得分',
	`feedback_rate` DECIMAL(10,2) UNSIGNED NULL DEFAULT '0.00' COMMENT '好评率',
	`gmt_modified` DATETIME NULL DEFAULT NULL COMMENT '修改时间',
	`gmt_create` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for merchant_store_audit
-- ----------------------------
DROP TABLE IF EXISTS `merchant_store_audit`;
CREATE TABLE `merchant_store_audit` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
	`merchant_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '商家',
	`merchant_store_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '门店',
	`content` TEXT NOT NULL COMMENT '修改内容json',
	`status` TINYINT(2) UNSIGNED NOT NULL COMMENT '审核状态0:未审核1:审核通过2:审核不通过',
	`type` TINYINT(2) NULL DEFAULT NULL COMMENT '审核类型2：修改资料1：申请实体店',
	`is_show` TINYINT(1) NOT NULL DEFAULT '1' COMMENT '1:展示0不展示',
	`auditor_id` INT(11) NULL DEFAULT NULL COMMENT '审核人员ID',
	`remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '审核备注',
	`audit_time` DATETIME NULL DEFAULT NULL COMMENT '审核时间',
	`syn_time` DATETIME NULL DEFAULT NULL COMMENT '同步时间',
	`gmt_modified` DATETIME NOT NULL COMMENT '修改时间',
	`gmt_create` DATETIME NOT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for merchant_store_image
-- ----------------------------
DROP TABLE IF EXISTS `merchant_store_image`;
CREATE TABLE `merchant_store_image` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
	`merchant_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '商家',
	`merchant_store_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '门店',
	`status` TINYINT(1) NOT NULL COMMENT '状态(0-删除1-正常)',
	`type` TINYINT(3) UNSIGNED NOT NULL COMMENT '图片类别,1：门店照，2：店内环境照，3：门店logo，4：营业执照，5：其他许可证，6：手持身份证照',
	`path` VARCHAR(1200) NULL DEFAULT NULL,
	`gmt_modified` DATETIME NOT NULL COMMENT '修改时间',
	`gmt_create` DATETIME NOT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for merchant_store_profile
-- ----------------------------
DROP TABLE IF EXISTS `merchant_store_profile`;
CREATE TABLE `merchant_store_profile` (
	`id` BIGINT(20) UNSIGNED NOT NULL COMMENT '门店ID',
	`merchant_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '商家ID',
	`company_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '注册公司名称' ,
	`reg_number` VARCHAR(20) NULL DEFAULT NULL COMMENT '营业执照号码' ,
	`company_address` VARCHAR(75) NULL DEFAULT NULL COMMENT '经营住所' ,
	`license_indate` DATE NULL DEFAULT NULL COMMENT '营业执照有效期',
	`manage_type` TINYINT(2) UNSIGNED NOT NULL COMMENT '经营类型(1-普通商户2-实体店铺)',
	`certif_type` TINYINT(3) NULL DEFAULT NULL COMMENT '证件类型(1-身份证2-营业执照)',
	`operator_card_id` VARCHAR(20) NULL DEFAULT NULL COMMENT '个人经营者身份证号码',
	`operator_name` VARCHAR(20) NULL DEFAULT NULL COMMENT '个人经营者姓名',
	`gmt_modified` DATETIME NOT NULL COMMENT '修改时间',
	`gmt_create` DATETIME NOT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for property
-- ----------------------------
DROP TABLE IF EXISTS `property`;
CREATE TABLE `property` (
	`id` BIGINT(20) UNSIGNED NOT NULL,
	`name` VARCHAR(50) NOT NULL COMMENT '键',
	`value` VARCHAR(50) NOT NULL COMMENT '值',
	`remark` TEXT NULL,
	`gmt_modified` DATETIME NULL DEFAULT NULL,
	`gmt_create` DATETIME NOT NULL,
	PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for transaction_record
-- ----------------------------
DROP TABLE IF EXISTS `transaction_record`;
CREATE TABLE `transaction_record` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
	`relate_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '关联ID',
	`type` TINYINT(3) UNSIGNED NOT NULL COMMENT '事务类型',
	`is_processed` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '已处理，0否，1是',
	`times` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '执行次数',
	`gmt_modified` DATETIME NOT NULL COMMENT '修改时间',
	`gmt_create` DATETIME NOT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for user_login_log
-- ----------------------------
DROP TABLE IF EXISTS `user_login_log`;
CREATE TABLE `user_login_log` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
	`user_num` VARCHAR(19) NOT NULL COMMENT '用户编号',
	`account` VARCHAR(20) NOT NULL COMMENT '用户账号',
	`user_type` TINYINT(2) UNSIGNED NOT NULL DEFAULT '0' COMMENT '用户类型，1是商家，2是会员',
	`imei` VARCHAR(20) NULL DEFAULT NULL COMMENT '设备imei',
	`platform` TINYINT(2) NOT NULL COMMENT '请求类型平台。iOS、Android、pcweb、h5',
	`platform_ver` VARCHAR(20) NULL DEFAULT NULL COMMENT '设备的版本号',
	`app_ver` VARCHAR(12) NULL DEFAULT NULL COMMENT '请求来源的version，指app的版本号',
	`city_id` INT(8) UNSIGNED NULL DEFAULT NULL COMMENT '定位到的区域id',
	`channel` VARCHAR(15) NULL DEFAULT NULL COMMENT '分发渠道标识',
	`ip_addr` VARCHAR(15) NULL DEFAULT NULL COMMENT '请求IP',
	`gmt_create` DATETIME NOT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`)
);

