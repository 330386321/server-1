package com.lawu.eshop.member.api.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.CommentOrderDTO;
import com.lawu.eshop.mall.param.ShoppingOrderSettlementParam;

/**
 * @author Sunny
 * @date 2017/04/06
 */
@FeignClient(value= "order-srv")
public interface ShoppingOrderService {
	
	/**
	 * 批量保存订单
	 * 
	 * @param params
	 */
	@RequestMapping(value = "shoppingOrder/save", method = RequestMethod.POST)
	Result<List<Long>> save(@RequestBody List<ShoppingOrderSettlementParam> params);

	/**
	 * 查询订单评价状态
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "shoppingOrder/getOrderCommentStatus/{orderId}", method = RequestMethod.GET)
    Result<CommentOrderDTO> getOrderCommentStatus(@PathVariable("orderId") Long orderId);
}
