package com.lawu.eshop.product.srv.controller;

import com.lawu.eshop.product.dto.ProductCategoryDTO;
import com.lawu.eshop.product.srv.bo.ProductCategoryBO;
import com.lawu.eshop.product.srv.converter.ProductCategoryConverter;
import com.lawu.eshop.product.srv.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 查询所有商品分类
     * @return
     */
    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    public List<ProductCategoryDTO> findAll() {
        List<ProductCategoryBO> productCategoryBOS = productCategoryService.findAll();
        return productCategoryBOS.isEmpty() ? null : ProductCategoryConverter.convertDTOS(productCategoryBOS);
    }

    /**
     * 根据ID查询商品分类
     * @param id
     * @return
     */
    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public ProductCategoryDTO getById(@RequestParam Integer id) {
        ProductCategoryBO productCategoryBO = productCategoryService.getById(id);
        return productCategoryBO == null ? null : ProductCategoryConverter.convertDTO(productCategoryBO);
    }
}
