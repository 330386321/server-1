package com.lawu.eshop.order.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.dto.CommentOrderDTO;
import com.lawu.eshop.mall.param.ShoppingOrderSettlementParam;
import com.lawu.eshop.order.srv.bo.CommentOrderBO;
import com.lawu.eshop.order.srv.converter.ShoppingOrderConverter;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物订单
 * 
 * @author Sunny
 * @date 2017/04/06
 */
@RestController
@RequestMapping(value = "shoppingOrder/")
public class ShoppingOrderController extends BaseController {

	@Autowired
	private ShoppingOrderService shoppingOrderService;

	/**
	 * 保存订单
	 * 
	 * @param param
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Result<List<Long>> save(@RequestBody List<ShoppingOrderSettlementParam> params) {
		
		// 参数验证
		if (params == null || params.isEmpty()) {
			successCreated(ResultCode.REQUIRED_PARM_EMPTY);
		}
		
		List<Long> ids = shoppingOrderService.save(params);
		
		if (ids == null || params.isEmpty()) {
			return successCreated(ResultCode.SAVE_FAIL);
		}
		
		return successCreated(ids);
	}

	/**
	 * 获取商品评价状态
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "getOrderCommentStatus/{orderId}",method = RequestMethod.GET)
	public Result<CommentOrderDTO> getOrderCommentStatus(@PathVariable("orderId") Long orderId){
		//查询订单商品评价状态
		CommentOrderBO commentOrderBO = shoppingOrderService.getOrderCommentStatusById(orderId);
		CommentOrderDTO commentOrderDTO = ShoppingOrderConverter.coverCommentStatusDTO(commentOrderBO);
		return successGet(commentOrderDTO);
	}

}
