package com.lawu.eshop.property.srv.pay;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.pay.sdk.weixin.base.HttpUtil;
import com.lawu.eshop.pay.sdk.weixin.base.PayCommonUtil;
import com.lawu.eshop.pay.sdk.weixin.base.RandomStringGenerator;
import com.lawu.eshop.pay.sdk.weixin.base.XMLUtil;
import com.lawu.eshop.property.constants.UserTypeEnum;
import com.lawu.eshop.property.param.ThirdPayDataParam;
import com.lawu.eshop.property.srv.PropertySrvConfig;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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

	@Autowired
	private PropertySrvConfig propertySrvConfig;

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
	public Result getPrepayInfo(@RequestBody @Valid ThirdPayDataParam param, BindingResult result)
			throws JDOMException, IOException {

		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			StringBuffer es = new StringBuffer();
			for (FieldError e : errors) {
				String msg = e.getDefaultMessage();
				es.append(msg);
			}
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, es.toString());
		}

		String key = propertySrvConfig.getWxpayKey();
		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		packageParams.put("trade_type", "APP");
		packageParams.put("notify_url", propertySrvConfig.getWxpayNotifyUrl());
		if (UserTypeEnum.MEMBER.val == param.getUserTypeEnum().val) {
			packageParams.put("appid", propertySrvConfig.getWxpayAppIdMember());
			packageParams.put("mch_id", propertySrvConfig.getWxpayMchIdMember());
		} else if (UserTypeEnum.MEMCHANT.val == param.getUserTypeEnum().val) {
			packageParams.put("appid", propertySrvConfig.getWxpayAppIdBusiness());
			packageParams.put("mch_id", propertySrvConfig.getWxpayMchIdBusiness());
		} else if (UserTypeEnum.MEMCHANT_PC.val == param.getUserTypeEnum().val) {
			packageParams.put("appid", propertySrvConfig.getWxpayAppId());
			packageParams.put("mch_id", propertySrvConfig.getWxpayMchId());
			packageParams.put("trade_type", "NATIVE");
			packageParams.put("notify_url", propertySrvConfig.getWxpayNotifyUrlPc());
			key =propertySrvConfig.getWxpayKey();
		}
		packageParams.put("nonce_str", RandomStringGenerator.getRandomStringByLength(32));
		packageParams.put("body", param.getThirdPayBodyEnum().val);
		packageParams.put("out_trade_no", param.getOutTradeNo());
		Float fTotalAmount = Float.valueOf(param.getTotalAmount());
		int iTotalAmount = (int) (fTotalAmount * 100);
		packageParams.put("total_fee", iTotalAmount + "");
		packageParams.put("spbill_create_ip", propertySrvConfig.getWxpayIp());
		packageParams.put("attach", param.getBizFlagEnum().val + split + param.getUserNum() + split
				+ param.getThirdPayBodyEnum().val + split + param.getBizIds() + split + param.getSideUserNum());

		String sign = PayCommonUtil.createSign("UTF-8", packageParams, key);
		packageParams.put("sign", sign);

		String requestXML = PayCommonUtil.getRequestXml(packageParams);

		String resXml = HttpUtil.postData(propertySrvConfig.getWxpayNativePayApi(), requestXML);
		Map map = XMLUtil.doXMLParse(resXml);

		String return_code = map.get("return_code") == null ? "" : map.get("return_code").toString();
		if (return_code.equals("FAIL")) {
			String return_msg = map.get("return_msg") == null ? "" : map.get("return_msg").toString();
			logger.error("微信支付预支付订单失败，return_code={},return_msg={}", return_code, return_msg);
			return successCreated(ResultCode.FAIL, return_code + ":" + return_msg);

		} else {
			String result_code = map.get("result_code") == null ? "" : map.get("result_code").toString();
			if (result_code.equals("FAIL")) {
				String err_code = map.get("err_code") == null ? "" : map.get("err_code").toString();
				String err_code_des = map.get("err_code_des") == null ? "" : map.get("err_code_des").toString();
				logger.error("微信支付预支付订单失败，result_code={},err_code={},err_code_des={}", result_code, err_code,
						err_code_des);
				return successCreated(ResultCode.FAIL, err_code + ":" + err_code_des);

			} else {
				if (UserTypeEnum.MEMCHANT_PC.val == param.getUserTypeEnum().val) {
					String code_url = map.get("code_url") == null ? "" : map.get("code_url").toString();
					packageParams.clear();
					packageParams.put("codeUrl", code_url);

				} else {
					String prepay_id = map.get("prepay_id") == null ? "" : map.get("prepay_id").toString();
					packageParams.clear();
					if (UserTypeEnum.MEMBER.val == param.getUserTypeEnum().val) {
						packageParams.put("appid", propertySrvConfig.getWxpayAppIdMember());
						packageParams.put("partnerid", propertySrvConfig.getWxpayMchIdMember());
					} else if (UserTypeEnum.MEMCHANT.val == param.getUserTypeEnum().val) {
						packageParams.put("appid", propertySrvConfig.getWxpayAppIdBusiness());
						packageParams.put("partnerid", propertySrvConfig.getWxpayMchIdBusiness());
					}
					packageParams.put("prepayid", prepay_id);
					packageParams.put("package", "Sign=WXPay");
					packageParams.put("noncestr", RandomStringGenerator.getRandomStringByLength(32));
					packageParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
					sign = PayCommonUtil.createSign("UTF-8", packageParams, key);
					packageParams.put("sign", sign);
				}

			}
		}
		return successCreated(packageParams);
	}

}
