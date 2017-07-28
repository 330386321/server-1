package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.ShoppingRefundDetailService;
import com.lawu.eshop.order.dto.foreign.ShoppingRefundDetailDTO;
import com.lawu.eshop.order.param.ShoppingRefundDetailLogisticsInformationParam;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


@Service
public class MockShoppingRefundDetailService implements ShoppingRefundDetailService {

	@Override
	public Result fillLogisticsInformation(@PathVariable("id") Long id, @RequestParam("memberId") Long memberId, @RequestBody ShoppingRefundDetailLogisticsInformationParam param) {
		return null;
	}

	@Override
	public Result platformIntervention(@PathVariable("id") Long id, @RequestParam("memberId") Long memberId) {
		return null;
	}

	@Override
	public Result<ShoppingRefundDetailDTO> getRefundDetail(@PathVariable("shoppingOrderItemId") Long shoppingOrderItemId, @RequestParam("memberId") Long memberId) {
		return null;
	}

	@Override
	public Result revokeRefundRequest(@PathVariable("id") Long id, @RequestParam("memberId") Long memberId) {
		return null;
	}
}
