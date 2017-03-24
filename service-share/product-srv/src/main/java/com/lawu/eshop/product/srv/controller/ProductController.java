package com.lawu.eshop.product.srv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.product.dto.ProductDTO;
import com.lawu.eshop.product.query.ProductQuery;
import com.lawu.eshop.product.srv.bo.ProductBO;
import com.lawu.eshop.product.srv.converter.ProductConverter;
import com.lawu.eshop.product.srv.service.ProductService;

/**
 * @author Leach
 * @date 2017/3/13
 */
@RestController
@RequestMapping(value = "product/")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 查询商品列表
     * @param query
     * @return
     */
    @RequestMapping(value = "getProductList", method = RequestMethod.POST)
    public List<ProductDTO> getProductList(@ModelAttribute @RequestBody ProductQuery query) {
        List<ProductBO> productBOS = productService.getProductList(query);
        return productBOS.isEmpty() ? null : ProductConverter.convertDTOS(productBOS);
    }
}
