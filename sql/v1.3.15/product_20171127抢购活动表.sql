-- 添加是否提醒字段，用于定时任务
use eshop_product;
ALTER TABLE `seckill_activity`
ADD COLUMN `is_remind` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否提醒' AFTER `activity_status`;