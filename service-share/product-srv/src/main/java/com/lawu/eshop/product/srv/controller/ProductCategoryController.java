package com.lawu.eshop.product.srv.controller;

import com.lawu.eshop.product.dto.ProductCategoryDTO;
import com.lawu.eshop.product.srv.bo.ProductCategoryBO;
import com.lawu.eshop.product.srv.converter.ProductCategoryConverter;
import com.lawu.eshop.product.srv.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leach
 * @date 2017/3/13
 */
@RestController
@RequestMapping(value = "productCategory/")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    public List<ProductCategoryDTO> findAll() {
        List<ProductCategoryBO> productCategoryBOS = productCategoryService.findAll();
        System.out.println("---------------------->"+productCategoryBOS.size()+"->"+productCategoryBOS.get(0).getName());
        return productCategoryBOS.isEmpty() ? null : ProductCategoryConverter.convertDTOS(productCategoryBOS);
    }
}
