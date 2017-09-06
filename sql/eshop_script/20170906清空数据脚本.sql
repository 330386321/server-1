/*
-- eshop_ad
DELETE FROM eshop_ad.ad;
alter table eshop_ad.ad AUTO_INCREMENT=1;
DELETE FROM eshop_ad.ad_platform;
alter table eshop_ad.ad_platform AUTO_INCREMENT=1;
DELETE FROM eshop_ad.ad_region;
alter table eshop_ad.ad_region AUTO_INCREMENT=1;
DELETE FROM eshop_ad.follow_transaction_record;
alter table eshop_ad.follow_transaction_record AUTO_INCREMENT=1;
DELETE FROM eshop_ad.member_ad_record;
alter table eshop_ad.member_ad_record AUTO_INCREMENT=1;
DELETE FROM eshop_ad.point_pool;
alter table eshop_ad.point_pool AUTO_INCREMENT=1;
DELETE FROM eshop_ad.transaction_record;
alter table eshop_ad.transaction_record AUTO_INCREMENT=1;
DELETE FROM eshop_ad.red_packet;
alter table eshop_ad.red_packet AUTO_INCREMENT=1;
DELETE FROM eshop_ad.user_red_packet;
alter table eshop_ad.user_red_packet AUTO_INCREMENT=1;
DELETE FROM eshop_ad.user_taked_red_packet;
alter table eshop_ad.user_taked_red_packet AUTO_INCREMENT=1;

-- eshop_agent
DELETE FROM eshop_agent.agent_user;
alter table eshop_agent.agent_user AUTO_INCREMENT=1;

-- eshop_mall
DELETE FROM eshop_mall.comment_image;
alter table eshop_mall.comment_image AUTO_INCREMENT=1;
DELETE FROM eshop_mall.comment_merchant;
alter table eshop_mall.comment_merchant AUTO_INCREMENT=1;
DELETE FROM eshop_mall.comment_product;
alter table eshop_mall.comment_product AUTO_INCREMENT=1;
DELETE FROM eshop_mall.discount_package;
alter table eshop_mall.discount_package AUTO_INCREMENT=1;
DELETE FROM eshop_mall.discount_package_content;
alter table eshop_mall.discount_package_content AUTO_INCREMENT=1;
DELETE FROM eshop_mall.discount_package_image;
alter table eshop_mall.discount_package_image AUTO_INCREMENT=1;
DELETE FROM eshop_mall.follow_transaction_record;
alter table eshop_mall.follow_transaction_record AUTO_INCREMENT=1;
DELETE FROM eshop_mall.merchant_favored;
alter table eshop_mall.merchant_favored AUTO_INCREMENT=1;
DELETE FROM eshop_mall.message;
alter table eshop_mall.message AUTO_INCREMENT=1;
DELETE FROM eshop_mall.property;
alter table eshop_mall.property AUTO_INCREMENT=1;
DELETE FROM eshop_mall.region;
alter table eshop_mall.region AUTO_INCREMENT=1;
DELETE FROM eshop_mall.sms_record;
alter table eshop_mall.sms_record AUTO_INCREMENT=1;
DELETE FROM eshop_mall.suggestion;
alter table eshop_mall.suggestion AUTO_INCREMENT=1;
DELETE FROM eshop_mall.transaction_record;
alter table eshop_mall.transaction_record AUTO_INCREMENT=1;
DELETE FROM eshop_mall.verify_code;
alter table eshop_mall.verify_code AUTO_INCREMENT=1;
DELETE FROM eshop_mall.work_order;
alter table eshop_mall.work_order AUTO_INCREMENT=1;
DELETE FROM eshop_mall.inform;
alter table eshop_mall.inform AUTO_INCREMENT=1;

-- eshop_operator
DELETE FROM eshop_operator.log;
alter table eshop_operator.log AUTO_INCREMENT=1;
DELETE FROM eshop_operator.user WHERE id != 1;
alter table eshop_operator.user AUTO_INCREMENT=2;
DELETE FROM eshop_operator.role WHERE id != 1;
alter table eshop_operator.role AUTO_INCREMENT=2;
DELETE FROM eshop_operator.role_permission WHERE role_id != 1;
DELETE FROM eshop_operator.user_role WHERE user_id != 1;

-- eshop_order
DELETE FROM eshop_order.follow_transaction_record;
alter table eshop_order.follow_transaction_record AUTO_INCREMENT=1;
DELETE FROM eshop_order.pay_order;
alter table eshop_order.pay_order AUTO_INCREMENT=1;
DELETE FROM eshop_order.shopping_cart;
alter table eshop_order.shopping_cart AUTO_INCREMENT=1;
DELETE FROM eshop_order.shopping_order;
alter table eshop_order.shopping_order AUTO_INCREMENT=1;
DELETE FROM eshop_order.shopping_order_item;
alter table eshop_order.shopping_order_item AUTO_INCREMENT=1;
DELETE FROM eshop_order.shopping_refund_detail;
alter table eshop_order.shopping_refund_detail AUTO_INCREMENT=1;
DELETE FROM eshop_order.shopping_refund_process;
alter table eshop_order.shopping_refund_process AUTO_INCREMENT=1;
DELETE FROM eshop_order.transaction_record;
alter table eshop_order.transaction_record AUTO_INCREMENT=1;

-- eshop_product
DELETE FROM eshop_product.favorite_product;
alter table eshop_product.favorite_product AUTO_INCREMENT=1;
DELETE FROM eshop_product.follow_transaction_record;
alter table eshop_product.follow_transaction_record AUTO_INCREMENT=1;
DELETE FROM eshop_product.product;
alter table eshop_product.product AUTO_INCREMENT=1;
DELETE FROM eshop_product.product_image;
alter table eshop_product.product_image AUTO_INCREMENT=1;
DELETE FROM eshop_product.product_model;
alter table eshop_product.product_model AUTO_INCREMENT=1;
DELETE FROM eshop_product.product_model_inventory;
alter table eshop_product.product_model_inventory AUTO_INCREMENT=1;
DELETE FROM eshop_product.transaction_record;
alter table eshop_product.transaction_record AUTO_INCREMENT=1;

-- eshop_property
DELETE FROM eshop_property.bank_account;
alter table eshop_property.bank_account AUTO_INCREMENT=1;
DELETE FROM eshop_property.business_deposit;
alter table eshop_property.business_deposit AUTO_INCREMENT=1;
DELETE FROM eshop_property.fans_invite_detail;
alter table eshop_property.fans_invite_detail AUTO_INCREMENT=1;
DELETE FROM eshop_property.follow_transaction_record;
alter table eshop_property.follow_transaction_record AUTO_INCREMENT=1;
DELETE FROM eshop_property.freeze;
alter table eshop_property.freeze AUTO_INCREMENT=1;
DELETE FROM eshop_property.love_detail;
alter table eshop_property.love_detail AUTO_INCREMENT=1;
DELETE FROM eshop_property.point_detail;
alter table eshop_property.point_detail AUTO_INCREMENT=1;
DELETE FROM eshop_property.property_info;
alter table eshop_property.property_info AUTO_INCREMENT=1;
DELETE FROM eshop_property.recharge;
alter table eshop_property.recharge AUTO_INCREMENT=1;
DELETE FROM eshop_property.transaction_detail;
alter table eshop_property.transaction_detail AUTO_INCREMENT=1;
DELETE FROM eshop_property.transaction_record;
alter table eshop_property.transaction_record AUTO_INCREMENT=1;
DELETE FROM eshop_property.withdraw_cash;
alter table eshop_property.withdraw_cash AUTO_INCREMENT=1;

-- eshop_statistic
DELETE FROM eshop_statistic.report_ad_earnings;
alter table eshop_statistic.report_ad_earnings AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_area_ad_point_daily;
alter table eshop_statistic.report_area_ad_point_daily AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_area_ad_point_month;
alter table eshop_statistic.report_area_ad_point_month AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_area_point_consume_daily;
alter table eshop_statistic.report_area_point_consume_daily AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_area_point_consume_month;
alter table eshop_statistic.report_area_point_consume_month AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_area_recharge_daily;
alter table eshop_statistic.report_area_recharge_daily AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_area_recharge_month;
alter table eshop_statistic.report_area_recharge_month AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_area_user_reg_daily;
alter table eshop_statistic.report_area_user_reg_daily AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_area_user_reg_month;
alter table eshop_statistic.report_area_user_reg_month AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_area_volume_daily;
alter table eshop_statistic.report_area_volume_daily AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_area_volume_month;
alter table eshop_statistic.report_area_volume_month AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_area_withdraw_daily;
alter table eshop_statistic.report_area_withdraw_daily AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_area_withdraw_month;
alter table eshop_statistic.report_area_withdraw_month AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_earnings_daily;
alter table eshop_statistic.report_earnings_daily AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_earnings_month;
alter table eshop_statistic.report_earnings_month AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_point_consume_daily;
alter table eshop_statistic.report_point_consume_daily AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_point_consume_month;
alter table eshop_statistic.report_point_consume_month AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_recharge_balance_daily;
alter table eshop_statistic.report_recharge_balance_daily AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_recharge_balance_month;
alter table eshop_statistic.report_recharge_balance_month AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_sales_daily;
alter table eshop_statistic.report_sales_daily AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_sales_month;
alter table eshop_statistic.report_sales_month AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_user_active_area_daily;
alter table eshop_statistic.report_user_active_area_daily AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_user_active_area_month;
alter table eshop_statistic.report_user_active_area_month AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_user_active_daily;
alter table eshop_statistic.report_user_active_daily AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_user_active_month;
alter table eshop_statistic.report_user_active_month AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_user_income_expenditure;
alter table eshop_statistic.report_user_income_expenditure AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_user_reg_daily;
alter table eshop_statistic.report_user_reg_daily AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_user_reg_month;
alter table eshop_statistic.report_user_reg_month AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_withdraw_daily;
alter table eshop_statistic.report_withdraw_daily AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.report_withdraw_month;
alter table eshop_statistic.report_withdraw_month AUTO_INCREMENT=1;
DELETE FROM eshop_statistic.user_visit_record;
alter table eshop_statistic.user_visit_record AUTO_INCREMENT=1;
update eshop_statistic.report_user_reg_area set member_count = 0, merchant_count = 0, merchant_common_count = 0, merchant_entity_count = 0

-- eshop_user
DELETE FROM eshop_user.address;
alter table eshop_user.address AUTO_INCREMENT=1;
DELETE FROM eshop_user.fans_invite_content;
alter table eshop_user.fans_invite_content AUTO_INCREMENT=1;
DELETE FROM eshop_user.fans_invite_result;
alter table eshop_user.fans_invite_result AUTO_INCREMENT=1;
DELETE FROM eshop_user.fans_merchant;
alter table eshop_user.fans_merchant AUTO_INCREMENT=1;
DELETE FROM eshop_user.favorite_merchant;
alter table eshop_user.favorite_merchant AUTO_INCREMENT=1;
DELETE FROM eshop_user.follow_transaction_record;
alter table eshop_user.follow_transaction_record AUTO_INCREMENT=1;
DELETE FROM eshop_user.invite_relation;
alter table eshop_user.invite_relation AUTO_INCREMENT=1;
DELETE FROM eshop_user.member;
alter table eshop_user.member AUTO_INCREMENT=1;
DELETE FROM eshop_user.member_profile;
alter table eshop_user.member_profile AUTO_INCREMENT=1;
DELETE FROM eshop_user.merchant;
alter table eshop_user.merchant AUTO_INCREMENT=1;
DELETE FROM eshop_user.merchant_profile;
alter table eshop_user.merchant_profile AUTO_INCREMENT=1;
DELETE FROM eshop_user.merchant_store;
alter table eshop_user.merchant_store AUTO_INCREMENT=1;
DELETE FROM eshop_user.merchant_store_audit;
alter table eshop_user.merchant_store_audit AUTO_INCREMENT=1;
DELETE FROM eshop_user.merchant_store_image;
alter table eshop_user.merchant_store_image AUTO_INCREMENT=1;
DELETE FROM eshop_user.merchant_store_profile;
alter table eshop_user.merchant_store_profile AUTO_INCREMENT=1;
DELETE FROM eshop_user.property;
alter table eshop_user.property AUTO_INCREMENT=1;
DELETE FROM eshop_user.transaction_record;
alter table eshop_user.transaction_record AUTO_INCREMENT=1;
DELETE FROM eshop_user.user_login_log;
alter table eshop_user.user_login_log AUTO_INCREMENT=1;
*/