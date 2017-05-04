package com.lawu.eshop.external.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author meishuquan
 * @date 2017/5/4
 */
@Component
public class ExternalApiConfig {

    //支付宝支付参数
    @Value(value = "${alipay_partner}")
    private String alipay_partner;

    @Value(value = "${alipay_seller_id}")
    private String alipay_seller_id;

    @Value(value = "${alipay_app_id_member}")
    private String alipay_app_id_member;

    @Value(value = "${alipay_app_id_business}")
    private String alipay_app_id_business;

    @Value(value = "${alipay_private_key}")
    private String alipay_private_key;

    @Value(value = "${alipay_public_key}")
    private String alipay_public_key;

    @Value(value = "${alipay_edian_member_public_key}")
    private String alipay_edian_member_public_key;

    @Value(value = "${alipay_edian_business_public_key}")
    private String alipay_edian_business_public_key;

    @Value(value = "${alipay_sign_type}")
    private String alipay_sign_type;

    @Value(value = "${alipay_input_charset}")
    private String alipay_input_charset;

    @Value(value = "${alipay_payment_type}")
    private String alipay_payment_type;

    @Value(value = "${alipay_service}")
    private String alipay_service;

    @Value(value = "${alipay_https_verify_url}")
    private String alipay_https_verify_url;

    @Value(value = "${alipay_refund_url}")
    private String alipay_refund_url;

    @Value(value = "${alipay_notify_url}")
    private String alipay_notify_url;

    @Value(value = "${alipay_notify_url_pc}")
    private String alipay_notify_url_pc;

    @Value(value = "${alipay_return_url_pc}")
    private String alipay_return_url_pc;


    //微信支付参数
    @Value(value = "${wxpay_key}")
    private String wxpay_key;

    @Value(value = "${wxpay_mch_id}")
    private String wxpay_mch_id;

    @Value(value = "${wxpay_app_id}")
    private String wxpay_app_id;

    @Value(value = "${wxpay_app_secret}")
    private String wxpay_app_secret;

    @Value(value = "${wxpay_mch_id_member}")
    private String wxpay_mch_id_member;

    @Value(value = "${wxpay_app_id_member}")
    private String wxpay_app_id_member;

    @Value(value = "${wxpay_mch_id_business}")
    private String wxpay_mch_id_business;

    @Value(value = "${wxpay_app_id_business}")
    private String wxpay_app_id_business;

    @Value(value = "${wxpay_cert_password_member}")
    private String wxpay_cert_password_member;

    @Value(value = "${wxpay_key_app}")
    private String wxpay_key_app;

    @Value(value = "${wxpay_transfers_api}")
    private String wxpay_transfers_api;

    @Value(value = "${wxpay_native_pay_api}")
    private String wxpay_native_pay_api;

    @Value(value = "${wxpay_refund_api}")
    private String wxpay_refund_api;

    @Value(value = "${wxpay_https_request_class_name}")
    private String wxpay_https_request_class_name;

    @Value(value = "${wxpay_cert_local_path_member}")
    private String wxpay_cert_local_path_member;

    @Value(value = "${wxpay_notify_url}")
    private String wxpay_notify_url;

    @Value(value = "${wxpay_notify_url_pc}")
    private String wxpay_notify_url_pc;

    @Value(value = "${wxpay_ip}")
    private String wxpay_ip;

    public String getAlipay_partner() {
        return alipay_partner;
    }

    public String getAlipay_seller_id() {
        return alipay_seller_id;
    }

    public String getAlipay_app_id_member() {
        return alipay_app_id_member;
    }

    public String getAlipay_app_id_business() {
        return alipay_app_id_business;
    }

    public String getAlipay_private_key() {
        return alipay_private_key;
    }

    public String getAlipay_public_key() {
        return alipay_public_key;
    }

    public String getAlipay_edian_member_public_key() {
        return alipay_edian_member_public_key;
    }

    public String getAlipay_edian_business_public_key() {
        return alipay_edian_business_public_key;
    }

    public String getAlipay_sign_type() {
        return alipay_sign_type;
    }

    public String getAlipay_input_charset() {
        return alipay_input_charset;
    }

    public String getAlipay_payment_type() {
        return alipay_payment_type;
    }

    public String getAlipay_service() {
        return alipay_service;
    }

    public String getAlipay_notify_url() {
        return alipay_notify_url;
    }

    public String getAlipay_notify_url_pc() {
        return alipay_notify_url_pc;
    }

    public String getAlipay_return_url_pc() {
        return alipay_return_url_pc;
    }

    public String getWxpay_key() {
        return wxpay_key;
    }

    public String getWxpay_mch_id() {
        return wxpay_mch_id;
    }

    public String getWxpay_app_id() {
        return wxpay_app_id;
    }

    public String getWxpay_app_secret() {
        return wxpay_app_secret;
    }

    public String getWxpay_mch_id_member() {
        return wxpay_mch_id_member;
    }

    public String getWxpay_app_id_member() {
        return wxpay_app_id_member;
    }

    public String getWxpay_mch_id_business() {
        return wxpay_mch_id_business;
    }

    public String getWxpay_app_id_business() {
        return wxpay_app_id_business;
    }

    public String getWxpay_cert_password_member() {
        return wxpay_cert_password_member;
    }

    public String getWxpay_key_app() {
        return wxpay_key_app;
    }

    public String getWxpay_transfers_api() {
        return wxpay_transfers_api;
    }

    public String getWxpay_native_pay_api() {
        return wxpay_native_pay_api;
    }

    public String getWxpay_refund_api() {
        return wxpay_refund_api;
    }

    public String getWxpay_https_request_class_name() {
        return wxpay_https_request_class_name;
    }

    public String getWxpay_cert_local_path_member() {
        return wxpay_cert_local_path_member;
    }

    public String getWxpay_notify_url() {
        return wxpay_notify_url;
    }

    public String getWxpay_notify_url_pc() {
        return wxpay_notify_url_pc;
    }

    public String getAlipay_https_verify_url() {
        return alipay_https_verify_url;
    }

    public String getAlipay_refund_url() {
        return alipay_refund_url;
    }

    public String getWxpay_ip() {
        return wxpay_ip;
    }
}
