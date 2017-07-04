package com.lawu.eshop.property.srv.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
    public Result saveBankAccount(@RequestParam String userNum,@RequestBody BankAccountParam bankAccountParam) {
		Boolean  flag=bankAccountService.selectByAccount(bankAccountParam.getAccountNumber(),userNum);
		if(!flag){
			return successCreated(ResultCode.BANK_ACCOUNT_IS_EXIST);
		}
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
	
	/**
	 * 删除绑定的银行卡
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "remove/{id}", method = RequestMethod.DELETE)
    public Result remove(@PathVariable Long id) {
		Integer i = bankAccountService.remove(id);
		if(i>0){
    		return successDelete();
    	}else{
    		return successCreated(ResultCode.FAIL);
    	}
    }
	
	/**
	 * 单个查询
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "selectAccount/{id}", method = RequestMethod.GET)
    public Result<BankAccountDTO> selectAccount(@PathVariable Long id) {
		BankAccountBO BO = bankAccountService.selectAccount(id);
		return  successAccepted(BankAccountConverter.convertDTO(BO));
    }
	
	/**
	 * 修改
	 * @param id
	 * @param bankAccountParam
	 * @return
	 */
	@RequestMapping(value = "updateBankAccount/{id}", method = RequestMethod.PUT)
    public Result updateBankAccount(@PathVariable Long id,@RequestParam String userNum,@RequestBody BankAccountParam bankAccountParam) {
		Boolean  flag=bankAccountService.selectByAccount(bankAccountParam.getAccountNumber(),userNum);
		if(!flag){
			return successCreated(ResultCode.BANK_ACCOUNT_IS_EXIST);
		}
		bankAccountService.updateBankAccount(id, bankAccountParam);
		return  successCreated();
    }

}
