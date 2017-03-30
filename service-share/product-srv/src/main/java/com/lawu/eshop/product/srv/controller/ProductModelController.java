package com.lawu.eshop.product.srv.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.product.dto.ShoppingCartProductModelDTO;
import com.lawu.eshop.product.srv.bo.ShoppingCartProductModelBO;
import com.lawu.eshop.product.srv.converter.ShoppingCartProductModelConverter;
import com.lawu.eshop.product.srv.service.ProductModelService;

/**
 * @author Yangqh
 * @date 2017/3/13
 */
/**
 * 
 * @author Sunny
 * @date 2017/3/30
 */
@RestController
@RequestMapping(value = "productModel/")
public class ProductModelController extends BaseController{

    @Autowired
    private ProductModelService productModelService;

    /**
     * 根据商品型号ID查询购物车商品信息
     * 
     * @param query
     * @return
     */
    @RequestMapping(value = "shoppingCart/{id}", method = RequestMethod.GET)
    public Result<ShoppingCartProductModelDTO> getShoppingCartProductModel(@PathVariable("id") Long id) {
    	
    	ShoppingCartProductModelBO shoppingCartProductModelBO = productModelService.getShoppingCartProductModel(id);
    	
    	if (shoppingCartProductModelBO == null) {
    		successGet(ResultCode.RESOURCE_NOT_FOUND);
    	}
    	
    	if (StringUtils.isEmpty(shoppingCartProductModelBO.getProductName())) {
    		successGet(ResultCode.GOODS_DO_NOT_EXIST);
    	}
    	
    	return successGet(ShoppingCartProductModelConverter.convert(shoppingCartProductModelBO));
    }
    
}
