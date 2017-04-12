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
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.constants.TransactionTitle;
import com.lawu.eshop.property.param.PropertyInfoDataParam;
import com.lawu.eshop.property.srv.service.PropertyInfoDataService;

/**
 * 
 * <p>
 * Description:处理存在积分操作的业务 ：商家邀请粉丝、商家发布广告
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月12日 下午12:54:59
 *
 */
@RestController
@RequestMapping(value = "propertyInfoData/")
public class PropertyInfoDataController extends BaseController {

	@Autowired
	private PropertyInfoDataService propertyInfoDataService;

	/**
	 * 商家粉丝邀请
	 * 
	 * @param userNum
	 * @param consumePoint
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "inviteFans/{userNum}", method = RequestMethod.POST)
	public Result inviteFans(@RequestBody @Valid PropertyInfoDataParam param, BindingResult result) {
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			StringBuffer es = new StringBuffer();
			for (FieldError e : errors) {
				String msg = e.getDefaultMessage();
				es.append(msg);
			}
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, es.toString());
		}
		param.setTitle(TransactionTitle.INVITE_FANS);
		param.setMerchantTransactionTypeEnum(MerchantTransactionTypeEnum.INVITE_FANS);
		int retCode = propertyInfoDataService.doHanlder(param);
		return successCreated(retCode);
	}

	/**
	 * 商家发广告扣除积分
	 * 
	 * @param param
	 * @param result
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "addAd", method = RequestMethod.POST)
	public Result addAd(@RequestBody @Valid PropertyInfoDataParam param, BindingResult result) {
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			StringBuffer es = new StringBuffer();
			for (FieldError e : errors) {
				String msg = e.getDefaultMessage();
				es.append(msg);
			}
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, es.toString());
		}
		param.setTitle(TransactionTitle.ADD_AD);
		param.setMerchantTransactionTypeEnum(MerchantTransactionTypeEnum.ADD_AD);
		int retCode = propertyInfoDataService.doHanlder(param);
		return successCreated(retCode);
	}

}
