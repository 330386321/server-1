package com.lawu.eshop.merchant.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.merchant.api.service.ProductService;
import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.dto.ProductEditInfoDTO;
import com.lawu.eshop.product.dto.ProductQueryDTO;
import com.lawu.eshop.product.query.ProductDataQuery;
import com.lawu.eshop.product.query.ProductQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Yangqh
 * @date 2017/3/11
 */
@Api(tags = "product")
@RestController
@RequestMapping(value = "product/")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "分页查询商品", notes = "分页查询商品，[201|400]。(杨清华)", httpMethod = "POST")
    @Authorization
    @RequestMapping(value = "selectProduct/{merchantId}", method = RequestMethod.POST)
    public Result selectProduct(@PathVariable @ApiParam(required = true, value = "merchantId") Long merchantId,
    							ProductStatusEnum productStatus,
    					        @ModelAttribute @ApiParam ProductQuery query) {
    	ProductDataQuery queryData = new ProductDataQuery();
    	queryData.setStatus(productStatus);
    	queryData.setMerchantId(merchantId);
    	queryData.setName(query.getName());
    	Result<Page<ProductQueryDTO>> page = productService.selectProduct(queryData);
        return successGet(page);
    }
    
    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "商品批量处理", notes = "商品批量处理，[1000|1002]。(杨清华)", httpMethod = "POST")
    @Authorization
    @RequestMapping(value = "updateProductStatus", method = RequestMethod.POST)
    public Result updateProductStatus(@RequestParam @ApiParam(required = true, value = "商家ID(多个英文逗号分开)") String ids,
    								  ProductStatusEnum productStatus) {
    	
    	return productService.updateProductStatus(ids,productStatus);
    }
    
    @ApiOperation(value = "查询商品详情", notes = "编辑商品时，根据商品ID查询商品详情信息，[1000|1002|1003]，（杨清华）", httpMethod = "GET")
    @Authorization
    @RequestMapping(value = "selectEditProductById", method = RequestMethod.GET)
    public Result<ProductEditInfoDTO> selectEditProductById(@RequestParam @ApiParam(name = "productId", required = true, value = "商品ID") Long productId) {
    	
    	Result<ProductEditInfoDTO> result = productService.selectEditProductById(productId);
    	
        return result;
    }
}
