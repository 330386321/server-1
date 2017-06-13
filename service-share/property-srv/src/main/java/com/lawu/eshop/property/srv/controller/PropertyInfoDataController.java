package com.lawu.eshop.property.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.param.PropertyInfoDataParam;
import com.lawu.eshop.property.srv.service.PropertyInfoDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 
 * <p>
 * Description:处理存在积分操作的业务 ：
 * 
 * 商家减积分：商家邀请粉丝、商家发布广告、商家发红包
 * 会员加积分：抢红包
 * 
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
	 * 减积分的业务：
	 * 
	 * 商家邀请粉丝、商家发布广告、商家发红包、大额抢占扣除积分
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "doHanlderMinusPoint", method = RequestMethod.POST)
	public Result doHanlderMinusPoint(@RequestBody @Valid PropertyInfoDataParam param, BindingResult result) {
		String message = validate(result);
    	if (message != null) {
    		return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
    	}
		
		int retCode = propertyInfoDataService.doHanlderMinusPoint(param);
		return successCreated(retCode);
	}

	/**
	 * 加积分的业务：
	 * 
	 * 抢红包、
	 * 
	 * @param param
	 * @param result
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "memberRedPacketAddPoint", method = RequestMethod.POST)
	public Result memberRedPacketAddPoint(@RequestBody @Valid PropertyInfoDataParam param, BindingResult result) {
		String message = validate(result);
    	if (message != null) {
    		return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
    	}
		
		int retCode = propertyInfoDataService.doHanlderAddPoint(param);
		return successCreated(retCode);
	}
}
