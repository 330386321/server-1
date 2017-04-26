package com.lawu.eshop.order.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.order.constants.RefundStatusEnum;
import com.lawu.eshop.order.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExpressDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingRefundDetailDTO;
import com.lawu.eshop.order.param.ShoppingRefundDetailLogisticsInformationParam;
import com.lawu.eshop.order.param.ShoppingRefundDetailRerurnAddressParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundDetailAgreeToApplyForeignParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundDetailAgreeToRefundForeignParam;
import com.lawu.eshop.order.srv.bo.ExpressInquiriesDetailBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemBO;
import com.lawu.eshop.order.srv.bo.ShoppingRefundDetailBO;
import com.lawu.eshop.order.srv.converter.ShoppingRefundDetailConverter;
import com.lawu.eshop.order.srv.service.ShoppingOrderItemService;
import com.lawu.eshop.order.srv.service.ShoppingRefundDetailService;
import com.lawu.eshop.order.srv.strategy.ExpressStrategy;

/**
 * 购物退货详情
 * 
 * @author Sunny
 * @date 2017/04/06
 */
@RestController
@RequestMapping(value = "shoppingRefundDetail/")
public class ShoppingRefundDetailController extends BaseController {

	@Autowired
	private ShoppingRefundDetailService shoppingRefundDetailService;

	@Autowired
	private ShoppingOrderItemService shoppingOrderItemService;

	@Autowired
	@Qualifier("kDNiaoExpressStrategy")
	private ExpressStrategy expressStrategy;

	/**
	 * 根据订单项id查询退款信息
	 * 
	 * @param shoppingOrderitemId
	 *            购物订单项id
	 * @return
	 */
	@RequestMapping(value = "getRefundDetail/{shoppingOrderItemId}", method = RequestMethod.GET)
	public Result<ShoppingRefundDetailDTO> getRefundDetail(@PathVariable("shoppingOrderItemId") Long shoppingOrderItemId) {
		if (shoppingOrderItemId == null || shoppingOrderItemId <= 0) {
			return successCreated(ResultCode.ID_EMPTY);
		}

		ShoppingRefundDetailBO shoppingRefundDetailBO = shoppingRefundDetailService.getByShoppingOrderitemId(shoppingOrderItemId);

		if (shoppingRefundDetailBO == null || shoppingRefundDetailBO.getId() == null || shoppingRefundDetailBO.getId() <= 0) {
			return successCreated(ResultCode.RESOURCE_NOT_FOUND);
		}

		return successCreated(ShoppingRefundDetailConverter.convert(shoppingRefundDetailBO));
	}

	/**
	 * 商家是否同意买家的退货申请
	 * 
	 * @param id
	 *            退款详情id
	 * @param param
	 *            参数 是否同意申请
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "agreeToApply/{id}", method = RequestMethod.PUT)
	public Result agreeToApply(@PathVariable("id") Long id, @RequestBody ShoppingRefundDetailAgreeToApplyForeignParam param) {
		if (id == null || id <= 0) {
			return successCreated(ResultCode.ID_EMPTY);
		}

		if (param.getIsAgree() == null) {
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
		}

		ShoppingRefundDetailBO shoppingRefundDetailBO = shoppingRefundDetailService.get(id);

		if (shoppingRefundDetailBO == null || shoppingRefundDetailBO.getId() == null || shoppingRefundDetailBO.getId() <= 0) {
			return successCreated(ResultCode.RESOURCE_NOT_FOUND);
		}

		ShoppingOrderItemBO shoppingOrderItemBO = shoppingOrderItemService.get(shoppingRefundDetailBO.getShoppingOrderItemId());

		if (shoppingOrderItemBO == null || shoppingOrderItemBO.getId() == null || shoppingOrderItemBO.getId() <= 0) {
			return successCreated(ResultCode.RESOURCE_NOT_FOUND);
		}

		// 订单状态必须为退款中
		if (!shoppingOrderItemBO.getOrderStatus().equals(ShoppingOrderStatusEnum.REFUNDING)) {
			return successCreated(ResultCode.NOT_REFUNDING);
		}

		// 退款状态必须为待商家确认
		if (!shoppingOrderItemBO.getRefundStatus().equals(RefundStatusEnum.TO_BE_CONFIRMED)) {
			return successCreated(ResultCode.NOT_AGREE_TO_APPLY);
		}

		shoppingRefundDetailService.agreeToApply(shoppingRefundDetailBO, param);

		return successCreated();
	}

	/**
	 * 商家填写退货地址 根据退款详情id更新退货地址
	 * 
	 * @param id
	 *            退款详情id
	 * @param param
	 *            退货信息参数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "fillReturnAddress/{id}", method = RequestMethod.PUT)
	public Result fillReturnAddress(@PathVariable("id") Long id, @RequestBody ShoppingRefundDetailRerurnAddressParam param) {
		
		int resultCode = shoppingRefundDetailService.fillReturnAddress(id, param);

		if (resultCode != ResultCode.SUCCESS) {
			return successCreated(resultCode);
		}
		
		return successCreated();
	}

	/**
	 * 买家提交退货物流 修改订单项退款状态为待退款
	 * 
	 * @param shoppingOrderitemId
	 *            购物订单项id
	 * @param param
	 *            参数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "fillLogisticsInformation/{id}", method = RequestMethod.PUT)
	public Result fillLogisticsInformation(@PathVariable("id") Long id, @RequestBody ShoppingRefundDetailLogisticsInformationParam param) {
		if (id == null || id <= 0) {
			return successCreated(ResultCode.ID_EMPTY);
		}

		ShoppingRefundDetailBO shoppingRefundDetailBO = shoppingRefundDetailService.get(id);

		if (shoppingRefundDetailBO == null || shoppingRefundDetailBO.getId() == null || shoppingRefundDetailBO.getId() <= 0) {
			return successCreated(ResultCode.RESOURCE_NOT_FOUND);
		}

		ShoppingOrderItemBO shoppingOrderItemBO = shoppingOrderItemService.get(shoppingRefundDetailBO.getShoppingOrderItemId());

		if (shoppingOrderItemBO == null || shoppingOrderItemBO.getId() == null || shoppingOrderItemBO.getId() <= 0) {
			return successCreated(ResultCode.RESOURCE_NOT_FOUND);
		}

		// 订单状态必须为退款中
		if (!shoppingOrderItemBO.getOrderStatus().equals(ShoppingOrderStatusEnum.REFUNDING)) {
			return successCreated(ResultCode.NOT_REFUNDING);
		}

		// 只有退款状态为待退货才能被允许填写退货物流
		if (!shoppingOrderItemBO.getRefundStatus().equals(RefundStatusEnum.TO_BE_RETURNED)) {
			return successCreated(ResultCode.NOT_RETURNED_STATE);
		}

		// 修改订单项的退款状态为待退款状态，更新退款详情的物流信息
		shoppingRefundDetailService.fillLogisticsInformation(shoppingRefundDetailBO, param);

		return successCreated();
	}

	/**
	 * 商家是否同意退款
	 * 
	 * @param id
	 *            退款详情id
	 * @param param
	 *            参数 是否同意申请
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "agreeToRefund/{id}", method = RequestMethod.PUT)
	public Result agreeToRefund(@PathVariable("id") Long id, @RequestBody ShoppingRefundDetailAgreeToRefundForeignParam param) {

		int resultCode = shoppingRefundDetailService.agreeToRefund(id, param.getIsAgree());

		if (resultCode != ResultCode.SUCCESS) {
			return successCreated(resultCode);
		}
		
		return successCreated();
	}

	/**
	 * 如果商家拒绝买家的退款申请或者拒绝退款<br/>买家可以申请平台介入
	 * 
	 * @param id
	 *            退款详情id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "platformIntervention/{id}", method = RequestMethod.PUT)
	public Result platformIntervention(@PathVariable("id") Long id) {
		if (id == null || id <= 0) {
			return successCreated(ResultCode.ID_EMPTY);
		}

		ShoppingRefundDetailBO shoppingRefundDetailBO = shoppingRefundDetailService.get(id);

		if (shoppingRefundDetailBO == null || shoppingRefundDetailBO.getId() == null || shoppingRefundDetailBO.getId() <= 0) {
			return successCreated(ResultCode.RESOURCE_NOT_FOUND);
		}

		ShoppingOrderItemBO shoppingOrderItemBO = shoppingOrderItemService.get(shoppingRefundDetailBO.getShoppingOrderItemId());

		if (shoppingOrderItemBO == null || shoppingOrderItemBO.getId() == null || shoppingOrderItemBO.getId() <= 0) {
			return successCreated(ResultCode.RESOURCE_NOT_FOUND);
		}

		// 订单状态必须为退款中
		if (!shoppingOrderItemBO.getOrderStatus().equals(ShoppingOrderStatusEnum.REFUNDING)) {
			return successCreated(ResultCode.NOT_REFUNDING);
		}

		// 只有退款失败才能申请平台介入
		if (!shoppingOrderItemBO.getRefundStatus().equals(RefundStatusEnum.REFUND_FAILED)) {
			return successCreated(ResultCode.ORDER_NOT_REFUND_FAILED);
		}

		shoppingRefundDetailService.platformIntervention(shoppingRefundDetailBO);

		return successCreated();
	}

	/**
	 * 根据id查询退款详情的物流信息
	 * 
	 * @param id
	 *            退款详情id
	 * @return
	 */
	@RequestMapping(value = "getExpressInfo/{id}", method = RequestMethod.GET)
	public Result<ShoppingOrderExpressDTO> getExpressInfo(@PathVariable("id") Long id) {

		if (id == null || id <= 0) {
			return successGet(ResultCode.ID_EMPTY);
		}

		ShoppingRefundDetailBO shoppingRefundDetailBO = shoppingRefundDetailService.get(id);

		if (shoppingRefundDetailBO == null) {
			return successGet(ResultCode.RESOURCE_NOT_FOUND);
		}

		ExpressInquiriesDetailBO expressInquiriesDetailBO = expressStrategy.inquiries(shoppingRefundDetailBO.getExpressCompanyCode(), shoppingRefundDetailBO.getWaybillNum());

		return successGet(ShoppingRefundDetailConverter.covert(shoppingRefundDetailBO, expressInquiriesDetailBO));
	}
	
	/**
	 * 买家撤销退货申请
	 * 
	 * @param id
	 *            退款详情id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "revokeRefundRequest/{id}", method = RequestMethod.PUT)
	public Result revokeRefundRequest(@PathVariable("id") Long id) {

		int result = shoppingRefundDetailService.revokeRefundRequest(id);
		
		if (result != ResultCode.SUCCESS) {
			successCreated(result);
		}

		return successCreated();
	}
}
