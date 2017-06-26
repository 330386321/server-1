package com.lawu.eshop.pay.handle;

import com.lawu.eshop.pay.ThirdPayRefundParam;
import com.lawu.eshop.pay.sdk.weixin.sdk.WXPay;
import com.lawu.eshop.pay.sdk.weixin.sdk.common.JsonResult;
import com.lawu.eshop.pay.sdk.weixin.sdk.protocol.refund_protocol.RefundReqData;
import com.lawu.eshop.pay.sdk.weixin.base.WxPayConfigParam;

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
	public static void refund(ThirdPayRefundParam rparam, JsonResult jsonResult, WxPayConfigParam wxPayConfigParam) throws Exception {
		double refundMoney = Double.parseDouble(rparam.getRefundMoney());
		double totalMoney = Double.parseDouble(rparam.getTotalMoney());
		int refundMoneyInt = (int) (refundMoney * 100);
		int totalMoneyInt = (int) (totalMoney * 100);
		RefundReqData refundReqData = new RefundReqData(wxPayConfigParam.getWxpayAppIdMember(),wxPayConfigParam.getWxpayMchIdMember(),rparam.getTradeNo(),rparam.getRefundId(), totalMoneyInt, refundMoneyInt, wxPayConfigParam.getWxpayMchIdMember(),wxPayConfigParam.getWxpayKeyApp());
		WXPay.requestRefundService(refundReqData,jsonResult,wxPayConfigParam);
	}
	
}
