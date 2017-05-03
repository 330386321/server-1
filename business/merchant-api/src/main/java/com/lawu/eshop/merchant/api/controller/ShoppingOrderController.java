package com.lawu.eshop.merchant.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
import com.lawu.eshop.mall.dto.ExpressCompanyDTO;
import com.lawu.eshop.merchant.api.service.ExpressCompanyService;
import com.lawu.eshop.merchant.api.service.ShoppingOrderService;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExpressDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExtendDetailDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderItemRefundForMerchantDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderNumberOfOrderStatusForMerchantForeignDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderQueryToMerchantDTO;
import com.lawu.eshop.order.param.ShoppingOrderLogisticsInformationParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderLogisticsInformationForeignParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderQueryForeignToMerchantParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundQueryForeignParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * 
 * @author Sunny
 * @date 2017/04/12
 */
@Api(tags = "shoppingOrder")
@RestController
@RequestMapping(value = "shoppingOrder/")
public class ShoppingOrderController extends BaseController {

	@Autowired
	private ShoppingOrderService shoppingOrderService;

	@Autowired
	private ExpressCompanyService expressCompanyService;

	/**
	 * To商家 根据查询参数分页查询
	 * 
	 * @param params
	 *            查询参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Audit(date = "2017-04-15", reviewer = "孙林青")
	@ApiOperation(value = "分页查询订单", notes = "根据查询参数分页查询。[]（蒋鑫俊）", httpMethod = "GET")
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@Authorization
	@RequestMapping(value = "selectPageByMerchantId", method = RequestMethod.GET)
	public Result<Page<ShoppingOrderQueryToMerchantDTO>> selectPageByMerchantId(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(name = "param", value = "查询参数") ShoppingOrderQueryForeignToMerchantParam param) {
		Long merchantId = UserUtil.getCurrentUserId(getRequest());

		Result<Page<ShoppingOrderQueryToMerchantDTO>> result = shoppingOrderService.selectPageByMerchantId(merchantId, param);
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
	@Audit(date = "2017-04-15", reviewer = "孙林青")
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
	 * 商家发货填写物流信息 并修改购物订单以及购物订单项的状态为待收货
	 * 
	 * @param id
	 *            购物订单id
	 * @param param
	 *            订单物流参数
	 * @return
	 */
	@Audit(date = "2017-04-15", reviewer = "孙林青")
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "填写物流信息", notes = "填写物流信息。[1002|1003|4008]（蒋鑫俊）", httpMethod = "PUT")
	@ApiResponse(code = HttpCode.SC_CREATED, message = "success")
	@Authorization
	@RequestMapping(value = "fillLogisticsInformation/{id}", method = RequestMethod.PUT)
	public Result fillLogisticsInformation(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable @ApiParam(name = "id", value = "购物订单id") Long id, @ModelAttribute @ApiParam(name = "param", value = "物流参数") @Validated ShoppingOrderLogisticsInformationForeignParam param, BindingResult bindingResult) {

		// 校验参数
		String message = validate(bindingResult);
		if (message != null) {
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
		}
		
		ShoppingOrderLogisticsInformationParam ShoppingOrderLogisticsInformationParam = new ShoppingOrderLogisticsInformationParam();
		ShoppingOrderLogisticsInformationParam.setIsNeedsLogistics(param.getIsNeedsLogistics());
		
		if (param.getIsNeedsLogistics()) {
			Result<ExpressCompanyDTO> resultExpressCompanyDTO = expressCompanyService.get(param.getExpressCompanyId());
			if (!isSuccess(resultExpressCompanyDTO)) {
				return successCreated(resultExpressCompanyDTO.getRet());
			}
	
			ShoppingOrderLogisticsInformationParam.setExpressCompanyId(resultExpressCompanyDTO.getModel().getId());
			ShoppingOrderLogisticsInformationParam.setExpressCompanyCode(resultExpressCompanyDTO.getModel().getCode());
			ShoppingOrderLogisticsInformationParam.setExpressCompanyName(resultExpressCompanyDTO.getModel().getName());
			ShoppingOrderLogisticsInformationParam.setWaybillNum(param.getWaybillNum());
		}
		
		Result result = shoppingOrderService.fillLogisticsInformation(id, ShoppingOrderLogisticsInformationParam);
		if (!isSuccess(result)) {
			return successCreated(result.getRet());
		}

		return successCreated(result);
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
	@Audit(date = "2017-05-02", reviewer = "孙林青")
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "分页查询退款记录", notes = "分页查询退款记录。[]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "selectRefundPageByMerchantId", method = RequestMethod.GET)
    public Result<Page<ShoppingOrderItemRefundForMerchantDTO>> selectRefundPageByMerchantId(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(name = "param", value="查询参数") ShoppingRefundQueryForeignParam param) {
    	Long merchantId = UserUtil.getCurrentUserId(getRequest());
    	
    	Result<Page<ShoppingOrderItemRefundForMerchantDTO>> result = shoppingOrderService.selectRefundPageByMerchantId(merchantId, param);
    	
    	if (!isSuccess(result)) {
    		return successGet(result.getRet());
    	}
    	return successGet(result);
    }
	
    /**
	 * 查询订单各种状态的数量
	 * 
	 * @return
	 */
	@Audit(date = "2017-05-02", reviewer = "孙林青")
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "查询订单各种状态的数量", notes = "查询订单各种状态的数量。[]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "numberOfOrderStartusByMerchant", method = RequestMethod.GET)
    public Result<ShoppingOrderNumberOfOrderStatusForMerchantForeignDTO> numberOfOrderStartusByMerchant(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
    	Long merchantId = UserUtil.getCurrentUserId(getRequest());
    	
    	Result<ShoppingOrderNumberOfOrderStatusForMerchantForeignDTO> result = shoppingOrderService.numberOfOrderStartusByMerchant(merchantId);
    	
    	if (!isSuccess(result)) {
    		return successGet(result.getRet());
    	}
    	return successGet(result);
    }
	
    /**
     * 查询物流动态
     * 
     * @param token
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
	@ApiOperation(value = "查询物流动态", notes = "查询物流动态。[1002|1003]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "getExpressInfo/{id}", method = RequestMethod.GET)
    public Result<ShoppingOrderExpressDTO> getExpressInfo(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("id") @ApiParam(name = "id", value = "购物订单id") Long id) {
    	
    	Result<ShoppingOrderExpressDTO> resultShoppingOrderExpressDTO = shoppingOrderService.getExpressInfo(id);
    	
    	if (!isSuccess(resultShoppingOrderExpressDTO)) {
    		return successGet(resultShoppingOrderExpressDTO.getRet());
    	}
    	
    	return successGet(resultShoppingOrderExpressDTO);
    }
}
