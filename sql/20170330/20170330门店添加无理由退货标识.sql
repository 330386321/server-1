alter table merchant_store add column `is_no_reason_return` tinyint(1) unsigned NOT NULL DEFAULT 0 COMMENT '是否支持无理由退货,0否 1是' after `principal_mobile`;