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
import com.lawu.eshop.property.param.NotifyCallBackParam;
import com.lawu.eshop.property.srv.service.OrderService;

/**
 * 
 * <p>
 * Description: 商品订单
 * </p>
 * @author Yangqh
 * @date 2017年4月13日 下午1:56:41
 *
 */
@RestController
@RequestMapping(value = "order/")
public class OrderController extends BaseController {

	@Autowired
	private OrderService orderService;

	/**
	 * 用户微信/支付宝订单支付回调
	 * @param param
	 * @param result
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "doHandleOrderPayNotify", method = RequestMethod.POST)
	public Result doHandleOrderPayNotify(@RequestBody @Valid NotifyCallBackParam param, BindingResult result) {
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			StringBuffer es = new StringBuffer();
			for (FieldError e : errors) {
				String msg = e.getDefaultMessage();
				es.append(msg);
			}
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, es.toString());
		}
		return orderService.doHandleOrderPayNotify(param);
	}
	
	/**
	 * 第三方支付买单回调
	 * @param param
	 * @param result
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "doHandlePayOrderNotify", method = RequestMethod.POST)
	public Result doHandlePayOrderNotify(@RequestBody @Valid NotifyCallBackParam param, BindingResult result) {
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			StringBuffer es = new StringBuffer();
			for (FieldError e : errors) {
				String msg = e.getDefaultMessage();
				es.append(msg);
			}
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, es.toString());
		}
		return orderService.doHandlePayOrderNotify(param);
	}

	
}
