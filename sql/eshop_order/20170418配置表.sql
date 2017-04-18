/*
 * 20170418
 * 自动取消订单时间
 * 自动提醒卖家发货时间
 * 自动收货时间
 * 删除订单时间
 * */
INSERT INTO property (id, name, value, remark, gmt_create, gmt_modified) VALUES (3, "automatic_cancel_order", "2", "自动取消未付款订单的时间", NOW(), NOW())
INSERT INTO property (id, name, value, remark, gmt_create, gmt_modified) VALUES (4, "automatic_remind_shipments", "5", "自动提醒卖家发货时间", NOW(), NOW())
INSERT INTO property (id, name, value, remark, gmt_create, gmt_modified) VALUES (5, "automatic_receipt", "14", "自动收货时间", NOW(), NOW())
INSERT INTO property (id, name, value, remark, gmt_create, gmt_modified) VALUES (6, "delete_order", "7", "删除订单时间", NOW(), NOW())