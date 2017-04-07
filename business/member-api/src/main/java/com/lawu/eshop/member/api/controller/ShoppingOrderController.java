package com.lawu.eshop.member.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderExtendQueryDTO;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderQueryForeignParam;
import com.lawu.eshop.member.api.service.ShoppingOrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * @author Sunny 
 * @date 2017/04/06
 */
@Api(tags = "shoppingOrder")
@RestController
@RequestMapping(value = "shoppingOrder/")
public class ShoppingOrderController extends BaseController {

    @Autowired
    private ShoppingOrderService shoppingOrderService;
    
    /**
     * 分页查询购物订单
     * 
     * @param token
     * @param param
     * @return
     */
    @ApiOperation(value = "分页查询购物订单", notes = "根据订单状态和是否评论分页查询购物订单。[]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    //@Authorization
    @RequestMapping(value = "page", method = RequestMethod.GET)
    public Result<Page<ShoppingOrderExtendQueryDTO>> page(/*@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,*/ @ModelAttribute @ApiParam(name = "param", value = "购物订单查询参数") ShoppingOrderQueryForeignParam param) {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
    	
    	return successCreated(shoppingOrderService.selectPageByMemberId(memberId, param));
    }
}
