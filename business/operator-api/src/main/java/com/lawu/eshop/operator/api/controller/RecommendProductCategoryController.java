package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.operator.api.service.RecommendProductCategoryService;
import com.lawu.eshop.product.dto.ProductCategoryDTO;
import com.lawu.eshop.product.dto.RecommendProductCategoryDTO;
import com.lawu.eshop.product.param.ListRecommendProductCategoryParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/10.
 */
@Api(tags = "recommendProductCategory")
@RestController
@RequestMapping(value = "recommendProductCategory/")
public class RecommendProductCategoryController extends BaseController {

    @Autowired
    private RecommendProductCategoryService recommendProductCategoryService;

    @ApiOperation(value = "新增推荐商品类别", notes = "新增推荐商品类别。（梅述全）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "saveRecommendProductCategory", method = RequestMethod.POST)
    public Result saveRecommendProductCategory(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                               @RequestParam @ApiParam(name = "categoryId", required = true, value = "分类ID") Integer categoryId,
                                               @RequestParam @ApiParam(name = "categoryName", required = true, value = "分类ID") String categoryName) {
        return recommendProductCategoryService.saveRecommendProductCategory(categoryId, categoryName);
    }

    @ApiOperation(value = "删除推荐商品类别", notes = "根据ID推荐商品类别。[1002]（梅述全）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @Authorization
    @RequestMapping(value = "deleteRecommendProductCategory/{id}", method = RequestMethod.DELETE)
    public Result deleteRecommendProductCategory(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                 @PathVariable @ApiParam(name = "id", required = true, value = "ID") Long id) {
        return recommendProductCategoryService.deleteRecommendProductCategoryById(id);
    }

    @ApiOperation(value = "推荐商品类别列表", notes = "推荐商品类别列表。（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "listRecommendProductCategory", method = RequestMethod.GET)
    public Result<Page<RecommendProductCategoryDTO>> listRecommendProductCategory(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                                                  @ModelAttribute @ApiParam(required = true, value = "查询条件") ListRecommendProductCategoryParam listRecommendProductCategoryParam) {
        return recommendProductCategoryService.listRecommendProductCategory(listRecommendProductCategoryParam);
    }

    @ApiOperation(value = "查询商品一级类别", notes = "商品一级类别列表。[1002]（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "listLevelOneProductCategory", method = RequestMethod.GET)
    public Result<List<ProductCategoryDTO>> listLevelOneProductCategory(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        return recommendProductCategoryService.listRecommendProductCategory();
    }
}
