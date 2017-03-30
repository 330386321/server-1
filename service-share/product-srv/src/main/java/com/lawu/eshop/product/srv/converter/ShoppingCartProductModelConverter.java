package com.lawu.eshop.product.srv.converter;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.product.dto.ShoppingCartProductModelDTO;
import com.lawu.eshop.product.srv.bo.ShoppingCartProductModelBO;
import com.lawu.eshop.product.srv.domain.ProductDO;
import com.lawu.eshop.product.srv.domain.ProductModelDO;

/**
 * 购物车产品型号转换器
 * 
 * @author Sunny
 * @date 2017/3/30
 */
public class ShoppingCartProductModelConverter {

	/**
	 * BO转换
	 * 
	 * @param productModelDO
	 * @param productDO
	 * @return
	 */
	public static ShoppingCartProductModelBO convert(ProductModelDO productModelDO, ProductDO productDO) {
		if (productModelDO == null) {
			return null;
		}

		ShoppingCartProductModelBO shoppingCartProductModelBO = new ShoppingCartProductModelBO();
		BeanUtils.copyProperties(productModelDO, shoppingCartProductModelBO);

		if (productDO != null) {
			shoppingCartProductModelBO.setProductName(productDO.getName());
		}

		return shoppingCartProductModelBO;
	}

	/**
	 * BO转换
	 * 
	 * @param productModelDO
	 * @param productDO
	 * @return
	 */
	public static ShoppingCartProductModelDTO convert(ShoppingCartProductModelBO shoppingCartProductModelBO) {
		if (shoppingCartProductModelBO == null) {
			return null;
		}

		ShoppingCartProductModelDTO shoppingCartProductModelDTO = new ShoppingCartProductModelDTO();
		BeanUtils.copyProperties(shoppingCartProductModelBO, shoppingCartProductModelDTO);

		return shoppingCartProductModelDTO;
	}

}
