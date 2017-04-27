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
import com.lawu.eshop.statistics.service.ProductOrderCommissionService;
import com.lawu.eshop.statistics.service.OrderSrvService;
import com.lawu.eshop.statistics.service.SaleAndVolumeCommissionService;

@Service
public class ProductOrderCommissionServiceImpl implements ProductOrderCommissionService {

	private static Logger logger = LoggerFactory.getLogger(ProductOrderCommissionServiceImpl.class);

	@Autowired
	private OrderSrvService orderSrvService;
	@Autowired
	private SaleAndVolumeCommissionService saleAndVolumeCommissionService;

	@SuppressWarnings("rawtypes")
	@Override
	public void executeAutoConsumeAndSalesCommission() {

		// 查询确认收货后7天冻结资金转给商家后且未计算提成的订单
		// 查询出14天系统自动确认收货且未计算提成的订单
		Result<List<ShoppingOrderCommissionDTO>> ordersResult = orderSrvService.commissionShoppingOrder();
		List<ShoppingOrderCommissionDTO> orders = ordersResult.getModel();

		List<Long> successOrderIds = new ArrayList<Long>();
		saleAndVolumeCommissionService.commission(successOrderIds, orders, "商品订单销售");

		if (!successOrderIds.isEmpty() && successOrderIds.size() > 0) {
			Result result = orderSrvService.updateCommissionStatus(successOrderIds);
			if (result.getRet() != ResultCode.SUCCESS) {
				logger.error("商品订单提成更新订单状态返回错误,retCode={}", result.getRet());
			}
		}
	}

}
