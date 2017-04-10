package com.lawu.eshop.operator.api.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.product.dto.RecommendProductCategoryDTO;
import com.lawu.eshop.product.param.ListRecommendProductCategoryParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author meishuquan
 * @date 2017/4/10.
 */
@FeignClient(value = "product-srv")
public interface RecommendProductCategoryService {

    @RequestMapping(method = RequestMethod.POST, value = "recommendProductCategory/saveRecommendProductCategory")
    Result saveRecommendProductCategory(@RequestParam("categoryId") Integer categoryId, @RequestParam("categoryName") String categoryName);

    /**
     * 根据ID删除商品类别
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "recommendProductCategory/deleteRecommendProductCategory/{id}")
    Result deleteRecommendProductCategoryById(@PathVariable("id") Long id);

    /**
     * 商品类别列表
     *
     * @param listRecommendProductCategoryParam
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "recommendProductCategory/listRecommendProductCategory")
    Result<Page<RecommendProductCategoryDTO>> listRecommendProductCategory(@ModelAttribute ListRecommendProductCategoryParam listRecommendProductCategoryParam);

}
