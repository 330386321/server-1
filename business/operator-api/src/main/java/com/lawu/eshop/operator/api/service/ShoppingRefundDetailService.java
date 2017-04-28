package com.lawu.eshop.operator.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.order.param.foreign.ShoppingRefundDetailAgreeToRefundForeignParam;

/**
 * @author Sunny
 * @date 2017/04/14
 */
@FeignClient(value = "order-srv")
public interface ShoppingRefundDetailService {
	
	/**
	 * 退款给买家
	 * 
	 * @param id
	 *            退款详情id
	 * @param param
	 *            参数 是否同意申请
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "shoppingRefundDetail/agreeToRefund/{id}", method = RequestMethod.PUT)
	Result agreeToRefund(@PathVariable("id") Long id, @RequestBody ShoppingRefundDetailAgreeToRefundForeignParam param);
	
	/**
	 * 撤销退货申请
	 * 
	 * @param id
	 *            退款详情id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "shoppingRefundDetail/revokeRefundRequest/{id}", method = RequestMethod.PUT)
	Result revokeRefundRequest(@PathVariable("id") Long id);
}