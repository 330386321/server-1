package com.lawu.eshop.statistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.order.dto.ShoppingOrderCommissionDTO;
import com.lawu.eshop.statistics.service.ProductOrderCommissionService;
import com.lawu.eshop.statistics.service.OrderSrvService;
import com.lawu.eshop.statistics.service.SaleAndVolumeCommissionService;

@Service
public class ProductOrderCommissionServiceImpl implements ProductOrderCommissionService {

	//private static Logger logger = LoggerFactory.getLogger(ProductOrderCommissionServiceImpl.class);

	@Autowired
	private OrderSrvService orderSrvService;
	@Autowired
	private SaleAndVolumeCommissionService saleAndVolumeCommissionService;

	@Override
	public void executeAutoConsumeAndSalesCommission() {

		// 查询确认收货后7天冻结资金转给商家后且未计算提成的订单
		// 查询出14天系统自动确认收货且未计算提成的订单
		Result<List<ShoppingOrderCommissionDTO>> ordersResult = orderSrvService.commissionShoppingOrder();
		List<ShoppingOrderCommissionDTO> orders = ordersResult.getModel();

		saleAndVolumeCommissionService.commission(orders,2, "商品订单销售");

	}

}
