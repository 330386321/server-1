package com.lawu.eshop.order.srv.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.dto.ShoppingCartDTO;
import com.lawu.eshop.mall.param.ShoppingCartSaveParam;
import com.lawu.eshop.mall.param.ShoppingCartUpdateParam;
import com.lawu.eshop.order.srv.converter.ShoppingCartConverter;
import com.lawu.eshop.order.srv.domain.ShoppingCartDO;
import com.lawu.eshop.order.srv.service.ShoppingCartService;

/**
 * @author Sunny
 * @date 2017/3/27
 */
@RestController
@RequestMapping(value = "shoppingCart/")
public class ShoppingCartController extends BaseController {

	@Autowired
	private ShoppingCartService shoppingCartService;

	/**
	 * 加入购物车
	 * 
	 * @param param
	 */
	@RequestMapping(value = "{memberId}", method = RequestMethod.POST)
	public Result save(@PathVariable("memberId") Long memberId, @RequestBody ShoppingCartSaveParam param) {
		// 参数验证
		if (param == null || StringUtils.isEmpty(param.getMerchantName()) || param.getMerchantId() == null 
				|| param.getProductId() == null || param.getProductModelId() == null
				|| param.getQuantity() == null || param.getSalesPrice() == null || param.getQuantity() == null) {
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
		}
		
		Long id = shoppingCartService.save(memberId, param);
		
		if (id == null || id <= 0) {
			return successCreated(ResultCode.SAVE_FAIL);
		}
		
		return successCreated(ResultCode.SUCCESS);
	}
	
	/**
	 * 根据memberId查询用户的购物车列表
	 * 
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "list/{memberId}",  method = RequestMethod.GET)
	Result<List<ShoppingCartDTO>> findListByMemberId(@PathVariable(name = "memberId") Long memberId){
		if (memberId == null) {
			return successGet(ResultCode.REQUIRED_PARM_EMPTY);
		}
		
		return successGet(ShoppingCartConverter.convertDTOS(shoppingCartService.findListByMemberId(memberId)));
	}
	
	/**
	 * 根据id更新购物车的商品（使用实时更新不采用批量更新的方式）
	 * 
	 * @param id
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
	public Result update(@PathVariable(name = "id") Long id, @RequestParam("memberId") Long memberId, @RequestBody ShoppingCartUpdateParam param) {
		if (id == null) {
			return successCreated(ResultCode.ID_EMPTY);
		}
		
		// 判断id对应的数据是否存在
		ShoppingCartDO shoppingCartDO = shoppingCartService.get(id);
		if (shoppingCartDO == null || shoppingCartDO.getId() == null || shoppingCartDO.getId() <= 0) {
			return successCreated(ResultCode.RESOURCE_NOT_FOUND);
		}
		
		if (!shoppingCartDO.getMemberId().equals(memberId)) {
			return successCreated(ResultCode.PRODUCT_NOT_FOUND_IN_CART);
		}
		
		shoppingCartService.update(id, param);
		
		return successCreated(ResultCode.SUCCESS);
	}
	
	/**
	 * 根据id删除购物车的商品
	 * 
	 * @param id
	 */
	@RequestMapping(value = "delete/{id}", method = RequestMethod.PUT)
	public Result delete(@PathVariable(name = "id") Long id, @RequestParam("memberId") Long memberId) {
		if (id == null) {
			return successCreated(ResultCode.ID_EMPTY);
		}
		
		ShoppingCartDO shoppingCartDO = shoppingCartService.get(id);
		if (shoppingCartDO == null || shoppingCartDO.getId() == null || shoppingCartDO.getId() <= 0) {
			return successCreated(ResultCode.RESOURCE_NOT_FOUND);
		}
		
		if (!shoppingCartDO.getMemberId().equals(memberId)) {
			return successCreated(ResultCode.PRODUCT_NOT_FOUND_IN_CART);
		}
		
		shoppingCartService.remove(id);
		
		return successCreated(ResultCode.SUCCESS);
	}
}
