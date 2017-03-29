package com.lawu.eshop.member.api.controller;
	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
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

    // TODO 2016.03.29 商品评价另外提供接口，与商品详情分开。
    //@ApiOperation(value = "查询商品详情", notes = "根据商品ID查询商品详情信息，[1000|1002|1003]，（杨清华）", httpMethod = "GET")
    @RequestMapping(value = "selectProductById/{productId}", method = RequestMethod.GET)
    public Result<ProductInfoDTO> selectProductById(@RequestParam @ApiParam(name = "productId", required = true, value = "商品ID") Long productId) {
    	
    	Result<ProductInfoDTO> result = productService.selectProductById(productId);
    	
    	//TODO 查询商家是否支持7天无理由退货
    	result.getModel().setIsSupportEleven("0");
    	
    	//TODO 查询商品评价信息
    	result.getModel().setEvaluateJson("[]");
    	
        return result;
    }

}
