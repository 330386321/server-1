package com.lawu.eshop.property.srv.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.dto.BankAccountDTO;
import com.lawu.eshop.property.param.BankAccountParam;
import com.lawu.eshop.property.srv.bo.BankAccountBO;
import com.lawu.eshop.property.srv.converter.BankAccountConverter;
import com.lawu.eshop.property.srv.service.BankAccountService;

/**
 * @author zhangrc
 * @date 2017/3/29
 */
@RestController
@RequestMapping(value = "bankAccount/")
public class BankAccountController extends BaseController{
	
	@Resource
	private BankAccountService bankAccountService;
	
	/**
	 * 银行卡绑定
	 * @param userNum
	 * @param bankAccountParam
	 * @return
	 */
	@RequestMapping(value = "saveBankAccount", method = RequestMethod.POST)
    public Result saveBankAccount(String userNum,BankAccountParam bankAccountParam) {
		Integer id= bankAccountService.saveBankAccount(userNum,bankAccountParam);
		if(id>0){
    		return successCreated(ResultCode.SUCCESS);
    	}else if(id==0){
    		return successCreated(ResultCode.BANK_ACCOUNT_ERROR);
    	}else{
    		return successCreated(ResultCode.FAIL);
    	}
    }
	
	/**
	 * 查询已经绑定的银行卡
	 * @param userNum
	 * @return
	 */
	@RequestMapping(value = "selectMyBankAccount", method = RequestMethod.GET)
    public Result<List<BankAccountDTO>> selectMyBankAccount(@RequestParam String userNum) {
		List<BankAccountBO> BOS = bankAccountService.selectMyBank(userNum);
		return  successAccepted(BankAccountConverter.convertDTOS(BOS));
    }

}
