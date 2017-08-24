package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.BalancePayService;
import com.lawu.eshop.property.param.BalancePayDataParam;
import com.lawu.eshop.property.param.BalancePayParam;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class MockBalancePayService extends BaseController implements BalancePayService {


	@Override
	public Result orderPay(@RequestBody BalancePayDataParam param) {
		return successCreated();
	}

	@Override
	public Result billPay(BalancePayDataParam dparam) {
		return successCreated();
	}

	@Override
	public Result balancePayPoint(BalancePayParam param) {
		return successCreated();
	}

	@Override
	public Result memberRedPacketPay(BalancePayDataParam param) {
		return null;
	}
}
