package com.lawu.eshop.merchant.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.mall.dto.ExpressCompanyDTO;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderExtendDetailDTO;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderQueryToMerchantDTO;
import com.lawu.eshop.mall.param.ShoppingOrderLogisticsInformationParam;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderLogisticsInformationForeignParam;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderQueryForeignToMerchantParam;
import com.lawu.eshop.merchant.api.service.ExpressCompanyService;
import com.lawu.eshop.merchant.api.service.ShoppingOrderService;

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
	 * To商家
	 * 根据查询参数分页查询
	 * 
	 * @param params
	 *            查询参数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "分页查询订单", notes = "根据查询参数分页查询。[]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
	@RequestMapping(value = "selectPageByMerchantId", method = RequestMethod.GET)
    public Result selectPageByMerchantId(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(name = "param", value = "查询参数") ShoppingOrderQueryForeignToMerchantParam param) {
		Long merchantId = UserUtil.getCurrentUserId(getRequest());
    	// 校验参数
    	if (param == null) {
    		return successGet(ResultCode.REQUIRED_PARM_EMPTY);
    	}
    	
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
    @SuppressWarnings("unchecked")
	@ApiOperation(value = "查询购物订单详情", notes = "根据购物订单id查询购物订单详情。[1002|1003]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public Result<ShoppingOrderExtendDetailDTO> get(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @RequestParam("id") @ApiParam(name = "id", value = "购物订单id") Long id) {
    	
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
	 * 商家发货填写物流信息
	 * 并修改购物订单以及购物订单项的状态为待收货
	 * 
	 * @param id
	 *            购物订单id
	 * @param param
	 *            订单物流参数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "填写物流信息", notes = "填写物流信息。[1002|1003|4008]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "fillLogisticsInformation/{id}", method = RequestMethod.PUT)
    public Result fillLogisticsInformation(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
    		@RequestParam @ApiParam(name = "id", value = "购物订单id") Long id,
    		@ModelAttribute @ApiParam(name = "param", value = "物流参数") ShoppingOrderLogisticsInformationForeignParam param) {
    	
    	// 校验参数
    	if (param == null || StringUtils.isEmpty(param.getExpressCompanyId()) || StringUtils.isEmpty(param.getWaybillNum())) {
    		return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
    	}
    	
    	Result<ExpressCompanyDTO> resultExpressCompanyDTO = expressCompanyService.get(param.getExpressCompanyId());
    	if (!isSuccess(resultExpressCompanyDTO)) {
    		return successCreated(resultExpressCompanyDTO.getRet());
    	}
    	
    	ShoppingOrderLogisticsInformationParam ShoppingOrderLogisticsInformationParam = new ShoppingOrderLogisticsInformationParam();
    	ShoppingOrderLogisticsInformationParam.setExpressCompanyId(resultExpressCompanyDTO.getModel().getId());
    	ShoppingOrderLogisticsInformationParam.setExpressCompanyCode(resultExpressCompanyDTO.getModel().getCode());
    	ShoppingOrderLogisticsInformationParam.setExpressCompanyName(resultExpressCompanyDTO.getModel().getName());
    	ShoppingOrderLogisticsInformationParam.setWaybillNum(param.getWaybillNum());
    	
    	Result result = shoppingOrderService.fillLogisticsInformation(id, ShoppingOrderLogisticsInformationParam);
    	if (!isSuccess(result)) {
    		return successCreated(result.getRet());
    	}
    	
    	return successCreated(result);
    }
}
