package com.lawu.eshop.member.api.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.dto.BankAccountDTO;
import com.lawu.eshop.property.param.BankAccountParam;

/**
 * 会员api 银行卡管理接口
 * @author zhangrc
 * @date 2017/03/30
 *
 */
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
	Result<List<BankAccountDTO>> selectMyBank(@RequestParam("userNum") String userNum);
	
	/**
	 * 删除银行卡
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "bankAccount/remove/{id}")
    Result delete(@PathVariable("id") Long id);
	
	/**
	 * 单个查询
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "bankAccount/selectAccount/{id}", method = RequestMethod.GET)
    public Result<BankAccountDTO> selectAccount(@PathVariable("id") Long id);
	
	
	/**
	 * 修改
	 * @param id
	 * @param bankAccountParam
	 * @return
	 */
	@RequestMapping(value = "bankAccount/updateBankAccount/{id}", method = RequestMethod.PUT)
    public Result updateBankAccount(@PathVariable("id") Long id,@RequestBody BankAccountParam bankAccountParam);

}
