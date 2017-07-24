package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.ShoppingCartExtendService;
import com.lawu.eshop.order.dto.foreign.ShoppingCartQueryDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingCartSettlementDTO;
import com.lawu.eshop.order.param.ShoppingCartParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderBuyNowCreateOrderForeignParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderSettlementForeignParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MockShoppingCartExtendService implements ShoppingCartExtendService {


	@Override
	public Result<Long> save(Long memberId, ShoppingCartParam param) {
		return null;
	}

	@Override
	public Result<ShoppingCartQueryDTO> findListByMemberId(Long memberId) {
		return null;
	}

	@Override
	public Result<ShoppingCartSettlementDTO> settlement(List<Long> idList, String memberNum, Long memberId) {
		return null;
	}

	@Override
	public Result<List<Long>> createOrder(Long memberId, List<ShoppingOrderSettlementForeignParam> params) {
		return null;
	}

	@Override
	public Result<ShoppingCartSettlementDTO> buyNow(Long memberId, String memberNum, ShoppingCartParam param) {
		return null;
	}

	@Override
	public Result<Long> buyNowCreateOrder(Long memberId, ShoppingOrderBuyNowCreateOrderForeignParam param) {
		return null;
	}
}
