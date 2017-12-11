package com.lawu.eshop.external.api.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.lawu.eshop.ad.dto.AdPayInfoDTO;
import com.lawu.eshop.common.constants.MessageTypeEnum;
import com.lawu.eshop.external.api.ExternalApiConfig;
import com.lawu.eshop.external.api.service.AdService;
import com.lawu.eshop.external.api.service.AdUserRedPacketService;
import com.lawu.eshop.external.api.service.DepositService;
import com.lawu.eshop.external.api.service.MemberService;
import com.lawu.eshop.external.api.service.MerchantStoreService;
import com.lawu.eshop.external.api.service.MessageService;
import com.lawu.eshop.external.api.service.OrderService;
import com.lawu.eshop.external.api.service.PayOrderService;
import com.lawu.eshop.external.api.service.PropertySrvService;
import com.lawu.eshop.external.api.service.PropertyinfoAdService;
import com.lawu.eshop.external.api.service.PropertyinfoUserRedPacketService;
import com.lawu.eshop.external.api.service.RechargeService;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.param.MessageInfoParam;
import com.lawu.eshop.mall.param.MessageTempParam;
import com.lawu.eshop.order.dto.PayOrderBaseDTO;
import com.lawu.eshop.order.dto.ShoppingOrderMoneyDTO;
import com.lawu.eshop.order.dto.ThirdPayCallBackQueryPayOrderDTO;
import com.lawu.eshop.pay.sdk.alipay.AliPayConfigParam;
import com.lawu.eshop.pay.sdk.alipay.util.AlipayNotify;
import com.lawu.eshop.property.constants.PropertyType;
import com.lawu.eshop.property.constants.ThirdPartyBizFlagEnum;
import com.lawu.eshop.property.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.dto.PropertyPointAndBalanceDTO;
import com.lawu.eshop.property.param.NotifyCallBackParam;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.PayOrderMerchantStoreInfoDTO;
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
@RequestMapping(value = "/alipay/")
public class AlipayNotifyController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(AlipayNotifyController.class);

	public static final String splitStr = "\\|";

	@Autowired
	private RechargeService rechargeService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private DepositService depositService;
	@Autowired
	private ExternalApiConfig externalApiConfig;
	@Autowired
	private MessageService messageService;
	@Autowired
	private PayOrderService payOrderService;
	@Autowired
	private PropertySrvService propertyService;
	@Autowired
	private AdUserRedPacketService userRedPacketService;
	@Autowired
	private PropertyinfoUserRedPacketService propertyinfoUserRedPacketService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MerchantStoreService merchantStoreService;
	@Autowired
	private AdService adService;
	@Autowired
	private PropertyinfoAdService propertyinfoAdService;

	/**
	 * 支付宝异步回调接口
	 * @throws AlipayApiException 
	 * @throws IOException 
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "appNotifyHandle", method = RequestMethod.POST)
	public void appNotifyHandle() throws IOException, AlipayApiException  {
		logger.info("#####################APP支付宝回调开始#####################");
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		Result result = successCreated();

		String member_public_key = externalApiConfig.getAlipayEdianMemberPublicKey();
		String merchant_public_key = externalApiConfig.getAlipayEdianBusinessPublicKey();
		String input_charset = externalApiConfig.getAlipayInputCharset();
		String app_id_member = externalApiConfig.getAlipayAppIdMember();
		String app_id_business = externalApiConfig.getAlipayAppIdBusiness();

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
		String passback_params = request.getParameter("passback_params");
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
		String app_id = new String(request.getParameter("app_id").getBytes("ISO-8859-1"), "UTF-8");
		String buyer_logon_id = new String(request.getParameter("buyer_logon_id").getBytes("ISO-8859-1"), "UTF-8");

		boolean isSendMsg = false;
		double dTotalMoney = 0;
		boolean b = false;
		String merchantUserNum = "0";
		int bizFlagInt = 0;
		String extra[] = null;
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

					extra = passback_params.split(splitStr);
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

					dTotalMoney = Double.parseDouble(total_amount);
					bizFlagInt = Integer.parseInt(bizFlag);
					if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BALANCE.getVal().equals(StringUtil.intToByte(bizFlagInt))
							|| ThirdPartyBizFlagEnum.BUSINESS_PAY_POINT.getVal().equals(StringUtil.intToByte(bizFlagInt))
							|| ThirdPartyBizFlagEnum.MEMBER_PAY_BALANCE.getVal().equals(StringUtil.intToByte(bizFlagInt))
							|| ThirdPartyBizFlagEnum.MEMBER_PAY_POINT.getVal().equals(StringUtil.intToByte(bizFlagInt))) {
						isSendMsg = true;
						result = rechargeService.doHandleRechargeNotify(param);
						
					} else if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BOND.getVal().equals(StringUtil.intToByte(bizFlagInt))) {
						param.setMerchantId(Long.valueOf(extra[5]));
						result = depositService.doHandleDepositNotify(param);
						
					} else if (ThirdPartyBizFlagEnum.MEMBER_PAY_ORDER.getVal().equals(StringUtil.intToByte(bizFlagInt))) {
						Result<ShoppingOrderMoneyDTO> order = payOrderService.orderMoneyForNotify(param.getBizIds());
						double money = order.getModel().getOrderTotalPrice().doubleValue();
						if (StringUtil.doubleCompareTo(money, dTotalMoney) == 0) {
							Result<String> orderItemProductNameRet = payOrderService.getOrderItemProductName(param.getBizIds().split(",")[0]);
							param.setTitle(orderItemProductNameRet.getModel());

							result = orderService.doHandleOrderPayNotify(param);
						} else {
							result.setRet(ResultCode.NOTIFY_MONEY_ERROR);
							result.setMsg(ResultCode.get(ResultCode.NOTIFY_MONEY_ERROR));
						}
					} else if (ThirdPartyBizFlagEnum.MEMBER_PAY_BILL.getVal().equals(StringUtil.intToByte(bizFlagInt))) {
						ThirdPayCallBackQueryPayOrderDTO payOrderCallback = payOrderService.selectThirdPayCallBackQueryPayOrder(param.getBizIds());
						merchantUserNum = payOrderCallback.getBusinessUserNum();
						isSendMsg = true;
						if (StringUtil.doubleCompareTo(payOrderCallback.getActualMoney(), dTotalMoney) == 0) {
							param.setRegionPath(extra[6]);

							PayOrderBaseDTO dto = payOrderService.getPayOrderById(param.getBizIds());
							Result<MemberDTO> member = memberService.findMemberInfoById(dto.getMemberId());
							PayOrderMerchantStoreInfoDTO merchantStore = merchantStoreService.getPayOrderDetailStoreInfo(dto.getMerchantId());
							param.setTitle(merchantStore.getName());
							param.setTitleMerchant(StringUtil.hideUserAccount(member.getModel().getAccount()));

							result = orderService.doHandlePayOrderNotify(param);
						} else {
							result.setRet(ResultCode.NOTIFY_MONEY_ERROR);
							result.setMsg(ResultCode.get(ResultCode.NOTIFY_MONEY_ERROR));
						}
					} else if (ThirdPartyBizFlagEnum.MEMBER_RED_PACKET.getVal().equals(StringUtil.intToByte(bizFlagInt))) {
						Result<ThirdPayCallBackQueryPayOrderDTO> moneyResult = userRedPacketService.selectUserRedPacketInfoForThrid(Long.valueOf(param.getBizIds()));
						if (StringUtil.doubleCompareTo(moneyResult.getModel().getActualMoney(), dTotalMoney) == 0) {
							result = propertyinfoUserRedPacketService.doHandleMemberRedPacketNotify(param);
						} else {
							result.setRet(ResultCode.NOTIFY_MONEY_ERROR);
							result.setMsg(ResultCode.get(ResultCode.NOTIFY_MONEY_ERROR));
						}
					} else if (ThirdPartyBizFlagEnum.BUSINESS_ADD_AD.getVal().equals(StringUtil.intToByte(bizFlagInt))) {
						Result<AdPayInfoDTO> ad = adService.selectAdPayInfoById(Long.valueOf(param.getBizIds()));
						if (StringUtil.doubleCompareTo(ad.getModel().getTotalPoint().doubleValue(), dTotalMoney) == 0) {
							param.setRegionPath(extra[6]);
							result = propertyinfoAdService.doHandleMerchantAdNotify(param);
						} else {
							result.setRet(ResultCode.NOTIFY_MONEY_ERROR);
							result.setMsg(ResultCode.get(ResultCode.NOTIFY_MONEY_ERROR));
						}
					} else{
						result = successCreated(ResultCode.FAIL, "非法的业务类型回调");
					}
				}
			}
		}

		//成功过处理或已处理过(重复处理)
		if (ResultCode.SUCCESS == result.getRet() || ResultCode.PROCESSED_RETURN_SUCCESS == result.getRet()) {
			logger.info("APP支付宝回调成功");
			PrintWriter out = response.getWriter();
			out.print("success");// 请不要修改或删除
			out.flush();
			out.close();

			// ------------------------------发送站内消息
			if(isSendMsg && ResultCode.SUCCESS == result.getRet()){
				DecimalFormat df = new DecimalFormat("######0.00");
				MessageInfoParam messageInfoParam = new MessageInfoParam();
				messageInfoParam.setRelateId(0L);
				MessageTempParam messageTempParam = new MessageTempParam();
				messageTempParam.setRechargeBalance(new BigDecimal(df.format(dTotalMoney)));
				Result<PropertyPointAndBalanceDTO> moneyResult = propertyService.getPropertyInfoMoney(extra[1]);
				if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BALANCE.getVal().equals(StringUtil.intToByte(bizFlagInt))
						|| ThirdPartyBizFlagEnum.MEMBER_PAY_BALANCE.getVal().equals(StringUtil.intToByte(bizFlagInt))) {
					messageInfoParam.setTypeEnum(MessageTypeEnum.MESSAGE_TYPE_RECHARGE_BALANCE);
					messageTempParam.setBalance(moneyResult.getModel().getBalance().setScale(2, BigDecimal.ROUND_HALF_UP));
				} else if (ThirdPartyBizFlagEnum.BUSINESS_PAY_POINT.getVal().equals(StringUtil.intToByte(bizFlagInt))
						|| ThirdPartyBizFlagEnum.MEMBER_PAY_POINT.getVal().equals(StringUtil.intToByte(bizFlagInt))) {
					String property_key = PropertyType.MERCHANT_BALANCE_PAY_POINT_SCALE;
					if(ThirdPartyBizFlagEnum.MEMBER_PAY_POINT.getVal().equals(StringUtil.intToByte(bizFlagInt))) {
						property_key = PropertyType.MEMBER_BALANCE_PAY_POINT_SCALE;
					}
			        String scale = propertyService.getValue(property_key).getModel().toString();
					messageInfoParam.setTypeEnum(MessageTypeEnum.MESSAGE_TYPE_RECHARGE_POINT);
					messageTempParam.setPoint(moneyResult.getModel().getPoint().setScale(2, BigDecimal.ROUND_HALF_UP));
					messageTempParam.setRechargePoint(new BigDecimal(df.format(dTotalMoney*Double.valueOf(scale))));
				} else if(ThirdPartyBizFlagEnum.MEMBER_PAY_BILL.getVal().equals(StringUtil.intToByte(bizFlagInt))) {
					messageInfoParam.setTypeEnum(MessageTypeEnum.MESSAGE_TYPE_PAY_ORDER_SUCCESS_MERCHANT);
					messageTempParam.setOrderAmount(new BigDecimal(df.format(Double.valueOf(total_amount))));
				}
				if (extra[1].startsWith(UserCommonConstant.MEMBER_NUM_TAG)) {
					messageTempParam.setUserName("E店会员");
				} else if (extra[1].startsWith(UserCommonConstant.MERCHANT_NUM_TAG)) {
					messageTempParam.setUserName("E店商家");
				}
				messageInfoParam.setMessageParam(messageTempParam);
				messageService.saveMessage(ThirdPartyBizFlagEnum.MEMBER_PAY_BILL.getVal().equals(StringUtil.intToByte(bizFlagInt)) ? merchantUserNum : extra[1], messageInfoParam);
			}
			// ------------------------------发送站内消息

		} else {
			logger.error("APP支付宝回调失败,错误码:{},错误信息：{},回调参数：{}", result.getRet(), result.getMsg(), requestParams);
		}
		logger.info("#####################APP支付宝回调结束#####################");
	}

	/**
	 * 支付宝异步回调接口
	 * @throws IOException 
	 * @throws  
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "pcNotifyHandle", method = RequestMethod.POST)
	public void pcNotifyHandle() throws IOException  {
		logger.info("#####################PC支付宝回调开始#####################");
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		Result result = successCreated();

		String alipay_pc_public_key = externalApiConfig.getAlipayPublicKey();

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
		String extra_common_param = request.getParameter("extra_common_param");
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
		String buyer_email = new String(request.getParameter("buyer_email").getBytes("ISO-8859-1"), "UTF-8");

		AliPayConfigParam aliPayConfigParam = new AliPayConfigParam();
		aliPayConfigParam.setAlipayPublicKey(alipay_pc_public_key);
		aliPayConfigParam.setAlipayPartner(externalApiConfig.getAlipayPartner());
		aliPayConfigParam.setAlipayHttpsVerifyUrl(externalApiConfig.getAlipayHttpsVerifyUrl());
		aliPayConfigParam.setAlipaySignType(externalApiConfig.getAlipaySignType());
		aliPayConfigParam.setAlipayInputCharset(externalApiConfig.getAlipayInputCharset());

		boolean isSendMsg = false;
		double dTotalFee = 0;
		int bizFlagInt = 0;
		String extra[] = null;
		boolean	b = AlipayNotify.verify(params, aliPayConfigParam);

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

					extra = extra_common_param.split(splitStr);
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
					param.setTransactionPayTypeEnum(TransactionPayTypeEnum.ALIPAY);

					dTotalFee = Double.parseDouble(total_fee);
					bizFlagInt = Integer.parseInt(bizFlag);
					if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BALANCE.getVal().equals(StringUtil.intToByte(bizFlagInt))
							|| ThirdPartyBizFlagEnum.BUSINESS_PAY_POINT.getVal().equals(StringUtil.intToByte(bizFlagInt))) {
						result = rechargeService.doHandleRechargeNotify(param);
						isSendMsg = true;
						
					} else if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BOND.getVal().equals(StringUtil.intToByte(bizFlagInt))) {
						param.setMerchantId(Long.valueOf(extra[4]));
						result = depositService.doHandleDepositNotify(param);

					} else if (ThirdPartyBizFlagEnum.BUSINESS_ADD_AD.getVal().equals(StringUtil.intToByte(bizFlagInt))) {
						Result<AdPayInfoDTO> ad = adService.selectAdPayInfoById(Long.valueOf(param.getBizIds()));
						if (StringUtil.doubleCompareTo(ad.getModel().getTotalPoint().doubleValue(), dTotalFee) == 0) {
							param.setRegionPath(extra[4]);
							result = propertyinfoAdService.doHandleMerchantAdNotify(param);
						} else {
							result.setRet(ResultCode.NOTIFY_MONEY_ERROR);
							result.setMsg(ResultCode.get(ResultCode.NOTIFY_MONEY_ERROR));
						}
					}  else {
						result = successCreated(ResultCode.FAIL, "非法的业务类型回调");
					}
				}
			}
		}

		//成功过处理或已处理过(重复处理)
		if (ResultCode.SUCCESS == result.getRet() || ResultCode.PROCESSED_RETURN_SUCCESS == result.getRet()) {
			logger.info("APP支付宝回调成功");
			PrintWriter out = response.getWriter();
			out.print("success");// 请不要修改或删除
			out.flush();
			out.close();

			// ------------------------------发送站内消息
			if(isSendMsg && ResultCode.SUCCESS == result.getRet()){
				DecimalFormat df = new DecimalFormat("######0.00");
				MessageInfoParam messageInfoParam = new MessageInfoParam();
				messageInfoParam.setRelateId(0L);
				MessageTempParam messageTempParam = new MessageTempParam();
				messageTempParam.setRechargeBalance(new BigDecimal(df.format(dTotalFee)));
				Result<PropertyPointAndBalanceDTO> moneyResult = propertyService.getPropertyInfoMoney(extra[1]);
				if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BALANCE.getVal().equals(StringUtil.intToByte(bizFlagInt))) {
					messageInfoParam.setTypeEnum(MessageTypeEnum.MESSAGE_TYPE_RECHARGE_BALANCE);
					messageTempParam.setBalance(moneyResult.getModel().getBalance().setScale(2, BigDecimal.ROUND_HALF_UP));
				} else if (ThirdPartyBizFlagEnum.BUSINESS_PAY_POINT.getVal().equals(StringUtil.intToByte(bizFlagInt))) {
					messageInfoParam.setTypeEnum(MessageTypeEnum.MESSAGE_TYPE_RECHARGE_POINT);
					messageTempParam.setPoint(moneyResult.getModel().getPoint().setScale(2, BigDecimal.ROUND_HALF_UP));
				}
				messageTempParam.setUserName("E店商家");
				messageInfoParam.setMessageParam(messageTempParam);
				messageService.saveMessage(extra[1], messageInfoParam);
			}
			// ------------------------------发送站内消息

		} else {
			logger.error("PC支付宝回调失败,错误码:{},错误信息：{},回调参数：{}", result.getRet(), result.getMsg(), requestParams);
		}
		logger.info("#####################PC支付宝回调结束#####################");
	}

}
