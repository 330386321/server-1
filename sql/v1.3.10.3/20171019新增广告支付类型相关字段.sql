alter table ad add column  `pay_type` tinyint(2)  COMMENT '支付类型' after is_pay;
alter table ad add column  `third_number` varchar(30)  COMMENT '第三方交易号' after status;