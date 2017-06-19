package com.lawu.eshop.ad.srv;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Leach
 * @date 2017/3/13
 */
@Component
public class AdSrvConfig {

    @Value(value = "${solr_url}")
    private String solrUrl;

    @Value(value = "${solr_ad_core}")
    private String solrAdCore;

    public String getSolrUrl() {
        return solrUrl;
    }

    public String getSolrAdCore() {
        return solrAdCore;
    }
}
