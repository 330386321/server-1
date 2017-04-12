package com.lawu.eshop.member.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "order-srv")
public interface OrderService {

	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.GET, value = "shoppingOrder/selectOrderMoney")
	double selectOrderMoney(@RequestParam("orderIds") String orderIds);

}
