/*20170406 建表*/
CREATE TABLE `shopping_order_item` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `shopping_order_id` bigint(20) unsigned NOT NULL COMMENT '订单id',
  `product_id` bigint(11) unsigned NOT NULL COMMENT '商品id',
  `product_name` varchar(100) NOT NULL COMMENT '商品名称',
  `product_model_id` bigint(20) unsigned NOT NULL COMMENT '商品型号id',
  `product_model_name` varchar(100) NOT NULL COMMENT '商品型号名称',
  `product_feature_image` varchar(120) NOT NULL COMMENT '商品特征图片',
  `regular_price` decimal(10,2) unsigned NOT NULL COMMENT '原价',
  `sales_price` decimal(10,2) unsigned NOT NULL COMMENT '现价',
  `quantity` int(11) unsigned NOT NULL COMMENT '数量',
  `order_status` tinyint(2) unsigned NOT NULL COMMENT '订单项状态(0-待付款|1-待发货|2-交易成功|3-交易取消|4-待商家确认|5-待退货|6-待退款|7-退款成功)',
  `gmt_return` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物订单';

