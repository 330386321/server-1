package com.lawu.eshop.operator.api.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.product.dto.ProductCategoryDTO;
import com.lawu.eshop.product.dto.RecommendProductCategoryDTO;
import com.lawu.eshop.product.param.EditRecommendProductCategoryParam;
import com.lawu.eshop.product.param.ListRecommendProductCategoryParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/10.
 */
@FeignClient(value = "product-srv")
public interface RecommendProductCategoryService {

    /**
     * 保存推荐商品类别
     *
     * @param param
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "recommendProductCategory/saveRecommendProductCategory")
    Result saveRecommendProductCategory(@RequestBody EditRecommendProductCategoryParam param);

    /**
     * 根据ID删除商品类别
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "recommendProductCategory/deleteRecommendProductCategory/{id}")
    Result deleteRecommendProductCategoryById(@PathVariable("id") Long id);

    /**
     * 根据ID修改推荐商品类别信息
     *
     * @param id
     * @param param
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "recommendProductCategory/updateRecommendProductCategory/{id}")
    Result updateRecommendProductCategoryById(@PathVariable("id") Long id, @RequestBody EditRecommendProductCategoryParam param);

    /**
     * 根据ID查询商品类别
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "recommendProductCategory/getRecommendProductCategory/{id}")
    Result<RecommendProductCategoryDTO> getRecommendProductCategoryById(@PathVariable("id") Long id);

    /**
     * 商品类别列表
     *
     * @param listRecommendProductCategoryParam
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "recommendProductCategory/listRecommendProductCategory")
    Result<Page<RecommendProductCategoryDTO>> listRecommendProductCategory(@RequestBody ListRecommendProductCategoryParam listRecommendProductCategoryParam);

    /**
     * 查询推荐商品列表类别(一级)
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "productCategory/listRecommendProductCategory")
    Result<List<ProductCategoryDTO>> listRecommendProductCategory();

}
