alter table ad add column  `pay_type` tinyint(2)  COMMENT '支付类型' after is_pay;
alter table ad add column  `order_num` varchar(30)  COMMENT '订单编号' after status;