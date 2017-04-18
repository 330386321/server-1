package com.lawu.eshop.pay.srv.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.pay.srv.sdk.alipay.util.AlipaySubmit;
import com.lawu.eshop.pay.srv.sdk.alipay.util.SignUtils;
import com.lawu.eshop.property.constants.ThirdPartyBizFlagEnum;
import com.lawu.eshop.property.constants.UserTypeEnum;
import com.lawu.eshop.property.param.ThirdPayDataParam;
import com.lawu.eshop.property.param.PcAlipayDataParam;
import com.lawu.eshop.utils.DateUtil;
import com.lawu.eshop.utils.PropertiesUtil;

/**
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月7日 下午8:00:08
 *
 */
@RestController
@RequestMapping(value = "alipay/")
public class AlipayController extends BaseController {

	//private static Logger logger = LoggerFactory.getLogger(AlipayController.class);
	
	public static final String split = "|";

	/**
	 * 客户端调用支付宝获取请求参数
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "getAppAlipayReqParams", method = RequestMethod.POST)
	public Result getAppAlipayReqParams(@RequestBody ThirdPayDataParam param) {

//		int retCode = AlipayParamValidateor.appAlipayReqValidate(param);
//		if (retCode != ResultCode.SUCCESS) {
//			return successCreated(retCode);
//		}

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

		String passback_params = param.getBizFlagEnum().val + split + param.getUserNum() + split + param.getThirdPayBodyEnum().val
				+ split + param.getBizIds() + split + param.getSideUserNum();

		paramMap.put("biz_content", "{\"subject\":\"" + param.getSubject() + "\",\"out_trade_no\":\""
				+ param.getOutTradeNo() + "\",\"total_amount\":\"" + param.getTotalAmount()
				+ "\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"passback_params\":\"" + passback_params + "\"}");

		String paramStr = SignUtils.buildMapToString(paramMap);
		String sign = SignUtils.getSign(paramMap, PropertiesUtil.getPropertyValue("private_key", "alipay.properties"),
				false);

		return successCreated(paramStr + "&" + sign);
	}

	/**
	 * pc端生成预支付订单返回扫码二维码
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "initPcPay", method = RequestMethod.POST)
	public Result initPcPay(@RequestBody PcAlipayDataParam param) {

//		int retCode = AlipayParamValidateor.pcAlipayReqValidate(param);
//		if (retCode != ResultCode.SUCCESS) {
//			return successCreated(retCode);
//		}
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("service", "create_direct_pay_by_user");
		paramMap.put("partner", PropertiesUtil.getPropertyValue("partner", "alipay.properties"));
		paramMap.put("seller_id", PropertiesUtil.getPropertyValue("seller_id", "alipay.properties"));
		paramMap.put("_input_charset", PropertiesUtil.getPropertyValue("input_charset", "alipay.properties"));
		paramMap.put("payment_type", "1");
		paramMap.put("notify_url", PropertiesUtil.getPropertyValue("notify_url_pc", "alipay.properties"));
		paramMap.put("return_url", PropertiesUtil.getPropertyValue("notify_url_pc", "alipay.properties"));
		paramMap.put("anti_phishing_key", "");
		paramMap.put("exter_invoke_ip", "");

		paramMap.put("out_trade_no", param.getOutTradeNo());
		paramMap.put("subject", param.getSubject());
		if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BOND.val.equals(param.getBizFlagEnum().val)) {
			paramMap.put("extra_common_param", param.getBizFlagEnum().val + split + param.getUserNum() + split
					+ "商家缴纳保证金P" + split + param.getBizId());
		} else if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BALANCE.val.equals(param.getBizFlagEnum().val)) {
			paramMap.put("extra_common_param",
					param.getBizFlagEnum().val + split + param.getUserNum() + split + "商家充值余额P");
		} else if (ThirdPartyBizFlagEnum.BUSINESS_PAY_POINT.val.equals(param.getBizFlagEnum().val)) {
			paramMap.put("extra_common_param",
					param.getBizFlagEnum().val + split + param.getUserNum() + split + "商家充值积分P");
		}
		paramMap.put("total_fee", param.getTotalAmount());
		String sHtmlText = AlipaySubmit.buildRequest(paramMap, "get", "确认");
		return successCreated(sHtmlText);
	}

}
