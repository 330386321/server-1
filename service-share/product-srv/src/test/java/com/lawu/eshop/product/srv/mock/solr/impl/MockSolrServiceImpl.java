/**
 * 
 */
package com.lawu.eshop.product.srv.mock.solr.impl;

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
 * @author lihj
 * @date 2017年7月24日
 */
@Service
public class MockSolrServiceImpl implements SolrService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lawu.eshop.solr.service.SolrService#addSolrDocs(org.apache.solr.
	 * common.SolrInputDocument, java.lang.String, java.lang.String,
	 * java.lang.Boolean)
	 */
	@Override
	public boolean addSolrDocs(SolrInputDocument document, String solrUrl, String solrCore, Boolean isCloudSolr) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lawu.eshop.solr.service.SolrService#addSolrDocsList(java.util.
	 * Collection, java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	@Override
	public boolean addSolrDocsList(Collection<SolrInputDocument> documents, String solrUrl, String solrCore,
			Boolean isCloudSolr) {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lawu.eshop.solr.service.SolrService#delSolrDocsById(java.lang.Long,
	 * java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	@Override
	public boolean delSolrDocsById(Long id, String solrUrl, String solrCore, Boolean isCloudSolr) {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lawu.eshop.solr.service.SolrService#delSolrDocsByIds(java.util.List,
	 * java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	@Override
	public boolean delSolrDocsByIds(List<String> ids, String solrUrl, String solrCore, Boolean isCloudSolr) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean delAllSolrDocs(String solrUrl, String solrCore, Boolean isCloudSolr) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lawu.eshop.solr.service.SolrService#getSolrDocsByQuery(org.apache.
	 * solr.client.solrj.SolrQuery, java.lang.String, java.lang.String,
	 * java.lang.Boolean)
	 */
	@Override
	public SolrDocumentList getSolrDocsByQuery(SolrQuery query, String solrUrl, String solrCore, Boolean isCloudSolr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SolrDocumentList getSolrDocsByQueryPost(SolrQuery query, String solrUrl, String solrCore, Boolean isCloudSolr) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lawu.eshop.solr.service.SolrService#getSolrDocsById(java.lang.Long,
	 * java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	@Override
	public SolrDocument getSolrDocsById(Long id, String solrUrl, String solrCore, Boolean isCloudSolr) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lawu.eshop.solr.service.SolrService#getTermsResponseByQuery(org.
	 * apache.solr.client.solrj.SolrQuery, java.lang.String, java.lang.String,
	 * java.lang.Boolean)
	 */
	@Override
	public TermsResponse getTermsResponseByQuery(SolrQuery query, String solrUrl, String solrCore,
			Boolean isCloudSolr) {
		// TODO Auto-generated method stub
		return null;
	}

}
