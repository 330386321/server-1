package com.lawu.eshop.external.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.order.dto.ShoppingOrderMoneyDTO;
import com.lawu.eshop.order.dto.ThirdPayCallBackQueryPayOrderDTO;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年5月12日 下午3:35:28
 *
 */
@FeignClient(value = "order-srv")
public interface PayOrderService {

    /**
     * 第三方支付时获取买单的实际总金额，用于调用第三方支付平台
     * @param orderId
     * @return
     * @author Yangqh
     */
	@RequestMapping(method = RequestMethod.GET, value = "payOrder/selectThirdPayCallBackQueryPayOrder")
	ThirdPayCallBackQueryPayOrderDTO selectThirdPayCallBackQueryPayOrder(@RequestParam("orderId") String orderId);
	
	/**
	 * 获取商品订单的总金额
	 * @param orderIds
	 * @return
	 * @author Sunny
	 */
	@RequestMapping(method = RequestMethod.GET, value = "shoppingOrder/selectOrderMoney")
	Result<ShoppingOrderMoneyDTO> selectOrderMoney(@RequestParam("orderIds") String orderIds);
}
