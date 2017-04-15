package com.lawu.eshop.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.TermsResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author meishuquan
 * @date 2017/4/12.
 */
public class SolrUtil {

    private static Logger logger = LoggerFactory.getLogger(SolrUtil.class);

    //solr服务器地址
    private static final String SOLR_URL = "http://192.168.1.22:8983/solr/";

    //商家索引库
    public static final String SOLR_MERCHANT_CORE = "merchant";

    //商品索引库
    public static final String SOLR_PRODUCT_CORE = "product";

    //广告索引库
    public static final String SOLR_AD_CORE = "ad";


    /**
     * 获取solr客户端
     *
     * @param solrCore
     * @return
     */
    private static HttpSolrClient getSolrClient(String solrCore) {
        HttpSolrClient solrClient = new HttpSolrClient(SOLR_URL + solrCore);
        return solrClient;
    }

    /**
     * 关闭solr客户端
     *
     * @param solrClient
     */
    private static void closeSolrClient(SolrClient solrClient) {
        if (solrClient != null) {
            try {
                solrClient.close();
            } catch (IOException e) {
                logger.error("solr关闭异常：{}", e.getMessage());
            }
        }
    }

    /**
     * 新增
     *
     * @param document
     * @param solrCore
     * @return
     */
    public static boolean addSolrDocs(SolrInputDocument document, String solrCore) {
        HttpSolrClient client = getSolrClient(solrCore);
        try {
            UpdateResponse rspAdd = client.add(document);
            UpdateResponse rspCommit = client.commit();
            closeSolrClient(client);
            if (rspAdd.getStatus() == 0 && rspCommit.getStatus() == 0) {
                return true;
            }
        } catch (Exception e) {
            logger.error("solr新增异常：{}", e.getMessage());
        }
        return false;
    }

    /**
     * 根据ID删除
     *
     * @param id
     * @param solrCore
     * @return
     */
    public static boolean delSolrDocsById(Long id, String solrCore) {
        HttpSolrClient client = getSolrClient(solrCore);
        try {
            UpdateResponse rspAdd = client.deleteById(String.valueOf(id));
            UpdateResponse rspCommit = client.commit();
            closeSolrClient(client);
            if (rspAdd.getStatus() == 0 && rspCommit.getStatus() == 0) {
                return true;
            }
        } catch (Exception e) {
            logger.error("solr删除异常：{}", e.getMessage());
        }
        return false;
    }

    /**
     * 根据条件查询
     *
     * @param query
     * @param solrCore
     * @return
     * @throws Exception
     */
    public static SolrDocumentList getSolrDocsByQuery(SolrQuery query, String solrCore) {
        HttpSolrClient client = getSolrClient(solrCore);
        try {
            QueryResponse rsp = client.query(query);
            SolrDocumentList docsList = rsp.getResults();
            closeSolrClient(client);
            return docsList;
        } catch (Exception e) {
            logger.error("solr查询异常：{}", e.getMessage());
        }
        return null;
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @param solrCore
     * @return
     */
    public static SolrDocument getSolrDocsById(Long id, String solrCore) {
        HttpSolrClient client = getSolrClient(solrCore);
        SolrDocument solrDocument = null;
        SolrQuery query = new SolrQuery();
        query.setQuery("id:" + id);
        try {
            QueryResponse rsp = client.query(query);
            SolrDocumentList docsList = rsp.getResults();
            closeSolrClient(client);
            if (docsList.getNumFound() > 0) {
                solrDocument = docsList.get(0);
            }
        } catch (Exception e) {
            logger.error("solr查询异常：{}", e.getMessage());
        }
        return solrDocument;
    }

    /**
     * 词频统计
     *
     * @param query
     * @param solrCore
     * @return
     */
    public static TermsResponse getTermsResponseByQuery(SolrQuery query, String solrCore) {
        HttpSolrClient client = getSolrClient(solrCore);
        try {
            QueryResponse rsp = client.query(query);
            TermsResponse termsResponse = rsp.getTermsResponse();
            closeSolrClient(client);
            return termsResponse;
        } catch (Exception e) {
            logger.error("solr查询异常：{}", e.getMessage());
        }
        return null;
    }

}
