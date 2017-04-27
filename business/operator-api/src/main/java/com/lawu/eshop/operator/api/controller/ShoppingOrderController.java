package com.lawu.eshop.operator.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.operator.api.service.ShoppingOrderExtendService;
import com.lawu.eshop.operator.api.service.ShoppingOrderService;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExtendDetailDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderQueryToOperatorDTO;
import com.lawu.eshop.order.param.foreign.ShoppingOrderQueryForeignToOperatorParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderUpdateInfomationForeignParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * 
 * @author Sunny
 * @date 2017/04/13
 */
@Api(tags = "shoppingOrder")
@RestController
@RequestMapping(value = "shoppingOrder/")
public class ShoppingOrderController extends BaseController {

	@Autowired
	private ShoppingOrderService shoppingOrderService;

	@Autowired
	private ShoppingOrderExtendService shoppingOrderExtendService;

	/**
	 * 根据查询参数分页查询
	 * 
	 * @param params
	 *            查询参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "分页查询订单", notes = "根据查询参数分页查询。[1004]（蒋鑫俊）", httpMethod = "GET")
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@PageBody
	//@RequiresPermissions("shoppingOrder:selectPage")
	@RequestMapping(value = "selectPage", method = RequestMethod.GET)
	public Result<Page<ShoppingOrderQueryToOperatorDTO>> selectPageByMerchantId(@ModelAttribute @ApiParam(name = "param", value = "查询参数") ShoppingOrderQueryForeignToOperatorParam param) {
		// 校验参数
		if (param == null) {
			return successGet(ResultCode.REQUIRED_PARM_EMPTY);
		}

		Result<Page<ShoppingOrderQueryToOperatorDTO>> result = shoppingOrderService.selectPage(param);
		if (!isSuccess(result)) {
			return successGet(result.getRet());
		}

		return successGet(result);
	}

	/**
	 * 根据购物订单id查询购物订单详情
	 * 
	 * @param token
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "查询购物订单详情", notes = "根据购物订单id查询购物订单详情。[1002|1003]（蒋鑫俊）", httpMethod = "GET")
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	//@RequiresPermissions("shoppingOrder:get/{id}")
	@RequestMapping(value = "get/{id}", method = RequestMethod.GET)
	public Result<ShoppingOrderExtendDetailDTO> get(@PathVariable("id") @ApiParam(name = "id", value = "购物订单id") Long id) {

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
	 * 更新订单信息
	 * 
	 * @param id
	 *            购物订单id
	 * @param param
	 *            查询参数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "更新订单信息", notes = "更新订单信息。[1002|1003]（蒋鑫俊）", httpMethod = "POST")
	@ApiResponse(code = HttpCode.SC_CREATED, message = "success")
	//@RequiresPermissions("shoppingOrder:updateInformation/{id}")
	@RequestMapping(value = "updateInformation/{id}", method = RequestMethod.POST)
	public Result updateInformation(@PathVariable("id") @ApiParam(name = "id", value = "购物订单id") Long id, @ModelAttribute @ApiParam(name = "param", value = "更新参数") ShoppingOrderUpdateInfomationForeignParam param) {
		Result result = shoppingOrderExtendService.updateInformation(id, param);

		if (!isSuccess(result)) {
			return successCreated(result.getRet());
		}

		return successCreated(result);
	}
	
    /**
     * 取消购物订单
     * 
     * @param token
     * @param id
     * @return
     */
    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "取消购物订单", notes = "取消购物订单。[1002|1003|4002]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
  //@RequiresPermissions("shoppingOrder:cancelOrder/{id}")
    @RequestMapping(value = "cancelOrder/{id}", method = RequestMethod.PUT)
    public Result cancelOrder(@PathVariable("id") @ApiParam(name = "id", value = "购物订单id") Long id) {
    	
    	Result resultShoppingOrderExpressDTO = shoppingOrderService.cancelOrder(id);
    	
    	if (!isSuccess(resultShoppingOrderExpressDTO)) {
    		return successCreated(resultShoppingOrderExpressDTO.getRet());
    	}
    	
    	return successCreated();
    }
}
