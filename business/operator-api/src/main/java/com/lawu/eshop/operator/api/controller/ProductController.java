package com.lawu.eshop.operator.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.operator.api.service.ProductService;
import com.lawu.eshop.product.dto.ProductPlatDTO;
import com.lawu.eshop.product.param.ProductParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * @author zhangrc
 * @date 2017/4/21.
 */
@Api(tags = "product")
@RestController
@RequestMapping(value = "product/")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "查询所有商家的商品", notes = "查询所有商家的商品  [](张荣成)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "selectProductByPlat", method = RequestMethod.GET)
    public Result<List<ProductPlatDTO>> selectProductByPlat(@ModelAttribute @ApiParam ProductParam param) {
    	return productService.selectProductByPlat(param);
    }


}
