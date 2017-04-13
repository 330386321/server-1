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
import com.lawu.eshop.property.constants.PayTypeEnum;
import com.lawu.eshop.property.constants.PropertyType;
import com.lawu.eshop.property.dto.RechargeSaveDTO;
import com.lawu.eshop.property.param.NotifyCallBackParam;
import com.lawu.eshop.property.param.RechargeSaveDataParam;
import com.lawu.eshop.property.srv.service.PropertyService;
import com.lawu.eshop.property.srv.service.RechargeService;
import com.lawu.eshop.user.constants.UserCommonConstant;

/**
 * 
 * <p>
 * Description:充值
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月12日 下午8:35:46
 *
 */
@RestController
@RequestMapping(value = "recharge/")
public class RechargeController extends BaseController {

	@Autowired
	private RechargeService rechargeService;
	@Autowired
	private PropertyService propertySrevice;

	/**
	 * 用户商家第三方充值余额积分保存充值记录
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Result save(@RequestBody @Valid RechargeSaveDataParam param, BindingResult result)
			throws Exception {
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			StringBuffer es = new StringBuffer();
			for (FieldError e : errors) {
				String msg = e.getDefaultMessage();
				es.append(msg);
			}
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, es.toString());
		}
		String value = "1";//充值余额
		if (PayTypeEnum.POINT.val.equals(param.getPayTypeEnum().val)) {
			// 获取第三方支付充值积分的比例
			String name = PropertyType.MEMBER_THIRD_PAY_POINT;
			if (param.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)) {
				name = PropertyType.MERCHANT_THIRD_PAY_POINT;
			}
			value = propertySrevice.getValue(name);
			if ("".equals(value)) {
				value = PropertyType.THIRD_PAY_POINT_DEFAULT;
			}
		}
		param.setRechargeScale(value);
		RechargeSaveDTO dto = rechargeService.save(param);
		if(dto == null){
			return successCreated(ResultCode.FAIL, "充值失败！");
		}
		return successCreated(dto);
	}
	
	/**
	 * 用户/商家微信/支付宝充值余额积分回调
	 * @param param
	 * @param result
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "doHandleRechargeNotify", method = RequestMethod.POST)
	public Result doHandleRechargeNotify(@RequestBody @Valid NotifyCallBackParam param, BindingResult result) {
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			StringBuffer es = new StringBuffer();
			for (FieldError e : errors) {
				String msg = e.getDefaultMessage();
				es.append(msg);
			}
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, es.toString());
		}
		return rechargeService.doHandleRechargeNotify(param);
	}

	
}
