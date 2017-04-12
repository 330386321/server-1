package com.lawu.eshop.member.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.param.ShoppingRefundDetailLogisticsInformationParam;

/**
 * @author Sunny
 * @date 2017/04/06
 */
@FeignClient(value= "order-srv")
public interface ShoppingRefundDetailService {
	
	/**
	 * 买家提交退货物流
	 * 修改订单项退款状态为待退款
	 * 
	 * @param shoppingOrderitemId 购物订单项id
	 * @param param 参数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "shoppingRefundDetail/fillLogisticsInformation/{id}", method = RequestMethod.PUT)
	Result fillLogisticsInformation(@PathVariable("id") Long id, @RequestBody ShoppingRefundDetailLogisticsInformationParam param);
	
}
