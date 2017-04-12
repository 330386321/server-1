package com.lawu.eshop.external.api.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alipay.api.internal.util.AlipaySignature;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.utils.PropertiesUtil;

/**
 * 
 * <p>
 * Description: 支付宝第三方回调入口
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月12日 下午2:26:54
 *
 */
@RestController
@RequestMapping(value = "alipay/")
public class AlipayController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(AlipayController.class);

	public static final String splitStr = "\\|";

	/**
	 * 支付宝异步回调接口
	 * 
	 * @throws Exception
	 */
	// @SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping(value = "notifyHandle", method = RequestMethod.POST)
	public void notifyHandle() throws Exception {
		logger.info("#####################APP支付宝回调开始#####################");
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		PrintWriter out = response.getWriter();
		String member_public_key = PropertiesUtil.getPropertyValue("alipay_member_public_key", "alipay.properties");
		String merchant_public_key = PropertiesUtil.getPropertyValue("alipay_business_public_key", "alipay.properties");
		String input_charset = PropertiesUtil.getPropertyValue("input_charset", "alipay.properties");
		String app_id_member = PropertiesUtil.getPropertyValue("app_id_member", "alipay.properties");
		String app_id_business = PropertiesUtil.getPropertyValue("app_id_business", "alipay.properties");

		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
		String total_fee = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
		String passback_params = new String(request.getParameter("passback_params").getBytes("ISO-8859-1"), "UTF-8");
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
		String app_id = new String(request.getParameter("app_id").getBytes("ISO-8859-1"), "UTF-8");

		String logStr = "";
		boolean b = false;
		if (app_id_member.equals(app_id)) {
			params.remove("sign_type");
			b = AlipaySignature.rsaCheckV2(params, member_public_key, input_charset);
		} else if (app_id_business.equals(app_id)) {
			params.remove("sign_type");
			b = AlipaySignature.rsaCheckV2(params, merchant_public_key, input_charset);
		} else {
			logStr = "app_id不匹配";
		}

		if (!b) {
			logStr = !"".equals(logStr) ? logStr : "APP支付宝回调验签失败！";
			
		} else {
			if ("TRADE_FINISHED".equals(trade_status)) {
				// 超3个月不允许商家退款

			} else if ("TRADE_SUCCESS".equals(trade_status)) {
				logger.info("回传参数passback_params={}", passback_params);

				if (passback_params == null || "".equals(passback_params)) {
					logStr = "支付宝充值异步返回回传参数passback_params为空";

				} else {

					String extra[] = passback_params.split(splitStr);
					// 1-商家充值余额、2-商家充值积分、3-缴纳保证金、4-用户充值余额、5-用户充值积分、6-订单付款、7-买单
					String bizFlag = extra[0];
					String userNum = extra[1];
					String body = extra[2];
					String bizIds = extra[3];

					Boolean bt = false;
					String retCode = "1";
					SortedMap<String, Object> itrMap = new TreeMap<String, Object>();
					if ("1".equals(bizFlag)) {

					} else if ("2".equals(bizFlag)) {

					} else if ("3".equals(bizFlag)) {

					} else if ("4".equals(bizFlag)) {

					} else if ("5".equals(bizFlag)) {

					} else if ("6".equals(bizFlag)) {

					} else if ("7".equals(bizFlag)) {

					} else {
						logStr = "非法的业务类型回调";
					}
					
					if (bt) {
						logger.info("APPA支付宝回调成功");
						out.print("success");// 请不要修改或删除
					}
				}
			}
		}
		logger.info("#####################APP支付宝回调结束#####################");
	}

}
