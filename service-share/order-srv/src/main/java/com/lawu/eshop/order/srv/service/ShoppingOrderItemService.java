package com.lawu.eshop.order.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.param.foreign.ShoppingRefundQueryForeignParam;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemRefundBO;

/**
 * 购物订单项服务接口
 *
 * @author Sunny
 * @date 2017/4/11
 */
public interface ShoppingOrderItemService {

	/**
	 * 根据购物订单id获取购物订单项
	 * 
	 * @param id 购物订单项id
	 * @return
	 */
	ShoppingOrderItemBO get (Long id);
	
	/**
	 * 查询处于退款中的订单项
	 * 
	 * @param memberId
	 *            会员id
	 * @param param
	 *            查询参数
	 * @return 订单列表
	 */
	Page<ShoppingOrderItemRefundBO> selectRefundPageByMemberId(Long memberId, ShoppingRefundQueryForeignParam param);
}
