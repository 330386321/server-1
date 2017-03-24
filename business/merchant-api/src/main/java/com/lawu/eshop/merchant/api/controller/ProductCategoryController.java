package com.lawu.eshop.merchant.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.merchant.api.service.ProductCategoryService;
import com.lawu.eshop.product.dto.ProductCategoryDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Leach
 * @date 2017/3/11
 */
@Api(tags = "productCategory")
@RestController
@RequestMapping(value = "productCategory/")
public class ProductCategoryController extends BaseController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @ApiOperation(value = "查询所有商品分类", notes = "查询所有商品分类", httpMethod = "GET")
    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    public List<ProductCategoryDTO> findAll() {

        List<ProductCategoryDTO> dtos = productCategoryService.findAll();
        return dtos;
    }
    
    @ApiOperation(value = "根据ID查询商品分类", notes = "根据ID查询商品分类", httpMethod = "GET")
    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public ProductCategoryDTO getById(@RequestParam @ApiParam(name = "id", required = true, value = "商品分类ID") Integer id) {

        ProductCategoryDTO dto = productCategoryService.getById(id);
        return dto;
    }

}
