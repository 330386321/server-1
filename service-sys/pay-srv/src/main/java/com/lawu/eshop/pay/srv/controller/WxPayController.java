package com.lawu.eshop.pay.srv.controller;

import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.pay.srv.sdk.weixin.base.Configure;
import com.lawu.eshop.pay.srv.sdk.weixin.base.HttpUtil;
import com.lawu.eshop.pay.srv.sdk.weixin.base.PayCommonUtil;
import com.lawu.eshop.pay.srv.sdk.weixin.base.RandomStringGenerator;
import com.lawu.eshop.pay.srv.sdk.weixin.base.XMLUtil;
import com.lawu.eshop.property.constants.UserTypeEnum;
import com.lawu.eshop.property.param.ThirdPayDataParam;
import com.lawu.eshop.utils.DateUtil;

/**
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月7日 下午8:00:00
 *
 */
@RestController
@RequestMapping(value = "wxPay/")
public class WxPayController extends BaseController { 

	private static Logger logger = LoggerFactory.getLogger(WxPayController.class);

	public static final String split = "|";
	public static final String splitStr = "\\|";

	/**
	 * app和PC获取预支付订单返回的信息 1、app端通过后台服务获取prepay_id、pc获取预支付订单二维码 2、组装app调用支付接口参数
	 * 
	 * @param param
	 * @return
	 * @throws IOException
	 * @throws JDOMException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "getPrepayInfo", method = RequestMethod.POST)
	public Result getPrepayInfo(@RequestBody ThirdPayDataParam param) throws JDOMException, IOException {
		int retCode = ResultCode.SUCCESS;
//		if (UserTypeEnum.MEMCHANT_PC.val == param.getUserTypeEnum().val) {
//			retCode = WxPayParamValidateor.pcAlipayReqValidate(param);
//		} else {
//			retCode = WxPayParamValidateor.appAlipayReqValidate(param);
//		}
		if (retCode != ResultCode.SUCCESS) {
			return successCreated(retCode);
		}

		String key = Configure.key_app;
		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		packageParams.put("trade_type", "APP");
		packageParams.put("notify_url", Configure.notify_url);
		if (UserTypeEnum.MEMBER.val == param.getUserTypeEnum().val) {
			packageParams.put("appid", Configure.appID_member);
			packageParams.put("mch_id", Configure.mchID_member);
		} else if (UserTypeEnum.MEMCHANT.val == param.getUserTypeEnum().val) {
			packageParams.put("appid", Configure.appID_business);
			packageParams.put("mch_id", Configure.mchID_business);
		} else if (UserTypeEnum.MEMCHANT_PC.val == param.getUserTypeEnum().val) {
			packageParams.put("appid", Configure.appID);
			packageParams.put("mch_id", Configure.mchID);
			packageParams.put("trade_type", "NATIVE");
			packageParams.put("notify_url", Configure.notify_url_pc);
			key = Configure.key;
		}
		packageParams.put("nonce_str", RandomStringGenerator.getRandomStringByLength(32));
		packageParams.put("body", param.getThirdPayBodyEnum().val);
		packageParams.put("out_trade_no", param.getOutTradeNo());
		Float fTotalAmount = Float.valueOf(param.getTotalAmount());
		int iTotalAmount = (int) (fTotalAmount * 100);
		packageParams.put("total_fee", iTotalAmount + "");
		packageParams.put("spbill_create_ip", Configure.ip);
		packageParams.put("attach", param.getBizFlagEnum().val + split + param.getUserNum() + split + param.getThirdPayBodyEnum().val
				+ split + param.getBizIds() + split + param.getSideUserNum());

		String sign = PayCommonUtil.createSign("UTF-8", packageParams, key);
		packageParams.put("sign", sign);

		String requestXML = PayCommonUtil.getRequestXml(packageParams);

		String resXml = HttpUtil.postData(Configure.NATIVE_PAY_API, requestXML);
		Map map = XMLUtil.doXMLParse(resXml);

		String return_code = map.get("return_code") == null ? "" : map.get("return_code").toString();
		if (return_code.equals("FAIL")) {
			String return_msg = map.get("return_msg") == null ? "" : map.get("return_msg").toString();
			logger.error("微信支付预支付订单失败，return_code={},return_msg={}", return_code, return_msg);
//			return successCreated(ResultCode.WEIXIN_PAY_RETURN_CODE_FAIL);

		} else {
			String result_code = map.get("result_code") == null ? "" : map.get("result_code").toString();
			if (result_code.equals("FAIL")) {
				String err_code = map.get("err_code") == null ? "" : map.get("err_code").toString();
				String err_code_des = map.get("err_code_des") == null ? "" : map.get("err_code_des").toString();
				logger.error("微信支付预支付订单失败，result_code={},err_code={},err_code_des={}", result_code, err_code,
						err_code_des);
//				return successCreated(ResultCode.WEIXIN_PAY_RESULT_CODE_FAIL);

			} else {
				if (UserTypeEnum.MEMCHANT_PC.val == param.getUserTypeEnum().val) {
					String code_url = map.get("code_url") == null ? "" : map.get("code_url").toString();
					packageParams.clear();
					packageParams.put("codeUrl", code_url);

				} else {
					String prepay_id = map.get("prepay_id") == null ? "" : map.get("prepay_id").toString();
					packageParams.clear();
					if (UserTypeEnum.MEMBER.val == param.getUserTypeEnum().val) {
						packageParams.put("appid", Configure.appID_member);
						packageParams.put("mch_id", Configure.mchID_member);
					} else if (UserTypeEnum.MEMCHANT.val == param.getUserTypeEnum().val) {
						packageParams.put("appid", Configure.appID_business);
						packageParams.put("mch_id", Configure.mchID_business);
					}
					packageParams.put("prepayid", prepay_id);
					packageParams.put("package", "Sign=WXPay");
					packageParams.put("noncestr", RandomStringGenerator.getRandomStringByLength(32));
					packageParams.put("timestamp", DateUtil.getDateTime());
					sign = PayCommonUtil.createSign("UTF-8", packageParams, Configure.key_app);
					packageParams.put("sign", sign);
				}

			}
		}
		return successCreated(packageParams);
	}

}
