package com.lawu.eshop.ad.srv.controller;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.dto.AdSolrDTO;
import com.lawu.eshop.ad.param.AdSolrParam;
import com.lawu.eshop.ad.srv.converter.AdConverter;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.solr.SolrUtil;

/**
 * @author zhangrc
 * @date 2017/4/13.
 */
@RestController
@RequestMapping(value = "adSolr/")
public class AdSolrController extends BaseController {

    /**
     * 根据商品类别查询商品信息
     *
     * @param productSolrParam
     * @return
     */
    @RequestMapping(value = "queryAdByTitle", method = RequestMethod.POST)
    public Result<Page<AdSolrDTO>> queryAdByTitle(@RequestBody AdSolrParam adSolrParam) {
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setFilterQueries("title_s:" + adSolrParam.getTilte());
        query.setStart(adSolrParam.getOffset());
        query.setRows(adSolrParam.getPageSize());
        SolrDocumentList solrDocumentList = SolrUtil.getSolrDocsByQuery(query, SolrUtil.SOLR_AD_CORE);
        if (solrDocumentList == null || solrDocumentList.isEmpty()) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }

        Page<AdSolrDTO> page = new Page<AdSolrDTO>();
        page.setRecords(AdConverter.convertDTO(solrDocumentList));
        page.setTotalCount((int) solrDocumentList.getNumFound());
        page.setCurrentPage(adSolrParam.getCurrentPage());
        return successGet(page);
    }

    
}
