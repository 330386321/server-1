package com.lawu.eshop.product.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.product.dto.RecommendProductCategoryDTO;
import com.lawu.eshop.product.param.ListRecommendProductCategoryParam;
import com.lawu.eshop.product.srv.bo.RecommendProductCategoryBO;
import com.lawu.eshop.product.srv.converter.RecommendProductCategoryConverter;
import com.lawu.eshop.product.srv.service.RecommendProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author meishuquan
 * @date 2017/4/10.
 */
@RestController
@RequestMapping(value = "recommendProductCategory/")
public class RecommendProductCategoryController extends BaseController {

    @Autowired
    private RecommendProductCategoryService recommendProductCategoryService;

    /**
     * 保存商品类别
     *
     * @param categoryId
     * @param categoryName
     * @return
     */
    @RequestMapping(value = "saveRecommendProductCategory", method = RequestMethod.POST)
    public Result saveRecommendProductCategory(@RequestParam Integer categoryId, @RequestParam String categoryName) {
        recommendProductCategoryService.saveRecommendProductCategory(categoryId, categoryName);
        return successCreated();
    }

    /**
     * 根据ID删除商品类别
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteRecommendProductCategory/{id}", method = RequestMethod.DELETE)
    public Result deleteRecommendProductCategory(@PathVariable Long id) {
        RecommendProductCategoryBO recommendProductCategoryBO = recommendProductCategoryService.getRecommendProductCategoryById(id);
        if (recommendProductCategoryBO == null) {
            return successCreated(ResultCode.RESOURCE_NOT_FOUND);
        }
        recommendProductCategoryService.deleteRecommendProductCategoryById(id);
        return successDelete();
    }

    /**
     * 商品类别列表
     *
     * @param listRecommendProductCategoryParam
     * @return
     */
    @RequestMapping(value = "listRecommendProductCategory", method = RequestMethod.GET)
    public Result<Page<RecommendProductCategoryDTO>> listRecommendProductCategory(@RequestBody ListRecommendProductCategoryParam listRecommendProductCategoryParam) {
        Page<RecommendProductCategoryBO> recommendProductCategoryBOPage = recommendProductCategoryService.listRecommendProductCategory(listRecommendProductCategoryParam);
        Page<RecommendProductCategoryDTO> page = new Page<>();
        page.setRecords(RecommendProductCategoryConverter.convertDTO(recommendProductCategoryBOPage.getRecords()));
        page.setCurrentPage(recommendProductCategoryBOPage.getCurrentPage());
        page.setTotalCount(recommendProductCategoryBOPage.getTotalCount());
        return successGet(page);
    }
}
