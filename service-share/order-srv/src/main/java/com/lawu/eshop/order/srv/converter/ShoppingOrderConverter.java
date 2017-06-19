package com.lawu.eshop.order.srv.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lawu.eshop.order.constants.CommissionStatusEnum;
import com.lawu.eshop.order.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.order.constants.StatusEnum;
import com.lawu.eshop.order.constants.TransactionPayTypeEnum;
import com.lawu.eshop.order.dto.CommentOrderDTO;
import com.lawu.eshop.order.dto.ShoppingOrderCommissionDTO;
import com.lawu.eshop.order.dto.ShoppingOrderIsNoOnGoingOrderDTO;
import com.lawu.eshop.order.dto.ShoppingOrderPaymentDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExpressDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderNumberOfOrderStatusDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderNumberOfOrderStatusForMerchantForeignDTO;
import com.lawu.eshop.order.param.ShoppingOrderSettlementParam;
import com.lawu.eshop.order.param.ShoppingOrderUpdateInfomationParam;
import com.lawu.eshop.order.srv.bo.ExpressInquiriesDetailBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderIsNoOnGoingOrderBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderNumberOfOrderStatusBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderNumberOfOrderStatusForMerchantBO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderDO;
import com.lawu.eshop.utils.RandomUtil;

/**
 *
 * 购物订单转换器
 *
 * @author Sunny
 * @date 2017/04/06
 */
public class ShoppingOrderConverter {

	/**
	 * ShoppingOrderDO转换
	 * 
	 * @param param
	 * @return
	 */
	public static ShoppingOrderDO convert(ShoppingOrderSettlementParam param) {
		ShoppingOrderDO rtn = null;
		if (param == null) {
			return rtn;
		}

		rtn = new ShoppingOrderDO();
		rtn.setCommodityTotalPrice(param.getCommodityTotalPrice());
		rtn.setConsigneeAddress(param.getConsigneeAddress());
		rtn.setConsigneeMobile(param.getConsigneeMobile());
		rtn.setConsigneeName(param.getConsigneeName());
		rtn.setFreightPrice(param.getFreightPrice());
		rtn.setIsFans(param.getIsFans());
		rtn.setIsNoReasonReturn(param.getIsNoReasonReturn());
		rtn.setMemberId(param.getMemberId());
		rtn.setMemberNum(param.getMemberNum());
		rtn.setMerchantId(param.getMerchantId());
		rtn.setMerchantName(param.getMerchantName());
		rtn.setMerchantNum(param.getMerchantNum());
		rtn.setMerchantStoreId(param.getMerchantStoreId());
		rtn.setMessage(param.getMessage());
		rtn.setOrderTotalPrice(param.getOrderTotalPrice());
		
		// 设置自动收货为false
		rtn.setIsAutomaticReceipt(false);
		// 设置为待处理状态
		rtn.setOrderStatus(ShoppingOrderStatusEnum.PENDING.getValue());
		// 记录状态设置为正常
		rtn.setStatus(StatusEnum.VALID.getValue());
		
		// 设置提成状态，和支付给商家的实际金额
		rtn.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
		rtn.setActualAmount(param.getOrderTotalPrice());
		
		rtn.setOrderNum(RandomUtil.getTableNumRandomString(""));
		rtn.setGmtCreate(new Date());
		rtn.setGmtModified(new Date());

		return rtn;
	}
	
	/**
	 * 
	 * @param shoppingOrderItemBO
	 * @return
	 * @author Sunny
	 * @date 2017年6月16日
	 */
	public static CommentOrderDTO coverCommentStatusDTO(ShoppingOrderItemBO shoppingOrderItemBO) {
		CommentOrderDTO rtn = null;
		if (shoppingOrderItemBO == null) {
			return rtn;
		}
		rtn = new CommentOrderDTO();
		rtn.setEvaluation(shoppingOrderItemBO.getIsEvaluation());
		return rtn;
	}

	/**
	 * ShoppingOrderExpressDTO转换
	 * 
	 * @param shoppingOrderBO
	 * @param expressInquiriesDetailBO
	 * @return
	 */
	public static ShoppingOrderExpressDTO covert(ShoppingOrderBO shoppingOrderBO, ExpressInquiriesDetailBO expressInquiriesDetailBO) {
		ShoppingOrderExpressDTO rtn = null;
		if (shoppingOrderBO == null) {
			return rtn;
		}

		rtn = new ShoppingOrderExpressDTO();
		rtn.setExpressCompanyName(shoppingOrderBO.getExpressCompanyName());
		rtn.setWaybillNum(shoppingOrderBO.getWaybillNum());
		
		rtn.setExpressInquiriesDetailDTO(ExpressInquiriesDetailConverter.convert(expressInquiriesDetailBO));

		return rtn;
	}

	public static ShoppingOrderBO convertShoppingOrderBO(ShoppingOrderDO shoppingOrderDO) {
		ShoppingOrderBO rtn = null;
		if (shoppingOrderDO == null) {
			return rtn;
		}

		rtn = new ShoppingOrderBO();
		rtn.setActualAmount(shoppingOrderDO.getActualAmount());
		rtn.setCommodityTotalPrice(shoppingOrderDO.getCommodityTotalPrice());
		rtn.setConsigneeAddress(shoppingOrderDO.getConsigneeAddress());
		rtn.setConsigneeMobile(shoppingOrderDO.getConsigneeMobile());
		rtn.setConsigneeName(shoppingOrderDO.getConsigneeName());
		rtn.setExpressCompanyCode(shoppingOrderDO.getExpressCompanyCode());
		rtn.setExpressCompanyId(shoppingOrderDO.getExpressCompanyId());
		rtn.setExpressCompanyName(shoppingOrderDO.getExpressCompanyName());
		rtn.setFreightPrice(shoppingOrderDO.getFreightPrice());
		rtn.setGmtCommission(shoppingOrderDO.getGmtCommission());
		rtn.setGmtCreate(shoppingOrderDO.getGmtCreate());
		rtn.setGmtModified(shoppingOrderDO.getGmtModified());
		rtn.setGmtPayment(shoppingOrderDO.getGmtPayment());
		rtn.setGmtTransaction(shoppingOrderDO.getGmtTransaction());
		rtn.setGmtTransport(shoppingOrderDO.getGmtTransport());
		rtn.setId(shoppingOrderDO.getId());
		rtn.setIsAutomaticReceipt(shoppingOrderDO.getIsAutomaticReceipt());
		rtn.setIsDone(shoppingOrderDO.getIsDone());
		rtn.setIsFans(shoppingOrderDO.getIsFans());
		rtn.setIsNeedsLogistics(shoppingOrderDO.getIsNeedsLogistics());
		rtn.setIsNoReasonReturn(shoppingOrderDO.getIsNoReasonReturn());
		rtn.setMemberId(shoppingOrderDO.getMemberId());
		rtn.setMemberNum(shoppingOrderDO.getMemberNum());
		rtn.setMerchantId(shoppingOrderDO.getMerchantId());
		rtn.setMerchantName(shoppingOrderDO.getMerchantName());
		rtn.setMerchantNum(shoppingOrderDO.getMerchantNum());
		rtn.setMerchantStoreId(shoppingOrderDO.getMerchantStoreId());
		rtn.setMessage(shoppingOrderDO.getMessage());
		rtn.setOrderNum(shoppingOrderDO.getOrderNum());
		rtn.setOrderTotalPrice(shoppingOrderDO.getOrderTotalPrice());
		rtn.setRemark(shoppingOrderDO.getRemark());
		rtn.setSendTime(shoppingOrderDO.getSendTime());
		rtn.setShoppingCartIdsStr(shoppingOrderDO.getShoppingCartIdsStr());
		rtn.setThirdNumber(shoppingOrderDO.getThirdNumber());
		rtn.setWaybillNum(shoppingOrderDO.getWaybillNum());
		
		rtn.setStatus(StatusEnum.getEnum(shoppingOrderDO.getStatus()));
		rtn.setOrderStatus(ShoppingOrderStatusEnum.getEnum(shoppingOrderDO.getOrderStatus()));
		rtn.setPaymentMethod(TransactionPayTypeEnum.getEnum(shoppingOrderDO.getPaymentMethod()));
		rtn.setCommissionStatus(CommissionStatusEnum.getEnum(shoppingOrderDO.getCommissionStatus()));

		return rtn;
	}

	/**
	 * 组装更新订单参数
	 * 
	 * @param shoppingOrderDO
	 * @return
	 * @author Sunny
	 */
	public static ShoppingOrderDO convert(ShoppingOrderDO shoppingOrderDO, ShoppingOrderUpdateInfomationParam param) {
		ShoppingOrderDO rtn = null;
		if (shoppingOrderDO == null || param == null) {
			return rtn;
		}

		rtn = new ShoppingOrderDO();
		rtn.setId(shoppingOrderDO.getId());
		rtn.setConsigneeAddress(param.getConsigneeAddress());
		rtn.setConsigneeMobile(param.getConsigneeMobile());
		rtn.setConsigneeName(param.getConsigneeName());
		rtn.setExpressCompanyCode(param.getExpressCompanyCode());
		rtn.setExpressCompanyId(param.getExpressCompanyId());
		rtn.setExpressCompanyName(param.getExpressCompanyName());
		rtn.setWaybillNum(param.getWaybillNum());
		
		rtn.setOrderStatus(param.getOrderStatus().getValue());

		return rtn;
	}

	/**
	 * 组装订单是否有正在进行中的订单参数
	 * 
	 * @param count
	 * @return
	 * @author Sunny
	 */
	public static ShoppingOrderIsNoOnGoingOrderBO convert(long count) {
		ShoppingOrderIsNoOnGoingOrderBO rtn = new ShoppingOrderIsNoOnGoingOrderBO();

		if (count <= 0) {
			rtn.setIsNoOnGoingOrder(true);
		} else {
			rtn.setIsNoOnGoingOrder(false);
		}

		return rtn;
	}

	/**
	 * 组装订单是否有正在进行中的订单参数
	 * 
	 * @param ShoppingOrderIsNoOnGoingOrderBO
	 * @return
	 * @author Sunny
	 */
	public static ShoppingOrderIsNoOnGoingOrderDTO convert(ShoppingOrderIsNoOnGoingOrderBO shoppingOrderIsNoOnGoingOrderBO) {
		ShoppingOrderIsNoOnGoingOrderDTO rtn = null;
		if (shoppingOrderIsNoOnGoingOrderBO == null) {
			return rtn;
		}

		rtn = new ShoppingOrderIsNoOnGoingOrderDTO();
		rtn.setIsNoOnGoingOrder(shoppingOrderIsNoOnGoingOrderBO.getIsNoOnGoingOrder());
		
		return rtn;
	}

	/**
	 * 组装订单对应订单状态的数量
	 * 
	 * @param shoppingOrderNumberOfOrderStatusBO
	 * @return
	 * @author Sunny
	 */
	public static ShoppingOrderNumberOfOrderStatusDTO convert(ShoppingOrderNumberOfOrderStatusBO shoppingOrderNumberOfOrderStatusBO) {
		ShoppingOrderNumberOfOrderStatusDTO rtn = null;
		if (shoppingOrderNumberOfOrderStatusBO == null) {
			return rtn;
		}
		
		rtn = new ShoppingOrderNumberOfOrderStatusDTO();
		rtn.setBeShippedCount(shoppingOrderNumberOfOrderStatusBO.getBeShippedCount());
		rtn.setEvaluationCount(shoppingOrderNumberOfOrderStatusBO.getEvaluationCount());
		rtn.setPendingPaymentCount(shoppingOrderNumberOfOrderStatusBO.getPendingPaymentCount());
		rtn.setRefundingCount(shoppingOrderNumberOfOrderStatusBO.getRefundingCount());
		rtn.setToBeReceivedCount(shoppingOrderNumberOfOrderStatusBO.getToBeReceivedCount());

		return rtn;
	}
	
	/**
	 * @param shoppingOrderDOList
	 * @return
	 * @author Sunny
	 */
	public static List<ShoppingOrderBO> convert(List<ShoppingOrderDO> shoppingOrderDOList) {
		List<ShoppingOrderBO> rtn = null;
		
		if (shoppingOrderDOList == null || shoppingOrderDOList.isEmpty()) {
			return rtn;
		}
		
		rtn = new ArrayList<ShoppingOrderBO>();
		
		for (ShoppingOrderDO item : shoppingOrderDOList) {
			rtn.add(convertShoppingOrderBO(item));
		}

		return rtn;
	}
	
	/**
	 * ShoppingOrderNumberOfOrderStatusForMerchantForeignDTO转换
	 * 
	 * 
	 * @param shoppingOrderNumberOfOrderStatusForMerchantBO
	 * @return
	 * @author Sunny
	 */
	public static ShoppingOrderNumberOfOrderStatusForMerchantForeignDTO convert(ShoppingOrderNumberOfOrderStatusForMerchantBO shoppingOrderNumberOfOrderStatusForMerchantBO) {
		ShoppingOrderNumberOfOrderStatusForMerchantForeignDTO rtn = null;
		if (shoppingOrderNumberOfOrderStatusForMerchantBO == null) {
			return rtn;
		}
		
		rtn = new ShoppingOrderNumberOfOrderStatusForMerchantForeignDTO();
		rtn.setBeShippedCount(shoppingOrderNumberOfOrderStatusForMerchantBO.getBeShippedCount());
		rtn.setPendingPaymentCount(shoppingOrderNumberOfOrderStatusForMerchantBO.getPendingPaymentCount());
		rtn.setRefundingCount(shoppingOrderNumberOfOrderStatusForMerchantBO.getRefundingCount());
		rtn.setToBeReceivedCount(shoppingOrderNumberOfOrderStatusForMerchantBO.getToBeReceivedCount());

		return rtn;
	}
	
	/**
	 * 
	 * @param ShoppingOrderPaymentDTO
	 * @return
	 * @author Sunny
	 */
	public static ShoppingOrderPaymentDTO convert(ShoppingOrderBO shoppingOrderBO) {
		ShoppingOrderPaymentDTO rtn = null;
		
		if (shoppingOrderBO == null) {
			return rtn;
		}

		rtn = new ShoppingOrderPaymentDTO();
		rtn.setId(shoppingOrderBO.getId());
		rtn.setOrderNum(shoppingOrderBO.getOrderNum());
		rtn.setOrderTotalPrice(shoppingOrderBO.getOrderTotalPrice());
		
		return rtn;
	}
	
	/**
	 * @param shoppingOrderBO
	 * @return
	 * @author Sunny
	 */
	public static ShoppingOrderCommissionDTO convertShoppingOrderCommissionDTO(ShoppingOrderBO shoppingOrderBO) {
		ShoppingOrderCommissionDTO rtn = null;
		
		if (shoppingOrderBO == null) {	
			return rtn;
		}
		
		rtn = new ShoppingOrderCommissionDTO();
		rtn.setId(shoppingOrderBO.getId());
		rtn.setMemberNum(shoppingOrderBO.getMemberNum());
		rtn.setMerchantNum(shoppingOrderBO.getMerchantNum());
		rtn.setActualAmount(shoppingOrderBO.getActualAmount());
		
		return rtn;
	}
	
	/**
	 * @param shoppingOrderBOList
	 * @return
	 * @author Sunny
	 */
	public static List<ShoppingOrderCommissionDTO> convertShoppingOrderCommissionDTOList(List<ShoppingOrderBO> shoppingOrderBOList) {
		List<ShoppingOrderCommissionDTO> rtn = new ArrayList<ShoppingOrderCommissionDTO>();
		
		if (shoppingOrderBOList == null || shoppingOrderBOList.isEmpty()) {
			return rtn;
		}
		
		for (ShoppingOrderBO item : shoppingOrderBOList) {
			rtn.add(convertShoppingOrderCommissionDTO(item));
		}

		return rtn;
	}
}
