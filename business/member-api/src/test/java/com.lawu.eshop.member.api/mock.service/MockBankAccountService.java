package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.BankAccountService;
import com.lawu.eshop.property.dto.BankAccountDTO;
import com.lawu.eshop.property.param.BankAccountParam;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class MockBankAccountService implements BankAccountService {


	@Override
	public Result saveBankAccount(@RequestParam("userNum") String userNum, @RequestBody BankAccountParam bankAccountParam) {
		return null;
	}

	@Override
	public Result<List<BankAccountDTO>> selectMyBank(@RequestParam("userNum") String userNum) {
		return null;
	}

	@Override
	public Result delete(@PathVariable("id") Long id) {
		return null;
	}

	@Override
	public Result<BankAccountDTO> selectAccount(@PathVariable("id") Long id) {
		return null;
	}

	@Override
	public Result updateBankAccount(@PathVariable("id") Long id, @RequestParam("userNum") String userNum, @RequestBody BankAccountParam bankAccountParam) {
		return null;
	}
}
