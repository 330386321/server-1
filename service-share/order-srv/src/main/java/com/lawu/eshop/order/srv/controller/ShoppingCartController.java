package com.lawu.eshop.order.srv.controller;

import java.util.List;

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
import com.lawu.eshop.order.dto.ShoppingCartDTO;
import com.lawu.eshop.order.param.ShoppingCartSaveParam;
import com.lawu.eshop.order.param.ShoppingCartUpdateParam;
import com.lawu.eshop.order.srv.bo.ShoppingCartBO;
import com.lawu.eshop.order.srv.converter.ShoppingCartConverter;
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
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "{memberId}", method = RequestMethod.POST)
	public Result<Long> save(@PathVariable("memberId") Long memberId, @RequestBody ShoppingCartSaveParam param) {
		
		Result<Long> result = shoppingCartService.save(memberId, param);
		
		if (!isSuccess(result)) {
			return successCreated(result.getRet());
		}
		
		return successCreated(result);
	}
	
	/**
	 * 根据memberId查询用户的购物车列表
	 * 
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "list/{memberId}",  method = RequestMethod.GET)
	public Result<List<ShoppingCartDTO>> findListByMemberId(@PathVariable(name = "memberId") Long memberId){
		
		List<ShoppingCartBO> shoppingCartBOList = shoppingCartService.findListByMemberId(memberId);
		
		return successGet(ShoppingCartConverter.convertDTOS(shoppingCartBOList));
	}
	
	/**
	 * 根据id更新购物车的商品（使用实时更新不采用批量更新的方式）
	 * 
	 * @param id
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
	public Result update(@PathVariable(name = "id") Long id, @RequestParam("memberId") Long memberId, @RequestBody ShoppingCartUpdateParam param) {
		
		int resultCode = shoppingCartService.update(id, memberId, param);
		
		if (resultCode != ResultCode.SUCCESS) {
			return successCreated(resultCode);
		}
		
		return successCreated();
	}
	
	/**
	 * 根据id列表删除购物车的商品
	 * 
	 * @param memberId
	 * @param ids
	 * @return
	 * @author Sunny
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "delete", method = RequestMethod.PUT)
	public Result delete(@RequestParam("memberId") Long memberId, @RequestBody List<Long> ids) {
		int resultCode = shoppingCartService.remove(memberId, ids);
		
		if (resultCode != ResultCode.SUCCESS) {
			return successCreated(resultCode);
		}
		
		return successCreated();
	}
	
	
	/**
	 * 根据购物车id列表查询购物车列表
	 * 
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "list/findListByIds",  method = RequestMethod.GET)
	public Result<List<ShoppingCartDTO>> findListByIds(@RequestParam(name = "ids") List<Long> ids) {
		
		Result<List<ShoppingCartBO>> shoppingCartBOListResult = shoppingCartService.findListByIds(ids);
		
		if (!isSuccess(shoppingCartBOListResult)) {
			return successGet(shoppingCartBOListResult.getRet());
		}
		
		return successGet(ShoppingCartConverter.convertDTOS(shoppingCartBOListResult.getModel()));
	}
	
	/**
	 * 根据用户id列表查询购物车数量
	 * 
	 * @param memberId 用户id
	 * @return
	 */
	@RequestMapping(value = "count/{memberId}",  method = RequestMethod.GET)
	public Result<Long> count(@PathVariable("memberId") Long memberId) {
		return successGet(shoppingCartService.count(memberId));
	}
}
