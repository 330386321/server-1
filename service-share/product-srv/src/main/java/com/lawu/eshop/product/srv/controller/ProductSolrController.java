package com.lawu.eshop.product.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.product.dto.ProductSearchDTO;
import com.lawu.eshop.product.dto.ProductSearchWordDTO;
import com.lawu.eshop.product.param.ProductSolrParam;
import com.lawu.eshop.product.srv.converter.ProductConverter;
import com.lawu.eshop.solr.SolrUtil;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.TermsResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author meishuquan
 * @date 2017/3/30.
 */
@RestController
@RequestMapping(value = "productSolr/")
public class ProductSolrController extends BaseController {

    /**
     * 根据商品类别查询商品信息
     *
     * @param productSolrParam
     * @return
     */
    @RequestMapping(value = "listProductByCategoryId", method = RequestMethod.POST)
    public Result<Page<ProductSearchDTO>> listProductByCategoryId(@RequestBody ProductSolrParam productSolrParam) {
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setFilterQueries("categoryId_is:" + productSolrParam.getCategoryId());
        query.setSort("averageDailySales_d", SolrQuery.ORDER.desc);
        query.setStart(productSolrParam.getOffset());
        query.setRows(productSolrParam.getPageSize());
        SolrDocumentList solrDocumentList = SolrUtil.getSolrDocsByQuery(query, SolrUtil.SOLR_PRODUCT_CORE);
        if (solrDocumentList == null || solrDocumentList.isEmpty()) {
            return successGet(ResultCode.NOT_FOUND_DATA);
        }

        Page<ProductSearchDTO> page = new Page<>();
        page.setRecords(ProductConverter.convertDTO(solrDocumentList));
        page.setTotalCount((int) solrDocumentList.getNumFound());
        page.setCurrentPage(productSolrParam.getCurrentPage());
        return successGet(page);
    }

    /**
     * 商品详情为你推荐(同类别按销量排行)
     *
     * @param productSolrParam
     * @return
     */
    @RequestMapping(value = "listRecommendProduct", method = RequestMethod.POST)
    public Result<Page<ProductSearchDTO>> listRecommendProduct(@RequestBody ProductSolrParam productSolrParam) {
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setFilterQueries("categoryId_is:" + productSolrParam.getCategoryId());
        query.setSort("salesVolume_i", SolrQuery.ORDER.desc);
        query.setStart(productSolrParam.getOffset());
        query.setRows(productSolrParam.getPageSize());
        SolrDocumentList solrDocumentList = SolrUtil.getSolrDocsByQuery(query, SolrUtil.SOLR_PRODUCT_CORE);
        if (solrDocumentList == null || solrDocumentList.isEmpty()) {
            return successGet(ResultCode.NOT_FOUND_DATA);
        }

        Page<ProductSearchDTO> page = new Page<>();
        page.setRecords(ProductConverter.convertDTO(solrDocumentList));
        page.setTotalCount((int) solrDocumentList.getNumFound());
        page.setCurrentPage(productSolrParam.getCurrentPage());
        return successGet(page);
    }

    /**
     * 要购物首页猜你喜欢
     *
     * @param productSolrParam
     * @return
     */
    @RequestMapping(value = "listYouLikeProduct", method = RequestMethod.POST)
    public Result<Page<ProductSearchDTO>> listYouLikeProduct(@RequestBody ProductSolrParam productSolrParam) {
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setSort("averageDailySales_d", SolrQuery.ORDER.desc);
        query.setStart(productSolrParam.getOffset());
        query.setRows(productSolrParam.getPageSize());
        SolrDocumentList solrDocumentList = SolrUtil.getSolrDocsByQuery(query, SolrUtil.SOLR_PRODUCT_CORE);
        if (solrDocumentList == null || solrDocumentList.isEmpty()) {
            return successGet(ResultCode.NOT_FOUND_DATA);
        }

        Page<ProductSearchDTO> page = new Page<>();
        page.setRecords(ProductConverter.convertDTO(solrDocumentList));
        page.setTotalCount((int) solrDocumentList.getNumFound());
        page.setCurrentPage(productSolrParam.getCurrentPage());
        return successGet(page);
    }

    /**
     * 会员APP商品搜索
     *
     * @param productSolrParam
     * @return
     */
    @RequestMapping(value = "listProductByName", method = RequestMethod.POST)
    public Result<Page<ProductSearchDTO>> listProductByName(@RequestBody ProductSolrParam productSolrParam) {
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setFilterQueries("name_s:*" + productSolrParam.getName() + "*");
        query.setSort("averageDailySales_d", SolrQuery.ORDER.desc);
        query.setStart(productSolrParam.getOffset());
        query.setRows(productSolrParam.getPageSize());
        SolrDocumentList solrDocumentList = SolrUtil.getSolrDocsByQuery(query, SolrUtil.SOLR_PRODUCT_CORE);
        if (solrDocumentList == null || solrDocumentList.isEmpty()) {
            return successGet(ResultCode.NOT_FOUND_DATA);
        }

        Page<ProductSearchDTO> page = new Page<>();
        page.setRecords(ProductConverter.convertDTO(solrDocumentList));
        page.setTotalCount((int) solrDocumentList.getNumFound());
        page.setCurrentPage(productSolrParam.getCurrentPage());
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
        SolrQuery query = new SolrQuery();
        query.set("q", "*:*");
        query.set("qt", "/terms");
        query.set("terms", "true");
        query.set("terms.fl", "name_s");
        query.set("terms.regex", name + "+.*");
        query.set("terms.regex.flag", "case_insensitive");
        TermsResponse termsResponse = SolrUtil.getTermsResponseByQuery(query, SolrUtil.SOLR_MERCHANT_CORE);

        List<ProductSearchWordDTO> productSearchWordDTOS = new ArrayList<>();
        if (termsResponse != null) {
            Map<String, List<TermsResponse.Term>> termsMap = termsResponse.getTermMap();
            for (Map.Entry<String, List<TermsResponse.Term>> termsEntry : termsMap.entrySet()) {
                List<TermsResponse.Term> termList = termsEntry.getValue();
                for (TermsResponse.Term term : termList) {
                    ProductSearchWordDTO productSearchWordDTO = new ProductSearchWordDTO();
                    productSearchWordDTO.setName(term.getTerm());
                    productSearchWordDTO.setCount((int) term.getFrequency());
                    productSearchWordDTOS.add(productSearchWordDTO);
                }
            }
        } else {
            ProductSearchWordDTO productSearchWordDTO = new ProductSearchWordDTO();
            productSearchWordDTO.setName(name);
            productSearchWordDTO.setCount(0);
            productSearchWordDTOS.add(productSearchWordDTO);
        }
        return successGet(productSearchWordDTOS);
    }

}
