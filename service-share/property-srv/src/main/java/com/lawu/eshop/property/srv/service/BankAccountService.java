package com.lawu.eshop.property.srv.service;

import java.util.List;

import com.lawu.eshop.property.srv.bo.BankAccountBO;
import com.lawu.eshop.property.srv.domain.BankAccountDO;

/**
 * 银行卡管理接口
 * @author zhangrc
 * @date 2017/03/29
 *
 */
public interface BankAccountService {
	
	/**
	 * 添加银行卡
	 * @param bankAccountDO
	 * @return
	 */
	Integer saveBankAccount(BankAccountDO bankAccountDO);
	
	/**
	 * 查询我绑定的银行卡
	 * @return
	 */
	List<BankAccountBO> findMyBank(Long userId);
	
	

}
