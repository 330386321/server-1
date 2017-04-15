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
import com.lawu.eshop.property.constants.PropertyType;
import com.lawu.eshop.property.dto.BusinessDepositInitDTO;
import com.lawu.eshop.property.param.BusinessDepositSaveDataParam;
import com.lawu.eshop.property.param.NotifyCallBackParam;
import com.lawu.eshop.property.srv.service.BusinessDepositService;
import com.lawu.eshop.property.srv.service.PropertyService;

/**
 * 
 * <p>
 * Description: 商家保证金
 * </p>
 * @author Yangqh
 * @date 2017年4月15日 上午10:57:34
 *
 */
@RestController
@RequestMapping(value = "businessDeposit/")
public class BusinessDepositController extends BaseController {

	@Autowired
	private BusinessDepositService businessDepositService;
	@Autowired
	private PropertyService propertyService;

	/**
	 * 初始化保证金记录
	 * @param param
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Result<BusinessDepositInitDTO> save(@RequestBody @Valid BusinessDepositSaveDataParam param, BindingResult result) {
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			StringBuffer es = new StringBuffer();
			for (FieldError e : errors) {
				String msg = e.getDefaultMessage();
				es.append(msg);
			}
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, es.toString());
		}
		String deposit = propertyService.getValue(PropertyType.MERCHANT_BONT);
		if ("".equals(deposit)) {
			deposit = PropertyType.MERCHANT_BONT_DEFAULT;
		}
		param.setDeposit(deposit);
		BusinessDepositInitDTO dto = businessDepositService.save(param);
		return successCreated(dto);
	}

	/**
	 * 处理第三方支付回调
	 * @param param
	 * @param result
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "doHandleDepositNotify", method = RequestMethod.POST)
	public Result doHandleDepositNotify(@RequestBody @Valid NotifyCallBackParam param, BindingResult result) {
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			StringBuffer es = new StringBuffer();
			for (FieldError e : errors) {
				String msg = e.getDefaultMessage();
				es.append(msg);
			}
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, es.toString());
		}
		return businessDepositService.doHandleDepositNotify(param);
	}
}
