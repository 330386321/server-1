package com.lawu.eshop.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
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

    private volatile static HttpSolrClient productSolrClient;

    private volatile static HttpSolrClient merchantSolrClient;

    private volatile static HttpSolrClient adSolrClient;

    private volatile static CloudSolrClient productCloudSolrClient;

    private volatile static CloudSolrClient merchantCloudSolrClient;

    private volatile static CloudSolrClient adCloudSolrClient;

    private static final String PRODUCT_CORE = "product";

    private static final String MERCHANT_CORE = "merchant";

    private static final String AD_CORE = "ad";

    private static final int DEFAULT_MAX_CONNECTIONS_PERHOST = 100;

    private static final int MAX_TOTAL_CONNECTIONS = 10000;

    private SolrUtil() {
    }

    private static SolrClient getSolrClient(String solrUrl, String solrCore, Boolean isCloudSolr) {
        if (PRODUCT_CORE.equals(solrCore)) {
            if (isCloudSolr) {
                return getProductCloudSolrClient(solrUrl, solrCore);
            } else {
                return getProductSolrClient(solrUrl, solrCore);
            }
        }
        if (MERCHANT_CORE.equals(solrCore)) {
            if (isCloudSolr) {
                return getMerchantCloudSolrClient(solrUrl, solrCore);
            } else {
                return getMerchantSolrClient(solrUrl, solrCore);
            }
        }
        if (AD_CORE.equals(solrCore)) {
            if (isCloudSolr) {
                return getAdCloudSolrClient(solrUrl, solrCore);
            } else {
                return getAdSolrClient(solrUrl, solrCore);
            }
        }
        return null;
    }

    /**
     * 获取solr客户端
     *
     * @param solrUrl
     * @param solrCore
     * @return
     */
    private static SolrClient getProductSolrClient(String solrUrl, String solrCore) {
        if (productSolrClient == null) {
            synchronized (SolrUtil.class) {
                if (productSolrClient == null) {
                    productSolrClient = new HttpSolrClient(solrUrl + solrCore);
                    productSolrClient.setDefaultMaxConnectionsPerHost(DEFAULT_MAX_CONNECTIONS_PERHOST);
                    productSolrClient.setMaxTotalConnections(MAX_TOTAL_CONNECTIONS);
                }
            }
        }
        return productSolrClient;
    }

    private static SolrClient getMerchantSolrClient(String solrUrl, String solrCore) {
        if (merchantSolrClient == null) {
            synchronized (SolrUtil.class) {
                if (merchantSolrClient == null) {
                    merchantSolrClient = new HttpSolrClient(solrUrl + solrCore);
                    merchantSolrClient.setDefaultMaxConnectionsPerHost(DEFAULT_MAX_CONNECTIONS_PERHOST);
                    merchantSolrClient.setMaxTotalConnections(MAX_TOTAL_CONNECTIONS);
                }
            }
        }
        return merchantSolrClient;
    }

    private static SolrClient getAdSolrClient(String solrUrl, String solrCore) {
        if (adSolrClient == null) {
            synchronized (SolrUtil.class) {
                if (adSolrClient == null) {
                    adSolrClient = new HttpSolrClient(solrUrl + solrCore);
                    adSolrClient.setDefaultMaxConnectionsPerHost(DEFAULT_MAX_CONNECTIONS_PERHOST);
                    adSolrClient.setMaxTotalConnections(MAX_TOTAL_CONNECTIONS);
                }
            }
        }
        return adSolrClient;
    }

    /**
     * 获取cloudSolr客户端
     *
     * @param solrUrl
     * @param solrCore
     * @return
     */
    private static SolrClient getProductCloudSolrClient(String solrUrl, String solrCore) {
        if (productCloudSolrClient == null) {
            synchronized (SolrUtil.class) {
                if (productCloudSolrClient == null) {
                    productCloudSolrClient = new CloudSolrClient(solrUrl);
                    productCloudSolrClient.setDefaultCollection(solrCore);
                }
            }
        }
        return productCloudSolrClient;
    }

    private static SolrClient getMerchantCloudSolrClient(String solrUrl, String solrCore) {
        if (merchantCloudSolrClient == null) {
            synchronized (SolrUtil.class) {
                if (merchantCloudSolrClient == null) {
                    merchantCloudSolrClient = new CloudSolrClient(solrUrl);
                    merchantCloudSolrClient.setDefaultCollection(solrCore);
                }
            }
        }
        return merchantCloudSolrClient;
    }

    private static SolrClient getAdCloudSolrClient(String solrUrl, String solrCore) {
        if (adCloudSolrClient == null) {
            synchronized (SolrUtil.class) {
                if (adCloudSolrClient == null) {
                    adCloudSolrClient = new CloudSolrClient(solrUrl);
                    adCloudSolrClient.setDefaultCollection(solrCore);
                }
            }
        }
        return adCloudSolrClient;
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
     * @param isCloudSolr
     * @return
     */
    public static boolean addSolrDocs(SolrInputDocument document, String solrUrl, String solrCore, Boolean isCloudSolr) {
        SolrClient solrClient = getSolrClient(solrUrl, solrCore, isCloudSolr);
        if (solrClient == null) {
            return false;
        }
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
     * @param solrUrl
     * @param solrCore
     * @param isCloudSolr
     * @return
     */
    public static boolean addSolrDocsList(Collection<SolrInputDocument> documents, String solrUrl, String solrCore, Boolean isCloudSolr) {
        if(documents.isEmpty()){
        	 return false;
        }
    	SolrClient solrClient = getSolrClient(solrUrl, solrCore, isCloudSolr);
        if (solrClient == null) {
            return false;
        }
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
     * @param solrUrl
     * @param solrCore
     * @param isCloudSolr
     * @return
     */
    public static boolean delSolrDocsById(Long id, String solrUrl, String solrCore, Boolean isCloudSolr) {
        SolrClient solrClient = getSolrClient(solrUrl, solrCore, isCloudSolr);
        if (solrClient == null) {
            return false;
        }
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
     * @param solrUrl
     * @param solrCore
     * @param isCloudSolr
     * @return
     */
    public static boolean delSolrDocsByIds(List<String> ids, String solrUrl, String solrCore, Boolean isCloudSolr) {
        SolrClient solrClient = getSolrClient(solrUrl, solrCore, isCloudSolr);
        if (solrClient == null) {
            return false;
        }
        try {
            UpdateResponse rspAdd = solrClient.deleteById(ids);
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
     * @param solrUrl
     * @param solrCore
     * @param isCloudSolr
     * @throws Exception
     */
    public static SolrDocumentList getSolrDocsByQuery(SolrQuery query, String solrUrl, String solrCore, Boolean isCloudSolr) {
        SolrClient solrClient = getSolrClient(solrUrl, solrCore, isCloudSolr);
        if (solrClient == null) {
            return null;
        }
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
     * @param solrUrl
     * @param solrCore
     * @param isCloudSolr
     * @return
     */
    public static SolrDocument getSolrDocsById(Long id, String solrUrl, String solrCore, Boolean isCloudSolr) {
        SolrClient solrClient = getSolrClient(solrUrl, solrCore, isCloudSolr);
        if (solrClient == null) {
            return null;
        }
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
     * @param solrUrl
     * @param solrCore
     * @param isCloudSolr
     * @return
     */
    public static TermsResponse getTermsResponseByQuery(SolrQuery query, String solrUrl, String solrCore, Boolean isCloudSolr) {
        SolrClient solrClient = getSolrClient(solrUrl, solrCore, isCloudSolr);
        if (solrClient == null) {
            return null;
        }
        try {
            QueryResponse rsp = solrClient.query(query);
            return rsp.getTermsResponse();
        } catch (Exception e) {
            logger.error("solr查询词频异常：{}", e);
        }
        return null;
    }

}
