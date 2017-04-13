package com.lawu.eshop.merchant.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.merchant.api.service.RechargeService;
import com.lawu.eshop.property.param.RechargeSaveDataParam;
import com.lawu.eshop.property.param.RechargeSaveParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * <p>
 * Description: 商家充值中心
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月12日 下午8:34:25
 *
 */
@Api(tags = "recharge")
@RestController
@RequestMapping(value = "recharge/")
public class RechargeController extends BaseController {

	@Autowired
	private RechargeService rechargeService;

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "商家充值余额和积分", notes = "商家充值余额和积分，[]，(杨清华)", httpMethod = "POST")
	@Authorization
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Result save(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam RechargeSaveParam param) {
		
		RechargeSaveDataParam dparam = new  RechargeSaveDataParam();
		dparam.setRechargeMoney(param.getRechargeMoney());
		dparam.setPayTypeEnum(param.getPayTypeEnum());
		dparam.setTransactionPayTypeEnum(param.getTransactionPayTypeEnum());
		dparam.setUserNum(UserUtil.getCurrentUserNum(getRequest()));
		return rechargeService.save(dparam);

	}

}
