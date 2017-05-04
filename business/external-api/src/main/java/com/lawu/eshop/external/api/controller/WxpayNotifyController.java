package com.lawu.eshop.external.api.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.external.api.service.DepositService;
import com.lawu.eshop.external.api.service.OrderService;
import com.lawu.eshop.external.api.service.RechargeService;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.pay.sdk.weixin.base.Configure;
import com.lawu.eshop.pay.sdk.weixin.base.PayCommonUtil;
import com.lawu.eshop.pay.sdk.weixin.base.XMLUtil;
import com.lawu.eshop.property.constants.ThirdPartyBizFlagEnum;
import com.lawu.eshop.property.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.param.NotifyCallBackParam;
import com.lawu.eshop.utils.StringUtil;

/**
 * 
 * <p>
 * Description: 微信第三方回调入口
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月13日 下午12:57:56
 *
 */
@RestController
@RequestMapping(value = "wxpay/")
public class WxpayNotifyController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(WxpayNotifyController.class);

	public static final String splitStr = "\\|";

	@Autowired
	private RechargeService rechargeService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private DepositService depositService;

	
	/**
	 * APP微信异步回调接口
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "appNotifyHandle", method = RequestMethod.POST)
	public void appNotifyHandle() throws Exception {
		logger.info("#####################APP微信回调开始#####################");
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		Result result = successCreated();

		SortedMap<Object, Object> packageParams = parseWxNotifyData(request);
		if (PayCommonUtil.isTenpaySign("UTF-8", packageParams, Configure.key_app)) {
			String return_code = packageParams.get("return_code") == null ? ""
					: packageParams.get("return_code").toString();
			if ("SUCCESS".equals(return_code)) {
				String result_code = packageParams.get("result_code") == null ? ""
						: packageParams.get("result_code").toString();
				if ("SUCCESS".equals(result_code)) {
					String attach = packageParams.get("attach") == null ? "" : packageParams.get("attach").toString();
					String transaction_id = packageParams.get("transaction_id") == null ? ""
							: packageParams.get("transaction_id").toString();
					String total_fee = packageParams.get("total_fee") == null ? ""
							: packageParams.get("total_fee").toString();
					String out_trade_no = packageParams.get("out_trade_no") == null ? ""
							: packageParams.get("out_trade_no").toString();

					String extra[] = attach.split(splitStr);
					double dmoney = new Double(total_fee).doubleValue();
					dmoney = dmoney / 100;
					// 1-商家充值余额、2-商家充值积分、3-缴纳保证金、4-用户充值余额、5-用户充值积分、6-订单付款、7-买单
					String bizFlag = extra[0];
					NotifyCallBackParam param = new NotifyCallBackParam();
					param.setBizFlag(bizFlag);
					param.setUserNum(extra[1]);
					param.setBody(extra[2]);
					param.setBizIds(extra[3]);
					param.setSideUserNum(extra[4]);
					param.setTotalFee(String.valueOf(dmoney));
					param.setOutTradeNo(out_trade_no);
					param.setTradeNo(transaction_id);
					param.setBuyerLogonId("回调没返回");
					param.setTransactionPayTypeEnum(TransactionPayTypeEnum.WX);

					int bizFlagInt = Integer.valueOf(bizFlag).intValue();
					if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BALANCE.val.equals(StringUtil.intToByte(bizFlagInt))
							|| ThirdPartyBizFlagEnum.BUSINESS_PAY_POINT.val.equals(bizFlagInt)
							|| ThirdPartyBizFlagEnum.MEMBER_PAY_BALANCE.val.equals(bizFlagInt)
							|| ThirdPartyBizFlagEnum.MEMBER_PAY_POINT.val.equals(bizFlagInt)) {
						result = rechargeService.doHandleRechargeNotify(param);

					} else if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BOND.val.equals(bizFlagInt)) {
						result = depositService.doHandleDepositNotify(param);
						
					} else if (ThirdPartyBizFlagEnum.MEMBER_PAY_ORDER.val.equals(bizFlagInt)) {
						result = orderService.doHandleOrderPayNotify(param);
						
					} else if (ThirdPartyBizFlagEnum.MEMBER_PAY_BILL.val.equals(bizFlagInt)) {
						result = orderService.doHandlePayOrderNotify(param);
						
					} else {
						result = successCreated(ResultCode.FAIL, "非法的业务类型回调");
					}

				} else {

					result = successCreated(ResultCode.FAIL, "APP端微信回调失败，return_code成功，但业务结果失败，err_code="
							+ packageParams.get("err_code") + "，err_code_des=" + packageParams.get("err_code_des"));
				}
			} else {
				result = successCreated(ResultCode.FAIL,
						"APP端微信回调失败，返回代码return_code=" + return_code + "，return_msg=" + packageParams.get("return_msg"));
			}
		} else {
			result = successCreated(ResultCode.FAIL, "APP微信回调验签失败！");
		}
		if (ResultCode.SUCCESS == result.getRet()) {
			logger.info("APP微信回调成功");
			PrintWriter out = response.getWriter();
			out.print("success");// 请不要修改或删除
			out.flush();
			out.close();
		} else {
			logger.error("APP微信回调失败,错误码:{},错误信息：{},回调参数：{}", result.getRet(), result.getMsg(), packageParams);
		}
		logger.info("#####################APP微信回调结束#####################");
	}

	/**
	 * PC微信异步回调接口
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "pcNotifyHandle", method = RequestMethod.POST)
	public void pcNotifyHandle() throws Exception {
		logger.info("#####################PC微信回调开始#####################");
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		Result result = successCreated();

		SortedMap<Object, Object> packageParams = parseWxNotifyData(request);
		if (PayCommonUtil.isTenpaySign("UTF-8", packageParams, Configure.key)) {
			String return_code = packageParams.get("return_code") == null ? ""
					: packageParams.get("return_code").toString();
			if ("SUCCESS".equals(return_code)) {
				String result_code = packageParams.get("result_code") == null ? ""
						: packageParams.get("result_code").toString();
				if ("SUCCESS".equals(result_code)) {
					String attach = packageParams.get("attach") == null ? "" : packageParams.get("attach").toString();
					String transaction_id = packageParams.get("transaction_id") == null ? ""
							: packageParams.get("transaction_id").toString();
					String total_fee = packageParams.get("total_fee") == null ? ""
							: packageParams.get("total_fee").toString();
					String out_trade_no = packageParams.get("out_trade_no") == null ? ""
							: packageParams.get("out_trade_no").toString();

					String extra[] = attach.split(splitStr);
					double dmoney = new Double(total_fee).doubleValue();
					dmoney = dmoney / 100;
					// 1-商家充值余额、2-商家充值积分、3-缴纳保证金
					String bizFlag = extra[0];
					NotifyCallBackParam param = new NotifyCallBackParam();
					param.setBizFlag(bizFlag);
					param.setUserNum(extra[1]);
					param.setBody(extra[2]);
					param.setBizIds(extra[3]);
					param.setTotalFee(String.valueOf(dmoney));
					param.setOutTradeNo(out_trade_no);
					param.setTradeNo(transaction_id);
					param.setBuyerLogonId("回调没返回");
					param.setTransactionPayTypeEnum(TransactionPayTypeEnum.WX);

					int bizFlagInt = Integer.valueOf(bizFlag).intValue();
					if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BALANCE.val.equals(StringUtil.intToByte(bizFlagInt))
							|| ThirdPartyBizFlagEnum.BUSINESS_PAY_POINT.val.equals(bizFlagInt)) {
						result = rechargeService.doHandleRechargeNotify(param);

					} else if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BOND.val.equals(bizFlagInt)) {
						//TODO 保证金回调
						
					} else {
						result = successCreated(ResultCode.FAIL, "非法的业务类型回调");
					}

				} else {

					result = successCreated(ResultCode.FAIL, "PC端微信回调失败，return_code成功，但业务结果失败，err_code="
							+ packageParams.get("err_code") + "，err_code_des=" + packageParams.get("err_code_des"));
				}
			} else {
				result = successCreated(ResultCode.FAIL,
						"APP端微信回调失败，返回代码return_code=" + return_code + "，return_msg=" + packageParams.get("return_msg"));
			}
		} else {
			result = successCreated(ResultCode.FAIL, "PC微信回调验签失败！");
		}
		if (ResultCode.SUCCESS == result.getRet()) {
			logger.info("APP微信回调成功");
			PrintWriter out = response.getWriter();
			out.print("success");// 请不要修改或删除
			out.flush();
			out.close();
		} else {
			logger.error("PC微信回调失败,错误码:{},错误信息：{},回调参数：{}", result.getRet(), result.getMsg(), packageParams);
		}
		logger.info("#####################PC微信回调结束#####################");
	}

	/**
	 * 获取微信异步post回调数据，并封装map
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private SortedMap<Object, Object> parseWxNotifyData(HttpServletRequest request) throws Exception {
		// 读取参数
		InputStream inputStream;
		StringBuffer sb = new StringBuffer();
		inputStream = request.getInputStream();
		String s;
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		while ((s = in.readLine()) != null) {
			sb.append(s);
		}
		in.close();
		inputStream.close();

		// 解析xml成map
		Map<String, String> m = new HashMap<String, String>();
		m = XMLUtil.doXMLParse(sb.toString());

		// 过滤空 设置 TreeMap
		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		Iterator it = m.keySet().iterator();
		while (it.hasNext()) {
			String parameter = (String) it.next();
			String parameterValue = m.get(parameter);

			String v = "";
			if (null != parameterValue) {
				v = parameterValue.trim();
			}
			packageParams.put(parameter, v);
		}
		return packageParams;
	}
}
