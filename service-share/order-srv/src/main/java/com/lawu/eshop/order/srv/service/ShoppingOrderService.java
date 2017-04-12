package com.lawu.eshop.order.srv.service;

import java.util.List;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.param.ShoppingOrderLogisticsInformationParam;
import com.lawu.eshop.mall.param.ShoppingOrderSettlementParam;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderQueryForeignParam;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderRequestRefundForeignParam;
import com.lawu.eshop.order.srv.bo.CommentOrderBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderExpressBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderExtendDetailBO;
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
	 * @param params
	 *            多个订单参数
	 * @return 返回保存的订单id
	 */
	List<Long> save(List<ShoppingOrderSettlementParam> params);

	CommentOrderBO getOrderCommentStatusById(Long orderId);

	/**
	 * 
	 * @param memberId
	 *            会员id
	 * @param param
	 *            查询参数
	 * @return 订单列表
	 */
	Page<ShoppingOrderExtendQueryBO> selectPageByMemberId(Long memberId, ShoppingOrderQueryForeignParam param);

	/**
	 * 根据id获取购物订单以及订单项
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	ShoppingOrderExtendDetailBO get(Long id);

	/**
	 * 根据id获取购物订单
	 * 
	 * @param id
	 * @return
	 */
	ShoppingOrderBO getShoppingOrder(Long id);

	/**
	 * 根据id获取购物订单物流信息
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	ShoppingOrderExpressBO getExpressInfo(Long id);

	/**
	 * 取消购物订单
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	Integer cancelOrder(Long id);

	/**
	 * 删除购物订单
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	Integer deleteOrder(Long id);

	/**
	 * 支付成功之后 修改购物订单以及订单项状态为待发货
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	Integer paymentSuccessful(Long id);

	/**
	 * 确认收货之后 修改购物订单以及订单项状态为交易成功
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	Integer tradingSuccess(Long id);

	/**
	 * 买家申请退款 修改订单状态为待商家确认
	 * 
	 * @param shoppingOrderitemId
	 *            购物订单项id
	 * @param shoppingOrderBO
	 *            购物订单
	 * @param param
	 *            退款参数
	 * @return
	 */
	Integer requestRefund(Long shoppingOrderitemId, ShoppingOrderBO shoppingOrderBO, ShoppingOrderRequestRefundForeignParam param);

	/**
	 * 商家填写物流信息
	 * 
	 * @param id
	 *            购物订单id
	 * @param param
	 *            物流信息参数
	 * @return
	 */
	Integer fillLogisticsInformation(Long id, ShoppingOrderLogisticsInformationParam param);

}
