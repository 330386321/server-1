package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.CashManageFrontService;
import com.lawu.eshop.property.dto.WithdrawCashDetailDTO;
import com.lawu.eshop.property.dto.WithdrawCashQueryDTO;
import com.lawu.eshop.property.dto.WithdrawCashStatusDTO;
import com.lawu.eshop.property.param.CashBillDataParam;
import com.lawu.eshop.property.param.CashDataParam;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class MockCashManageFrontService implements CashManageFrontService {

	@Override
	public Result save(@RequestBody CashDataParam cash) {
		return null;
	}

	@Override
	public Result<Page<WithdrawCashQueryDTO>> findCashList(@RequestBody CashBillDataParam cparam) {
		return null;
	}

	@Override
	public Result<WithdrawCashDetailDTO> cashDetail(@RequestParam("id") Long id) {
		return null;
	}

	@Override
	public Result<List<WithdrawCashStatusDTO>> findCashDetailStatus(@RequestParam("ids") List<Long> ids) {
		return null;
	}

	@Override
	public Result<Boolean> isExistCash(@RequestParam("userNum") String userNum, @RequestParam("bankAccountId") Long bankAccountId) {
		return null;
	}
}
