package com.lawu.eshop.external.api.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alipay.api.internal.util.AlipaySignature;
import com.lawu.eshop.external.api.service.OrderService;
import com.lawu.eshop.external.api.service.RechargeService;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.pay.srv.sdk.alipay.util.AlipayNotify;
import com.lawu.eshop.property.constants.ThirdPartyBizFlagEnum;
import com.lawu.eshop.property.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.param.NotifyCallBackParam;
import com.lawu.eshop.utils.PropertiesUtil;
import com.lawu.eshop.utils.StringUtil;

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
public class AlipayNotifyController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(AlipayNotifyController.class);

	public static final String splitStr = "\\|";

	@Autowired
	private RechargeService rechargeService;
	@Autowired
	private OrderService orderService;

	/**
	 * 支付宝异步回调接口
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "appNotifyHandle", method = RequestMethod.POST)
	public void appNotifyHandle() throws Exception {
		logger.info("#####################APP支付宝回调开始#####################");
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		Result result = successCreated();

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
		String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
		String passback_params = new String(request.getParameter("passback_params").getBytes("ISO-8859-1"), "UTF-8");
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
		String app_id = new String(request.getParameter("app_id").getBytes("ISO-8859-1"), "UTF-8");
		String buyer_logon_id = new String(request.getParameter("buyer_logon_id").getBytes("ISO-8859-1"), "UTF-8");

		boolean b = false;
		if (app_id_member.equals(app_id)) {
			params.remove("sign_type");
			b = AlipaySignature.rsaCheckV2(params, member_public_key, input_charset);
		} else if (app_id_business.equals(app_id)) {
			params.remove("sign_type");
			b = AlipaySignature.rsaCheckV2(params, merchant_public_key, input_charset);
		} else {
			result = successCreated(ResultCode.FAIL, "app_id不匹配");
		}

		if (!b) {
			result = successCreated(ResultCode.FAIL, "APP支付宝回调验签失败！");

		} else {
			if ("TRADE_FINISHED".equals(trade_status)) {
				// 超3个月不允许商家退款

			} else if ("TRADE_SUCCESS".equals(trade_status)) {
				logger.info("回传参数passback_params={}", passback_params);

				if (passback_params == null || "".equals(passback_params)) {
					result = successCreated(ResultCode.FAIL, "支付宝充值异步返回回传参数passback_params为空");

				} else {

					String extra[] = passback_params.split(splitStr);
					// 1-商家充值余额、2-商家充值积分、3-缴纳保证金、4-用户充值余额、5-用户充值积分、6-订单付款、7-买单
					String bizFlag = extra[0];
					NotifyCallBackParam param = new NotifyCallBackParam();
					param.setBizFlag(bizFlag);
					param.setUserNum(extra[1]);
					param.setBody(extra[2]);
					param.setBizIds(extra[3]);
					param.setSideUserNum(extra[4]);
					param.setTotalFee(total_amount);
					param.setOutTradeNo(out_trade_no);
					param.setTradeNo(trade_no);
					param.setBuyerLogonId(buyer_logon_id);
					param.setTransactionPayTypeEnum(TransactionPayTypeEnum.ALIPAY);

					int bizFlagInt = Integer.valueOf(bizFlag).intValue();
					if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BALANCE.val.equals(StringUtil.intToByte(bizFlagInt))
							|| ThirdPartyBizFlagEnum.BUSINESS_PAY_POINT.val.equals(bizFlagInt)
							|| ThirdPartyBizFlagEnum.MEMBER_PAY_BALANCE.val.equals(bizFlagInt)
							|| ThirdPartyBizFlagEnum.MEMBER_PAY_POINT.val.equals(bizFlagInt)) {
						result = rechargeService.doHandleRechargeNotify(param);

					} else if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BOND.val.equals(bizFlagInt)) {

					} else if (ThirdPartyBizFlagEnum.MEMBER_PAY_ORDER.val.equals(bizFlagInt)) {
						result = orderService.doHandleOrderPayNotify(param);
						
					} else if (ThirdPartyBizFlagEnum.MEMBER_PAY_BILL.val.equals(bizFlagInt)) {
						result = orderService.doHandlePayOrderNotify(param);
						
					} else {
						result = successCreated(ResultCode.FAIL, "非法的业务类型回调");
					}
				}
			}
		}

		if (ResultCode.SUCCESS == result.getRet()) {
			logger.info("APP支付宝回调成功");
			PrintWriter out = response.getWriter();
			out.print("success");// 请不要修改或删除
			out.flush();
			out.close();
		} else {
			logger.error("APP支付宝回调失败,错误码:{},错误信息：{},回调参数：{}", result.getRet(), result.getMsg(), requestParams);
		}
		logger.info("#####################APP支付宝回调结束#####################");
	}

	/**
	 * 支付宝异步回调接口
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "pcNotifyHandle", method = RequestMethod.POST)
	public void pcNotifyHandle() throws Exception {
		logger.info("#####################PC支付宝回调开始#####################");
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		Result result = successCreated();

		String alipay_pc_public_key = PropertiesUtil.getPropertyValue("alipay_pc_public_key", "alipay.properties");
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
		String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"), "UTF-8");
		String extra_common_param = new String(request.getParameter("extra_common_param").getBytes("ISO-8859-1"),
				"UTF-8");
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
		String app_id = new String(request.getParameter("app_id").getBytes("ISO-8859-1"), "UTF-8");
		String buyer_email = new String(request.getParameter("buyer_email").getBytes("ISO-8859-1"), "UTF-8");

		boolean b = false;
		if (!app_id_business.equals(app_id)) {
			result = successCreated(ResultCode.FAIL, "app_id不匹配");
		} else {
			b = AlipayNotify.verify(params, alipay_pc_public_key);
		}

		if (!b) {
			result = successCreated(ResultCode.FAIL, "PC支付宝回调验签失败！");
		} else {
			if ("TRADE_FINISHED".equals(trade_status)) {
				// 超3个月不允许商家退款

			} else if ("TRADE_SUCCESS".equals(trade_status)) {
				logger.info("回传参数extra_common_param={}", extra_common_param);

				if (extra_common_param == null || "".equals(extra_common_param)) {
					result = successCreated(ResultCode.FAIL, "PC支付宝充值异步返回回传参数extra_common_param为空");

				} else {

					String extra[] = extra_common_param.split(splitStr);
					// 1-商家充值余额、2-商家充值积分、3-缴纳保证金、4-用户充值余额、5-用户充值积分、6-订单付款、7-买单
					String bizFlag = extra[0];
					NotifyCallBackParam param = new NotifyCallBackParam();
					param.setBizFlag(bizFlag);
					param.setUserNum(extra[1]);
					param.setBody(extra[2]);
					param.setBizIds(extra[3]);
					param.setTotalFee(total_fee);
					param.setOutTradeNo(out_trade_no);
					param.setTradeNo(trade_no);
					param.setBuyerLogonId(buyer_email);

					int bizFlagInt = Integer.valueOf(bizFlag).intValue();
					if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BALANCE.val.equals(StringUtil.intToByte(bizFlagInt))
							|| ThirdPartyBizFlagEnum.BUSINESS_PAY_POINT.val.equals(bizFlagInt)) {
						result = rechargeService.doHandleRechargeNotify(param);

					} else if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BOND.val.equals(bizFlagInt)) {
						// TODO 保证金支付回调

					} else {
						result = successCreated(ResultCode.FAIL, "非法的业务类型回调");
					}
				}
			}
		}

		if (ResultCode.SUCCESS == result.getRet()) {
			logger.info("APP支付宝回调成功");
			PrintWriter out = response.getWriter();
			out.print("success");// 请不要修改或删除
			out.flush();
			out.close();
		} else {
			logger.error("PC支付宝回调失败,错误码:{},错误信息：{},回调参数：{}", result.getRet(), result.getMsg(), requestParams);
		}
		logger.info("#####################PC支付宝回调结束#####################");
	}

}
