package com.lawu.eshop.property.srv.service;

import com.lawu.eshop.property.param.NotifyCallBackParam;
import com.lawu.eshop.property.param.OrderComfirmDataParam;
import com.lawu.eshop.property.param.OrderRefundDataParam;

/**
 * 
 * <p>
 * Description: 订单处理
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月13日 下午1:58:23
 *
 */
public interface OrderService {

	/**
	 * 商品订单第三方付款后回调处理：新增会员交易记录，<更新订单状态>
	 * 
	 * 此时暂不保存商家交易明细和加余额，确认收货后处理
	 * 
	 * @param param
	 * @return
	 */
	int doHandleOrderPayNotify(NotifyCallBackParam param);

	/**
	 * 处理第三方买单支付回调处理：新增会员交易记录，加商家财产余额，<更新订单状态>
	 * 
	 * @param param
	 * @return
	 */
	int doHandlePayOrderNotify(NotifyCallBackParam param);

	/**
	 * 用户确认收货，将订单金额插入冻结资金表,<异步通知修改订单状态>
	 * 
	 * @param param
	 * @return
	 */
	int comfirmDelivery(OrderComfirmDataParam param);

	/**
	 * 商家同意订单退款（确认收货后7天内）,区分余额支付和第三方支付
	 * 余额支付：处理商家冻结资金（区分是否是最后一次退款，校验冻结资金记录是否存在和数量，退款金额不能大于冻结金额），新增会员交易订单退款交易记录，
	 * 加会员财产余额
	 * 第三方支付：处理商家冻结资金（区分是否是最后一次退款，校验冻结资金记录是否存在和数量，退款金额不能大于冻结金额），新增会员交易订单退款交易记录，
	 * 原路退回会员支付账户
	 *
	 * <异步通知修改订单状态>
	 * 
	 * @param param
	 * @return
	 */
	int doRefundScopeInside(OrderRefundDataParam param) throws Exception;

}
