package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.operator.api.service.ProductAuditService;
import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.dto.ProductEditInfoDTO;
import com.lawu.eshop.product.dto.ProductQueryDTO;
import com.lawu.eshop.product.param.ListProductParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author meishuquan
 * @date 2017/4/6.
 */
@Api(tags = "productAudit")
@RestController
@RequestMapping(value = "productAudit/")
public class ProductAuditController extends BaseController {

    @Autowired
    private ProductAuditService productAuditService;

    @ApiOperation(value = "商品审核", notes = "查询所有门店上架中商品  [1002]（梅述全）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    //@RequiresPermissions("product:list")
    @PageBody
    @RequestMapping(value = "listProduct", method = RequestMethod.POST)
    public Result<Page<ProductQueryDTO>> listProduct(@RequestBody @ApiParam ListProductParam listProductParam) {
        return productAuditService.listProduct(listProductParam);
    }

    @ApiOperation(value = "查询商品详情", notes = "编辑商品时，根据商品ID查询商品详情信息，[1002|1003]，（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    //@RequiresPermissions("product:detail")
    @RequestMapping(value = "getProduct/{id}", method = RequestMethod.GET)
    public Result<ProductEditInfoDTO> getProductById(@PathVariable @ApiParam(name = "id", required = true, value = "商品ID") Long id) {
        return productAuditService.selectEditProductById(id);
    }

    @ApiOperation(value = "商品批量下架", notes = "商品批量下架，[1002]。(梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    //@RequiresPermissions("product:down")
    @RequestMapping(value = "downProduct", method = RequestMethod.PUT)
    public Result downProduct(@RequestParam @ApiParam(required = true, value = "商品ID(多个英文逗号分开)") String ids) {
        return productAuditService.updateProductStatus(ids, ProductStatusEnum.PRODUCT_STATUS_DOWN);
    }

    @ApiOperation(value = "商品批量删除", notes = "商品批量删除，[1002]。(梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    //@RequiresPermissions("product:del")
    @RequestMapping(value = "deleteProduct", method = RequestMethod.PUT)
    public Result deleteProduct(@RequestParam @ApiParam(required = true, value = "商品ID(多个英文逗号分开)") String ids) {
        return productAuditService.updateProductStatus(ids, ProductStatusEnum.PRODUCT_STATUS_DEL);
    }

}
