package com.lawu.eshop.order.srv.service;

import java.util.List;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mq.dto.property.ShoppingOrderPaymentNotification;
import com.lawu.eshop.order.dto.ReportRiseRateDTO;
import com.lawu.eshop.order.dto.ReportRiseRerouceDTO;
import com.lawu.eshop.order.param.ReportDataParam;
import com.lawu.eshop.order.param.ShoppingOrderLogisticsInformationParam;
import com.lawu.eshop.order.param.ShoppingOrderRequestRefundParam;
import com.lawu.eshop.order.param.ShoppingOrderSettlementParam;
import com.lawu.eshop.order.param.ShoppingOrderUpdateInfomationParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderQueryForeignToMemberParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderQueryForeignToMerchantParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderQueryForeignToOperatorParam;
import com.lawu.eshop.order.srv.bo.ShoppingOrderBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderExtendBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderIsNoOnGoingOrderBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderMoneyBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderNumberOfOrderStatusBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderNumberOfOrderStatusForMerchantBO;

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
	int cancelOrder(Long id);

	/**
	 * 删除购物订单
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	int deleteOrder(Long id);

	/**
	 * 支付成功之后 修改购物订单以及订单项状态为待发货
	 * 
	 * @param id
	 *            购物订单id
	 */
	void paymentSuccessful(ShoppingOrderPaymentNotification notification);

	/**
	 * 确认收货之后 修改购物订单以及订单项状态为交易成功
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	int tradingSuccess(Long id, boolean isAutomaticReceipt);

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
	int requestRefund(Long shoppingOrderitemId, ShoppingOrderRequestRefundParam param);

	/**
	 * 商家填写物流信息
	 * 
	 * @param id
	 *            购物订单id
	 * @param param
	 *            物流信息参数
	 * @return
	 */
	int fillLogisticsInformation(Long id, ShoppingOrderLogisticsInformationParam param);

	/**
	 * 第三方支付时获取订单原始总金额，用于调用第三方支付平台
	 * 
	 * @param orderIds
	 * @return
	 * @author Yangqh
	 */
	Result<ShoppingOrderMoneyBO> selectOrderMoney(String orderIds);

	/**
	 * 减少产品库存成功回调 更改订单的状态为待支付状态
	 * 删除对应的购物车记录
	 * 
	 * @param id
	 *            购物订单id
	 * @author Sunny
	 */
	void minusInventorySuccess(Long id);
	
	/**
	 * 更新订单信息
	 * @param id
	 * 			     购物订单id
	 * @param param
	 *            查询参数
	 * @return
	 */
	int updateInformation(Long id, ShoppingOrderUpdateInfomationParam param);
	
	/**
	 * 检查数据库中超时评论的订单
	 * 执行自动评论
	 * 
	 * @author Sunny
	 */
	void executetAutoComment();
	
	/**
	 * 根据商家的id查询商家是否有进行中的订单
	 * 
	 * @param merchantId 商家的id
	 * @return
	 * @author Sunny
	 */
	ShoppingOrderIsNoOnGoingOrderBO isNoOnGoingOrder(Long merchantId);
	
	/**
	 * 根据购物订单项查询订单以及订单项
	 * 
	 * @param ShoppingOrderItemId 购物订单项id
	 * @param isAll 是否查找全部的订单项 
	 * @return
	 * @author Sunny
	 */
	ShoppingOrderExtendBO getByShoppingOrderItemId(Long shoppingOrderItemId, boolean isAll);
	
	/**
	 * 自动取消为付款的订单
	 * 
	 * @author Sunny
	 */
	void executeAutoCancelOrder();
	
	/**
	 * 自动提醒发货
	 * 
	 * @author Sunny
	 */
	void executeAutoRemindShipments();
	
	/**
	 * 自动收货
	 * 
	 * @author Sunny
	 */
	void executeAutoReceipt();
	
	/**
	 * 查询各种订单状态的数量
	 * To Member
	 * 
	 * @param memberId 会员id
	 * @return
	 * @author Sunny
	 */
	ShoppingOrderNumberOfOrderStatusBO numberOfOrderStartus(Long memberId);
	
	/**
	 * 查询未计算提成订单
	 * 
	 * @return
	 * @author Sunny
	 */
	List<ShoppingOrderBO> commissionShoppingOrder();
	
	/**
	 * 根据订单id更新购物订单的提成状态和提成时间
	 * 
	 * @param ids 购物订单id集合
	 * @return
	 * @author Sunny
	 */
	int updateCommissionStatus(List<Long> ids);
	
	/**
	 * 查询各种订单状态的数量
	 * To Merchant
	 * 
	 * @param merchantId 商家id
	 * @return
	 * @author Sunny
	 */
	ShoppingOrderNumberOfOrderStatusForMerchantBO numberOfOrderStartusByMerchant(Long merchantId);
	
	/**
	 * 统计商家的交易数据
	 * 
	 * @param param
	 * @return
	 * @author Sunny
	 */
	ReportRiseRateDTO selectByTransactionData(ReportDataParam param);
	
	/**
	 * 粉丝数据-消费转化
	 * 
	 * @param param
	 * @return
	 * @author Sunny
	 */
	List<ReportRiseRerouceDTO> fansSaleTransform(ReportDataParam param);
	
	/**
	 * 订单收货之后
	 * 如果超过退款申请时间，直接付款给商家
	 * 
	 * @author Sunny
	 */
	void executeAutoPaymentsToMerchant();
	
}
