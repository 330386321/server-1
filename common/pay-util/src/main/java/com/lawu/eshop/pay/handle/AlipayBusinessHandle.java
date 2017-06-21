package com.lawu.eshop.pay.handle;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.lawu.eshop.pay.ThirdPayRefundParam;
import com.lawu.eshop.pay.sdk.alipay.AliPayConfigParam;
import com.lawu.eshop.pay.sdk.weixin.sdk.common.JsonResult;

/**
 * 
 * <p>
 * Description: 处理支付对接业务
 * </p>
 * @author Yangqh
 * @date 2017年4月14日 下午3:25:25
 *
 */
public class AlipayBusinessHandle {

	/**
	 * 支付宝退款
	 * @param rparam 参数
	 * @param jsonResult 返回参数
	 * @throws Exception 
	 */
	public static void refund(ThirdPayRefundParam rparam, JsonResult jsonResult, AliPayConfigParam aliPayConfigParam) throws Exception {
		AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfigParam.getAlipayRefundUrl(),aliPayConfigParam.getAlipayAppIdMember(),aliPayConfigParam.getAlipayPrivateKey(),"JSON","utf-8",aliPayConfigParam.getAlipayEdianMemberPublicKey(),"RSA");
		AlipayTradeRefundRequest req = new AlipayTradeRefundRequest();
		req.setBizContent("{" +
		"    \"trade_no\":\""+rparam.getTradeNo()+"\"," +
		"    \"refund_amount\":"+rparam.getRefundMoney()+"," +
		"    \"out_request_no\":\""+rparam.getRefundId()+"\"" +
		"  }");
		AlipayTradeRefundResponse res = alipayClient.execute(req);
		jsonResult.setSuccess(res.isSuccess());
		if(!res.isSuccess()){
			jsonResult.setMessage("errorCode:"+res.getErrorCode()+",msg:"+res.getMsg());
		}
		
	}
	
}
