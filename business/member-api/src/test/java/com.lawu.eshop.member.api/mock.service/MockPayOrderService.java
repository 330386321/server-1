package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.PayOrderService;
import com.lawu.eshop.order.constants.PayOrderStatusEnum;
import com.lawu.eshop.order.dto.MemberPayOrderInfoDTO;
import com.lawu.eshop.order.dto.PayOrderDTO;
import com.lawu.eshop.order.dto.PayOrderIdDTO;
import com.lawu.eshop.order.dto.ThirdPayCallBackQueryPayOrderDTO;
import com.lawu.eshop.order.param.PayOrderListParam;
import com.lawu.eshop.order.param.PayOrderParam;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Service
public class MockPayOrderService implements PayOrderService {

	@Override
	public Result<PayOrderIdDTO> savePayOrderInfo(@PathVariable("memberId") Long memberId, @ModelAttribute PayOrderParam param, @RequestParam("numNum") String numNum) {
		return null;
	}

	@Override
	public Result<Page<PayOrderDTO>> getpayOrderList(@PathVariable("memberId") Long memberId, @ModelAttribute PayOrderListParam param) {
		return null;
	}

	@Override
	public Result delPayOrderInfo(@PathVariable("id") Long id, @RequestParam("memberId") Long memberId) {
		return null;
	}

	@Override
	public ThirdPayCallBackQueryPayOrderDTO selectThirdPayCallBackQueryPayOrder(@RequestParam("orderId") String orderId) {
		ThirdPayCallBackQueryPayOrderDTO dto = new ThirdPayCallBackQueryPayOrderDTO();
		dto.setPayOrderStatusEnum(PayOrderStatusEnum.STATUS_UNPAY);
		dto.setBusinessUserNum("B00001");
		dto.setActualMoney(Double.parseDouble("100"));
		dto.setOrderNum("323212321312");
		return dto;
	}

	@Override
	public Result<MemberPayOrderInfoDTO> getOrderInfo(@RequestParam("id") Long id, @RequestParam("memberId") Long memberId) {
		return null;
	}
}
