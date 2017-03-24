package com.lawu.eshop.product.srv.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.product.query.ProductQuery;
import com.lawu.eshop.product.srv.bo.ProductBO;
import com.lawu.eshop.product.srv.service.ProductService;

/**
 * @author Yangqh
 * @date 2017/3/13
 */
@RestController
@RequestMapping(value = "product/")
public class ProductController extends BaseController{

    @Autowired
    private ProductService productService;

    /**
     * 查询商品列表
     * @param query
     * @return
     */
    @RequestMapping(value = "selectProduct", method = RequestMethod.POST)
    public Result<Page<ProductBO>> selectProduct(@RequestBody ProductQuery query) {
    	Page<ProductBO> page = productService.selectProduct(query);
    	List<ProductBO> list = page.getRecords();
    	return successAccepted(page);
    }
}
