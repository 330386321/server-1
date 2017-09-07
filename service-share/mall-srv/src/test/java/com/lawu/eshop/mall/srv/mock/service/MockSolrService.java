package com.lawu.eshop.mall.srv.mock.service;

import java.util.Collection;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.TermsResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.lawu.eshop.solr.service.SolrService;

/**
 * @author meishuquan
 * @date 2017/7/27.
 */
@Service
public class MockSolrService implements SolrService {
    @Override
    public boolean addSolrDocs(SolrInputDocument document, String solrUrl, String solrCore, Boolean isCloudSolr) {
        return false;
    }

    @Override
    public boolean addSolrDocsList(Collection<SolrInputDocument> documents, String solrUrl, String solrCore, Boolean isCloudSolr) {
        return false;
    }

    @Override
    public boolean delSolrDocsById(Long id, String solrUrl, String solrCore, Boolean isCloudSolr) {
        return false;
    }

    @Override
    public boolean delSolrDocsByIds(List<String> ids, String solrUrl, String solrCore, Boolean isCloudSolr) {
        return false;
    }

    @Override
    public boolean delAllSolrDocs(String solrUrl, String solrCore, Boolean isCloudSolr) {
        return false;
    }

    @Override
    public SolrDocumentList getSolrDocsByQuery(SolrQuery query, String solrUrl, String solrCore, Boolean isCloudSolr) {
        return null;
    }

    @Override
    public SolrDocument getSolrDocsById(Long id, String solrUrl, String solrCore, Boolean isCloudSolr) {
        return null;
    }

    @Override
    public TermsResponse getTermsResponseByQuery(SolrQuery query, String solrUrl, String solrCore, Boolean isCloudSolr) {
        return null;
    }
}
