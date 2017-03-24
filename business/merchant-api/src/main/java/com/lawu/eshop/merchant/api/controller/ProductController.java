package com.lawu.eshop.merchant.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.merchant.api.service.ProductService;
import com.lawu.eshop.product.dto.ProductDTO;
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

    @ApiOperation(value = "分页查询商品", notes = "分页查询商品。(杨清华)", httpMethod = "POST")
    @RequestMapping(value = "selectProduct", method = RequestMethod.POST)
    public Result selectProduct(@RequestBody @ApiParam ProductQuery query) {
    	
        List<ProductDTO> dtos = productService.selectProduct(query);
        if(dtos == null || dtos.isEmpty()){
        	return successAccepted(new ArrayList<ProductDTO>());
        }
        return successAccepted(dtos);
    }
    

}
