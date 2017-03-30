package com.lawu.eshop.member.api.controller;
	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.member.api.service.MerchantStoreService;
import com.lawu.eshop.member.api.service.ProductService;
import com.lawu.eshop.product.dto.ProductInfoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * <p>Description:商品 </p>
 * @author Yangqh
 * @date 2017年3月27日 下午2:40:23
 *
 */
@Api(tags = "product")
@RestController
@RequestMapping(value = "product/")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;
    @Autowired
    private MerchantStoreService merchantStoreService;

    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "查询商品详情", notes = "根据商品ID查询商品详情信息，[1002|1003]，（杨清华）", httpMethod = "GET")
    @Authorization
    @RequestMapping(value = "{productId}", method = RequestMethod.GET)
    public Result<ProductInfoDTO> selectProductById(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
    											    @PathVariable @ApiParam(name = "productId", required = true, value = "商品ID") Long productId) {
    	
    	Result<ProductInfoDTO> result = productService.selectProductById(productId);
    	if(result.getRet() != ResultCode.SUCCESS){
    		return result;
    	}
    	
    	Result rb = merchantStoreService.findIsNoReasonReturnById(result.getModel().getMerchantId());
    	if(rb.getRet() != ResultCode.SUCCESS){
    		return successCreated(rb.getRet());
    	}
    	result.getModel().setSupportEleven((boolean) rb.getModel());
    	
    	//TODO 商品评价额外调用服务
    	
        return result;
    }

}
