package com.lawu.eshop.member.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.member.api.service.CashManageFrontService;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.dto.WithdrawCashDetailDTO;
import com.lawu.eshop.property.dto.WithdrawCashQueryDTO;
import com.lawu.eshop.property.param.CashBillDataParam;
import com.lawu.eshop.property.param.CashBillParam;
import com.lawu.eshop.property.param.CashDataParam;
import com.lawu.eshop.property.param.CashParam;
import com.lawu.eshop.user.constants.UserTypeEnum;
import com.lawu.eshop.user.dto.CashUserInfoDTO;
import com.lawu.eshop.utils.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * <p>
 * Description: 前端用户提现管理
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月5日 下午6:16:25
 *
 */
@Api(tags = "cashFront")
@RestController
@RequestMapping(value = "cashFront/")
public class CashManageFrontController extends BaseController {

	@Autowired
	private CashManageFrontService cashManageFrontService;
	@Autowired
	private MemberService memberService;

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "用户提现", notes = "用户提现，[6001|6002|6003|6004|6005|6006|6007|6008]，(杨清华)", httpMethod = "POST")
//	@Authorization
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Result save(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam CashParam param) {

		String cashMoney = param.getCashMoney();
		if (cashMoney.contains(".") && cashMoney.split(".")[1].length() > 2) {
			return successCreated(ResultCode.MONEY_IS_POINT_2);
		}

		CashDataParam dataParam = new CashDataParam();
		dataParam.setCashMoney(param.getCashMoney());
		dataParam.setBusinessBankAccountId(param.getBusinessBankAccountId());
		dataParam.setAccount(UserUtil.getCurrentAccount(getRequest()));
		dataParam.setUserNum(UserUtil.getCurrentUserNum(getRequest()));
		dataParam.setTransactionType(MemberTransactionTypeEnum.WITHDRAW.getValue());
		dataParam.setUserType(UserTypeEnum.MEMBER.val);
		dataParam.setCashNumber(StringUtil.getRandomNum(""));
		
		Long memberId = UserUtil.getCurrentUserId(getRequest());
		CashUserInfoDTO cashUserInfoDTO = memberService.findCashUserInfo(memberId);
		if(cashUserInfoDTO == null){
			return successCreated(ResultCode.PROPERTY_CASH_USER_INFO_NULL);
		}
		dataParam.setName(cashUserInfoDTO.getName());
		dataParam.setProvinceId(cashUserInfoDTO.getProvinceId());
		dataParam.setCityId(cashUserInfoDTO.getCityId());
		dataParam.setAreaId(cashUserInfoDTO.getAreaId());

		return cashManageFrontService.save(dataParam);

	}

	@ApiOperation(value = "提现明细", notes = "用户提现明细，[]，(杨清华)", httpMethod = "POST")
	@Authorization
	@RequestMapping(value = "findCashList", method = RequestMethod.POST)
	public Result<Page<WithdrawCashQueryDTO>> findCashList(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam CashBillParam param) {
		CashBillDataParam cparam = new CashBillDataParam();
		cparam.setUserNum(UserUtil.getCurrentUserNum(getRequest()));
		return cashManageFrontService.findCashList(cparam);
	}

	@ApiOperation(value = "提现详情", notes = "用户提现详情，[]，(杨清华)", httpMethod = "GET")
	@Authorization
	@RequestMapping(value = "cashDetail/{id}", method = RequestMethod.GET)
	public Result<WithdrawCashDetailDTO> cashDetail(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@PathVariable @ApiParam(required = true, value = "提现记录ID") Long id) {
		return cashManageFrontService.cashDetail(id);
	}
}
