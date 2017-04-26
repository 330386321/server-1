package com.lawu.eshop.statistics.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.order.dto.ShoppingOrderCommissionDTO;
import com.lawu.eshop.statistics.service.CommonPropertyService;
import com.lawu.eshop.statistics.service.ConsumeAndSalesCommissionService;

@Service
public class ConsumeAndSalesCommissionServiceImpl implements ConsumeAndSalesCommissionService {

	@Autowired
	private CommonPropertyService commonPropertyService;
	
	@Override
	public void executeAutoConsumeAndSalesCommission() {
		//查询确认收货后7天且未计算提成的订单
		//查询出系统自动确认收货且未计算提成的订单
		//查询订单相关用户商家的上级邀请关系
		//计算提成
		
		List<ShoppingOrderCommissionDTO> orders = new ArrayList<>();
		if (orders != null && orders.size() > 0) {

			Map<String, BigDecimal> property = commonPropertyService.getCommissionPropertys();
			
			
		}
		
	}

}
