package com.lawu.eshop.member.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
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

    // TODO 2016.03.29 加入购物车只需要一个商品型号ID，可以通过服务端查询的信息不要通过客户端传递。接口加上路径
    //@ApiOperation(value = "加入购物车", notes = "加入购物车。[1000|1004|1005]（蒋鑫俊）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(name = "parm", required = true, value = "购物车资料") ShoppingCartParam param) {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
    	return successCreated(shoppingCartService.save(memberId, param));
    }

    // TODO 2016.03.29 不要把多余的信息返回给客户端。接口加上路径
    //@ApiOperation(value = "查询用户的购物车列表", notes = "根据memberId查询用户的购物车列表。[1000|1004]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(method = RequestMethod.GET)
    Result<List<ShoppingCartDTO>> findListByMemberId(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
    	return successGet(shoppingCartService.findListByMemberId(memberId));
    }

    // TODO 2016.03.29 更新参数只需要商品型号ID和数量
    @ApiOperation(value = "更新购物车商品", notes = "根据id更新购物车的商品（使用实时更新不采用批量更新的方式）。[100|1002|1003]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
	public Result update(@PathVariable(name = "id") Long id, @RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute ShoppingCartParam parm) {
    	return successCreated(shoppingCartService.update(id, parm));
    }
    
    //@ApiOperation(value = "删除购物车的商品", notes = "根据id删除购物车的商品。[1000|1002|1003]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @Authorization
	@RequestMapping(value = "delete/{id}", method = RequestMethod.PUT)
	public Result delete(@PathVariable(name = "id") Long id, @RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
    	return successCreated(shoppingCartService.delete(id));
    }
    
}
