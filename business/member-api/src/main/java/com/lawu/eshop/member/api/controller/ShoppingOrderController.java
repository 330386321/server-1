package com.lawu.eshop.member.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderExpressDTO;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderExtendDetailDTO;
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
    @Authorization
    @RequestMapping(value = "page", method = RequestMethod.GET)
    public Result<Page<ShoppingOrderExtendQueryDTO>> page(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(name = "param", value = "购物订单查询参数") ShoppingOrderQueryForeignParam param) {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
    	
    	return successCreated(shoppingOrderService.selectPageByMemberId(memberId, param));
    }
    
    /**
     * 根据购物订单id查询购物订单详情
     * 
     * @param token
     * @param id
     * @return
     */
    @ApiOperation(value = "查询购物订单详情", notes = "根据购物订单id查询购物订单详情。[1002|1003]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    //@Authorization
    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public Result<ShoppingOrderExtendDetailDTO> get(/*@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,*/ @RequestParam("id") @ApiParam(name = "id", value = "购物订单id") Long id) {
    	
    	Result<ShoppingOrderExtendDetailDTO> resultShoppingOrderExtendDetailDTO = shoppingOrderService.get(id);
    	
    	if (!isSuccess(resultShoppingOrderExtendDetailDTO)) {
    		return successGet(resultShoppingOrderExtendDetailDTO.getRet());
    	}
    	
    	return successCreated(resultShoppingOrderExtendDetailDTO);
    }
    
    /**
     * 查询物流动态
     * 
     * @param token
     * @param id
     * @return
     */
    @ApiOperation(value = "查询物流动态", notes = "查询物流动态。[1002|1003]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    //@Authorization
    @RequestMapping(value = "expressInquiries/{id}", method = RequestMethod.GET)
    public Result<ShoppingOrderExpressDTO> expressInquiries(/*@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,*/ @RequestParam("id") @ApiParam(name = "id", value = "购物订单id") Long id) {
    	
    	Result<ShoppingOrderExpressDTO> resultShoppingOrderExpressDTO = shoppingOrderService.getExpressInfo(id);
    	
    	if (!isSuccess(resultShoppingOrderExpressDTO)) {
    		return successGet(resultShoppingOrderExpressDTO.getRet());
    	}
    	
    	return successCreated(resultShoppingOrderExpressDTO);
    }
}
