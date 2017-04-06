package com.lawu.eshop.property.srv.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.property.param.BankAccountParam;
import com.lawu.eshop.property.srv.bo.BankAccountBO;
import com.lawu.eshop.property.srv.converter.BankAccountConverter;
import com.lawu.eshop.property.srv.domain.BankAccountDO;
import com.lawu.eshop.property.srv.domain.BankAccountDOExample;
import com.lawu.eshop.property.srv.domain.BankDO;
import com.lawu.eshop.property.srv.mapper.BankAccountDOMapper;
import com.lawu.eshop.property.srv.mapper.BankDOMapper;
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
	private BankAccountDOMapper bankAccountDOMapper;
	
	@Autowired
	private BankDOMapper bankDOMapper;
	

	@Override
	@Transactional
	public Integer saveBankAccount(String userNum,BankAccountParam bankAccountParam) {
		String accountNumber=bankAccountParam.getAccountNumber();
		String reg="^(?!0)\\d{15,19}$"; 
		boolean flag=accountNumber.matches(reg);
		if(!flag){
			return 0;
		}
		BankAccountDO bankAccountDO=new BankAccountDO();
		bankAccountDO.setUserNum(userNum);
		bankAccountDO.setAccountName(bankAccountParam.getAccountName());
		bankAccountDO.setAccountNumber(bankAccountParam.getAccountNumber());
		bankAccountDO.setBankId(bankAccountParam.getBankId());
		bankAccountDO.setSubBranchName(bankAccountParam.getSubBranchName());
		bankAccountDO.setStatus(new Byte("1"));
		Integer id= bankAccountDOMapper.insert(bankAccountDO);
		return id;
	}

	@Override
	public List<BankAccountBO> selectMyBank(String userNum) {
		BankAccountDOExample example = new BankAccountDOExample();
		example.createCriteria().andUserNumEqualTo(userNum).andStatusEqualTo(new Byte("1"));
		List<BankAccountDO> DOS=bankAccountDOMapper.selectByExample(example);
		List<BankDO> bankDOS=new ArrayList<BankDO>();
		for (BankAccountDO bankAccountDO : DOS) {
			BankDO bankDO=bankDOMapper.selectByPrimaryKey(bankAccountDO.getBankId());
			bankDOS.add(bankDO);
		}
		return DOS.isEmpty() ? null :BankAccountConverter.convertBOS(DOS,bankDOS);
	}

	@Override
	@Transactional
	public Integer remove(Long id) {
		BankAccountDOExample example = new BankAccountDOExample();
		example.createCriteria().andIdEqualTo(id);
		BankAccountDO bankAccount=new BankAccountDO();
		bankAccount.setStatus(new Byte("0"));
		Integer i=bankAccountDOMapper.updateByExampleSelective(bankAccount, example);
		return i;
	}

}
