package com.lawu.eshop.operator.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.operator.api.service.CashManageBackageService;
import com.lawu.eshop.property.dto.WithdrawCashBackageQueryDTO;
import com.lawu.eshop.property.dto.WithdrawCashBackageQuerySumDTO;
import com.lawu.eshop.property.param.CashBackageOperDataParam;
import com.lawu.eshop.property.param.CashBackageOperParam;
import com.lawu.eshop.property.param.CashBackageQueryDataParam;
import com.lawu.eshop.property.param.CashBackageQueryDetailParam;
import com.lawu.eshop.property.param.CashBackageQueryParam;
import com.lawu.eshop.property.param.CashBackageQuerySumParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * <p>
 * Description: 运营后台提现管理
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月10日 下午2:08:11
 *
 */
@Api(tags = "cashBackage")
@RestController
@RequestMapping(value = "cashBackage/")
public class CashManageBackageController extends BaseController {

	@Autowired
	private CashManageBackageService cashManageBackageService;

	/**
	 * 运营平台财务提现管理
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "商家、用户提现管理", notes = "商家、用户提现明细查询,[]（杨清华）", httpMethod = "POST")
	// @Authorization
	@RequestMapping(value = "findCashInfo", method = RequestMethod.POST)
	public Result<Page<WithdrawCashBackageQueryDTO>> findCashInfo(@ModelAttribute @ApiParam CashBackageQueryParam param)
			throws Exception {
		CashBackageQueryDataParam dparam = new CashBackageQueryDataParam();
		dparam.setContent(param.getContent());
		dparam.setRegionPath(param.getRegionPath());
		dparam.setBeginDate(param.getBeginDate());
		dparam.setEndDate(param.getEndDate());
		dparam.setCashStatsuEnum(param.getCashStatsuEnum());
		dparam.setUserTypeEnum(param.getUserTypeEnum());

		Result<Page<WithdrawCashBackageQueryDTO>> dtos = cashManageBackageService.findCashInfo(dparam);
		return dtos;
	}

	@ApiOperation(value = "商家、用户提现管理总数统计", notes = "商家、用户提现明细查询总数统计,[]（杨清华）", httpMethod = "POST")
	// @Authorization
	@RequestMapping(value = "getTotalNum", method = RequestMethod.POST)
	public Result<WithdrawCashBackageQuerySumDTO> getTotalNum(
			@ModelAttribute @ApiParam CashBackageQuerySumParam param) {
		return cashManageBackageService.getTotalNum(param);
	}

	@ApiOperation(value = "商家、用户提现详情", notes = "商家、用户提现详情查询,[]（杨清华）", httpMethod = "POST")
	// @Authorization
	@RequestMapping(value = "findCashInfoDetail", method = RequestMethod.POST)
	public Result<Page<WithdrawCashBackageQueryDTO>> findCashInfoDetail(
			@ModelAttribute @ApiParam CashBackageQueryDetailParam param) {

		Result<Page<WithdrawCashBackageQueryDTO>> dtos = cashManageBackageService.findCashInfoDetail(param);
		return dtos;
	}
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "商家、用户提现数据操作", notes = "商家、用户提现数据操作,[]（杨清华）", httpMethod = "POST")
	@RequestMapping(value = "updateWithdrawCash", method = RequestMethod.POST)
	public Result updateWithdrawCash(@ModelAttribute @ApiParam CashBackageOperParam param) {
		CashBackageOperDataParam dparam = new CashBackageOperDataParam();
		dparam.setIds(param.getIds());
		dparam.setCashOperEnum(param.getCashOperEnum());
		dparam.setAuditFailReason(param.getAuditFailReason());
		//TODO 
		dparam.setAuditUserId(1L);
		dparam.setAuditUserName("super");
		Result result = cashManageBackageService.updateWithdrawCash(dparam);
		if(ResultCode.SUCCESS != result.getRet()){
			return result;
		}
		
		return result;
	}
}
