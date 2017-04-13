package com.lawu.eshop.property.srv.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.param.NotifyCallBackParam;

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
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Result doHandleOrderPayNotify(NotifyCallBackParam param);

	/**
	 * 处理第三方买单支付回调处理：新增会员交易记录，加商家财产余额，<更新订单状态>
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Result doHandlePayOrderNotify(NotifyCallBackParam param);

}
