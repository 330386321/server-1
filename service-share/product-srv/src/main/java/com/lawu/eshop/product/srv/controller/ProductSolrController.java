package com.lawu.eshop.product.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.product.dto.ProductSearchDTO;
import com.lawu.eshop.product.dto.ProductSearchWordDTO;
import com.lawu.eshop.product.param.ProductSearchParam;
import com.lawu.eshop.product.param.ProductSearchRealParam;
import com.lawu.eshop.product.srv.ProductSrvConfig;
import com.lawu.eshop.product.srv.converter.ProductConverter;
import com.lawu.eshop.solr.SolrUtil;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/3/30.
 */
@RestController
@RequestMapping(value = "productSolr/")
public class ProductSolrController extends BaseController {

    @Autowired
    private ProductSrvConfig productSrvConfig;

    /**
     * 根据商品类别查询商品信息
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "listProductByCategoryId", method = RequestMethod.POST)
    public Result<Page<ProductSearchDTO>> listProductByCategoryId(@RequestBody ProductSearchRealParam param) {
        SolrQuery query = new SolrQuery();
        query.setQuery("categoryId_i:" + param.getCategoryId());
        query.setSort("averageDailySales_d", SolrQuery.ORDER.desc);
        query.setStart(param.getOffset());
        query.setRows(param.getPageSize());
        SolrDocumentList solrDocumentList = SolrUtil.getSolrDocsByQuery(query, productSrvConfig.getSolrUrl(), productSrvConfig.getSolrProductCore(), productSrvConfig.getIsCloudSolr());
        if (solrDocumentList == null || solrDocumentList.isEmpty()) {
            return successGet(ResultCode.NOT_FOUND_DATA);
        }

        Page<ProductSearchDTO> page = new Page<>();
        page.setRecords(ProductConverter.convertDTO(solrDocumentList));
        page.setTotalCount((int) solrDocumentList.getNumFound());
        page.setCurrentPage(param.getCurrentPage());
        return successGet(page);
    }

    /**
     * 商品详情为你推荐(同类别按销量排行)
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "listRecommendProduct", method = RequestMethod.POST)
    public Result<Page<ProductSearchDTO>> listRecommendProduct(@RequestBody ProductSearchRealParam param) {
        SolrQuery query = new SolrQuery();
        query.setQuery("categoryId_i:" + param.getCategoryId());
        query.addFilterQuery("-id:" + param.getProductId());
        query.setSort("salesVolume_i", SolrQuery.ORDER.desc);
        query.setStart(param.getOffset());
        query.setRows(param.getPageSize());
        SolrDocumentList solrDocumentList = SolrUtil.getSolrDocsByQuery(query, productSrvConfig.getSolrUrl(), productSrvConfig.getSolrProductCore(), productSrvConfig.getIsCloudSolr());
        if (solrDocumentList == null || solrDocumentList.isEmpty()) {
            return successGet(ResultCode.NOT_FOUND_DATA);
        }

        Page<ProductSearchDTO> page = new Page<>();
        page.setRecords(ProductConverter.convertDTO(solrDocumentList));
        page.setTotalCount((int) solrDocumentList.getNumFound());
        page.setCurrentPage(param.getCurrentPage());
        return successGet(page);
    }

    /**
     * 要购物首页猜你喜欢
     *
     * @param productSearchParam
     * @return
     */
    @RequestMapping(value = "listYouLikeProduct", method = RequestMethod.POST)
    public Result<Page<ProductSearchDTO>> listYouLikeProduct(@RequestBody ProductSearchParam productSearchParam) {
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setSort("averageDailySales_d", SolrQuery.ORDER.desc);
        query.setStart(productSearchParam.getOffset());
        query.setRows(productSearchParam.getPageSize());
        SolrDocumentList solrDocumentList = SolrUtil.getSolrDocsByQuery(query, productSrvConfig.getSolrUrl(), productSrvConfig.getSolrProductCore(), productSrvConfig.getIsCloudSolr());
        if (solrDocumentList == null || solrDocumentList.isEmpty()) {
            return successGet(ResultCode.NOT_FOUND_DATA);
        }

        Page<ProductSearchDTO> page = new Page<>();
        page.setRecords(ProductConverter.convertDTO(solrDocumentList));
        page.setTotalCount((int) solrDocumentList.getNumFound());
        page.setCurrentPage(productSearchParam.getCurrentPage());
        return successGet(page);
    }

    /**
     * 会员APP商品搜索
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "listProductByName", method = RequestMethod.POST)
    public Result<Page<ProductSearchDTO>> listProductByName(@RequestBody ProductSearchRealParam param) {
        SolrQuery query = new SolrQuery();
        query.setQuery("name_s:*" + param.getName() + "*");
        query.setSort("averageDailySales_d", SolrQuery.ORDER.desc);
        query.setStart(param.getOffset());
        query.setRows(param.getPageSize());
        SolrDocumentList solrDocumentList = SolrUtil.getSolrDocsByQuery(query, productSrvConfig.getSolrUrl(), productSrvConfig.getSolrProductCore(), productSrvConfig.getIsCloudSolr());
        if (solrDocumentList == null || solrDocumentList.isEmpty()) {
            return successGet(ResultCode.NOT_FOUND_DATA);
        }

        Page<ProductSearchDTO> page = new Page<>();
        page.setRecords(ProductConverter.convertDTO(solrDocumentList));
        page.setTotalCount((int) solrDocumentList.getNumFound());
        page.setCurrentPage(param.getCurrentPage());
        return successGet(page);
    }

    /**
     * 搜索词关联词频查询
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "listProductSearchWord", method = RequestMethod.GET)
    public Result<List<ProductSearchWordDTO>> listStoreSearchWord(@RequestParam String name) {
        //SolrQuery query = new SolrQuery();
        //query.set("q", "*:*");
        //query.set("qt", "/terms");
        //query.set("terms", "true");
        //query.set("terms.fl", "name_s");
        //query.set("terms.regex", name + "+.*");
        //query.set("terms.regex.flag", "case_insensitive");
        //TermsResponse termsResponse = SolrUtil.getTermsResponseByQuery(query, productSrvConfig.getSolrUrl(), productSrvConfig.getSolrProductCore(), productSrvConfig.getIsCloudSolr());

        List<ProductSearchWordDTO> productSearchWordDTOS = new ArrayList<>();
        //if (termsResponse != null) {
        //    Map<String, List<TermsResponse.Term>> termsMap = termsResponse.getTermMap();
        //    for (Map.Entry<String, List<TermsResponse.Term>> termsEntry : termsMap.entrySet()) {
        //        List<TermsResponse.Term> termList = termsEntry.getValue();
        //        for (TermsResponse.Term term : termList) {
        //            ProductSearchWordDTO productSearchWordDTO = new ProductSearchWordDTO();
        //            productSearchWordDTO.setName(term.getTerm());
        //            productSearchWordDTO.setCount((int) term.getFrequency());
        //            productSearchWordDTOS.add(productSearchWordDTO);
        //        }
        //    }
        //} else {
        //    ProductSearchWordDTO productSearchWordDTO = new ProductSearchWordDTO();
        //    productSearchWordDTO.setName(name);
        //    productSearchWordDTO.setCount(0);
        //    productSearchWordDTOS.add(productSearchWordDTO);
        //}
        return successGet(productSearchWordDTOS);
    }

}
