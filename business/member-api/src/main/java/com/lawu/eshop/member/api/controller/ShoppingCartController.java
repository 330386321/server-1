package com.lawu.eshop.member.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.ShoppingCartDTO;
import com.lawu.eshop.mall.param.ShoppingCartParam;
import com.lawu.eshop.member.api.service.ShoppingCartService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * @author Sunny 
 * @date 2017/3/27
 */
@Api(tags = "shoppingCart")
@RestController
@RequestMapping(value = "shoppingCart/")
public class ShoppingCartController extends BaseController {

    @Autowired
    private ShoppingCartService shoppingCartService;
    
    @ApiOperation(value = "加入购物车", notes = "加入购物车。[1000|1004|1005]（蒋鑫俊）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@ModelAttribute @ApiParam(name = "parm", required = true, value = "购物车资料") ShoppingCartParam param) {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
    	return successCreated(shoppingCartService.save(memberId, param));
    }
    
    @ApiOperation(value = "查询用户的购物车列表", notes = "根据memberId查询用户的购物车列表。[1000|1004]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(method = RequestMethod.GET)
    Result<List<ShoppingCartDTO>> findListByMemberId() {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
    	return successGet(shoppingCartService.findListByMemberId(memberId));
    }
    
    @ApiOperation(value = "更新购物车商品", notes = "根据id更新购物车的商品（使用实时更新不采用批量更新的方式）。[100|1002|1003]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
	public Result update(@PathVariable(name = "id") Long id, @ModelAttribute ShoppingCartParam parm) {
    	return successCreated(shoppingCartService.update(id, parm));
    }
    
    @ApiOperation(value = "删除购物车的商品", notes = "根据id删除购物车的商品。[1000|1002|1003]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @Authorization
	@RequestMapping(value = "delete/{id}", method = RequestMethod.PUT)
	public Result delete(@PathVariable(name = "id") Long id) {
    	return successCreated(shoppingCartService.delete(id));
    }
    
}
