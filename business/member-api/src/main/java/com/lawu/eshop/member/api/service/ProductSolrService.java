package com.lawu.eshop.member.api.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.product.dto.ProductSearchDTO;
import com.lawu.eshop.product.param.ProductSolrParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author meishuquan
 * @date 2017/4/12.
 */
@FeignClient(value = "product-srv")
public interface ProductSolrService {

    /**
     * 根据商品类别查询商品信息
     *
     * @param productSolrParam
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "productSolr/listProductByCategoryId")
    Result<Page<ProductSearchDTO>> listProductByCategoryId(@ModelAttribute ProductSolrParam productSolrParam);

    /**
     * 商品详情为你推荐(同类别按销量排行)
     *
     * @param productSolrParam
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "productSolr/listRecommendProduct")
    Result<Page<ProductSearchDTO>> listRecommendProduct(@ModelAttribute ProductSolrParam productSolrParam);

    /**
     * 会员APP商品搜索
     *
     * @param productSolrParam
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "productSolr/listProductByName")
    Result<Page<ProductSearchDTO>> listProductByName(@ModelAttribute ProductSolrParam productSolrParam);

}
