alter table ad add merchant_num varchar(19) not Null COMMENT '商家编号' after merchant_id;
alter table point_pool add member_num varchar(19) not Null COMMENT '会员编号' after member_id;
alter table red_packet add merchant_num varchar(19) not Null COMMENT '商家编号' after merchant_id;