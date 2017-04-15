package com.lawu.eshop.operator.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.operator.api.service.BusinessDepositManageService;
import com.lawu.eshop.property.dto.BusinessDepositQueryDTO;
import com.lawu.eshop.property.param.BusinessDepositOperDataParam;
import com.lawu.eshop.property.param.BusinessDepositOperParam;
import com.lawu.eshop.property.param.BusinessDepositQueryDataParam;
import com.lawu.eshop.property.param.BusinessDepositQueryParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * <p>
 * Description: 运营平台商家保证金管理
 * </p>
 * @author Yangqh
 * @date 2017年4月15日 下午3:15:16
 *
 */
@Api(tags = "businessDepositBackage")
@RestController
@RequestMapping(value = "businessDepositBackage/")
public class BusinessDepositManageController extends BaseController {

	@Autowired
	private BusinessDepositManageService businessDepositManageService;

	/**
	 * 运营平台财务提现管理
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "保证金明细查询", notes = "保证金明细查询,[]（杨清华）", httpMethod = "POST")
	@RequestMapping(value = "selectDepositList", method = RequestMethod.POST)
	public Result<Page<BusinessDepositQueryDTO>> selectDepositList(@ModelAttribute @ApiParam BusinessDepositQueryParam param)
			throws Exception {
		BusinessDepositQueryDataParam dparam = new BusinessDepositQueryDataParam();
		dparam.setContent(param.getContent());
		dparam.setRegionPath(param.getRegionPath());
		dparam.setBeginDate(param.getBeginDate());
		dparam.setEndDate(param.getEndDate());
		dparam.setBusinessDepositStatusEnum(param.getBusinessDepositStatusEnum());
		dparam.setTransactionPayTypeEnum(param.getTransactionPayTypeEnum());
		Result<Page<BusinessDepositQueryDTO>> dtos = businessDepositManageService.selectDepositList(dparam);
		return dtos;
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "保证金运营处理操作", notes = "保证金运营处理操作(核实、受理、成功、失败),[]（杨清华）", httpMethod = "POST")
	@RequestMapping(value = "updateBusinessDeposit", method = RequestMethod.POST)
	public Result updateBusinessDeposit(@ModelAttribute @ApiParam BusinessDepositOperParam param) {
		BusinessDepositOperDataParam dparam = new BusinessDepositOperDataParam();
		dparam.setId(param.getId());
		dparam.setBusinessDepositOperEnum(param.getBusinessDepositOperEnum());
		dparam.setFailReason(param.getFailReason());
		dparam.setUserNum(param.getUserNum());
		//TODO 
		dparam.setOperUserId(1L);
		dparam.setOperUserName("super");
		return businessDepositManageService.updateBusinessDeposit(dparam);
	}
}
