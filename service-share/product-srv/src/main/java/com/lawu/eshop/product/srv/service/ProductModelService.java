package com.lawu.eshop.product.srv.service;


import java.util.List;

import com.lawu.eshop.product.srv.bo.ShoppingCartProductModelBO;
import com.lawu.eshop.product.srv.bo.transaction.ProductModeUpdateInventoryBO;
import com.lawu.eshop.product.srv.bo.transaction.ShoppingCartCreateOrderNotification;

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
	 * 订单模块发送消息更新商品库存，并且保存商品库存流水记录
	 * 
	 * @param shoppingCartCreateOrderNotification 发送的数据
	 * @author Sunny
	 */
	void updateInventory(ShoppingCartCreateOrderNotification shoppingCartCreateOrderNotification);

}
