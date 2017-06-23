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

    @Value(value = "${is_cloud_solr}")
    private Boolean isCloudSolr;
    
    @Value(value = "${image.ad_default_mediaUrl}")
    private String adDefaultMediaUrl;

    public String getSolrUrl() {
        return solrUrl;
    }

    public String getSolrAdCore() {
        return solrAdCore;
    }

    public Boolean getIsCloudSolr() {
        return isCloudSolr;
    }

	public String getAdDefaultMediaUrl() {
		return adDefaultMediaUrl;
	}

	public void setAdDefaultMediaUrl(String adDefaultMediaUrl) {
		this.adDefaultMediaUrl = adDefaultMediaUrl;
	}
    
    
}
