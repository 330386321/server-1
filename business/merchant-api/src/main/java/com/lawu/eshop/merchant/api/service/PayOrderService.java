package com.lawu.eshop.merchant.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * <p>
 * Description: 买单
 * </p>
 * @author Yangqh
 * @date 2017年4月13日 下午3:06:48
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
	@RequestMapping(method = RequestMethod.GET, value = "payOrder/selectPayOrderActueMoney")
	double selectPayOrderActueMoney(@RequestParam("orderId") String orderId);
}
