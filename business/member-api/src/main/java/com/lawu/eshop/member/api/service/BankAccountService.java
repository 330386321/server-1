package com.lawu.eshop.member.api.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.dto.BankAccountDTO;
import com.lawu.eshop.property.param.BankAccountParam;

@FeignClient(value = "property-srv")
public interface BankAccountService {
	
	
	/**
	 * 添加银行卡
	 * @param bankAccountDO
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "bankAccount/saveBankAccount")
	Result saveBankAccount(@RequestParam("userNum") String userNum,@RequestBody BankAccountParam bankAccountParam);
	
	/**
	 * 查询我绑定的银行卡
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "bankAccount/selectMyBankAccount")
	Result<List<BankAccountDTO>> selectMyBank(String userNum);

}
