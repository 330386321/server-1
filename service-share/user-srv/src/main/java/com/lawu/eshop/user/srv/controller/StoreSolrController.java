package com.lawu.eshop.user.srv.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.solr.service.SolrService;
import com.lawu.eshop.user.constants.StoreSolrEnum;
import com.lawu.eshop.user.dto.StoreSearchWordDTO;
import com.lawu.eshop.user.dto.StoreSolrDTO;
import com.lawu.eshop.user.param.DiscountStoreParam;
import com.lawu.eshop.user.param.StoreSolrParam;
import com.lawu.eshop.user.srv.UserSrvConfig;
import com.lawu.eshop.user.srv.converter.MerchantStoreConverter;

/**
 * @author meishuquan
 * @date 2017/3/30.
 */
@RestController
@RequestMapping(value = "storeSolr/")
public class StoreSolrController extends BaseController {

    @Autowired
    private UserSrvConfig userSrvConfig;

    @Autowired
    private SolrService solrService;

    /**
     * 搜索门店
     *
     * @param storeSolrParam
     * @return
     */
    @RequestMapping(value = "listStore", method = RequestMethod.POST)
    public Result<Page<StoreSolrDTO>> listStore(@RequestBody StoreSolrParam storeSolrParam) {
        double lat = storeSolrParam.getLatitude().setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        double lon = storeSolrParam.getLongitude().setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        String latLon = lat + "," + lon;
        StringBuilder sb = new StringBuilder("regionPath_s:");
        sb.append(storeSolrParam.getRegionPath()).append("*");
        if (StringUtils.isNotEmpty(storeSolrParam.getName())) {
            sb.append(" AND text:").append(storeSolrParam.getName()).append("*");
        }
        if (StringUtils.isNotEmpty(storeSolrParam.getIndustryPath())) {
            sb.append(" AND industryPath_s:").append(storeSolrParam.getIndustryPath()).append("*");
        }
        if (storeSolrParam.getStoreId() != null && storeSolrParam.getStoreId() > 0) {
            sb.append(" AND -id:").append(storeSolrParam.getStoreId());
        }
        SolrQuery query = new SolrQuery();
        query.setParam("q", sb.toString());
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
                query.setParam("sort", "averageScore_d desc");
            } else if (storeSolrParam.getStoreSolrEnum().val == StoreSolrEnum.POPULARITY_SORT.val) {
                query.setParam("sort", "favoriteNumber_i desc");
            } else {
                query.setParam("sort", "favoriteNumber_i desc,averageScore_d desc,geodist() asc");
            }
        }
        query.setStart(storeSolrParam.getOffset());
        query.setRows(storeSolrParam.getPageSize());
        SolrDocumentList solrDocumentList = solrService.getSolrDocsByQuery(query, userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore(), userSrvConfig.getIsCloudSolr());
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
    public Result<List<StoreSearchWordDTO>> listStoreSearchWord(@RequestParam String name, @RequestParam String regionPath) {
        List<StoreSearchWordDTO> searchWordDTOS = new ArrayList<>();
        SolrQuery query = new SolrQuery();
        query.setQuery("keyword_ss:" + name + "* AND regionPath_s:" + regionPath + "*");
        query.setFields("keyword_ss");
        query.setStart(0);
        query.setRows(10);
        SolrDocumentList solrDocumentList = solrService.getSolrDocsByQuery(query, userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore(), userSrvConfig.getIsCloudSolr());
        if (solrDocumentList == null || solrDocumentList.isEmpty()) {
            return successGet(searchWordDTOS);
        }

        for (SolrDocument solrDocument : solrDocumentList) {
            String keywords = solrDocument.get("keyword_ss").toString();
            keywords = keywords.substring(1, keywords.length() - 1);
            String[] keywordArr = keywords.split(",");
            for (String keyword : keywordArr) {
                if (keyword.startsWith(name)) {
                    StoreSearchWordDTO searchWordDTO = new StoreSearchWordDTO();
                    searchWordDTO.setName(keyword);

                    query = new SolrQuery();
                    query.setQuery("keyword_ss:" + keyword + " AND regionPath_s:" + regionPath + "*");
                    query.setFields("id");
                    solrDocumentList = solrService.getSolrDocsByQuery(query, userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore(), userSrvConfig.getIsCloudSolr());
                    searchWordDTO.setCount((int) solrDocumentList.getNumFound());
                    searchWordDTOS.add(searchWordDTO);
                }
            }
        }
        for (int i = 0; i < searchWordDTOS.size(); i++) {
            for (int j = 0; j < searchWordDTOS.size(); j++) {
                if (searchWordDTOS.get(i).getName().equals(searchWordDTOS.get(j).getName()) && i != j) {
                    searchWordDTOS.remove(i);
                }
            }
        }
        return successGet(searchWordDTOS);
    }

    /**
     * 专属特惠(按折扣系数升序)
     *
     * @param discountStoreParam
     * @return
     * @author meishuquan
     */
    @RequestMapping(value = "discountStore", method = RequestMethod.POST)
    public Result<Page<StoreSolrDTO>> discountStore(@RequestBody DiscountStoreParam discountStoreParam) {
        double lat = discountStoreParam.getLatitude().setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        double lon = discountStoreParam.getLongitude().setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        String latLon = lat + "," + lon;
        StringBuilder sb = new StringBuilder("regionPath_s:");
        sb.append(discountStoreParam.getRegionPath()).append("*");
        SolrQuery query = new SolrQuery();
        query.setParam("q", sb.toString());
        query.setParam("d", "10000000");
        query.setParam("pt", latLon);
        query.setParam("fq", "{!geofilt}");
        query.setParam("sfield", "latLon_p");
        query.setParam("fl", "*,distance:geodist(latLon_p," + latLon + ")");
        query.setParam("sort", "discountOrdinal_d asc");
        query.setStart(discountStoreParam.getOffset());
        query.setRows(discountStoreParam.getPageSize());
        SolrDocumentList solrDocumentList = solrService.getSolrDocsByQuery(query, userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore(), userSrvConfig.getIsCloudSolr());
        if (solrDocumentList == null || solrDocumentList.isEmpty()) {
            return successGet(ResultCode.NOT_FOUND_DATA);
        }

        Page<StoreSolrDTO> page = new Page<>();
        page.setRecords(MerchantStoreConverter.convertDTO(solrDocumentList));
        page.setTotalCount((int) solrDocumentList.getNumFound());
        page.setCurrentPage(discountStoreParam.getCurrentPage());
        return successGet(page);
    }

}
