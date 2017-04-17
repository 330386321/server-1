package com.lawu.eshop.member.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.mall.dto.ExpressCompanyDTO;
import com.lawu.eshop.member.api.service.ExpressCompanyService;
import com.lawu.eshop.member.api.service.ShoppingRefundDetailService;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExpressDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingRefundDetailDTO;
import com.lawu.eshop.order.param.ShoppingRefundDetailLogisticsInformationParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundDetailLogisticsInformationForeignParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * @author Sunny 
 * @date 2017/04/06
 */
@Api(tags = "shoppingRefundDetail")
@RestController
@RequestMapping(value = "shoppingRefundDetail/")
public class ShoppingRefundDetailController extends BaseController {

	@Autowired
	private ShoppingRefundDetailService shoppingRefundDetailservice;
	
	@Autowired
	private ExpressCompanyService expressCompanyService;
	
	/**
	 * 根据购物订单项id查询退款详情
	 * 
	 * @param shoppingOrderItemId
	 *            购物订单项id
	 * @return
	 */
	@Audit(date = "2017-04-15", reviewer = "孙林青")
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "查询退款详情", notes = "根据购物订单项id查询退款详情。[1002|1003]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "getRefundDetail/{shoppingOrderItemId}", method = RequestMethod.GET)
    public Result<ShoppingOrderExpressDTO> getRefundDetail(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("shoppingOrderItemId") @ApiParam(name = "shoppingOrderItemId", value = "购物订单项id") Long shoppingOrderItemId) {
    	if (shoppingOrderItemId == null || shoppingOrderItemId <= 0) {
    		return successCreated(ResultCode.ID_EMPTY);
    	}
		
    	Result<ShoppingRefundDetailDTO> resultShoppingRefundDetailDTO = shoppingRefundDetailservice.getRefundDetail(shoppingOrderItemId);
    	
    	if (!isSuccess(resultShoppingRefundDetailDTO)) {
    		return successGet(resultShoppingRefundDetailDTO.getRet());
    	}
		
    	return successCreated(resultShoppingRefundDetailDTO);
    }
	
	/**
	 * 买家提交退货物流
	 * 修改订单项退款状态为待退款
	 * 
	 * @param id 退款详情id
	 * @param param 退款物流信息参数
	 * @return
	 */
	@Audit(date = "2017-04-12", reviewer = "孙林青")
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "更新退货物流", notes = "根据购物退款详情更新退货物流信息。[1002|1003|4009]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
	@RequestMapping(value = "fillLogisticsInformation/{id}", method = RequestMethod.PUT)
	public Result fillLogisticsInformation(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("id") @ApiParam(name = "id", value = "购物退款详情id") Long id, @ModelAttribute @ApiParam(name = "param", value = "退货物流参数") ShoppingRefundDetailLogisticsInformationForeignParam param) {
		
		Result<ExpressCompanyDTO> resultExpressCompanyDTO = expressCompanyService.get(param.getExpressCompanyId());
		
		if (!isSuccess(resultExpressCompanyDTO)) {
			return successGet(resultExpressCompanyDTO.getRet());
		}
		
		ShoppingRefundDetailLogisticsInformationParam shoppingRefundDetailLogisticsInformationParam = new ShoppingRefundDetailLogisticsInformationParam();
		shoppingRefundDetailLogisticsInformationParam.setWaybillNum(param.getWaybillNum());
		shoppingRefundDetailLogisticsInformationParam.setExpressCompanyId(resultExpressCompanyDTO.getModel().getId());
		shoppingRefundDetailLogisticsInformationParam.setExpressCompanyCode(resultExpressCompanyDTO.getModel().getCode());
		shoppingRefundDetailLogisticsInformationParam.setExpressCompanyName(resultExpressCompanyDTO.getModel().getName());
		
		// 修改订单项的退款状态为待退款状态，更新退款详情的物流信息
		Result result = shoppingRefundDetailservice.fillLogisticsInformation(id, shoppingRefundDetailLogisticsInformationParam);
		
		if (!isSuccess(result)) {
			return successCreated(result.getRet());
		}
		
		return successCreated();
	}
	
	/**
	 * 如果商家拒绝买家的退款申请或者拒绝退款
	 * 买家可以申请平台介入
	 * 
	 * @param id 退款详情id
	 * @return
	 */
	@Audit(date = "2017-04-15", reviewer = "孙林青")
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "申请平台介入", notes = "申请平台介入。[1002|1003|1004|4011|4014]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
	@RequestMapping(value = "platformIntervention/{id}", method = RequestMethod.PUT)
	public Result platformIntervention(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("id") @ApiParam(name = "id", value = "购物退款详情id") Long id) {
		// 买家是否申请平台介入
		Result result = shoppingRefundDetailservice.platformIntervention(id);
		
		if (!isSuccess(result)) {
			return successCreated(result.getRet());
		}
		
		return successCreated();
	}
	
	/**
	 * 买家撤销退款申请
	 * 
	 * @param id
	 *            退款详情id
	 * @return
	 */
	@Audit(date = "2017-04-15", reviewer = "孙林青")
	@ApiOperation(value = "撤销退款申请", notes = "买家撤销退款申请。[1002|1003|1004|4011|4014]（蒋鑫俊）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @Authorization
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "revokeRefundRequest/{id}", method = RequestMethod.DELETE)
	public Result revokeRefundRequest(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("id") Long id) {
		
		Result result = shoppingRefundDetailservice.revokeRefundRequest(id);
		
		if (!isSuccess(result)) {
			return successCreated(result.getRet());
		}
		
		return successDelete();
	}
}
