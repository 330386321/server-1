package com.lawu.eshop.merchant.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExpressDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingRefundDetailDTO;
import com.lawu.eshop.order.param.ShoppingRefundDetailRerurnAddressParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundDetailAgreeToApplyForeignParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundDetailAgreeToRefundForeignParam;

/**
 * @author Sunny
 * @date 2017/04/06
 */
@FeignClient(value = "order-srv")
public interface ShoppingRefundDetailService {

	/**
	 * 根据id查询退款详情的物流信息
	 * 
	 * @param id
	 *            退款详情id
	 * @return
	 */
	@RequestMapping(value = "shoppingRefundDetail/getExpressInfo/{id}", method = RequestMethod.GET)
	Result<ShoppingOrderExpressDTO> getExpressInfo(@PathVariable("id") Long id);

	/**
	 * 根据订单项id查询退款详情
	 * 
	 * @param shoppingOrderitemId
	 *            购物订单项id
	 * @return
	 */
	@RequestMapping(value = "shoppingRefundDetail/getRefundDetail/{shoppingOrderItemId}", method = RequestMethod.GET)
	Result<ShoppingRefundDetailDTO> getRefundDetail(@PathVariable("shoppingOrderItemId") Long shoppingOrderItemId, @RequestParam("merchantId") Long merchantId);

	/**
	 * 商家是否同意买家的退货申请
	 * 
	 * @param id
	 *            退款详情id
	 * @param param
	 *            参数 是否同意申请
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "shoppingRefundDetail/agreeToApply/{id}", method = RequestMethod.PUT)
	Result agreeToApply(@PathVariable("id") Long id, @RequestBody ShoppingRefundDetailAgreeToApplyForeignParam param);
	
	/**
	 * 商家填写退货地址 根据退款详情id更新退货地址
	 * 
	 * @param id
	 *            退款详情id
	 * @param param
	 *            参数 退货信息参数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "shoppingRefundDetail/fillReturnAddress/{id}", method = RequestMethod.PUT)
	Result fillReturnAddress(@PathVariable("id") Long id, @RequestBody ShoppingRefundDetailRerurnAddressParam param);
	
	/**
	 * 商家是否同意退款
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
}