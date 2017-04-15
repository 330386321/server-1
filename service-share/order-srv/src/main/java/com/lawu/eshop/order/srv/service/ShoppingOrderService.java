package com.lawu.eshop.order.srv.service;

import java.util.List;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.param.ShoppingOrderLogisticsInformationParam;
import com.lawu.eshop.mall.param.ShoppingOrderSettlementParam;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderQueryForeignToMemberParam;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderQueryForeignToMerchantParam;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderQueryForeignToOperatorParam;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderRequestRefundForeignParam;
import com.lawu.eshop.order.srv.bo.ShoppingOrderBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderExtendBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemBO;

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

	/**
	 * 根据会员id和查询参数分页查询订单列表
	 * 
	 * @param memberId
	 *            会员id
	 * @param param
	 *            查询参数
	 * @return 订单列表
	 */
	Page<ShoppingOrderExtendBO> selectPageByMemberId(Long memberId, ShoppingOrderQueryForeignToMemberParam param);

	/**
	 * 根据商家id和查询参数分页查询订单列表
	 * 
	 * @param merchantId
	 *            商家id
	 * @param param
	 *            查询参数
	 * @return
	 */
	Page<ShoppingOrderExtendBO> selectPageByMerchantId(Long merchantId, ShoppingOrderQueryForeignToMerchantParam param);

	/**
	 * 根据查询参数分页查询订单列表
	 * 
	 * @param param
	 *            查询参数
	 * @return
	 */
	Page<ShoppingOrderExtendBO> selectPage(ShoppingOrderQueryForeignToOperatorParam param);

	/**
	 * 根据id获取购物订单以及订单项
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	ShoppingOrderExtendBO get(Long id);

	/**
	 * 根据id获取购物订单
	 * 
	 * @param id
	 * @return
	 */
	ShoppingOrderBO getShoppingOrder(Long id);

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
	 */
	void paymentSuccessful(Long id);

	/**
	 * 确认收货之后 修改购物订单以及订单项状态为交易成功
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	int tradingSuccess(Long id);

	/**
	 * 买家申请退款
	 * 修改订单项状态为待商家确认
	 * 
	 * @param shoppingOrderitemId
	 *            购物订单项id
	 * @param param
	 *            退款参数
	 * @return
	 */
	int requestRefund(Long shoppingOrderitemId, ShoppingOrderRequestRefundForeignParam param);

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

	/**
	 * 第三方支付时获取订单原始总金额，用于调用第三方支付平台
	 * 
	 * @param orderIds
	 * @return
	 * @author Yangqh
	 */
	double selectOrderMoney(String orderIds);

	/**
	 * 减少产品库存成功回调 更改订单的状态为待支付状态
	 * 删除对应的购物车记录
	 * 
	 * @param id
	 *            购物订单id
	 * @author Sunny
	 */
	void minusInventorySuccess(Long id);
}
