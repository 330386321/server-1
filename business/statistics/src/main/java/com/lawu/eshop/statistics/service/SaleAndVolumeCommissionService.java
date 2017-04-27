package com.lawu.eshop.statistics.service;

import java.util.List;

import com.lawu.eshop.order.dto.ShoppingOrderCommissionDTO;

public interface SaleAndVolumeCommissionService {

	/**
	 * 
	 * @param successOrderIds
	 *            计算提成成功的订单ID
	 * @param orders
	 *            需要计算提成的订单
	 * @param msg
	 *            log信息
	 * @param bTypeVal
	 *            商家操作类型
	 * @param mTypeVal
	 *            用户操作类型
	 * @param bTypeName
	 *            商家操作名称
	 * @param mTypeName
	 *            用户操作名称
	 */
	void commission(List<Long> successOrderIds, List<ShoppingOrderCommissionDTO> orders, String msg);

}
