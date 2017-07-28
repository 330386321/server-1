package com.lawu.eshop.mall.srv.converter;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;

/**
 * @author meishuquan
 * @date 2017/7/27.
 */
public class SolrDocumentConverter {

    public static SolrInputDocument converFavoredSolrInputDocument(SolrDocument solrDocument) {
        if (solrDocument == null) {
            return null;
        }

        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", solrDocument.get("id") == null ? 0 : solrDocument.get("id"));
        document.addField("merchantId_l", solrDocument.get("merchantId_l") == null ? 0 : solrDocument.get("merchantId_l"));
        document.addField("name_s", solrDocument.get("name_s") == null ? "" : solrDocument.get("name_s"));
        document.addField("regionPath_s", solrDocument.get("regionPath_s") == null ? "" : solrDocument.get("regionPath_s"));
        document.addField("latLon_p", solrDocument.get("latLon_p") == null ? "0,0" : solrDocument.get("latLon_p"));
        document.addField("industryPath_s", solrDocument.get("industryPath_s") == null ? "" : solrDocument.get("industryPath_s"));
        document.addField("industryName_s", solrDocument.get("industryName_s") == null ? "" : solrDocument.get("industryName_s"));
        document.addField("storePic_s", solrDocument.get("storePic_s") == null ? "" : solrDocument.get("storePic_s"));
        document.addField("favoriteNumber_i", solrDocument.get("favoriteNumber_i") == null ? 0 : solrDocument.get("favoriteNumber_i"));
        document.addField("averageConsumeAmount_d", solrDocument.get("averageConsumeAmount_d") == null ? 0.0 : solrDocument.get("averageConsumeAmount_d"));
        document.addField("averageScore_d", solrDocument.get("averageScore_d") == null ? 0.0 : solrDocument.get("averageScore_d"));
        document.addField("discountPackage_s", solrDocument.get("discountPackage_s") == null ? "" : solrDocument.get("discountPackage_s"));
        return document;
    }

    public static SolrInputDocument converDiscountSolrInputDocument(SolrDocument solrDocument) {
        if (solrDocument == null) {
            return null;
        }

        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", solrDocument.get("id") == null ? 0 : solrDocument.get("id"));
        document.addField("merchantId_l", solrDocument.get("merchantId_l") == null ? 0 : solrDocument.get("merchantId_l"));
        document.addField("name_s", solrDocument.get("name_s") == null ? "" : solrDocument.get("name_s"));
        document.addField("regionPath_s", solrDocument.get("regionPath_s") == null ? "" : solrDocument.get("regionPath_s"));
        document.addField("latLon_p", solrDocument.get("latLon_p") == null ? "0,0" : solrDocument.get("latLon_p"));
        document.addField("industryPath_s", solrDocument.get("industryPath_s") == null ? "" : solrDocument.get("industryPath_s"));
        document.addField("industryName_s", solrDocument.get("industryName_s") == null ? "" : solrDocument.get("industryName_s"));
        document.addField("storePic_s", solrDocument.get("storePic_s") == null ? "" : solrDocument.get("storePic_s"));
        document.addField("favoriteNumber_i", solrDocument.get("favoriteNumber_i") == null ? 0 : solrDocument.get("favoriteNumber_i"));
        document.addField("averageConsumeAmount_d", solrDocument.get("averageConsumeAmount_d") == null ? 0.0 : solrDocument.get("averageConsumeAmount_d"));
        document.addField("averageScore_d", solrDocument.get("averageScore_d") == null ? 0.0 : solrDocument.get("averageScore_d"));
        document.addField("discountOrdinal_d", solrDocument.get("discountOrdinal_d") == null ? 100 : solrDocument.get("discountOrdinal_d"));
        document.addField("favoreInfo_s", solrDocument.get("favoreInfo_s") == null ? "" : solrDocument.get("favoreInfo_s"));
        return document;
    }
}
