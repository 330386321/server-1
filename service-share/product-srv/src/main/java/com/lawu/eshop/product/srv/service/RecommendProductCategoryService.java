package com.lawu.eshop.product.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.param.ListRecommendProductCategoryParam;
import com.lawu.eshop.product.srv.bo.RecommendProductCategoryBO;

/**
 * @author meishuquan
 * @date 2017/4/10.
 */
public interface RecommendProductCategoryService {

    /**
     * 保存商品类别
     *
     * @param categoryId
     * @param categoryName
     */
    void saveRecommendProductCategory(Integer categoryId, String categoryName);

    /**
     * 根据ID删除商品类别
     *
     * @param id
     */
    void deleteRecommendProductCategoryById(Long id);

    /**
     * 根据ID查询商品类别
     *
     * @param id
     * @return
     */
    RecommendProductCategoryBO getRecommendProductCategoryById(Long id);

    /**
     * 商品类别列表
     *
     * @param listRecommendProductCategoryParam
     * @return
     */
    Page<RecommendProductCategoryBO> listRecommendProductCategory(ListRecommendProductCategoryParam listRecommendProductCategoryParam);
}