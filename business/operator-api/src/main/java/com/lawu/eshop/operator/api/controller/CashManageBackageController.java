package com.lawu.eshop.operator.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.operator.api.service.CashManageBackageService;
import com.lawu.eshop.property.dto.WithdrawCashBackageQueryDTO;
import com.lawu.eshop.property.param.CashBackageQueryDataParam;
import com.lawu.eshop.property.param.CashBackageQueryParam;
import com.lawu.eshop.utils.BeanUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

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

}
