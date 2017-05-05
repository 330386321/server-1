package com.lawu.eshop.user.srv;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Leach
 * @date 2017/3/13
 */
@Component
public class UserSrvConfig {

    @Value(value = "${db.type}")
    private String type;

    @Value(value = "${db.driver}")
    private String driver;

    @Value(value = "${db.url}")
    private String url;

    @Value(value = "${db.username}")
    private String username;

    @Value(value = "${db.password}")
    private String password;

    @Value(value="${image.user_default_headimg}")
    private String user_headimg;

    @Value(value="${image.merchant_default_headimg}")
    private String merchant_headimg;

    @Value(value = "${solr_url}")
    private String solrUrl;

    @Value(value = "${solr_merchant_core}")
    private String solrMerchantCore;

    @Value(value = "${default_headimg}")
    private String defaultHeadimg;

    public String getType() {
        return type;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUser_headimg() {
        return user_headimg;
    }

    public String getMerchant_headimg() {
        return merchant_headimg;
    }

    public String getSolrUrl() {
        return solrUrl;
    }

    public String getSolrMerchantCore() {
        return solrMerchantCore;
    }

    public String getDefaultHeadimg() {
        return defaultHeadimg;
    }
}
