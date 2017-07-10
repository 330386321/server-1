package com.lawu.eshop.order.srv.controller;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.order.constants.ShoppingRefundTypeEnum;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExpressDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingRefundDetailDTO;
import com.lawu.eshop.order.param.ShoppingRefundDetailLogisticsInformationParam;
import com.lawu.eshop.order.param.ShoppingRefundDetailRerurnAddressParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundDetailAgreeToApplyForeignParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundDetailAgreeToRefundForeignParam;
import com.lawu.eshop.order.srv.bo.ExpressInquiriesDetailBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemExtendBO;
import com.lawu.eshop.order.srv.bo.ShoppingRefundDetailBO;
import com.lawu.eshop.order.srv.bo.ShoppingRefundDetailExtendBO;
import com.lawu.eshop.order.srv.constants.PropertyNameConstant;
import com.lawu.eshop.order.srv.converter.ShoppingRefundDetailConverter;
import com.lawu.eshop.order.srv.exception.CanNotApplyForPlatformInterventionException;
import com.lawu.eshop.order.srv.exception.CanNotCancelApplicationException;
import com.lawu.eshop.order.srv.exception.CanNotFillOutTheReturnLogisticsException;
import com.lawu.eshop.order.srv.exception.DataNotExistException;
import com.lawu.eshop.order.srv.exception.IllegalOperationException;
import com.lawu.eshop.order.srv.service.PropertyService;
import com.lawu.eshop.order.srv.service.ShoppingRefundDetailService;
import com.lawu.eshop.order.srv.strategy.ExpressStrategy;
import com.lawu.eshop.utils.DateUtil;

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
	@Qualifier("kDNiaoExpressStrategy")
	private ExpressStrategy expressStrategy;
	
	@Autowired
	private PropertyService propertyService;
	
	/**
	 * 根据订单项id查询退款信息
	 * 
	 * @param shoppingOrderitemId
	 *            购物订单项id
	 * @return
	 */
	@RequestMapping(value = "getRefundDetail/{shoppingOrderItemId}", method = RequestMethod.GET)
	public Result<ShoppingRefundDetailDTO> getRefundDetail(@PathVariable("shoppingOrderItemId") Long shoppingOrderItemId, @RequestParam("memberId") Long memberId, @RequestParam("merchantId") Long merchantId) {
		ShoppingOrderItemExtendBO shoppingOrderItemExtendBO = shoppingRefundDetailService.getByShoppingOrderItemId(shoppingOrderItemId);
		if (shoppingOrderItemExtendBO == null || shoppingOrderItemExtendBO.getShoppingOrder() == null) {
			return successGet(ResultCode.NOT_FOUND_DATA);
		}
		if (shoppingOrderItemExtendBO.getShoppingOrder().getMemberId().equals(memberId)) {
			return successGet(ResultCode.ILLEGAL_OPERATION);
		}
		ShoppingRefundDetailExtendBO shoppingRefundDetailBO = shoppingOrderItemExtendBO.getShoppingRefundDetail();
		ShoppingRefundDetailDTO shoppingRefundDetailDTO = ShoppingRefundDetailConverter.convert(shoppingOrderItemExtendBO);
		// 倒计时在服务端放入
		Long countdown = null;
		String time = null;
		Date date = null;
		switch (shoppingOrderItemExtendBO.getRefundStatus()) {
			case TO_BE_CONFIRMED:
				// 1.等待商家确认超时，自动退款给用户
				
				if (ShoppingRefundTypeEnum.REFUND.equals(shoppingOrderItemExtendBO.getShoppingRefundDetail().getType())) {
					// 1)退款类型为退款
					time = propertyService.getByName(PropertyNameConstant.TO_BE_CONFIRMED_FOR_REFUND_REFUND_TIME);
					date = DateUtil.add(shoppingOrderItemExtendBO.getGmtModified(), Integer.valueOf(time), Calendar.DAY_OF_YEAR);
					countdown = DateUtil.interval(new Date(), date, Calendar.MILLISECOND);
				} else {
					// 2)退款类型为退货退款
					time = propertyService.getByName(PropertyNameConstant.TO_BE_CONFIRMED_FOR_RETURN_REFUND_REFUND_TIME);
					date = DateUtil.add(shoppingOrderItemExtendBO.getGmtModified(), Integer.valueOf(time), Calendar.DAY_OF_YEAR);
					countdown = DateUtil.interval(new Date(), date, Calendar.MILLISECOND);
				}
				break;
			case FILL_RETURN_ADDRESS:
				// 2.等待商家填写退货地址超时，自动退款
				
				// 自动退款时间
				time = propertyService.getByName(PropertyNameConstant.FILL_RETURN_ADDRESS_REFUND_TIME);
				date = DateUtil.add(shoppingOrderItemExtendBO.getGmtModified(), Integer.valueOf(time), Calendar.DAY_OF_YEAR);
				countdown = DateUtil.interval(new Date(), date, Calendar.MILLISECOND);
				break;
			case TO_BE_RETURNED:
				// 3.等待买家退货，自动取消退款申请
				
				// 自动取消退款申请时间
				time = propertyService.getByName(PropertyNameConstant.TO_BE_RETURNED_REVOKE_TIME);
				date = DateUtil.add(shoppingOrderItemExtendBO.getGmtModified(), Integer.valueOf(time), Calendar.DAY_OF_YEAR);
				countdown = DateUtil.interval(new Date(), date, Calendar.MILLISECOND);
				break;
			case TO_BE_REFUNDED:
				// 4.等待商家退款，自动退款
				
				// 自动退款时间
				if (ShoppingRefundTypeEnum.RETURN_REFUND.equals(shoppingRefundDetailBO.getType())) {
					// 如果退款类型为退货退款
					time = propertyService.getByName(PropertyNameConstant.TO_BE_REFUNDED_REFUND_TIME);
					date = DateUtil.add(shoppingOrderItemExtendBO.getGmtModified(), Integer.valueOf(time), Calendar.DAY_OF_YEAR);
				} else if (ShoppingRefundTypeEnum.REFUND.equals(shoppingRefundDetailBO.getType())) {
					// 如果退款类型为退款
					time = propertyService.getByName(PropertyNameConstant.TO_BE_CONFIRMED_FOR_REFUND_REFUND_TIME);
					date = DateUtil.add(shoppingOrderItemExtendBO.getGmtModified(), Integer.valueOf(time), Calendar.DAY_OF_YEAR);
				}
				countdown = DateUtil.interval(new Date(), date, Calendar.MILLISECOND);
				break;
			case REFUND_FAILED:
				// 5.退款失败，等待卖家处理超时，自动取消退款申请
				
				// 自动取消退款申请时间
				time = propertyService.getByName(PropertyNameConstant.REFUND_FAILED_REVOKE_TIME);
				date = DateUtil.add(shoppingOrderItemExtendBO.getGmtModified(), Integer.valueOf(time), Calendar.DAY_OF_YEAR);
				countdown = DateUtil.interval(new Date(), date, Calendar.MILLISECOND);
				break;
			case PLATFORM_INTERVENTION:
				// 6.平台介入，倒计时
				
				if (ShoppingRefundTypeEnum.REFUND.equals(shoppingOrderItemExtendBO.getShoppingRefundDetail().getType())) {
					time = propertyService.getByName(PropertyNameConstant.PLATFORM_INTERVENTION_REFUND_TIME);
				} else {
					time = propertyService.getByName(PropertyNameConstant.PLATFORM_INTERVENTION_RETURN_REFUND_TIME);
				}
				date = DateUtil.add(shoppingOrderItemExtendBO.getGmtModified(), Integer.valueOf(time), Calendar.DAY_OF_YEAR);
				countdown = DateUtil.interval(new Date(), date, Calendar.MILLISECOND);
				break;
			case REFUND_SUCCESSFULLY:
				// 7.退款成功，退款到账提示时间
				
				time = propertyService.getByName(PropertyNameConstant.REFUND_TO_THE_ACCOUNT_TIME);
				date = DateUtil.add(shoppingOrderItemExtendBO.getGmtModified(), Integer.valueOf(time), Calendar.DAY_OF_YEAR);
				shoppingRefundDetailDTO.setGmtToAccount(date);
				
				break;
			default:
				break;
		}
		shoppingRefundDetailDTO.setCountdown(countdown);
		return successCreated(shoppingRefundDetailDTO);
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
		int resultCode = shoppingRefundDetailService.agreeToApply(id, param);

		if (resultCode != ResultCode.SUCCESS) {
			return successCreated(resultCode);
		}
		 
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
	public Result fillLogisticsInformation(@PathVariable("id") Long id, @RequestParam("memberId") Long memberId, @RequestBody ShoppingRefundDetailLogisticsInformationParam param) {
		try {
			// 修改订单项的退款状态为待退款状态，更新退款详情的物流信息
			shoppingRefundDetailService.fillLogisticsInformation(id, memberId, param);
		} catch (DataNotExistException e) {
		 	return successCreated(ResultCode.NOT_FOUND_DATA, e.getMessage());
		} catch (IllegalOperationException e) {
		 	return successCreated(ResultCode.ILLEGAL_OPERATION, e.getMessage());
		} catch (CanNotFillOutTheReturnLogisticsException e) {
		 	return successCreated(ResultCode.CAN_NOT_FILL_OUT_THE_RETURN_LOGISTICS, e.getMessage());
		}
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

		int resultCode = shoppingRefundDetailService.agreeToRefund(id, param);

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
	public Result platformIntervention(@PathVariable("id") Long id, @RequestParam("memberId") Long memberId) {
		try {
			shoppingRefundDetailService.platformIntervention(id, memberId);
		} catch (DataNotExistException e) {
		 	return successCreated(ResultCode.NOT_FOUND_DATA, e.getMessage());
		} catch (IllegalOperationException e) {
		 	return successCreated(ResultCode.ILLEGAL_OPERATION, e.getMessage());
		} catch (CanNotApplyForPlatformInterventionException e) {
		 	return successCreated(ResultCode.CAN_NOT_APPLY_FOR_PLATFORM_INTERVENTION, e.getMessage());
		}
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
		
		// 如果快递公司编码和物流编号为空.不查询物流
		ExpressInquiriesDetailBO expressInquiriesDetailBO = null;
		if (StringUtils.isNotBlank(shoppingRefundDetailBO.getExpressCompanyCode()) && StringUtils.isNotBlank(shoppingRefundDetailBO.getWaybillNum())) {
			expressInquiriesDetailBO = expressStrategy.inquiries(shoppingRefundDetailBO.getExpressCompanyCode(), shoppingRefundDetailBO.getWaybillNum());
		}
		
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
	public Result revokeRefundRequest(@PathVariable("id") Long id, @RequestParam(name = "memberId", required = false) Long memberId) {
		try {
			shoppingRefundDetailService.revokeRefundRequest(id, memberId);
		} catch (DataNotExistException e) {
		 	return successCreated(ResultCode.NOT_FOUND_DATA, e.getMessage());
		} catch (IllegalOperationException e) {
		 	return successCreated(ResultCode.ILLEGAL_OPERATION, e.getMessage());
		} catch (CanNotCancelApplicationException e) {
		 	return successCreated(ResultCode.CAN_NOT_CANCEL_APPLICATION, e.getMessage());
		}
		return successCreated();
	}
}
