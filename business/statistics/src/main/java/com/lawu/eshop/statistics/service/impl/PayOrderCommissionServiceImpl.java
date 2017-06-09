package com.lawu.eshop.statistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.order.dto.ShoppingOrderCommissionDTO;
import com.lawu.eshop.statistics.service.OrderSrvService;
import com.lawu.eshop.statistics.service.PayOrderCommissionService;
import com.lawu.eshop.statistics.service.SaleAndVolumeCommissionService;

@Service
public class PayOrderCommissionServiceImpl implements PayOrderCommissionService {

//	private static Logger logger = LoggerFactory.getLogger(PayOrderCommissionServiceImpl.class);

	@Autowired
	private OrderSrvService orderSrvService;
	@Autowired
	private SaleAndVolumeCommissionService saleAndVolumeCommissionService;

	@Override
	public void executeAutoConsumeAndSalesCommission() {
		//查询支付成功且未计算提成的买单
		Result<List<ShoppingOrderCommissionDTO>> ordersResult = orderSrvService.selectNotCommissionOrder();
		List<ShoppingOrderCommissionDTO> orders = ordersResult.getModel();

		saleAndVolumeCommissionService.commission(orders,1, "买单销售");

	}

}
