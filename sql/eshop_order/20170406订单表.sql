/*20170406 建表*/
CREATE TABLE `shopping_order` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `member_id` bigint(20) unsigned NOT NULL COMMENT '用户id',
  `merchant_id` bigint(20) unsigned NOT NULL COMMENT '商家id',
  `merchant_name` varchar(100) NOT NULL COMMENT '商家名称',
  `consignee_name` varchar(20) NOT NULL COMMENT '收货人姓名',
  `consignee_address` varchar(100) NOT NULL COMMENT '收货人地址',
  `consignee_mobile` varchar(15) NOT NULL COMMENT '收货人手机号码',
  `message` varchar(200) DEFAULT NULL COMMENT '买家留言',
  `freight_price` decimal(10,2) unsigned NOT NULL COMMENT '运费',
  `commodity_total_price` decimal(10,2) unsigned NOT NULL COMMENT '商品总价',
  `order_total_price` decimal(10,2) unsigned NOT NULL COMMENT '订单总价',
  `order_status` tinyint(2) unsigned NOT NULL COMMENT '订单的总状态(0-待付款|1-待发货|2-交易成功|3-交易取消|4-待商家确认|5-待退货|6-待退款|7-退款成功)',
  `status` tinyint(2) NOT NULL COMMENT '状态(0删除1正常)',
  `is_evaluation` tinyint(1) unsigned NOT NULL COMMENT '是否评价',
  `order_num` varchar(20) NOT NULL COMMENT '订单编号',
  `waybill_num` varchar(20) DEFAULT NULL COMMENT '运单编号',
  `express_company_id` int(11) unsigned DEFAULT NULL COMMENT '快递公司id',
  `express_company_name` varchar(25) DEFAULT NULL COMMENT '快递公司名称',
  `gmt_payment` datetime DEFAULT NULL COMMENT '付款时间',
  `gmt_transport` datetime DEFAULT NULL COMMENT '发货时间',
  `gmt_transaction` datetime DEFAULT NULL COMMENT '确认收货时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物订单详情';

