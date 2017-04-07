package com.lawu.eshop.order.srv.service;

import java.util.List;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.param.ShoppingOrderSettlementParam;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderQueryForeignParam;
import com.lawu.eshop.order.srv.bo.CommentOrderBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderExtendQueryBO;

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
	
	/**
	 * 
	 * @param memberId 会员id
	 * @param param 查询参数
	 * @return 订单列表
	 */
	Page<ShoppingOrderExtendQueryBO> selectPageByMemberId(Long memberId, ShoppingOrderQueryForeignParam param);
	
}
