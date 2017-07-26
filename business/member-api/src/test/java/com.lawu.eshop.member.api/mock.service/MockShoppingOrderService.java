package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.ShoppingOrderService;
import com.lawu.eshop.order.dto.CommentOrderDTO;
import com.lawu.eshop.order.dto.ShoppingOrderMoneyDTO;
import com.lawu.eshop.order.dto.ShoppingOrderPaymentDTO;
import com.lawu.eshop.order.dto.foreign.*;
import com.lawu.eshop.order.param.ShoppingOrderRequestRefundParam;
import com.lawu.eshop.order.param.ShoppingOrderSettlementParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderQueryForeignToMemberParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundQueryForeignParam;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@Service
public class MockShoppingOrderService extends BaseController implements ShoppingOrderService {

	@Override
	public Result<List<Long>> save(@RequestBody List<ShoppingOrderSettlementParam> params) {
		return null;
	}

	@Override
	public Result<CommentOrderDTO> getOrderCommentStatus(@PathVariable("shoppingOrderItemId") Long shoppingOrderItemId) {
		return null;
	}

	@Override
	public Result<Page<ShoppingOrderExtendQueryDTO>> selectPageByMemberId(@PathVariable("memberId") Long memberId, @RequestBody ShoppingOrderQueryForeignToMemberParam param) {
		return null;
	}

	@Override
	public Result<ShoppingOrderExtendDetailDTO> get(@PathVariable("id") Long id, @RequestParam("memberId") Long memberId) {
		return null;
	}

	@Override
	public Result<ShoppingOrderExpressDTO> getExpressInfo(@PathVariable("id") Long id, @RequestParam("memberId") Long memberId) {
		return null;
	}

	@Override
	public Result cancelOrder(@PathVariable("memberId") Long memberId, @PathVariable("id") Long id) {
		return null;
	}

	@Override
	public Result deleteOrder(@PathVariable("memberId") Long memberId, @PathVariable("id") Long id) {
		return null;
	}

	@Override
	public Result tradingSuccess(@PathVariable("id") Long id) {
		return null;
	}

	@Override
	public Result requestRefund(@PathVariable("shoppingOrderitemId") Long shoppingOrderitemId, @RequestParam("memberId") Long memberId, @RequestBody ShoppingOrderRequestRefundParam param) {
		return null;
	}

	@Override
	public Result<Page<ShoppingOrderItemRefundDTO>> selectRefundPageByMemberId(@PathVariable("memberId") Long memberId, @RequestBody ShoppingRefundQueryForeignParam param) {
		return null;
	}

	@Override
	public Result<ShoppingOrderMoneyDTO> selectOrderMoney(@RequestParam("orderIds") String orderIds) {
		ShoppingOrderMoneyDTO dto = new ShoppingOrderMoneyDTO();
		dto.setOrderTotalPrice(new BigDecimal("100"));
		return successCreated(dto);
	}

	@Override
	public Result<ShoppingOrderNumberOfOrderStatusDTO> numberOfOrderStartus(@PathVariable("memberId") Long memberId) {
		return null;
	}

	@Override
	public Result<ShoppingOrderPaymentDTO> orderPayment(@PathVariable("id") Long id, @RequestParam("memberId") Long memberId) {
		return null;
	}
}
