package com.lawu.eshop.product.srv.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.lawu.eshop.product.dto.ProductSearchDTO;
import com.lawu.eshop.product.dto.ProductSearchWordDTO;
import com.lawu.eshop.product.param.ProductSearchParam;
import com.lawu.eshop.product.param.ProductSearchRealParam;
import com.lawu.eshop.product.srv.ProductSrvConfig;
import com.lawu.eshop.product.srv.converter.ProductConverter;
import com.lawu.eshop.solr.service.SolrService;

/**
 * @author meishuquan
 * @date 2017/3/30.
 */
@RestController
@RequestMapping(value = "productSolr/")
public class ProductSolrController extends BaseController {

    @Autowired
    private ProductSrvConfig productSrvConfig;

    @Autowired
    private SolrService solrService;

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
        SolrDocumentList solrDocumentList = solrService.getSolrDocsByQuery(query, productSrvConfig.getSolrUrl(), productSrvConfig.getSolrProductCore(), productSrvConfig.getIsCloudSolr());
        if (solrDocumentList == null || solrDocumentList.isEmpty()) {
            return successCreated(ResultCode.NOT_FOUND_DATA);
        }

        Page<ProductSearchDTO> page = new Page<>();
        page.setRecords(ProductConverter.convertDTO(solrDocumentList));
        page.setTotalCount((int) solrDocumentList.getNumFound());
        page.setCurrentPage(param.getCurrentPage());
        return successCreated(page);
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
        SolrDocumentList solrDocumentList = solrService.getSolrDocsByQuery(query, productSrvConfig.getSolrUrl(), productSrvConfig.getSolrProductCore(), productSrvConfig.getIsCloudSolr());
        if (solrDocumentList == null || solrDocumentList.isEmpty()) {
            return successCreated(ResultCode.NOT_FOUND_DATA);
        }

        Page<ProductSearchDTO> page = new Page<>();
        page.setRecords(ProductConverter.convertDTO(solrDocumentList));
        page.setTotalCount((int) solrDocumentList.getNumFound());
        page.setCurrentPage(param.getCurrentPage());
        return successCreated(page);
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
        SolrDocumentList solrDocumentList = solrService.getSolrDocsByQuery(query, productSrvConfig.getSolrUrl(), productSrvConfig.getSolrProductCore(), productSrvConfig.getIsCloudSolr());
        if (solrDocumentList == null || solrDocumentList.isEmpty()) {
            return successCreated(ResultCode.NOT_FOUND_DATA);
        }

        Page<ProductSearchDTO> page = new Page<>();
        page.setRecords(ProductConverter.convertDTO(solrDocumentList));
        page.setTotalCount((int) solrDocumentList.getNumFound());
        page.setCurrentPage(productSearchParam.getCurrentPage());
        return successCreated(page);
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
        query.setQuery("text:" + param.getName());
        query.setSort("averageDailySales_d", SolrQuery.ORDER.desc);
        query.setStart(param.getOffset());
        query.setRows(param.getPageSize());
        SolrDocumentList solrDocumentList = solrService.getSolrDocsByQuery(query, productSrvConfig.getSolrUrl(), productSrvConfig.getSolrProductCore(), productSrvConfig.getIsCloudSolr());
        if (solrDocumentList == null || solrDocumentList.isEmpty()) {
            return successCreated(ResultCode.NOT_FOUND_DATA);
        }

        Page<ProductSearchDTO> page = new Page<>();
        page.setRecords(ProductConverter.convertDTO(solrDocumentList));
        page.setTotalCount((int) solrDocumentList.getNumFound());
        page.setCurrentPage(param.getCurrentPage());
        return successCreated(page);
    }

    /**
     * 搜索词关联词频查询
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "listProductSearchWord", method = RequestMethod.GET)
    public Result<List<ProductSearchWordDTO>> listStoreSearchWord(@RequestParam String name) {
        List<ProductSearchWordDTO> searchWordDTOS = new ArrayList<>();
        SolrQuery query = new SolrQuery();
        query.setQuery("keyword_ss:" + name + "*");
        query.setFields("keyword_ss");
        query.setStart(0);
        query.setRows(10);
        SolrDocumentList solrDocumentList = solrService.getSolrDocsByQuery(query, productSrvConfig.getSolrUrl(), productSrvConfig.getSolrProductCore(), productSrvConfig.getIsCloudSolr());
        if (solrDocumentList == null || solrDocumentList.isEmpty()) {
            return successGet(searchWordDTOS);
        }

        for (SolrDocument solrDocument : solrDocumentList) {
            String keywords = solrDocument.get("keyword_ss").toString();
            keywords = keywords.substring(1, keywords.length() - 1);
            String[] keywordArr = keywords.split(",");
            for (String keyword : keywordArr) {
                if (keyword.trim().startsWith(name)) {
                    ProductSearchWordDTO searchWordDTO = new ProductSearchWordDTO();
                    searchWordDTO.setName(keyword.trim());

                    query = new SolrQuery();
                    query.setQuery("keyword_ss:" + keyword.trim());
                    query.setFields("id");
                    solrDocumentList = solrService.getSolrDocsByQuery(query, productSrvConfig.getSolrUrl(), productSrvConfig.getSolrProductCore(), productSrvConfig.getIsCloudSolr());
                    searchWordDTO.setCount((int) solrDocumentList.getNumFound());
                    searchWordDTOS.add(searchWordDTO);
                }
            }
        }
        return successGet(searchWordDTOS);
    }

    /**
     * 查询日销量商品列表
     * @param searchParam
     * @return
     * @author zhangy
     */
    @RequestMapping(method = RequestMethod.POST, value = "findProductSearchList")
    public List<ProductSearchDTO> findProductSearchList(@RequestBody  ProductSearchParam searchParam){
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setSort("averageDailySales_d", SolrQuery.ORDER.desc);
        query.setStart(searchParam.getOffset());
        query.setRows(searchParam.getPageSize());
        SolrDocumentList solrDocumentList = solrService.getSolrDocsByQuery(query, productSrvConfig.getSolrUrl(), productSrvConfig.getSolrProductCore(), productSrvConfig.getIsCloudSolr());
        return  ProductConverter.convertDTO(solrDocumentList);
    }

}
