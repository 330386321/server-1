package com.lawu.eshop.member.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.member.api.service.BankAccountService;
import com.lawu.eshop.property.dto.BankAccountDTO;
import com.lawu.eshop.property.param.BankAccountParam;
import com.lawu.eshop.user.param.AddressParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

@Api(tags = "bankAccount")
@RestController
@RequestMapping(value = "bankAccount/")
public class BankAccountController extends BaseController{
	
	@Autowired
	private BankAccountService bankAccountService;
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	@Authorization
    @ApiOperation(value = "我绑定的银行卡", notes = "查询当前用户绑定的银行卡[1000|1001]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Result<List<BankAccountDTO>> selectMyBank(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        String userNum = UserUtil.getCurrentUserNum(getRequest());
        Result<List<BankAccountDTO>> bankAccountDTOS = bankAccountService.selectMyBank(userNum);
        return bankAccountDTOS;
    }
	
	@Authorization
    @ApiOperation(value = "添加银行卡", notes = "添加银行卡[1000|1001|6000]（张荣成）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "saveBankAccount", method = RequestMethod.POST)
    public Result saveBankAccount(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                             @ModelAttribute @ApiParam(required = true, value = "银行卡信息") BankAccountParam bankAccountParam) {
		String userNum = UserUtil.getCurrentUserNum(getRequest());
        Result rs = bankAccountService.saveBankAccount(userNum, bankAccountParam);
        return rs;
    }

}
