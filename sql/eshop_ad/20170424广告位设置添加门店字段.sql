alter table ad_platform add column  `merchant_store_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '门店id';

alter table ad_platform modify column `type` tinyint(2)  COMMENT '广告类型(1-纯链接2-商品 3-门店)';
