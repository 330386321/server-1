package com.lawu.eshop.product.srv.controller;

import com.lawu.eshop.product.srv.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leach
 * @date 2017/3/13
 */
@RestController
@RequestMapping(value = "product/")
public class ProductController {

    @Autowired
    private ProductService productService;

    /*@RequestMapping(value = "findByCondition", method = RequestMethod.GET)
    public List<ProductDTO> findByCondition() {
        List<ProductBO> productBOS = productService.findByCondition();
        return productCategoryBOS.isEmpty() ? null : ProductCategoryConverter.convertDTOS(productCategoryBOS);
    }*/
}
