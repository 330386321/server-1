package com.lawu.eshop.merchant.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.param.ShoppingOrderLogisticsInformationParam;

/**
 * @author Sunny
 * @date 2017/04/12
 */
@FeignClient(value = "order-srv")
public interface ShoppingOrderService {

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
}
