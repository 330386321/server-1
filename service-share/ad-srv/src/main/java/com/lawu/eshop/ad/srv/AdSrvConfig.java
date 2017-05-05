package com.lawu.eshop.ad.srv;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Leach
 * @date 2017/3/13
 */
@Component
public class AdSrvConfig {

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

    @Value(value = "${solr_url}")
    private String solrUrl;

    @Value(value = "${solr_ad_core}")
    private String solrAdCore;

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

    public String getSolrUrl() {
        return solrUrl;
    }

    public String getSolrAdCore() {
        return solrAdCore;
    }
}
