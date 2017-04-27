package com.lawu.eshop.statistics.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.order.dto.ShoppingOrderCommissionDTO;
import com.lawu.eshop.statistics.service.OrderSrvService;
import com.lawu.eshop.statistics.service.PayOrderCommissionService;
import com.lawu.eshop.statistics.service.SaleAndVolumeCommissionService;

@Service
public class PayOrderCommissionServiceImpl implements PayOrderCommissionService {

	private static Logger logger = LoggerFactory.getLogger(PayOrderCommissionServiceImpl.class);

	@Autowired
	private OrderSrvService orderSrvService;
	@Autowired
	private SaleAndVolumeCommissionService saleAndVolumeCommissionService;

	@SuppressWarnings("rawtypes")
	@Override
	public void executeAutoConsumeAndSalesCommission() {
		//查询支付成功且未计算提成的买单
		Result<List<ShoppingOrderCommissionDTO>> ordersResult = orderSrvService.selectNotCommissionOrder();
		List<ShoppingOrderCommissionDTO> orders = ordersResult.getModel();

		List<Long> successOrderIds = new ArrayList<Long>();
		saleAndVolumeCommissionService.commission(successOrderIds, orders, "买单销售");

		if (!successOrderIds.isEmpty() && successOrderIds.size() > 0) {
			Result result = orderSrvService.updatePayOrderCommissionStatus(successOrderIds);
			if (result.getRet() != ResultCode.SUCCESS) {
				logger.error("买单提成更新订单状态返回错误,retCode={}", result.getRet());
			}
		}
	}

}
