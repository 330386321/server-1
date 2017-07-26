package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.RechargeService;
import com.lawu.eshop.order.dto.ThirdPayCallBackQueryPayOrderDTO;
import com.lawu.eshop.property.param.RechargeSaveDataParam;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class MockRechargeService implements RechargeService {


	@Override
	public Result save(@RequestBody RechargeSaveDataParam param) {
		return null;
	}

	@Override
	public ThirdPayCallBackQueryPayOrderDTO getRechargeMoney(@RequestParam("rechargeId") String rechargeId) {
		ThirdPayCallBackQueryPayOrderDTO dto = new ThirdPayCallBackQueryPayOrderDTO();
		dto.setActualMoney(Double.parseDouble("100"));
		dto.setOrderNum("4213123123");
		return dto;
	}
}
