package com.lawu.eshop.property.srv.service;

import java.util.List;

import com.lawu.eshop.property.param.BankAccountParam;
import com.lawu.eshop.property.srv.bo.BankAccountBO;

/**
 * 银行卡管理接口
 * @author zhangrc
 * @date 2017/03/29
 *
 */
public interface BankAccountService {
	
	/**
	 * 添加银行卡
	 * @param bankAccountParam
	 * @return
	 */
	Integer saveBankAccount(String userNum,BankAccountParam bankAccountParam);
	
	/**
	 * 查询我绑定的银行卡
	 * @return
	 */
	List<BankAccountBO> selectMyBank(String userNum);
	
	/**
	 * 删除绑定的银行卡
	 * @param id
	 * @return
	 */
	Integer remove(Long id);
	
	/**
	 * 根据卡号查询
	 * @param account
	 * @return
	 */
	Boolean selectByAccount(String account,String userNum);
	
	/**
	 * 单个查询
	 * @param id
	 * @return
	 */
	BankAccountBO selectAccount(Long id);
	
	/**
	 * 修改银行卡
	 * @param id
	 * @param bankAccountParam
	 * @return
	 */
	Integer updateBankAccount(Long id ,BankAccountParam bankAccountParam);
	

}
