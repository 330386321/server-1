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
    
    @Value(value = "${image.red_packet_common}")
    private String redPacketCommonMediaUrl;

    @Value(value = "${image.red_packet_luck}")
    private String redPacketLuckMediaUrl;

    @Value(value = "${ad_praise_allot_prob}")
    private Integer adPraiseAllotProb;
    
    @Value(value = "${is_cut_praise_point}")
    private Boolean isCutPraisePoint;

    public String getSolrUrl() {
        return solrUrl;
    }

    public String getSolrAdCore() {
        return solrAdCore;
    }

    public Boolean getIsCloudSolr() {
        return isCloudSolr;
    }

	public String getRedPacketCommonMediaUrl() {
		return redPacketCommonMediaUrl;
	}

	public String getRedPacketLuckMediaUrl() {
		return redPacketLuckMediaUrl;
	}

    public Integer getAdPraiseAllotProb() {
        return adPraiseAllotProb;
    }

	public Boolean getIsCutPraisePoint() {
		return isCutPraisePoint;
	}

    
}
