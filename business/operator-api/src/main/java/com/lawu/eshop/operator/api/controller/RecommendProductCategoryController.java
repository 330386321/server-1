package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.operator.api.service.RecommendProductCategoryService;
import com.lawu.eshop.product.dto.ProductCategoryDTO;
import com.lawu.eshop.product.dto.RecommendProductCategoryDTO;
import com.lawu.eshop.product.param.EditRecommendProductCategoryParam;
import com.lawu.eshop.product.param.ListRecommendProductCategoryParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
    @RequiresPermissions("product-recommend:add")
    @RequestMapping(value = "saveRecommendProductCategory", method = RequestMethod.POST)
    public Result saveRecommendProductCategory(@RequestBody EditRecommendProductCategoryParam param) {
        return recommendProductCategoryService.saveRecommendProductCategory(param);
    }

    /*@ApiOperation(value = "删除推荐商品类别", notes = "根据ID推荐商品类别。[1002]（梅述全）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequestMapping(value = "deleteRecommendProductCategory/{id}", method = RequestMethod.DELETE)
    public Result deleteRecommendProductCategory(@PathVariable @ApiParam(name = "id", required = true, value = "ID") Long id) {
        return recommendProductCategoryService.deleteRecommendProductCategoryById(id);
    }*/

    @ApiOperation(value = "修改推荐商品类别", notes = "修改推荐商品类别。[1002]（梅述全）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("product-recommend:edit")
    @RequestMapping(value = "updateRecommendProductCategory/{id}", method = RequestMethod.POST)
    public Result updateRecommendProductCategory(@PathVariable @ApiParam(name = "id", required = true, value = "ID") Long id,
                                                 @RequestBody EditRecommendProductCategoryParam param) {
        return recommendProductCategoryService.updateRecommendProductCategoryById(id, param);
    }

    @ApiOperation(value = "根据ID查询推荐商品类别", notes = "根据ID查询推荐商品类别。[1002]（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequiresPermissions("product-recommend:detail")
    @RequestMapping(value = "getRecommendProductCategory/{id}", method = RequestMethod.GET)
    public Result<RecommendProductCategoryDTO> getRecommendProductCategory(@PathVariable @ApiParam(name = "id", required = true, value = "ID") Long id) {
        return recommendProductCategoryService.getRecommendProductCategoryById(id);
    }

    @ApiOperation(value = "推荐商品类别列表", notes = "推荐商品类别列表。（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequiresPermissions("product-recommend:list")
    @PageBody
    @RequestMapping(value = "listRecommendProductCategory", method = RequestMethod.GET)
    public Result<Page<RecommendProductCategoryDTO>> listRecommendProductCategory(@RequestBody @ApiParam ListRecommendProductCategoryParam listRecommendProductCategoryParam) {
        return recommendProductCategoryService.listRecommendProductCategory(listRecommendProductCategoryParam);
    }

    @ApiOperation(value = "查询商品一级类别", notes = "商品一级类别列表。[1002]（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequiresPermissions("product-category-level1:list")
    @RequestMapping(value = "listLevelOneProductCategory", method = RequestMethod.GET)
    public Result<List<ProductCategoryDTO>> listLevelOneProductCategory() {
        return recommendProductCategoryService.listRecommendProductCategory();
    }
}
