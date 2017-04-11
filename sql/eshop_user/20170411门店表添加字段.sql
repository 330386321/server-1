alter table merchant_store add column `region_name` VARCHAR(50) COMMENT '区域名称' after `region_path`;

alter table merchant_store add column `feedback_rate` DECIMAL(10,2) unsigned DEFAULT 0 COMMENT '好评率' after `is_no_reason_return`;
alter table merchant_store add column `average_score` DECIMAL(10,2) unsigned DEFAULT 0 COMMENT '平均得分' after `is_no_reason_return`;
alter table merchant_store add column `average_consume_amount` DECIMAL(10,2) unsigned DEFAULT 0 COMMENT '人均消费' after `is_no_reason_return`;
alter table merchant_store add column `buy_numbers` int(10) unsigned DEFAULT 0 COMMENT '已买笔数' after `is_no_reason_return`;
alter table merchant_store add column `favorite_number` int(10) unsigned DEFAULT 0 COMMENT '收藏人数' after `is_no_reason_return`;