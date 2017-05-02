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
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.merchant.api.service.ReportFansService;
import com.lawu.eshop.user.dto.ReportFansRiseRateDTO;
import com.lawu.eshop.user.param.ReportFansDataParam;
import com.lawu.eshop.user.param.ReportFansParam;
import com.lawu.eshop.utils.BeanUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * <p>
 * Description: 报表----粉丝数据
 * </p>
 * 
 * @author Yangqh
 * @date 2017年5月2日 下午2:13:10
 *
 */
@Api(tags = "reportFans")
@RestController
@RequestMapping(value = "reportFans/")
public class ReportFansController extends BaseController {

	@Autowired
	private ReportFansService reportFansService;

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "粉丝数据，粉丝增长量", notes = "粉丝数据，粉丝增长量(日增长、月增长)。[]，(杨清华)", httpMethod = "GET")
	@Authorization
	@RequestMapping(value = "fansRiseRate", method = RequestMethod.GET)
	public Result<ReportFansRiseRateDTO> fansRiseRate(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam ReportFansParam param) throws Exception {
		Long merchantId = UserUtil.getCurrentUserId(getRequest());
		if (merchantId == 0L) {
			return successCreated(ResultCode.MEMBER_NO_EXIST);
		}
		ReportFansDataParam dparam = new ReportFansDataParam();
		BeanUtil.copyProperties(param, dparam);
		dparam.setMerchantId(merchantId);
		return reportFansService.fansRiseRate(dparam);
	}

}
