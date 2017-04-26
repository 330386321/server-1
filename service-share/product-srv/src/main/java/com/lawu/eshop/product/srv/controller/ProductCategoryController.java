package com.lawu.eshop.product.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.product.dto.ProductCategoryDTO;
import com.lawu.eshop.product.srv.bo.ProductCategoryBO;
import com.lawu.eshop.product.srv.converter.ProductCategoryConverter;
import com.lawu.eshop.product.srv.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Yangqh
 * @date 2017/3/13
 */
@RestController
@RequestMapping(value = "productCategory/")
public class ProductCategoryController extends BaseController {

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 查询所有商品分类
     *
     * @return
     */
    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    public List<ProductCategoryDTO> findAll() {
        List<ProductCategoryBO> productCategoryBOS = productCategoryService.findAll();
        return productCategoryBOS.isEmpty() ? null : ProductCategoryConverter.convertDTOS(productCategoryBOS);
    }

    /**
     * 根据ID查询商品分类
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public ProductCategoryDTO getById(@RequestParam Integer id) {
        ProductCategoryBO productCategoryBO = productCategoryService.getById(id);
        return productCategoryBO == null ? null : ProductCategoryConverter.convertDTO(productCategoryBO);
    }

    /**
     * 查询推荐商品类别(一级)
     *
     * @return
     */
    @RequestMapping(value = "listRecommendProductCategory", method = RequestMethod.GET)
    public Result<List<ProductCategoryDTO>> listRecommendProductCategory() {
        List<ProductCategoryBO> productCategoryBOS = productCategoryService.listRecommendProductCategory();
        if (productCategoryBOS == null || productCategoryBOS.isEmpty()) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        return successGet(ProductCategoryConverter.convertDTOS(productCategoryBOS));
    }
    
    /**
     * 根据parent_id查询
     *
     * @return
     */
    @RequestMapping(value = "find/{parentId}", method = RequestMethod.GET)
    public Result<List<ProductCategoryDTO>> find(@PathVariable Long parentId) {
        List<ProductCategoryBO> productCategoryBOS = productCategoryService.find(parentId);
        return successGet(ProductCategoryConverter.convertDTOS(productCategoryBOS));
    }
}
