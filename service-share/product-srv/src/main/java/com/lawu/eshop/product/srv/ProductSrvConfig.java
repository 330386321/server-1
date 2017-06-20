package com.lawu.eshop.product.srv;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Leach
 * @date 2017/3/13
 */
@Component
public class ProductSrvConfig {

    @Value(value = "${solr_url}")
    private String solrUrl;

    @Value(value = "${solr_product_core}")
    private String solrProductCore;

    @Value(value = "${is_cloud_solr}")
    private Boolean isCloudSolr;

    public String getSolrUrl() {
        return solrUrl;
    }

    public String getSolrProductCore() {
        return solrProductCore;
    }

    public Boolean getIsCloudSolr() {
        return isCloudSolr;
    }
}
