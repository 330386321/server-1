package com.lawu.eshop.user.srv.mock.service;

import com.lawu.eshop.solr.service.SolrService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.TermsResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/7/24.
 */
@Service
public class MockSolrService implements SolrService{

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
