package com.lawu.eshop.pay.handle;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.lawu.eshop.pay.sdk.alipay.config.AlipayConfig;
import com.lawu.eshop.pay.sdk.weixin.sdk.common.JsonResult;
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
public class AlipayBusinessHandle {

	/**
	 * 支付宝退款
	 * @param rparam 参数
	 * @param jsonResult 返回参数
	 * @throws Exception 
	 */
	public static void refund(ThirdPayRefundParam rparam, JsonResult jsonResult) throws Exception {
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",AlipayConfig.app_id_member,AlipayConfig.private_key,"JSON","utf-8",AlipayConfig.alipay_edian_member_public_key,"RSA");
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
