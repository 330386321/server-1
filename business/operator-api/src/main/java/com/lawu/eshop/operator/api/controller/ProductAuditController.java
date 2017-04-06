package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.operator.api.service.ProductAuditService;
import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.dto.ProductEditInfoDTO;
import com.lawu.eshop.product.dto.ProductQueryDTO;
import com.lawu.eshop.product.query.ProductDataQuery;
import com.lawu.eshop.product.query.ProductQuery;
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
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "listProduct", method = RequestMethod.POST)
    public Result<Page<ProductQueryDTO>> listProduct(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                       @ModelAttribute @ApiParam ProductQuery query) {
        ProductDataQuery queryData = new ProductDataQuery();
        queryData.setStatus(ProductStatusEnum.PRODUCT_STATUS_UP);
        queryData.setName(query.getName());
        return productAuditService.listProduct(queryData);
    }

    @ApiOperation(value = "商品批量处理", notes = "商品批量处理，[1002]。(梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "updateProductStatus", method = RequestMethod.PUT)
    public Result updateProductStatus(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                      @RequestParam @ApiParam(required = true, value = "商品ID(多个英文逗号分开)") String ids,
                                      ProductStatusEnum productStatus) {
        return productAuditService.updateProductStatus(ids,productStatus);
    }

    @ApiOperation(value = "查询商品详情", notes = "编辑商品时，根据商品ID查询商品详情信息，[1002|1003]，（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "selectEditProductById", method = RequestMethod.GET)
    public Result<ProductEditInfoDTO> selectEditProductById(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                            @RequestParam @ApiParam(name = "productId", required = true, value = "商品ID") Long productId) {
        return productAuditService.selectEditProductById(productId);
    }

}
