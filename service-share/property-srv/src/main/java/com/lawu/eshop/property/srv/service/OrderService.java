package com.lawu.eshop.property.srv.service;

import com.lawu.eshop.property.param.NotifyCallBackParam;
import com.lawu.eshop.property.param.OrderComfirmDataParam;

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

}
