alter table ad_platform add column  `merchant_store_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '�ŵ�id';

alter table ad_platform modify column `type` tinyint(2)  COMMENT '�������(1-������2-��Ʒ 3-�ŵ�)';
