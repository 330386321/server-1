package com.lawu.eshop.merchant.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.order.dto.ShoppingOrderIsNoOnGoingOrderDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExtendDetailDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderItemRefundForMerchantDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderNumberOfOrderStatusForMerchantForeignDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderQueryToMerchantDTO;
import com.lawu.eshop.order.param.ShoppingOrderLogisticsInformationParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderQueryForeignToMerchantParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundQueryForeignParam;

/**
 * @author Sunny
 * @date 2017/04/12
 */
@FeignClient(value = "order-srv")
public interface ShoppingOrderService {
	
	/**
	 * To商家
	 * 根据查询参数分页查询
	 * 
	 * @param merchantId
	 *            商家id
	 * @param params
	 *            查询参数
	 * @return
	 */
	@RequestMapping(value = "shoppingOrder/selectPageByMerchantId/{merchantId}", method = RequestMethod.POST)
	Result<Page<ShoppingOrderQueryToMerchantDTO>> selectPageByMerchantId(@PathVariable("merchantId") Long merchantId, @RequestBody ShoppingOrderQueryForeignToMerchantParam param);
	
	/**
	 * 根据id查询订单详情
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@RequestMapping(value = "shoppingOrder/get/{id}", method = RequestMethod.GET)
	Result<ShoppingOrderExtendDetailDTO> get(@PathVariable("id") Long id);
	
	/**
	 * 商家发货填写物流信息 并修改购物订单以及购物订单项的状态为待收货
	 * 
	 * @param id
	 *            购物订单id
	 * @param param
	 *            订单物流参数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "shoppingOrder/fillLogisticsInformation/{id}", method = RequestMethod.PUT)
	Result fillLogisticsInformation(@PathVariable("id") Long id, ShoppingOrderLogisticsInformationParam param);
	
	/**
	 * 查询商家是否有正在进行中的订单
	 * 
	 * @param merchantId 商家id
	 * @return
	 * @author Sunny
	 */
	@RequestMapping(value = "shoppingOrder/isNoOnGoingOrder/{merchantId}", method = RequestMethod.GET)
	Result<ShoppingOrderIsNoOnGoingOrderDTO> isNoOnGoingOrder(@PathVariable("merchantId") Long merchantId);
	
	/**
	 * 根据查询参数分页查询退款记录
	 * 购物订单 购物订单项 退款详情关联查询
	 * To Merchant
	 * 
	 * @param merchantId
	 *            商家id
	 * @param param
	 *            查询参数
	 * @return
	 */
	@RequestMapping(value = "shoppingOrder/selectRefundPageByMerchantId/{merchantId}", method = RequestMethod.POST)
	Result<Page<ShoppingOrderItemRefundForMerchantDTO>> selectRefundPageByMerchantId(@PathVariable("merchantId") Long merchantId, @RequestBody ShoppingRefundQueryForeignParam param);
	
	/**
	 * 查询各种订单状态的数量
	 * To Merchant
	 * 
	 * @param merchantId 商家id
	 * @return
	 * @author Sunny
	 */
	@RequestMapping(value = "shoppingOrder/numberOfOrderStartusByMerchant/{merchantId}", method = RequestMethod.GET)
	Result<ShoppingOrderNumberOfOrderStatusForMerchantForeignDTO> numberOfOrderStartusByMerchant(@PathVariable("memberId") Long merchantId);
}
