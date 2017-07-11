package com.lawu.eshop.jobs.service;

import java.util.List;

import com.lawu.eshop.order.dto.ShoppingOrderCommissionDTO;

public interface SaleAndVolumeCommissionService {

	/**
	 * 
	 * @param orders
	 *            需要计算提成的订单
	 * @param flag
	 * 			  标记1-买单2-商品订单
	 * @param msg
	 *            log信息
	 */
	void commission(List<ShoppingOrderCommissionDTO> orders,int flag, String msg);

}
