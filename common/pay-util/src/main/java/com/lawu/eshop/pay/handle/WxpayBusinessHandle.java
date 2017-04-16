package com.lawu.eshop.pay.handle;

import com.lawu.eshop.pay.sdk.weixin.base.Configure;
import com.lawu.eshop.pay.sdk.weixin.sdk.WXPay;
import com.lawu.eshop.pay.sdk.weixin.sdk.common.JsonResult;
import com.lawu.eshop.pay.sdk.weixin.sdk.protocol.refund_protocol.RefundReqData;
import com.lawu.eshop.property.param.ThirdPayRefundParam;

/**
 * 
 * <p>
 * Description: 处理支付对接业务
 * </p>
 * @author Yangqh
 * @date 2017年4月14日 下午3:25:25
 *
 */
public class WxpayBusinessHandle {

	/**
	 * 微信退款
	 * @param rparam 参数
	 * @param jsonResult 返回参数
	 * @throws Exception 
	 */
	public static void refund(ThirdPayRefundParam rparam, JsonResult jsonResult) throws Exception {
		double refundMoney = Double.valueOf(rparam.getRefundMoney()).doubleValue();
		double totalMoney = Double.valueOf(rparam.getTotalMoney()).doubleValue();
		int refundMoneyInt = (int) (refundMoney * 100);
		int totalMoneyInt = (int) (totalMoney * 100);
		RefundReqData refundReqData = new RefundReqData(Configure.appID_member,Configure.mchID_member,rparam.getTradeNo(),rparam.getRefundId(), totalMoneyInt, refundMoneyInt, Configure.mchID_member);
		WXPay.requestRefundService(refundReqData,jsonResult);
	}
	
}
