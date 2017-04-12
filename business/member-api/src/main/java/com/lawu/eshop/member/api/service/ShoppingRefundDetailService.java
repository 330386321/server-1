package com.lawu.eshop.member.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.foreign.ShoppingRefundDetailDTO;
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
	
	/**
	 * 如果商家拒绝买家的退款申请或者拒绝退款
	 * 买家可以申请平台介入
	 * 
	 * @param id 退款详情id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "shoppingRefundDetail/platformIntervention/{id}", method = RequestMethod.PUT)
	Result platformIntervention(@PathVariable("id") Long id);
	
	/**
	 * 根据订单项id查询退款详情
	 * 
	 * @param shoppingOrderitemId
	 *            购物订单项id
	 * @return
	 */
	@RequestMapping(value = "shoppingRefundDetail/getRefundDetail/{shoppingOrderItemId}", method = RequestMethod.GET)
	Result<ShoppingRefundDetailDTO> getRefundDetail(@PathVariable("shoppingOrderItemId") Long shoppingOrderItemId);
	
}
