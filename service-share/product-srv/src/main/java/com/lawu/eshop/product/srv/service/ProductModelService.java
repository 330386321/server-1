package com.lawu.eshop.product.srv.service;


import java.util.List;

import com.lawu.eshop.product.srv.bo.ShoppingCartProductModelBO;

/**
 * 
 * @author Sunny
 * @date 2017/3/30
 */
public interface ProductModelService {

	/**
	 * 根据产品型号ID查询
	 * 
	 * @param query
	 * @return
	 */
	ShoppingCartProductModelBO getShoppingCartProductModel(Long id);
	
	/**
	 * 根据产品型号ID列表查询
	 * 
	 * @param ids 产品型号ID列表
	 * @return
	 */
	List<ShoppingCartProductModelBO> getShoppingCartProductModel(List<Long> ids);

}
