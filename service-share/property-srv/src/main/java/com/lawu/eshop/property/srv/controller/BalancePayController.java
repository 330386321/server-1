package com.lawu.eshop.property.srv.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.constants.TransactionTitleEnum;
import com.lawu.eshop.property.param.BalancePayDataParam;
import com.lawu.eshop.property.srv.service.BalancePayService;

/**
 * 
 * <p>
 * Description: 余额支付
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月11日 下午5:23:33
 *
 */
@RestController
@RequestMapping(value = "balancePay/")
public class BalancePayController extends BaseController {

	@Autowired
	private BalancePayService balancePayService;

	/**
	 * 余额支付订单
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "orderPay", method = RequestMethod.POST)
	public Result orderPay(@RequestBody @Valid BalancePayDataParam param, BindingResult result) {
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			StringBuffer es = new StringBuffer();
			for (FieldError e : errors) {
				String msg = e.getDefaultMessage();
				es.append(msg);
			}
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, es.toString());
		}
		param.setMemberTransactionTypeEnum(MemberTransactionTypeEnum.PAY_ORDERS);
		param.setTitle(TransactionTitleEnum.ORDER_PAY.val);
		int retCode = balancePayService.balancePay(param);
		return successCreated(retCode);
	}

	/**
	 *  买单余额支付
	 * @param param
	 * @param result
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "billPay", method = RequestMethod.POST)
	public Result billPay(@RequestBody @Valid BalancePayDataParam param, BindingResult result) {
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			StringBuffer es = new StringBuffer();
			for (FieldError e : errors) {
				String msg = e.getDefaultMessage();
				es.append(msg);
			}
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, es.toString());
		}
		param.setMemberTransactionTypeEnum(MemberTransactionTypeEnum.PAY);
		param.setTitle(TransactionTitleEnum.PAY.val);
		int retCode = balancePayService.balancePay(param);
		return successCreated(retCode);
	}
	
	/**
	 * 余额充值积分
	 * @param param
	 * @param result
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "balancePayPoint", method = RequestMethod.POST)
	public Result balancePayPoint(@RequestBody @Valid BalancePayDataParam param, BindingResult result) {
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			StringBuffer es = new StringBuffer();
			for (FieldError e : errors) {
				String msg = e.getDefaultMessage();
				es.append(msg);
			}
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, es.toString());
		}
		param.setMemberTransactionTypeEnum(MemberTransactionTypeEnum.INTEGRAL_RECHARGE);
		param.setMerchantTransactionTypeEnum(MerchantTransactionTypeEnum.INTEGRAL_RECHARGE);
		param.setTitle(TransactionTitleEnum.INTEGRAL_RECHARGE.val);
		int retCode = balancePayService.balancePayPoint(param);
		return successCreated(retCode);
	}
}
