package com.lawu.eshop.property.srv.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.property.srv.bo.BankAccountBO;
import com.lawu.eshop.property.srv.domain.BankAccountDO;
import com.lawu.eshop.property.srv.service.BankAccountService;

/**
 * 银行卡管理接口实现
 * @author zhangrc
 * @date 2017/03/29
 *
 */
@Service
public class BankAccountServiceImpl implements BankAccountService {
	
	@Autowired

	@Override
	public Integer saveBankAccount(BankAccountDO bankAccountDO) {
		return null;
	}

	@Override
	public List<BankAccountBO> findMyBank(Long userId) {
		return null;
	}

}
