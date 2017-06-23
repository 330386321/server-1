package com.lawu.eshop.member.api.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.product.dto.ProductSearchDTO;
import com.lawu.eshop.product.dto.ProductSearchWordDTO;
import com.lawu.eshop.product.param.ProductSearchParam;
import com.lawu.eshop.product.param.ProductSearchRealParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/12.
 */
@FeignClient(value = "product-srv")
public interface ProductSolrService {

    /**
     * 根据商品类别查询商品信息
     *
     * @param param
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "productSolr/listProductByCategoryId")
    Result<Page<ProductSearchDTO>> listProductByCategoryId(@ModelAttribute ProductSearchRealParam param);

    /**
     * 商品详情为你推荐(同类别按销量排行)
     *
     * @param param
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "productSolr/listRecommendProduct")
    Result<Page<ProductSearchDTO>> listRecommendProduct(@ModelAttribute ProductSearchRealParam param);

    /**
     * 要购物首页猜你喜欢
     *
     * @param productSearchParam
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "productSolr/listYouLikeProduct")
    Result<Page<ProductSearchDTO>> listYouLikeProduct(@ModelAttribute ProductSearchParam productSearchParam);

    /**
     * 会员APP商品搜索
     *
     * @param param
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "productSolr/listProductByName")
    Result<Page<ProductSearchDTO>> listProductByName(@ModelAttribute ProductSearchRealParam param);

    /**
     * 搜索词关联词频查询
     *
     * @param name
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "productSolr/listProductSearchWord")
    Result<List<ProductSearchWordDTO>> listProductSearchWord(@RequestParam("name") String name);

    @RequestMapping(method = RequestMethod.POST, value = "productSolr/findProductSearchList")
    List<ProductSearchDTO> findProductSearchList(@RequestBody  ProductSearchParam searchParam);
}
