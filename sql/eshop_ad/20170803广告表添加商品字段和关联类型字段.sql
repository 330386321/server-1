alter table ad add product_id bigint(20)  COMMENT '商品id' after merchant_store_id;

alter table ad add relate_type tinyint(2)  COMMENT '关联类型 1店铺 2 商品' after product_id;