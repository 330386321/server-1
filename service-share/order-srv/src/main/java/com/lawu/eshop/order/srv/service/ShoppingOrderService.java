package com.lawu.eshop.order.srv.service;

import com.lawu.eshop.mall.param.ShoppingOrderSettlementParam;
import com.lawu.eshop.order.srv.bo.CommentOrderBO;

import java.util.List;

/**
 * 购物订单服务接口
 *
 * @author Sunny
 * @date 2017/3/27
 */
public interface ShoppingOrderService {
	
	/**
	 * 
	 * @param params 多个订单参数
	 * @return 返回保存的订单id
	 */
	List<Long> save(List<ShoppingOrderSettlementParam> params);

    CommentOrderBO getOrderCommentStatusById(Long orderId);
}
