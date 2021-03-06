package com.lawu.eshop.property.srv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.lawu.eshop.property.dto.BankAccountNameDTO;
import com.lawu.eshop.property.dto.BankAccountOperatorDTO;
import com.lawu.eshop.property.param.BankAccountOperatorParam;
import com.lawu.eshop.property.param.BankAccountParam;
import com.lawu.eshop.property.srv.bo.BankAccountBO;
import com.lawu.eshop.property.srv.bo.BankAccountOperatorBO;
import com.lawu.eshop.property.srv.converter.BankAccountConverter;
import com.lawu.eshop.property.srv.service.BankAccountService;

/**
 * @author zhangrc
 * @date 2017/3/29
 */
@RestController
@RequestMapping(value = "bankAccount/")
public class BankAccountController extends BaseController{
	
	@Autowired
	private BankAccountService bankAccountService;
	
	/**
	 * 银行卡绑定
	 * @param userNum
	 * @param bankAccountParam
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "saveBankAccount", method = RequestMethod.POST)
    public Result saveBankAccount(@RequestParam String userNum,@RequestBody BankAccountParam bankAccountParam) {
		Boolean  flag=bankAccountService.selectByAccount(bankAccountParam.getAccountNumber(),bankAccountParam.getUserType().val,userNum);
		if(flag){
			return successCreated(ResultCode.BANK_ACCOUNT_IS_EXIST);
		}
		bankAccountService.saveBankAccount(userNum,bankAccountParam);
    	return successCreated();
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
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "remove/{id}", method = RequestMethod.DELETE)
    public Result remove(@PathVariable Long id) {
		bankAccountService.remove(id);
		return successDelete();
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
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "updateBankAccount/{id}", method = RequestMethod.PUT)
    public Result updateBankAccount(@PathVariable Long id,@RequestParam String userNum,@RequestBody BankAccountParam bankAccountParam) {
		BankAccountBO bo = bankAccountService.selectAccount(id);
		if(!bo.getAccountNumber().equals(bankAccountParam.getAccountNumber())){
			Boolean  flag=bankAccountService.selectByAccount(bankAccountParam.getAccountNumber(),bankAccountParam.getUserType().val,userNum);
			if(flag){
				return successCreated(ResultCode.BANK_ACCOUNT_IS_EXIST);
			}
		}
		bankAccountService.updateBankAccount(id, bankAccountParam);
		return  successCreated();
    }
	
	/**
	 * 获取银行卡用户名称
	 * @param userNum
	 * @return
	 */
	@RequestMapping(value = "selectBankName", method = RequestMethod.GET)
    public Result<BankAccountNameDTO> selectBankName(@RequestParam String userNum) {
		String name = bankAccountService.selectBankName(userNum);
		BankAccountNameDTO dto = new BankAccountNameDTO();
		dto.setAccountName(name);
		return  successCreated(dto);
    }
	
	/**
	 * 运营平台修改银行卡
	 * @param id
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "updateBankOperator/{id}", method = RequestMethod.PUT)
    public Result updateBankOperator(@PathVariable Long id,@RequestBody BankAccountOperatorParam param){
		BankAccountBO bo = bankAccountService.selectAccount(id);
		if(!bo.getAccountNumber().equals(param.getAccountNumber())){
			Boolean  flag=bankAccountService.selectByAccount(param.getAccountNumber(),param.getUserType().val,param.getUserNum());
			if(flag){
				return successCreated(ResultCode.BANK_ACCOUNT_IS_EXIST);
			}
		}
		bankAccountService.updateBankOperator(id, param);
		
		return  successCreated();
	}
	
	
	/**
	 * 运营平台查询用户银行卡列表
	 * @param userNum
	 * @return
	 */
	@RequestMapping(value = "selectBankOperator", method = RequestMethod.GET)
    public Result<List<BankAccountOperatorDTO>> selectBankOperator(@RequestParam String userNum) {
		List<BankAccountOperatorBO> BOS = bankAccountService.selectBankOperator(userNum);
		return  successAccepted(BankAccountConverter.convertOperatorDTOS(BOS));
    }

}
