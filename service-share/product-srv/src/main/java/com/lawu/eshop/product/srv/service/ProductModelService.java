package com.lawu.eshop.product.srv.service;


import java.util.List;

import com.lawu.eshop.mq.dto.order.ShoppingOrderCancelOrderNotification;
import com.lawu.eshop.mq.dto.order.ShoppingOrderCreateOrderNotification;
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
	 * 创建购物订单
	 * 订单模块发送消息减商品库存，并且保存商品库存流水记录
	 * 
	 * @param shoppingCartCreateOrderNotification 发送的数据
	 * @author Sunny
	 */
	void lessInventory(ShoppingOrderCreateOrderNotification shoppingOrderCreateOrderNotification);
	
	/**
	 * 取消购物订单
	 * 订单模块发送消息释放商品库存，并且保存商品库存流水记录
	 * 
	 * @param shoppingOrderCancelOrderNotification 发送的数据
	 * @author Sunny
	 */
	void releaseInventory(ShoppingOrderCancelOrderNotification shoppingOrderCancelOrderNotification);

}
