package com.lawu.eshop.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
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

/**
 * @author meishuquan
 * @date 2017/06/06.
 */
public class SolrCloudUtil {

    private static Logger logger = LoggerFactory.getLogger(SolrCloudUtil.class);

    private SolrCloudUtil(){}

    /**
     * 获取solr客户端
     *
     * @param solrUrl
     * @param solrCore
     * @return
     */
    private static CloudSolrClient getSolrClient(String solrUrl, String solrCore) {
        CloudSolrClient solrClient = new CloudSolrClient(solrUrl);
        solrClient.setDefaultCollection(solrCore);
        return solrClient;
    }

    /**
     * 关闭solr客户端
     *
     * @param solrClient
     */
    private static void closeSolrClient(CloudSolrClient solrClient) {
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
        CloudSolrClient client = getSolrClient(solrUrl, solrCore);
        try {
            UpdateResponse rspAdd = client.add(document);
            UpdateResponse rspCommit = client.commit();
            closeSolrClient(client);
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
     * @return
     */
    public static boolean addSolrDocsList(Collection<SolrInputDocument> documents, String solrUrl, String solrCore) {
        CloudSolrClient client = getSolrClient(solrUrl, solrCore);
        try {
            UpdateResponse rspAdd = client.add(documents);
            UpdateResponse rspCommit = client.commit();
            closeSolrClient(client);
            if (rspAdd.getStatus() == 0 && rspCommit.getStatus() == 0) {
                return true;
            }
        } catch (Exception e) {
            logger.error("solr新增集合异常：{}", e);
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
        CloudSolrClient client = getSolrClient(solrUrl,solrCore);
        try {
            UpdateResponse rspAdd = client.deleteById(String.valueOf(id));
            UpdateResponse rspCommit = client.commit();
            closeSolrClient(client);
            if (rspAdd.getStatus() == 0 && rspCommit.getStatus() == 0) {
                return true;
            }
        } catch (Exception e) {
            logger.error("solr删除异常：{}", e);
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
        CloudSolrClient client = getSolrClient(solrUrl, solrCore);
        try {
            QueryResponse rsp = client.query(query);
            SolrDocumentList docsList = rsp.getResults();
            closeSolrClient(client);
            return docsList;
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
     * @return
     */
    public static SolrDocument getSolrDocsById(Long id, String solrUrl, String solrCore) {
        CloudSolrClient client = getSolrClient(solrUrl, solrCore);
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
     * @return
     */
    public static TermsResponse getTermsResponseByQuery(SolrQuery query, String solrUrl, String solrCore) {
        CloudSolrClient client = getSolrClient(solrUrl, solrCore);
        try {
            QueryResponse rsp = client.query(query);
            TermsResponse termsResponse = rsp.getTermsResponse();
            closeSolrClient(client);
            return termsResponse;
        } catch (Exception e) {
            logger.error("solr查询词频异常：{}", e);
        }
        return null;
    }

}
