alter table recharge add column `user_type` tinyint(2) unsigned NOT NULL COMMENT '用户类型(用户1|商家2)' AFTER `user_num`;