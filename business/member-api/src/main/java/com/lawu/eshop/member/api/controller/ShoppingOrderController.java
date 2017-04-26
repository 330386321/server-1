package com.lawu.eshop.member.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.member.api.service.ShoppingOrderService;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExpressDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExtendDetailDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExtendQueryDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderItemRefundDTO;
import com.lawu.eshop.order.param.foreign.ShoppingOrderQueryForeignToMemberParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderRequestRefundForeignParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundQueryForeignParam;

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
	@Audit(date = "2017-04-12", reviewer = "孙林青")
    @SuppressWarnings("unchecked")
	@ApiOperation(value = "分页查询购物订单", notes = "根据订单状态和是否评论分页查询购物订单。[]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "selectPageByMemberId", method = RequestMethod.GET)
    public Result<Page<ShoppingOrderExtendQueryDTO>> page(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(name = "param", value = "购物订单查询参数") ShoppingOrderQueryForeignToMemberParam param) {
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
	@Audit(date = "2017-04-12", reviewer = "孙林青")
    @SuppressWarnings("unchecked")
	@ApiOperation(value = "查询购物订单详情", notes = "根据购物订单id查询购物订单详情。[1002|1003]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public Result<ShoppingOrderExtendDetailDTO> get(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("id") @ApiParam(name = "id", value = "购物订单id") Long id) {
    	
		if (id == null || id <= 0) {
			return successGet(ResultCode.ID_EMPTY);
		}
		
    	Result<ShoppingOrderExtendDetailDTO> resultShoppingOrderExtendDetailDTO = shoppingOrderService.get(id);
    	
    	if (!isSuccess(resultShoppingOrderExtendDetailDTO)) {
    		return successGet(resultShoppingOrderExtendDetailDTO.getRet());
    	}
    	
    	return successGet(resultShoppingOrderExtendDetailDTO);
    }
    
    /**
     * 查询物流动态
     * 
     * @param token
     * @param id
     * @return
     */
	@Audit(date = "2017-04-12", reviewer = "孙林青")
    @SuppressWarnings("unchecked")
	@ApiOperation(value = "查询物流动态", notes = "查询物流动态。[1002|1003]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "getExpressInfo/{id}", method = RequestMethod.GET)
    public Result<ShoppingOrderExpressDTO> expressInquiries(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("id") @ApiParam(name = "id", value = "购物订单id") Long id) {
    	
    	Result<ShoppingOrderExpressDTO> resultShoppingOrderExpressDTO = shoppingOrderService.getExpressInfo(id);
    	
    	if (!isSuccess(resultShoppingOrderExpressDTO)) {
    		return successGet(resultShoppingOrderExpressDTO.getRet());
    	}
    	
    	return successGet(resultShoppingOrderExpressDTO);
    }
    
    /**
     * 取消购物订单
     * 
     * @param token
     * @param id
     * @return
     */
	@Audit(date = "2017-04-12", reviewer = "孙林青")
    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "取消购物订单", notes = "取消购物订单。[1002|1003|4002]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "cancelOrder/{id}", method = RequestMethod.PUT)
    public Result cancelOrder(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("id") @ApiParam(name = "id", value = "购物订单id") Long id) {
    	
    	Result resultShoppingOrderExpressDTO = shoppingOrderService.cancelOrder(id);
    	
    	if (!isSuccess(resultShoppingOrderExpressDTO)) {
    		return successCreated(resultShoppingOrderExpressDTO.getRet());
    	}
    	
    	return successCreated();
    }
    
	/**
	 * 删除购物订单
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@Audit(date = "2017-04-12", reviewer = "孙林青")
    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "删除购物订单", notes = "根据购物订单id删除购物订单。[1002|1003|4003]（蒋鑫俊）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @Authorization
    @RequestMapping(value = "deleteOrder/{id}", method = RequestMethod.DELETE)
    public Result deleteOrder(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("id") @ApiParam(name = "id", value = "购物订单id") Long id) {
    	
    	Result result = shoppingOrderService.deleteOrder(id);
    	
    	if (!isSuccess(result)) {
    		return successCreated(result.getRet());
    	}
    	return successDelete();
    }
    
	/**
	 * 确认收货之后
	 * 修改购物订单以及订单项状态为交易成功
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@Audit(date = "2017-04-12", reviewer = "孙林青")
    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "确认收货", notes = "根据购物订单id确认收货。[1002|1003|4005]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    //@Authorization
    @RequestMapping(value = "tradingSuccess/{id}", method = RequestMethod.PUT)
    public Result tradingSuccess(/*@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,*/ @PathVariable("id") @ApiParam(name = "id", value = "购物订单id") Long id) {
    	
    	Result result = shoppingOrderService.tradingSuccess(id);
    	
    	if (!isSuccess(result)) {
    		return successCreated(result.getRet());
    	}
    	return successCreated();
    }
    
	/**
	 * 买家申请退款
	 * 修改订单状态为待商家确认
	 * 
	 * @param shoppingOrderitemId 购物订单项id
	 * @param param 退款参数
	 * @return
	 */
	@Audit(date = "2017-04-12", reviewer = "孙林青")
    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "申请退款", notes = "根据购物订单项id申请退款。[1002|1003|4005]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "requestRefund/{shoppingOrderitemId}", method = RequestMethod.PUT)
    public Result requestRefund(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
    		@PathVariable("shoppingOrderitemId") @ApiParam(name = "shoppingOrderitemId", value = "购物订单项id") Long shoppingOrderitemId,
    		@ModelAttribute @ApiParam(name = "param", value="退款参数") ShoppingOrderRequestRefundForeignParam param) {
    	
    	Result result = shoppingOrderService.requestRefund(shoppingOrderitemId, param);
    	
    	if (!isSuccess(result)) {
    		return successCreated(result.getRet());
    	}
    	return successCreated();
    }
    
    /**
	 * 根据查询参数分页查询退款记录
	 * 购物订单 购物订单项 退款详情关联查询
	 * 
	 * @param memberId
	 *            会员id
	 * @param param
	 *            查询参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Audit(date = "2017-04-12", reviewer = "孙林青")
	@ApiOperation(value = "分页查询退款记录", notes = "分页查询退款记录。[]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "selectRefundPageByMemberId", method = RequestMethod.GET)
    public Result<Page<ShoppingOrderItemRefundDTO>> selectRefundPageByMemberId(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(name = "param", value="查询参数") ShoppingRefundQueryForeignParam param) {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
    	
    	Result<Page<ShoppingOrderItemRefundDTO>> result = shoppingOrderService.selectRefundPageByMemberId(memberId, param);
    	
    	if (!isSuccess(result)) {
    		return successGet(result.getRet());
    	}
    	return successGet(result);
    }
}
