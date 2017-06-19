package com.lawu.eshop.product.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.product.dto.RecommendProductCategoryDTO;
import com.lawu.eshop.product.param.EditRecommendProductCategoryParam;
import com.lawu.eshop.product.param.ListRecommendProductCategoryParam;
import com.lawu.eshop.product.srv.bo.RecommendProductCategoryBO;
import com.lawu.eshop.product.srv.converter.RecommendProductCategoryConverter;
import com.lawu.eshop.product.srv.service.RecommendProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @param param
     * @return
     */
    @RequestMapping(value = "saveRecommendProductCategory", method = RequestMethod.POST)
    public Result saveRecommendProductCategory(@RequestBody EditRecommendProductCategoryParam param) {
        recommendProductCategoryService.saveRecommendProductCategory(param);
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
     * 根据ID修改商品类别
     *
     * @param id
     * @param param
     * @return
     */
    @RequestMapping(value = "updateRecommendProductCategory/{id}", method = RequestMethod.PUT)
    public Result updateRecommendProductCategory(@PathVariable Long id, @RequestBody EditRecommendProductCategoryParam param) {
        RecommendProductCategoryBO recommendProductCategoryBO = recommendProductCategoryService.getRecommendProductCategoryById(id);
        if (recommendProductCategoryBO == null) {
            return successCreated(ResultCode.RESOURCE_NOT_FOUND);
        }
        recommendProductCategoryService.updateRecommendProductCategoryById(id, param);
        return successCreated();
    }

    /**
     * 根据ID查询商品类别
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "getRecommendProductCategory/{id}", method = RequestMethod.GET)
    public Result<RecommendProductCategoryDTO> getRecommendProductCategory(@PathVariable Long id) {
        RecommendProductCategoryBO recommendProductCategoryBO = recommendProductCategoryService.getRecommendProductCategoryById(id);
        return successGet(RecommendProductCategoryConverter.convertDTO(recommendProductCategoryBO));
    }

    /**
     * 商品类别列表
     *
     * @param listRecommendProductCategoryParam
     * @return
     */
    @RequestMapping(value = "listRecommendProductCategory", method = RequestMethod.POST)
    public Result<Page<RecommendProductCategoryDTO>> listRecommendProductCategory(@RequestBody ListRecommendProductCategoryParam listRecommendProductCategoryParam) {
        Page<RecommendProductCategoryBO> recommendProductCategoryBOPage = recommendProductCategoryService.listRecommendProductCategory(listRecommendProductCategoryParam);
        Page<RecommendProductCategoryDTO> page = new Page<>();
        page.setRecords(RecommendProductCategoryConverter.convertDTO(recommendProductCategoryBOPage.getRecords()));
        page.setCurrentPage(recommendProductCategoryBOPage.getCurrentPage());
        page.setTotalCount(recommendProductCategoryBOPage.getTotalCount());
        return successGet(page);
    }

    /**
     * 商品类别列表
     *
     * @return
     */
    @RequestMapping(value = "listAllRecommendProductCategory", method = RequestMethod.GET)
    public Result<List<RecommendProductCategoryDTO>> listAllRecommendProductCategory() {
        List<RecommendProductCategoryBO> recommendProductCategoryBOS = recommendProductCategoryService.listAllRecommendProductCategory();
        return successGet(RecommendProductCategoryConverter.convertDTO(recommendProductCategoryBOS));
    }

}
