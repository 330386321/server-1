-- 冗余活动相关id,用于关联查询
ALTER TABLE `shopping_order`
ADD COLUMN `activity_id`  bigint(20) UNSIGNED NULL COMMENT '抢购活动id' AFTER `merchant_store_region_path`,
ADD COLUMN `activity_product_id`  bigint(20) UNSIGNED NULL COMMENT '抢购活动商品id' AFTER `activity_id`;