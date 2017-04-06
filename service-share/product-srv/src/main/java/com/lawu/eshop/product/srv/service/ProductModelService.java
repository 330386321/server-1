package com.lawu.eshop.product.srv.service;


import java.util.List;

import com.lawu.eshop.product.param.ProductModeUpdateInventoryParam;
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
	
	/**
	 * 批量更新商品库存
	 * 
	 * @param params 商品模型更新库存参数
	 * @return 受影响的行数
	 */
	public List<Integer> updateInventory(List<ProductModeUpdateInventoryParam> params);

}
