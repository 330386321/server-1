package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.framework.web.doc.annotation.Audit;
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
import com.lawu.eshop.merchant.api.service.CashManageFrontService;
import com.lawu.eshop.merchant.api.service.MerchantStoreService;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
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
	private MerchantStoreService merchantStoreService;

	@Audit(date = "2017-04-12", reviewer = "孙林青")
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "商家提现", notes = "商家提现，[1018|6001|6002|6003|6004|6005|6006|6007|6008]，(杨清华)", httpMethod = "POST")
	@Authorization
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Result save(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam CashParam param) {

		String cashMoney = param.getCashMoney();
		if (cashMoney.contains(".") && cashMoney.split("\\.")[1].length() > 2) {
			return successCreated(ResultCode.MONEY_IS_POINT_2);
		}

		CashDataParam dataParam = new CashDataParam();
		dataParam.setCashMoney(param.getCashMoney());
		dataParam.setBusinessBankAccountId(param.getBusinessBankAccountId());
		dataParam.setAccount(UserUtil.getCurrentAccount(getRequest()));
		dataParam.setUserNum(UserUtil.getCurrentUserNum(getRequest()));
		dataParam.setTransactionType(MerchantTransactionTypeEnum.WITHDRAW.getValue());
		dataParam.setUserType(UserTypeEnum.MEMCHANT.val);
		dataParam.setCashNumber(StringUtil.getRandomNum(""));
		
		Long merchantId = UserUtil.getCurrentUserId(getRequest());
		CashUserInfoDTO cashUserInfoDTO = merchantStoreService.findCashUserInfo(merchantId);
		if(cashUserInfoDTO == null){
			return successCreated(ResultCode.PROPERTY_CASH_USER_INFO_NULL);
		}
		dataParam.setName(cashUserInfoDTO.getName());
		dataParam.setProvinceId(cashUserInfoDTO.getProvinceId());
		dataParam.setCityId(cashUserInfoDTO.getCityId());
		dataParam.setAreaId(cashUserInfoDTO.getAreaId());
		dataParam.setRegionFullName(cashUserInfoDTO.getRegionFullName());
		
		return cashManageFrontService.save(dataParam);

	}

	@ApiOperation(value = "提现明细", notes = "商家提现明细，[]，(杨清华)", httpMethod = "POST")
	@Authorization
	@RequestMapping(value = "findCashList", method = RequestMethod.POST)
	public Result<Page<WithdrawCashQueryDTO>> findCashList(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute CashBillParam param) {
		CashBillDataParam cparam = new CashBillDataParam();
		cparam.setUserNum(UserUtil.getCurrentUserNum(getRequest()));
		return cashManageFrontService.findCashList(cparam);
	}

	@ApiOperation(value = "提现详情", notes = "商家提现详情，[]，(杨清华)", httpMethod = "GET")
	@Authorization
	@RequestMapping(value = "cashDetail/{id}", method = RequestMethod.GET)
	public Result<WithdrawCashDetailDTO> cashDetail(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@PathVariable @ApiParam(required = true, value = "提现记录ID") Long id) {
		return cashManageFrontService.cashDetail(id);
	}
}
