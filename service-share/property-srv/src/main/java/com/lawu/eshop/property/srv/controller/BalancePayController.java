package com.lawu.eshop.property.srv.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.lawu.eshop.property.param.BalancePayParam;
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

//	private static Logger logger = LoggerFactory.getLogger(BalancePayController.class);
	
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
	public Result orderPay(@RequestBody @Valid BalancePayParam param, BindingResult result) {
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			StringBuffer es = new StringBuffer();
			for (FieldError e : errors) {
				String msg = e.getDefaultMessage();
				es.append(msg);
			}
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, es.toString());
		}
		int retCode = balancePayService.orderPay(param);
		return successCreated(retCode);
	}

}
