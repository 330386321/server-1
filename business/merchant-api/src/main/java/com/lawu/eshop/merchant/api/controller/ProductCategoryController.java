package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.merchant.api.service.ProductCategoryService;
import com.lawu.eshop.product.dto.ProductCategoryDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Leach
 * @date 2017/3/11
 */
@Api(tags = "common")
@RestController
@RequestMapping(value = "/productCategory/")
public class ProductCategoryController extends BaseController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @ApiOperation(value = "查询商品分类", notes = "查询商品分类", httpMethod = "POST")
    @RequestMapping(value = "findAll", method = RequestMethod.POST)
    public List<ProductCategoryDTO> findAll() {

        List<ProductCategoryDTO> dtos = productCategoryService.findAll();
        return dtos;
    }

}
