package com.lawu.eshop.user.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.solr.SolrUtil;
import com.lawu.eshop.user.constants.StoreSolrEnum;
import com.lawu.eshop.user.dto.StoreSearchWordDTO;
import com.lawu.eshop.user.dto.StoreSolrDTO;
import com.lawu.eshop.user.param.StoreSolrParam;
import com.lawu.eshop.user.srv.UserSrvConfig;
import com.lawu.eshop.user.srv.converter.MerchantStoreConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.TermsResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author meishuquan
 * @date 2017/3/30.
 */
@RestController
@RequestMapping(value = "storeSolr/")
public class StoreSolrController extends BaseController {

    @Autowired
    private UserSrvConfig userSrvConfig;

    /**
     * 搜索门店
     *
     * @param storeSolrParam
     * @return
     */
    @RequestMapping(value = "listStore", method = RequestMethod.POST)
    public Result<Page<StoreSolrDTO>> listStore(@RequestBody StoreSolrParam storeSolrParam) {
        String latLon = storeSolrParam.getLatitude() + "," + storeSolrParam.getLongitude();
        StringBuffer stringBuffer = new StringBuffer("regionPath_s:");
        stringBuffer.append(storeSolrParam.getRegionPath()).append("*");
        if (StringUtils.isNotEmpty(storeSolrParam.getName())) {
            stringBuffer.append(" AND name_s:*").append(storeSolrParam.getName()).append("*");
        }
        if (StringUtils.isNotEmpty(storeSolrParam.getIndustryPath())) {
            stringBuffer.append(" AND industryPath_s:").append(storeSolrParam.getIndustryPath()).append("*");
        }
        if(storeSolrParam.getStoreId() != null && storeSolrParam.getStoreId() > 0){
            stringBuffer.append(" AND -id:").append(storeSolrParam.getStoreId());
        }
        SolrQuery query = new SolrQuery();
        query.setParam("q", stringBuffer.toString());
        if (storeSolrParam.getDistance() != null && storeSolrParam.getDistance() > 0) {
            query.setParam("d", String.valueOf(storeSolrParam.getDistance()));
        } else {
            query.setParam("d", "10000000");
        }
        query.setParam("pt", latLon);
        query.setParam("fq", "{!geofilt}");
        query.setParam("sfield", "latLon_p");
        query.setParam("fl", "*,distance:geodist(latLon_p," + latLon + ")");

        if (storeSolrParam.getStoreSolrEnum() != null) {
            if (storeSolrParam.getStoreSolrEnum().val == StoreSolrEnum.DISTANCE_SORT.val) {
                query.setParam("sort", "geodist() asc");
            } else if (storeSolrParam.getStoreSolrEnum().val == StoreSolrEnum.FEEDBACK_SORT.val) {
                query.setParam("sort", "feedbackRate_d desc");
            } else if (storeSolrParam.getStoreSolrEnum().val == StoreSolrEnum.POPULARITY_SORT.val) {
                query.setParam("sort", "favoriteNumber_i desc");
            } else {
                query.setParam("sort", "favoriteNumber_i desc,feedbackRate_d desc,geodist() asc");
            }
        }
        query.setStart(storeSolrParam.getOffset());
        query.setRows(storeSolrParam.getPageSize());
        SolrDocumentList solrDocumentList = SolrUtil.getSolrDocsByQuery(query, userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore());
        if (solrDocumentList == null || solrDocumentList.isEmpty()) {
            return successGet(ResultCode.NOT_FOUND_DATA);
        }

        Page<StoreSolrDTO> page = new Page<>();
        page.setRecords(MerchantStoreConverter.convertDTO(solrDocumentList));
        page.setTotalCount((int) solrDocumentList.getNumFound());
        page.setCurrentPage(storeSolrParam.getCurrentPage());
        return successGet(page);
    }

    /**
     * 搜索词关联词频查询
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "listStoreSearchWord", method = RequestMethod.GET)
    public Result<List<StoreSearchWordDTO>> listStoreSearchWord(@RequestParam String name) {
        SolrQuery query = new SolrQuery();
        query.set("q", "*:*");
        query.set("qt", "/terms");
        query.set("terms", "true");
        query.set("terms.fl", "name_s");
        query.set("terms.regex", name + "+.*");
        query.set("terms.regex.flag", "case_insensitive");
        TermsResponse termsResponse = SolrUtil.getTermsResponseByQuery(query, userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore());

        List<StoreSearchWordDTO> storeSearchWordDTOS = new ArrayList<>();
        if (termsResponse != null) {
            Map<String, List<TermsResponse.Term>> termsMap = termsResponse.getTermMap();
            for (Map.Entry<String, List<TermsResponse.Term>> termsEntry : termsMap.entrySet()) {
                List<TermsResponse.Term> termList = termsEntry.getValue();
                for (TermsResponse.Term term : termList) {
                    StoreSearchWordDTO storeSearchWordDTO = new StoreSearchWordDTO();
                    storeSearchWordDTO.setName(term.getTerm());
                    storeSearchWordDTO.setCount((int) term.getFrequency());
                    storeSearchWordDTOS.add(storeSearchWordDTO);
                }
            }
        } else {
            StoreSearchWordDTO storeSearchWordDTO = new StoreSearchWordDTO();
            storeSearchWordDTO.setName(name);
            storeSearchWordDTO.setCount(0);
            storeSearchWordDTOS.add(storeSearchWordDTO);
        }
        return successGet(storeSearchWordDTOS);
    }

}
