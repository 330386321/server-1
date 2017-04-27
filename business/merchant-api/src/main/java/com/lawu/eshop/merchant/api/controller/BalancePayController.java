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
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.merchant.api.service.BalancePayService;
import com.lawu.eshop.merchant.api.service.RechargeService;
import com.lawu.eshop.property.param.BalancePayDataParam;
import com.lawu.eshop.property.param.BalancePayParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * <p>
 * Description: 余额支付
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月11日 下午8:18:54
 *
 */
@Api(tags = "balancePay")
@RestController
@RequestMapping(value = "balancePay/")
public class BalancePayController extends BaseController {

	@Autowired
	private RechargeService rechargeService;
	@Autowired
	private BalancePayService balancePayService;

	@Audit(date = "2017-04-15", reviewer = "孙林青")
	@SuppressWarnings("rawtypes")
	@Authorization
	@ApiOperation(value = "余额充值积分", notes = "余额充值积分,[]（杨清华）", httpMethod = "POST")
	@RequestMapping(value = "balancePayPoint", method = RequestMethod.POST)
	public Result balancePayPoint(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam BalancePayParam param) {
		BalancePayDataParam dparam = new BalancePayDataParam();
		dparam.setBizIds(param.getBizIds());
		dparam.setUserNum(UserUtil.getCurrentUserNum(getRequest()));
		dparam.setAccount(UserUtil.getCurrentAccount(getRequest()));

		double money = rechargeService.getRechargeMoney(param.getBizIds());
		dparam.setTotalAmount(String.valueOf(money));

		return balancePayService.balancePayPoint(dparam);
	}
}
