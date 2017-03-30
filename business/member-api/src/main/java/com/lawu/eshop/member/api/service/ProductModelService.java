package com.lawu.eshop.member.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.product.dto.ShoppingCartProductModelDTO;

/**
 * 商品型号接口
 * 
 * @author Sunny
 * @date 2017/3/30
 */
@FeignClient(value = "product-srv")
public interface ProductModelService {

	/**
	 * 根据商品型号ID查询购物车商品信息
	 * 
	 * @param id 商品型号ID
	 * @return
	 */
    @RequestMapping(method = RequestMethod.GET, value = "productModel/shoppingCart/{id}")
    Result<ShoppingCartProductModelDTO> getShoppingCartProductModel(@PathVariable("id") Long id);

}
