/*
 * 20170418
 * 自动取消订单时间
 * 自动提醒卖家发货时间
 * 自动收货时间
 * 删除订单时间
 * 买家申请退款，提醒处理商家时间
 * 买家申请退款，商家未处理，平台自动退款时间
 * 买家申请退货退款，提醒处理商家时间
 * 买家申请退货退款，商家未处理，平台自动退款时间
 * 商家拒绝退款，提醒买家处理时间
 * 商家拒绝退款，买家未处理，平台自动撤销退款时间
 * */
INSERT INTO property (id, name, value, remark, gmt_create, gmt_modified) VALUES (3, "automatic_cancel_order", "2", "自动取消未付款订单的时间", NOW(), NOW())
INSERT INTO property (id, name, value, remark, gmt_create, gmt_modified) VALUES (4, "automatic_remind_shipments", "5", "自动提醒卖家发货时间", NOW(), NOW())
INSERT INTO property (id, name, value, remark, gmt_create, gmt_modified) VALUES (5, "automatic_receipt", "14", "自动收货时间", NOW(), NOW())
INSERT INTO property (id, name, value, remark, gmt_create, gmt_modified) VALUES (6, "delete_order", "7", "删除订单时间", NOW(), NOW())
INSERT INTO property (id, name, value, remark, gmt_create, gmt_modified) VALUES (7, "to_be_confirmed_for_refund_remind_time", "2", "买家申请退款，提醒处理商家时间", NOW(), NOW())
INSERT INTO property (id, name, value, remark, gmt_create, gmt_modified) VALUES (8, "to_be_confirmed_for_refund_refund_time", "3", "买家申请退款，商家未处理，平台自动退款时间", NOW(), NOW())
INSERT INTO property (id, name, value, remark, gmt_create, gmt_modified) VALUES (9, "to_be_confirmed_for_refund_remind_time", "5", "买家申请退货退款，提醒处理商家时间", NOW(), NOW());
INSERT INTO property (id, name, value, remark, gmt_create, gmt_modified) VALUES (10, "to_be_confirmed_for_refund_refund_time", "7", "买家申请退货退款，商家未处理，平台自动退款时间", NOW(), NOW());
INSERT INTO property (id, name, value, remark, gmt_create, gmt_modified) VALUES (11, "refund_failed_remind_time", "5", "商家拒绝退款，提醒买家处理时间", NOW(), NOW());
INSERT INTO property (id, name, value, remark, gmt_create, gmt_modified) VALUES (12, "refund_failed_revoke_refund_time", "7", "商家拒绝退款，买家未处理，平台自动撤销退款时间", NOW(), NOW());