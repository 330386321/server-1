package com.lawu.eshop.merchant.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.merchant.api.service.BusinessDepositService;
import com.lawu.eshop.property.dto.BusinessDepositDetailDTO;
import com.lawu.eshop.property.param.BusinessDepositSaveDataParam;
import com.lawu.eshop.property.param.BusinessDepositSaveParam;
import com.lawu.eshop.property.param.BusinessRefundDepositParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * <p>
 * Description: 商家保证金
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月15日 上午10:57:34
 *
 */
@Api(tags="businessDeposit")
@RestController
@RequestMapping(value = "businessDeposit/")
public class BusinessDepositController extends BaseController {

	@Autowired
	private BusinessDepositService businessDepositService;

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "商家缴纳保证金初始化记录", notes = "商家缴纳保证金初始化记录，支付前需要调该接口初始化一条保证金记录，[]，(杨清华)", httpMethod = "POST")
	@Authorization
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Result save(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam BusinessDepositSaveParam param) {
		BusinessDepositSaveDataParam bparam = new BusinessDepositSaveDataParam();
		bparam.setBusinessId(UserUtil.getCurrentUserId(getRequest()));
		bparam.setUserNum(UserUtil.getCurrentUserNum(getRequest()));
		bparam.setBusinessAccount(UserUtil.getCurrentAccount(getRequest()));
		bparam.setBusinessName(param.getBusinessName());
		bparam.setProvinceId(param.getProvinceId());
		bparam.setCityId(param.getCityId());
		bparam.setAreaId(param.getAreaId());
		return businessDepositService.save(bparam);
	}

	@ApiOperation(value = "查看我的保证金", notes = "查看我的保证金,[]（杨清华）", httpMethod = "GET")
	@Authorization
	@RequestMapping(value = "selectDeposit/{businessId}", method = RequestMethod.GET)
	public Result<BusinessDepositDetailDTO> selectDeposit(@PathVariable("businessId") String businessId) {
		return businessDepositService.selectDeposit(businessId);
	}
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "申请退保证金操作", notes = "申请退保证金,[]（杨清华）", httpMethod = "POST")
	@Authorization
	@RequestMapping(value = "refundDeposit", method = RequestMethod.POST)
	public Result refundDeposit(@ModelAttribute @ApiParam BusinessRefundDepositParam param) {
		return businessDepositService.refundDeposit(param);
	}
}
