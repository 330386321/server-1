package com.lawu.eshop.mall.srv;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Leach
 * @date 2017/3/13
 */
@Component
public class MallSrvConfig {

    @Value(value = "${sms_url}")
    private String smsUrl;

    @Value(value = "${sms_encoding}")
    private String smsEncoding;

    @Value(value = "${sms_sp_code}")
    private String smsSpCode;

    @Value(value = "${sms_login_name}")
    private String smsLoginName;

    @Value(value = "${sms_password}")
    private String smsPassword;

    @Value(value = "${sms_serial_number}")
    private String smsSerialNumber;

    @Value(value = "${sms_f}")
    private String smsF;

    @Value(value = "${sms_template}")
    private String smsTemplate;

    @Value(value = "${sms_is_send}")
    private Boolean isSend;

    @Value(value = "${sms.send.hour.count}")
    private Integer smsSendHourCount;

    @Value(value = "${sms.send.ip.count}")
    private Integer smsSendIpCount;

    @Value(value = "${sms.send.mobile.count}")
    private Integer smsSendMobileCount;

    @Value(value = "${solr_url}")
    private String solrUrl;

    @Value(value = "${solr_merchant_core}")
    private String solrMerchantCore;

    @Value(value = "${is_cloud_solr}")
    private Boolean isCloudSolr;

    public String getSmsUrl() {
        return smsUrl;
    }

    public String getSmsEncoding() {
        return smsEncoding;
    }

    public String getSmsSpCode() {
        return smsSpCode;
    }

    public String getSmsLoginName() {
        return smsLoginName;
    }

    public String getSmsPassword() {
        return smsPassword;
    }

    public String getSmsSerialNumber() {
        return smsSerialNumber;
    }

    public String getSmsF() {
        return smsF;
    }

    public String getSmsTemplate() {
        return smsTemplate;
    }

    public Boolean getIsSend() {
        return isSend;
    }

    public Integer getSmsSendHourCount() {
        return smsSendHourCount;
    }

    public Integer getSmsSendIpCount() {
        return smsSendIpCount;
    }

    public Integer getSmsSendMobileCount() {
        return smsSendMobileCount;
    }

    public String getSolrUrl() {
        return solrUrl;
    }

    public String getSolrMerchantCore() {
        return solrMerchantCore;
    }

    public Boolean getIsCloudSolr() {
        return isCloudSolr;
    }
}
