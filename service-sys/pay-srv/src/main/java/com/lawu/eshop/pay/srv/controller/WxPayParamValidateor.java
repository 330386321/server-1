package com.lawu.eshop.pay.srv.controller;

import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.param.ThirdPayDataParam;

public class WxPayParamValidateor {

	/**
	 * app支付宝支付时请求服务获取加密参数接口，参数校验
	 * @param param
	 * @return
	 */
//	public static int appAlipayReqValidate(ThirdPayDataParam param){
//		if(param.getSubject() == null || "".equals(param.getSubject())){
//			return ResultCode.ALIPAY_INIT_VALIDATOR_SUBJECT_NULL;
//		}
//		if(param.getTotalAmount() == null || "".equals(param.getTotalAmount())){
//			return ResultCode.ALIPAY_INIT_VALIDATOR_MONEY_NULL;
//		}else if (param.getTotalAmount().contains(".") && param.getTotalAmount().split("\\.")[1].length() > 2) {
//			return ResultCode.MONEY_IS_POINT_2;
//		}
//		if(param.getOutTradeNo() == null || "".equals(param.getOutTradeNo())){
//			return ResultCode.ALIPAY_INIT_VALIDATOR_OUT_TRADE_NO_NULL;
//		}
//		if(param.getUserTypeEnum() == null || "".equals(param.getUserTypeEnum())){
//			return ResultCode.ALIPAY_INIT_VALIDATOR_USER_TYPE_NULL;
//		}
//		if(param.getThirdPayBodyEnum().val == null || "".equals(param.getThirdPayBodyEnum().val)){
//			return ResultCode.ALIPAY_INIT_VALIDATOR_BODY_NULL;
//		}
//		if(param.getUserNum() == null || "".equals(param.getUserNum())){
//			return ResultCode.ALIPAY_INIT_VALIDATOR_USER_NUM_NULL;
//		}
//		if(param.getBizFlagEnum() == null || "".equals(param.getBizFlagEnum())){
//			return ResultCode.ALIPAY_INIT_VALIDATOR_BIZ_FLAG_NULL;
//		}
//		return ResultCode.SUCCESS;
//	}
//
//	/**
//	 * pc调用支付宝是入参校验
//	 * @param param
//	 * @return
//	 */
//	public static int pcAlipayReqValidate(ThirdPayDataParam param) {
//		if(param.getSubject() == null || "".equals(param.getSubject())){
//			return ResultCode.ALIPAY_INIT_VALIDATOR_SUBJECT_NULL;
//		}
//		if(param.getTotalAmount() == null || "".equals(param.getTotalAmount())){
//			return ResultCode.ALIPAY_INIT_VALIDATOR_MONEY_NULL;
//		}else if (param.getTotalAmount().contains(".") && param.getTotalAmount().split("\\.")[1].length() > 2) {
//			return ResultCode.MONEY_IS_POINT_2;
//		}
//		if(param.getOutTradeNo() == null || "".equals(param.getOutTradeNo())){
//			return ResultCode.ALIPAY_INIT_VALIDATOR_OUT_TRADE_NO_NULL;
//		}
//		if(param.getUserNum() == null || "".equals(param.getUserNum())){
//			return ResultCode.ALIPAY_INIT_VALIDATOR_USER_NUM_NULL;
//		}
//		if(param.getBizFlagEnum() == null || "".equals(param.getBizFlagEnum())){
//			return ResultCode.ALIPAY_INIT_VALIDATOR_BIZ_FLAG_NULL;
//		}
//		return ResultCode.SUCCESS;
//	}
}
