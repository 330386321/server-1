use eshop_product;

alter table product add column `total_inventory` int(10) NOT NULL COMMENT '商品总库存' after `status`;
alter table product add column `total_sales_volume` int(10) NOT NULL COMMENT '商品总销量' after `total_inventory`;
alter table product add column `min_price` decimal(10,2) NOT NULL COMMENT '型号最低价' after `total_sales_volume`;
alter table product add column `max_price` decimal(10,2) NOT NULL COMMENT '型号最高价' after `min_price`;

alter table product_image add column `content` text COMMENT '描述' after `img_type`;
alter table product_image add column `sort` int(3) NOT NULL COMMENT '顺序' after `content`;
