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
import java.util.Collection;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/12.
 */
public class SolrUtil {

    private static Logger logger = LoggerFactory.getLogger(SolrUtil.class);

    private static HttpSolrClient productSolrClient;

    private static HttpSolrClient merchantSolrClient;

    private static HttpSolrClient adSolrClient;

    private static final String PRODUCT_CORE = "product";

    private static final String MERCHANT_CORE = "merchant";

    private static final String AD_CORE = "ad";

    private static final int CONNECTION_TIMEOUT = 5000;

    private static final int DEFAULT_MAX_CONNECTIONS_PERHOST = 1000;

    private static final int MAX_TOTAL_CONNECTIONS = 10000;

    private SolrUtil() {
    }


    /**
     * 获取solr客户端
     *
     * @param solrUrl
     * @param solrCore
     * @return
     */
    private static void getSolrClient(String solrUrl, String solrCore) {
        if (productSolrClient == null) {
            productSolrClient = new HttpSolrClient(solrUrl + solrCore);
            productSolrClient.setConnectionTimeout(CONNECTION_TIMEOUT);
            productSolrClient.setDefaultMaxConnectionsPerHost(DEFAULT_MAX_CONNECTIONS_PERHOST);
            productSolrClient.setMaxTotalConnections(MAX_TOTAL_CONNECTIONS);
        }
        if (merchantSolrClient == null) {
            merchantSolrClient = new HttpSolrClient(solrUrl + solrCore);
            merchantSolrClient.setConnectionTimeout(CONNECTION_TIMEOUT);
            merchantSolrClient.setDefaultMaxConnectionsPerHost(DEFAULT_MAX_CONNECTIONS_PERHOST);
            merchantSolrClient.setMaxTotalConnections(MAX_TOTAL_CONNECTIONS);
        }
        if (adSolrClient == null) {
            adSolrClient = new HttpSolrClient(solrUrl + solrCore);
            adSolrClient.setConnectionTimeout(CONNECTION_TIMEOUT);
            adSolrClient.setDefaultMaxConnectionsPerHost(DEFAULT_MAX_CONNECTIONS_PERHOST);
            adSolrClient.setMaxTotalConnections(MAX_TOTAL_CONNECTIONS);
        }
    }

    /**
     * 获取solr客户端
     *
     * @param solrUrl
     * @param solrCore
     * @return
     */
    /*private static void getSolrClient(String solrUrl, String solrCore) {
        if (productSolrClient == null) {
            productSolrClient = new CloudSolrClient(solrUrl);
            productSolrClient.setDefaultCollection(solrCore);
        }
        if (merchantSolrClient == null) {
            merchantSolrClient = new CloudSolrClient(solrUrl);
            merchantSolrClient.setDefaultCollection(solrCore);
        }
        if (adSolrClient == null) {
            adSolrClient = new CloudSolrClient(solrUrl);
            adSolrClient.setDefaultCollection(solrCore);
        }
    }*/

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
                logger.error("solr关闭异常：{}", e);
            }
        }
    }

    /**
     * 新增
     *
     * @param document
     * @param solrUrl
     * @param solrCore
     * @return
     */
    public static boolean addSolrDocs(SolrInputDocument document, String solrUrl, String solrCore) {
        getSolrClient(solrUrl, solrCore);
        if (PRODUCT_CORE.equals(solrCore)) {
            return addSolrDocs(document, productSolrClient);
        }
        if (MERCHANT_CORE.equals(solrCore)) {
            return addSolrDocs(document, merchantSolrClient);
        }
        if (AD_CORE.equals(solrCore)) {
            return addSolrDocs(document, adSolrClient);
        }
        return false;
    }

    /**
     * 新增
     *
     * @param documents
     * @param solrUrl
     * @param solrCore
     * @return
     */
    public static boolean addSolrDocsList(Collection<SolrInputDocument> documents, String solrUrl, String solrCore) {
        getSolrClient(solrUrl, solrCore);
        if (PRODUCT_CORE.equals(solrCore)) {
            return addSolrDocsList(documents, productSolrClient);
        }
        if (MERCHANT_CORE.equals(solrCore)) {
            return addSolrDocsList(documents, merchantSolrClient);
        }
        if (AD_CORE.equals(solrCore)) {
            return addSolrDocsList(documents, adSolrClient);
        }
        return false;
    }

    /**
     * 根据ID删除
     *
     * @param id
     * @param solrUrl
     * @param solrCore
     * @return
     */
    public static boolean delSolrDocsById(Long id, String solrUrl, String solrCore) {
        getSolrClient(solrUrl, solrCore);
        if (PRODUCT_CORE.equals(solrCore)) {
            return delSolrDocsById(id, productSolrClient);
        }
        if (MERCHANT_CORE.equals(solrCore)) {
            return delSolrDocsById(id, merchantSolrClient);
        }
        if (AD_CORE.equals(solrCore)) {
            return delSolrDocsById(id, adSolrClient);
        }
        return false;
    }

    /**
     * 根据ids删除
     *
     * @param ids
     * @param solrUrl
     * @param solrCore
     * @return
     */
    public static boolean delSolrDocsByIds(List<String> ids, String solrUrl, String solrCore) {
        getSolrClient(solrUrl, solrCore);
        if (PRODUCT_CORE.equals(solrCore)) {
            return delSolrDocsByIds(ids, productSolrClient);
        }
        if (MERCHANT_CORE.equals(solrCore)) {
            return delSolrDocsByIds(ids, merchantSolrClient);
        }
        if (AD_CORE.equals(solrCore)) {
            return delSolrDocsByIds(ids, adSolrClient);
        }
        return false;
    }

    /**
     * 根据条件查询
     *
     * @param query
     * @param solrUrl
     * @param solrCore
     * @return
     * @throws Exception
     */
    public static SolrDocumentList getSolrDocsByQuery(SolrQuery query, String solrUrl, String solrCore) {
        getSolrClient(solrUrl, solrCore);
        if (PRODUCT_CORE.equals(solrCore)) {
            return getSolrDocsByQuery(query, productSolrClient);
        }
        if (MERCHANT_CORE.equals(solrCore)) {
            return getSolrDocsByQuery(query, merchantSolrClient);
        }
        if (AD_CORE.equals(solrCore)) {
            return getSolrDocsByQuery(query, adSolrClient);
        }
        return null;
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @param solrUrl
     * @param solrCore
     * @return
     */
    public static SolrDocument getSolrDocsById(Long id, String solrUrl, String solrCore) {
        getSolrClient(solrUrl, solrCore);
        if (PRODUCT_CORE.equals(solrCore)) {
            return getSolrDocsById(id, productSolrClient);
        }
        if (MERCHANT_CORE.equals(solrCore)) {
            return getSolrDocsById(id, merchantSolrClient);
        }
        if (AD_CORE.equals(solrCore)) {
            return getSolrDocsById(id, adSolrClient);
        }
        return null;
    }

    /**
     * 词频统计
     *
     * @param query
     * @param solrUrl
     * @param solrCore
     * @return
     */
    public static TermsResponse getTermsResponseByQuery(SolrQuery query, String solrUrl, String solrCore) {
        getSolrClient(solrUrl, solrCore);
        if (PRODUCT_CORE.equals(solrCore)) {
            return getTermsResponseByQuery(query, productSolrClient);
        }
        if (MERCHANT_CORE.equals(solrCore)) {
            return getTermsResponseByQuery(query, merchantSolrClient);
        }
        if (AD_CORE.equals(solrCore)) {
            return getTermsResponseByQuery(query, adSolrClient);
        }
        return null;
    }

    /**
     * 新增
     *
     * @param document
     * @param solrClient
     * @return
     */
    private static boolean addSolrDocs(SolrInputDocument document, SolrClient solrClient) {
        try {
            UpdateResponse rspAdd = solrClient.add(document);
            UpdateResponse rspCommit = solrClient.commit();
            if (rspAdd.getStatus() == 0 && rspCommit.getStatus() == 0) {
                return true;
            }
        } catch (Exception e) {
            logger.error("solr新增异常：{}", e);
        }
        return false;
    }

    /**
     * 新增
     *
     * @param documents
     * @param solrClient
     * @return
     */
    private static boolean addSolrDocsList(Collection<SolrInputDocument> documents, SolrClient solrClient) {
        try {
            UpdateResponse rspAdd = solrClient.add(documents);
            UpdateResponse rspCommit = solrClient.commit();
            if (rspAdd.getStatus() == 0 && rspCommit.getStatus() == 0) {
                return true;
            }
        } catch (Exception e) {
            logger.error("solr批量新增异常：{}", e);
        }
        return false;
    }

    /**
     * 根据ID删除
     *
     * @param id
     * @param solrClient
     * @return
     */
    private static boolean delSolrDocsById(Long id, SolrClient solrClient) {
        try {
            UpdateResponse rspAdd = solrClient.deleteById(String.valueOf(id));
            UpdateResponse rspCommit = solrClient.commit();
            if (rspAdd.getStatus() == 0 && rspCommit.getStatus() == 0) {
                return true;
            }
        } catch (Exception e) {
            logger.error("solr删除异常：{}", e);
        }
        return false;
    }

    /**
     * 根据ids删除
     *
     * @param ids
     * @param solrClient
     * @return
     */
    private static boolean delSolrDocsByIds(List<String> ids, SolrClient solrClient) {
        try {
            UpdateResponse rspAdd = solrClient.deleteById(String.valueOf(ids));
            UpdateResponse rspCommit = solrClient.commit();
            if (rspAdd.getStatus() == 0 && rspCommit.getStatus() == 0) {
                return true;
            }
        } catch (Exception e) {
            logger.error("solr批量删除异常：{}", e);
        }
        return false;
    }

    /**
     * 根据条件查询
     *
     * @param query
     * @param solrClient
     * @return
     * @throws Exception
     */
    private static SolrDocumentList getSolrDocsByQuery(SolrQuery query, SolrClient solrClient) {
        try {
            QueryResponse rsp = solrClient.query(query);
            return rsp.getResults();
        } catch (Exception e) {
            logger.error("solr查询异常：{}", e);
        }
        return null;
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @param solrClient
     * @return
     */
    private static SolrDocument getSolrDocsById(Long id, SolrClient solrClient) {
        SolrDocument solrDocument = null;
        SolrQuery query = new SolrQuery();
        query.setQuery("id:" + id);
        try {
            QueryResponse rsp = solrClient.query(query);
            SolrDocumentList docsList = rsp.getResults();
            if (docsList.getNumFound() > 0) {
                solrDocument = docsList.get(0);
            }
        } catch (Exception e) {
            logger.error("solr根据ID查询异常：{}", e);
        }
        return solrDocument;
    }

    /**
     * 词频统计
     *
     * @param query
     * @param solrClient
     * @return
     */
    private static TermsResponse getTermsResponseByQuery(SolrQuery query, SolrClient solrClient) {
        try {
            QueryResponse rsp = solrClient.query(query);
            return rsp.getTermsResponse();
        } catch (Exception e) {
            logger.error("solr查询词频异常：{}", e);
        }
        return null;
    }

}
