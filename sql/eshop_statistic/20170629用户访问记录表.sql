ALTER TABLE user_visit_record ADD COLUMN `city_id` int (8) UNSIGNED NOT NULL COMMENT '市级区域ID' AFTER `visit_count`;

ALTER TABLE user_visit_record ADD COLUMN `city_name` varchar (50)  NOT NULL COMMENT '市级名称' AFTER `city_id`;


ALTER TABLE user_visit_record MODIFY COLUMN gmt_create date NOT NULL COMMENT '创建时间';