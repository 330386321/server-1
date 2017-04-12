package com.lawu.eshop.order.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.constants.ShoppingOrderItemRefundStatusEnum;
import com.lawu.eshop.mall.param.ShoppingRefundDetailLogisticsInformationParam;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemBO;
import com.lawu.eshop.order.srv.bo.ShoppingRefundDetailBO;
import com.lawu.eshop.order.srv.service.ShoppingOrderItemService;
import com.lawu.eshop.order.srv.service.ShoppingRefundDetailService;

/**
 * 购物退货详情
 * 
 * @author Sunny
 * @date 2017/04/06
 */
@RestController
@RequestMapping(value = "shoppingRefundDetail/")
public class ShoppingRefundDetailController extends BaseController {

	@Autowired
	private ShoppingRefundDetailService shoppingRefundDetailservice;
	
	@Autowired
	private ShoppingOrderItemService shoppingOrderItemService;
	
	/**
	 * 买家提交退货物流
	 * 修改订单项退款状态为待退款
	 * 
	 * @param shoppingOrderitemId 购物订单项id
	 * @param param 参数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "fillLogisticsInformation/{id}", method = RequestMethod.PUT)
	public Result fillLogisticsInformation(@PathVariable("id") Long id, @RequestBody ShoppingRefundDetailLogisticsInformationParam param) {
		if (id == null || id <= 0) {
			return successCreated(ResultCode.ID_EMPTY);
		}
		
		ShoppingRefundDetailBO shoppingRefundDetailBO = shoppingRefundDetailservice.get(id);
		
		if (shoppingRefundDetailBO == null || shoppingRefundDetailBO.getId() == null || shoppingRefundDetailBO.getId() <= 0) {
			return successCreated(ResultCode.RESOURCE_NOT_FOUND);
		}
		
		ShoppingOrderItemBO shoppingOrderItemBO = shoppingOrderItemService.get(shoppingRefundDetailBO.getShoppingOrderItemId());
		
		if (shoppingOrderItemBO == null || shoppingOrderItemBO.getId() == null || shoppingOrderItemBO.getId() <= 0) {
			return successCreated(ResultCode.RESOURCE_NOT_FOUND);
		}
		
		// 只有退款状态为待退货才能被允许填写退货物流
		if (!shoppingOrderItemBO.getRefundStatus().equals(ShoppingOrderItemRefundStatusEnum.TO_BE_RETURNED)){
			return successCreated(ResultCode.NOT_RETURNED_STATE);
		}
		
		// 修改订单项的退款状态为待退款状态，更新退款详情的物流信息
		shoppingRefundDetailservice.fillLogisticsInformation(shoppingRefundDetailBO, param);
		
		return successCreated();
	}
}
