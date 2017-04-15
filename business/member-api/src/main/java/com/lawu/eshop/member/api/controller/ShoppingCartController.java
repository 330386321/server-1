package com.lawu.eshop.member.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.mall.dto.foreign.MemberShoppingCartDTO;
import com.lawu.eshop.mall.dto.foreign.ShoppingCartSettlementDTO;
import com.lawu.eshop.mall.param.ShoppingCartParam;
import com.lawu.eshop.mall.param.ShoppingCartUpdateParam;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderSettlementForeignParam;
import com.lawu.eshop.member.api.service.ShoppingCartExtendService;
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
    
    @Autowired
    private ShoppingCartExtendService shoppingcartExtendService;
    
    /**
     * 加入购物车。
     * 
     * @param token
     * @param param
     * @return
     */
	@SuppressWarnings("rawtypes")
	@Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "加入购物车", notes = "加入购物车。[1004|1005]（蒋鑫俊）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Result save(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(name = "param", value = "加入购物车参数") ShoppingCartParam param) {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
    	
    	return successCreated(shoppingcartExtendService.save(memberId, param));
    }

    /**
     * 根据memberId查询用户的购物车列表。
     * 
     * @param token
     * @return
     */
	@SuppressWarnings("unchecked")
	@Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "查询用户的购物车列表", notes = "根据memberId查询用户的购物车列表。[1004]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "findListByMemberId", method = RequestMethod.GET)
    Result<List<MemberShoppingCartDTO>> findListByMemberId(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
    	
    	return successGet(shoppingcartExtendService.findListByMemberId(memberId));
    }
    
    /**
     * 根据id更新购物车的商品（使用实时更新不采用批量更新的方式）。
     * 
     * @param id
     * @param token
     * @param parm
     * @return
     */
	@SuppressWarnings("rawtypes")
	@Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "更新购物车商品", notes = "根据id更新购物车的商品（使用实时更新不采用批量更新的方式）。[1002|1003|4000]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
	public Result update(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@PathVariable("id") @ApiParam(name = "id", required = true, value = "购物车ID") Long id, @ModelAttribute @ApiParam(name = "param", value = "加入购物车参数") ShoppingCartParam param) {
    	
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
    	
    	ShoppingCartUpdateParam shoppingCartUpdateParam = new ShoppingCartUpdateParam();
    	shoppingCartUpdateParam.setProductModelId(param.getProductModelId());
    	shoppingCartUpdateParam.setQuantity(param.getQuantity());
    	
    	return successCreated(shoppingCartService.update(id, memberId, shoppingCartUpdateParam));
    }
    
    /**
     * 根据id删除购物车的商品。
     * 
     * @param id
     * @param token
     * @return
     */
    @SuppressWarnings({ "rawtypes", "deprecation" })
	@Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "删除购物车的商品", notes = "根据id删除购物车的商品。[1002|1003|4000]（蒋鑫俊）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @Authorization
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
	public Result delete(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable(name = "id") @ApiParam(name = "id", required = true, value = "购物车ID") Long id) {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
    	
    	Result result = successCreated(shoppingCartService.delete(id, memberId));
    	if (!isSuccess(result)) {
    		successCreated(result);
    	}
    	
    	return successDelete(result);
    }
    
    /**
     * 根据购物车id列表结算购物车的商品生成结算数据
     * 
     * @param params 购物车参数
     * @return
     */
    @Audit(date = "2017-04-15", reviewer = "孙林青")
    @SuppressWarnings("unchecked")
	@ApiOperation(value = "购物车的商品结算", notes = "根据购物车id列表结算购物车的商品生成结算数据。[1003]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
	@RequestMapping(value = "settlement", method = RequestMethod.GET)
	public Result<ShoppingCartSettlementDTO> settlement(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @RequestParam @ApiParam(required = true, value = "购物车id") List<Long> idList) {
    	if (idList == null || idList.isEmpty()) {
    		return successGet(ResultCode.ID_EMPTY);
    	}
    	
    	return successCreated(shoppingcartExtendService.settlement(idList));
    }
    
    /**
     * 根据订单参数列表批量创建订单
     * 
     * @param params 订单参数
     * @return
     */
    @Audit(date = "2017-04-15", reviewer = "孙林青")
    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "创建订单", notes = "根据订单参数创建订单。[1002|1003|1004|1005]（蒋鑫俊）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
	@RequestMapping(value = "createOrder", method = RequestMethod.POST)
	public Result<List<Long>> createOrder(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @RequestBody @ApiParam(name = "param", required = true, value = "订单参数") List<ShoppingOrderSettlementForeignParam> params) {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
    	
    	return successCreated(shoppingcartExtendService.createOrder(memberId, params));
    }
    
}
