package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
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

import java.util.ArrayList;
import java.util.List;

@Service
public class MockCashManageFrontService extends BaseController implements CashManageFrontService {

	@Override
	public Result save(@RequestBody CashDataParam cash) {
		return successCreated();
	}

	@Override
	public Result<Page<WithdrawCashQueryDTO>> findCashList(@RequestBody CashBillDataParam cparam) {
		WithdrawCashQueryDTO dto = new WithdrawCashQueryDTO();
		List<WithdrawCashQueryDTO> list = new ArrayList<>();
		list.add(dto);
		Page<WithdrawCashQueryDTO> page = new Page();
		page.setRecords(list);
		page.setCurrentPage(1);
		page.setTotalCount(100);
		return successCreated(page);
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
		return successCreated(true);
	}
}
