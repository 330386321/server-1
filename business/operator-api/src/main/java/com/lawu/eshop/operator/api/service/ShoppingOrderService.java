package com.lawu.eshop.operator.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExtendDetailDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderQueryToOperatorDTO;
import com.lawu.eshop.order.param.ShoppingOrderUpdateInfomationParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderQueryForeignToOperatorParam;

/**
 * @author Sunny
 * @date 2017/04/12
 */
@FeignClient(value = "order-srv")
public interface ShoppingOrderService {
	
	/**
	 * 根据查询参数分页查询
	 * 
	 * @param param
	 *            查询参数
	 * @return
	 */
	@RequestMapping(value = "shoppingOrder/selectPage", method = RequestMethod.POST)
	public Result<Page<ShoppingOrderQueryToOperatorDTO>> selectPage(@RequestBody ShoppingOrderQueryForeignToOperatorParam param);
	
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
	 * 更新订单信息
	 * @param id
	 * 			     购物订单id
	 * @param param
	 *            查询参数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "shoppingOrder/updateInformation/{id}", method = RequestMethod.PUT)
	Result updateInformation(@PathVariable("id") Long id , @RequestBody ShoppingOrderUpdateInfomationParam param);
	
	/**
	 * 取消购物订单
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "shoppingOrder/cancelOrder/{id}", method = RequestMethod.PUT)
	public Result cancelOrder(@PathVariable("id") Long id);
}
