package com.lawu.eshop.user.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.solr.SolrUtil;
import com.lawu.eshop.user.constants.StoreSolrEnum;
import com.lawu.eshop.user.dto.NearStoreDTO;
import com.lawu.eshop.user.param.StoreSolrParam;
import com.lawu.eshop.user.srv.converter.MerchantStoreConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author meishuquan
 * @date 2017/3/30.
 */
@RestController
@RequestMapping(value = "storeSolr/")
public class StoreSolrController extends BaseController {

    /**
     * 搜索门店
     *
     * @param storeSolrParam
     * @return
     */
    @RequestMapping(value = "listStore", method = RequestMethod.POST)
    public Result<Page<NearStoreDTO>> listStore(@RequestBody StoreSolrParam storeSolrParam) {
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");

        String latLon = storeSolrParam.getLatitude() + "," + storeSolrParam.getLongitude();
        StringBuffer stringBuffer = new StringBuffer("{!geofilt} AND sfield:latLon_p");
        stringBuffer.append(" AND pt:").append(latLon);
        if (StringUtils.isNotEmpty(storeSolrParam.getName())) {
            stringBuffer.append(" AND name_s:*").append(storeSolrParam.getName()).append("*");
        }
        if (StringUtils.isNotEmpty(storeSolrParam.getIndustryPath())) {
            stringBuffer.append(" AND industryPath_s:").append(storeSolrParam.getIndustryPath());
        }
        if (storeSolrParam.getDistance() != null && storeSolrParam.getDistance() > 0) {
            stringBuffer.append(" AND d:").append(storeSolrParam.getDistance());
        }
        query.setFields("*,distance:geodist(latLon_p," + latLon + ")");
        query.setFilterQueries(stringBuffer.toString());

        if (storeSolrParam.getStoreSolrEnum().val == StoreSolrEnum.DISTANCE_SORT.val) {
            query.setSort("geodist()", SolrQuery.ORDER.asc);
        } else if (storeSolrParam.getStoreSolrEnum().val == StoreSolrEnum.FEEDBACK_SORT.val) {
            query.setSort("feedbackRate_d", SolrQuery.ORDER.desc);
        } else if (storeSolrParam.getStoreSolrEnum().val == StoreSolrEnum.POPULARITY_SORT.val) {
            query.setSort("favoriteNumber_i", SolrQuery.ORDER.desc);
        } else {
            query.setSort("favoriteNumber_i", SolrQuery.ORDER.desc);
            query.setSort("feedbackRate_d", SolrQuery.ORDER.desc);
            query.setSort("geodist()", SolrQuery.ORDER.asc);
        }
        query.setStart(storeSolrParam.getOffset());
        query.setRows(storeSolrParam.getPageSize());
        SolrDocumentList solrDocumentList = SolrUtil.getSolrDocsByQuery(query, SolrUtil.SOLR_PRODUCT_CORE);
        if (solrDocumentList == null || solrDocumentList.isEmpty()) {
            return successGet(ResultCode.NOT_FOUND_DATA);
        }

        Page<NearStoreDTO> page = new Page<>();
        page.setRecords(MerchantStoreConverter.convertDTO(solrDocumentList));
        page.setTotalCount((int) solrDocumentList.getNumFound());
        page.setCurrentPage(storeSolrParam.getCurrentPage());
        return successGet(page);
    }

}
