package com.lawu.eshop.solr.service;

import java.util.Collection;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.TermsResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

/**
 * @author meishuquan
 * @date 2017/7/24.
 */
public interface SolrService {

    /**
     * 新增
     *
     * @param document
     * @param solrUrl
     * @param solrCore
     * @param isCloudSolr
     * @return
     */
    boolean addSolrDocs(SolrInputDocument document, String solrUrl, String solrCore, Boolean isCloudSolr);

    /**
     * 新增
     *
     * @param documents
     * @param solrUrl
     * @param solrCore
     * @param isCloudSolr
     * @return
     */
    boolean addSolrDocsList(Collection<SolrInputDocument> documents, String solrUrl, String solrCore, Boolean isCloudSolr);

    /**
     * 根据ID删除
     *
     * @param id
     * @param solrUrl
     * @param solrCore
     * @param isCloudSolr
     * @return
     */
    boolean delSolrDocsById(Long id, String solrUrl, String solrCore, Boolean isCloudSolr);

    /**
     * 根据ids删除
     *
     * @param ids
     * @param solrUrl
     * @param solrCore
     * @param isCloudSolr
     * @return
     */
    boolean delSolrDocsByIds(List<String> ids, String solrUrl, String solrCore, Boolean isCloudSolr);

    /**
     * 删除全部索引数据
     *
     * @param solrUrl
     * @param solrCore
     * @param isCloudSolr
     * @return
     */
    boolean delAllSolrDocs(String solrUrl, String solrCore, Boolean isCloudSolr);

    /**
     * 根据条件查询
     *
     * @param query
     * @param solrUrl
     * @param solrCore
     * @param isCloudSolr
     * @throws Exception
     */
    SolrDocumentList getSolrDocsByQuery(SolrQuery query, String solrUrl, String solrCore, Boolean isCloudSolr);

    /**
     * 根据主键查询
     *
     * @param id
     * @param solrUrl
     * @param solrCore
     * @param isCloudSolr
     * @return
     */
    SolrDocument getSolrDocsById(Long id, String solrUrl, String solrCore, Boolean isCloudSolr);

    /**
     * 词频统计
     *
     * @param query
     * @param solrUrl
     * @param solrCore
     * @param isCloudSolr
     * @return
     */
    TermsResponse getTermsResponseByQuery(SolrQuery query, String solrUrl, String solrCore, Boolean isCloudSolr);

}
