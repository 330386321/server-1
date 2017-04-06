package com.lawu.eshop.pay.srv.controller;

import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.pay.srv.util.SignUtils;
import com.lawu.eshop.property.constants.ThirdPartyBizFlagEnum;
import com.lawu.eshop.property.constants.UserTypeEnum;
import com.lawu.eshop.property.param.AppAlipayParam;
import com.lawu.eshop.utils.DateUtil;
import com.lawu.eshop.utils.PropertiesUtil;

@RestController
@RequestMapping(value = "alipay/")
public class AlipayController extends BaseController {

	/**
	 * app调用支付宝的请求参数
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "getAppAlipayReqParams", method = RequestMethod.GET)
	public Result getAppAlipayReqParams(@RequestBody AppAlipayParam param) {
		
		int retCode = ParamValidateor.appAlipayReqValidate(param);
		if(retCode != ResultCode.SUCCESS){
			return successCreated(retCode);
		}
		
		SortedMap<String, String> paramMap = new TreeMap<String, String>();
		String appId = "";
		if (param.getUserTypeEnum().val.equals(UserTypeEnum.MEMBER.val)) {
			appId = PropertiesUtil.getPropertyValue("app_id_member", "alipay.properties");
		} else if (param.getUserTypeEnum().val.equals(UserTypeEnum.MEMCHANT.val)) {
			appId = PropertiesUtil.getPropertyValue("app_id_business", "alipay.properties");
		}
		paramMap.put("app_id", appId);
		paramMap.put("method", "alipay.trade.app.pay");
		paramMap.put("charset", "utf-8");
		paramMap.put("sign_type", "RSA");
		paramMap.put("timestamp", DateUtil.getDateTime());
		paramMap.put("version", "1.0");
		paramMap.put("notify_url", PropertiesUtil.getPropertyValue("notify_url", "alipay.properties"));
		
		String passback_params = "";
		if (ThirdPartyBizFlagEnum.MEMBER_PAY_ORDER.val.equals(param.getBizFlagEnum().val)) {
			passback_params = ThirdPartyBizFlagEnum.MEMBER_PAY_ORDER.val + "|" + param.getBizIds() + "|"
					+ param.getUserNum() + "|" + param.getBody();
			
		} else if (ThirdPartyBizFlagEnum.MEMBER_PAY_BILL.val.equals(param.getBizFlagEnum().val)) {
			//TODO
			
		}else{
			passback_params = param.getBizFlagEnum().val + "|" + param.getUserNum() + "|"
					+ param.getBody();
		}

		paramMap.put("biz_content", "{\"subject\":\"" + param.getSubject() + "\",\"out_trade_no\":\""
				+ param.getOutTradeNo() + "\",\"total_amount\":\"" + param.getTotalAmount()
				+ "\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"passback_params\":\"" + passback_params + "\"}");

		String paramStr = SignUtils.buildMapToString(paramMap);
		String sign = SignUtils.getSign(paramMap, PropertiesUtil.getPropertyValue("private_key", "alipay.properties"), false);
		
		return successCreated(paramStr + "&" + sign);
	}
	
	
	
	
}
