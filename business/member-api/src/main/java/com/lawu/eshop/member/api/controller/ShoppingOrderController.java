package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.FileDirConstant;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.member.api.MemberApiConfig;
import com.lawu.eshop.member.api.service.PropertyInfoService;
import com.lawu.eshop.member.api.service.ShoppingOrderService;
import com.lawu.eshop.order.dto.ShoppingOrderPaymentDTO;
import com.lawu.eshop.order.dto.foreign.*;
import com.lawu.eshop.order.param.ShoppingOrderRequestRefundParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderQueryForeignToMemberParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderRequestRefundForeignParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundQueryForeignParam;
import com.lawu.eshop.property.dto.PropertyBalanceDTO;
import com.lawu.eshop.user.constants.UploadFileTypeConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import util.UploadFileUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

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
	
    @Autowired
    private PropertyInfoService propertyInfoService;

    @Autowired
	private MemberApiConfig memberApiConfig;
	
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

		Result<Page<ShoppingOrderExtendQueryDTO>> result = shoppingOrderService.selectPageByMemberId(memberId, param);

		if (!isSuccess(result)) {
			return successCreated(result.getRet());
		}

		return successCreated(result);
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
	 * 确认收货之后 修改购物订单以及订单项状态为交易成功
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@Audit(date = "2017-04-12", reviewer = "孙林青")
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "确认收货", notes = "根据购物订单id确认收货。[1002|1003|4005]（蒋鑫俊）", httpMethod = "PUT")
	@ApiResponse(code = HttpCode.SC_CREATED, message = "success")
	@Authorization
	@RequestMapping(value = "tradingSuccess/{id}", method = RequestMethod.PUT)
	public Result tradingSuccess(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("id") @ApiParam(name = "id", value = "购物订单id") Long id) {

		Result result = shoppingOrderService.tradingSuccess(id);

		if (!isSuccess(result)) {
			return successCreated(result.getRet());
		}
		return successCreated();
	}

	/**
	 * 买家申请退款 修改订单状态为待商家确认
	 * 
	 * @param shoppingOrderitemId
	 *            购物订单项id
	 * @param param
	 *            退款参数
	 * @return
	 */
	@Audit(date = "2017-04-12", reviewer = "孙林青")
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "申请退款", notes = "根据购物订单项id申请退款。[1002|1003|4005]（蒋鑫俊）", httpMethod = "POST")
	@ApiResponse(code = HttpCode.SC_CREATED, message = "success")
	@Authorization
	@RequestMapping(value = "requestRefund/{shoppingOrderitemId}", method = RequestMethod.POST)
	public Result requestRefund(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("shoppingOrderitemId") @ApiParam(name = "shoppingOrderitemId", value = "购物订单项id") Long shoppingOrderitemId, @ModelAttribute @ApiParam(name = "param", value = "退款参数") @Validated ShoppingOrderRequestRefundForeignParam param, BindingResult bindingResult) {
		
		String message = validate(bindingResult);
    	if (message != null) {
    		return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
    	}
		
    	HttpServletRequest request = getRequest();
        StringBuffer headImg = new StringBuffer();
        Collection<Part> parts = null;
        try {
           parts = request.getParts();
        } catch (IOException e) {
            return successCreated(e.getMessage());
        }
        catch (ServletException ex){
        }
        if(parts != null && StringUtils.isNotEmpty(parts.toString())) {
            for (Part part : parts) {
                Map<String, String> map = UploadFileUtil.uploadImages(request, FileDirConstant.DIR_ORDER, part, memberApiConfig.getImageUploadUrl());
                String flag = map.get("resultFlag");
                if (UploadFileTypeConstant.UPLOAD_RETURN_TYPE.equals(flag)) {
                    //有图片上传成功返回,拼接图片url
                    String imgUrl = map.get("imgUrl");
                    if (headImg.length() > 0) {
                    	headImg.append(",");
                    }
                    headImg.append(imgUrl);
                } else {
                    return successCreated(Integer.valueOf(flag));
                }
            }
        }
    	
        ShoppingOrderRequestRefundParam shoppingOrderRequestRefundParam = new ShoppingOrderRequestRefundParam();
        shoppingOrderRequestRefundParam.setDescription(param.getDescription());
        shoppingOrderRequestRefundParam.setReason(param.getReason());
        shoppingOrderRequestRefundParam.setType(param.getType());
        shoppingOrderRequestRefundParam.setVoucherPicture(headImg.toString());
        
		Result result = shoppingOrderService.requestRefund(shoppingOrderitemId, shoppingOrderRequestRefundParam);

		if (!isSuccess(result)) {
			return successCreated(result.getRet());
		}
		return successCreated();
	}

	/**
	 * 根据查询参数分页查询退款记录 购物订单 购物订单项 退款详情关联查询
	 * 
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
	public Result<Page<ShoppingOrderItemRefundDTO>> selectRefundPageByMemberId(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(name = "param", value = "查询参数") ShoppingRefundQueryForeignParam param) {
		Long memberId = UserUtil.getCurrentUserId(getRequest());

		Result<Page<ShoppingOrderItemRefundDTO>> result = shoppingOrderService.selectRefundPageByMemberId(memberId, param);

		if (!isSuccess(result)) {
			return successGet(result.getRet());
		}
		return successGet(result);
	}
	
	/**
	 * 订单支付页面
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "订单支付页面", notes = "订单支付页面。[]（蒋鑫俊）", httpMethod = "GET")
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@Authorization
	@RequestMapping(value = "orderPayment", method = RequestMethod.GET)
	public Result<ShoppingOrderPaymentForeignDTO> orderPayment(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,  @PathVariable("id") @ApiParam(name = "id", value = "购物订单id") Long id) {
		
		Result<ShoppingOrderPaymentDTO> result = shoppingOrderService.orderPayment(id);
		
		if (!isSuccess(result)) {
			return successGet(result.getRet());
		}
		
		String memberNum = UserUtil.getCurrentUserNum(getRequest());
		
		Result<PropertyBalanceDTO> propertyBalanceDTOResult = propertyInfoService.getPropertyBalance(memberNum);
		if (!isSuccess(propertyBalanceDTOResult)) {
			return successGet(propertyBalanceDTOResult.getRet());
		}
		
		ShoppingOrderPaymentForeignDTO shoppingOrderPaymentForeignDTO = new ShoppingOrderPaymentForeignDTO();
		shoppingOrderPaymentForeignDTO.setId(result.getModel().getId());
		shoppingOrderPaymentForeignDTO.setOrderNum(result.getModel().getOrderNum());
		shoppingOrderPaymentForeignDTO.setBalance(propertyBalanceDTOResult.getModel().getBalance());
		
		return successGet(shoppingOrderPaymentForeignDTO);
	}
}
