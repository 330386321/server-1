package com.lawu.eshop.solr.impl;

import com.lawu.eshop.solr.SolrUtil;
import com.lawu.eshop.solr.service.SolrService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.TermsResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/7/24.
 */
public class SolrServiceImpl implements SolrService {

    private static Logger logger = LoggerFactory.getLogger(SolrServiceImpl.class);

    @Override
    public boolean addSolrDocs(SolrInputDocument document, String solrUrl, String solrCore, Boolean isCloudSolr) {
        if (document == null) {
            return false;
        }
        SolrClient solrClient = SolrUtil.getSolrClient(solrUrl, solrCore, isCloudSolr);
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

    @Override
    public boolean addSolrDocsList(Collection<SolrInputDocument> documents, String solrUrl, String solrCore, Boolean isCloudSolr) {
        if (documents == null || documents.isEmpty()) {
            return false;
        }
        SolrClient solrClient = SolrUtil.getSolrClient(solrUrl, solrCore, isCloudSolr);
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

    @Override
    public boolean delSolrDocsById(Long id, String solrUrl, String solrCore, Boolean isCloudSolr) {
        if (id == null) {
            return false;
        }
        SolrClient solrClient = SolrUtil.getSolrClient(solrUrl, solrCore, isCloudSolr);
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

    @Override
    public boolean delSolrDocsByIds(List<String> ids, String solrUrl, String solrCore, Boolean isCloudSolr) {
        if (ids == null || ids.isEmpty()) {
            return false;
        }
        SolrClient solrClient = SolrUtil.getSolrClient(solrUrl, solrCore, isCloudSolr);
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

    @Override
    public SolrDocumentList getSolrDocsByQuery(SolrQuery query, String solrUrl, String solrCore, Boolean isCloudSolr) {
        SolrClient solrClient = SolrUtil.getSolrClient(solrUrl, solrCore, isCloudSolr);
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

    @Override
    public SolrDocument getSolrDocsById(Long id, String solrUrl, String solrCore, Boolean isCloudSolr) {
        if (id == null) {
            return null;
        }
        SolrClient solrClient = SolrUtil.getSolrClient(solrUrl, solrCore, isCloudSolr);
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

    @Override
    public TermsResponse getTermsResponseByQuery(SolrQuery query, String solrUrl, String solrCore, Boolean isCloudSolr) {
        SolrClient solrClient = SolrUtil.getSolrClient(solrUrl, solrCore, isCloudSolr);
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
