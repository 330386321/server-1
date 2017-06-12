package com.lawu.eshop.order.srv.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.order.constants.RefundStatusEnum;
import com.lawu.eshop.order.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderItemDTO;
import com.lawu.eshop.order.param.ShoppingOrderSettlementItemParam;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemBO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDO;

/**
 *
 * 购物订单项转换器
 *
 * @author Sunny
 * @date 2017/04/06
 */
public class ShoppingOrderItemConverter {

	/**
	 * ShoppingOrderItemDO转换
	 * 
	 * @param shoppingOrderId
	 * @param param
	 * @return
	 */
	public static ShoppingOrderItemDO convert(Long shoppingOrderId, ShoppingOrderSettlementItemParam param) {
		if (param == null) {
			return null;
		}

		ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
		BeanUtils.copyProperties(param, shoppingOrderItemDO, new String[] { "shoppingOrderId", "orderStatus", "gmtCreate", "gmtModified" });
		// 设置订单id
		shoppingOrderItemDO.setShoppingOrderId(shoppingOrderId);
		// 设置为待处理
		shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.PENDING.getValue());
		// 设置为未评价
		shoppingOrderItemDO.setIsEvaluation(false);
		shoppingOrderItemDO.setGmtCreate(new Date());
		shoppingOrderItemDO.setGmtModified(new Date());

		return shoppingOrderItemDO;
	}
	
	/**
	 * ShoppingOrderItemBO转换
	 * 
	 * @param shoppingOrderItemDO
	 * @return
	 */
	public static ShoppingOrderItemBO convert(ShoppingOrderItemDO shoppingOrderItemDO) {
		ShoppingOrderItemBO rtn = null;
		
		if (shoppingOrderItemDO == null) {
			return rtn;
		}

		rtn = new ShoppingOrderItemBO();
		BeanUtils.copyProperties(shoppingOrderItemDO, rtn, new String[]{"orderStatus", "refundStatus"});
		rtn.setId(shoppingOrderItemDO.getId());
		rtn.setIsAllowRefund(shoppingOrderItemDO.getIsAllowRefund());
		rtn.setIsEvaluation(shoppingOrderItemDO.getIsEvaluation());
		rtn.setProductFeatureImage(shoppingOrderItemDO.getProductFeatureImage());
		rtn.setProductId(shoppingOrderItemDO.getProductId());
		rtn.setProductModelId(shoppingOrderItemDO.getProductModelId());
		rtn.setProductModelName(shoppingOrderItemDO.getProductModelName());
		rtn.setProductName(shoppingOrderItemDO.getProductName());
		rtn.setQuantity(shoppingOrderItemDO.getQuantity());
		rtn.setRegularPrice(shoppingOrderItemDO.getRegularPrice());
		rtn.setSalesPrice(shoppingOrderItemDO.getSalesPrice());
		rtn.setSendTime(shoppingOrderItemDO.getSendTime());
		rtn.setShoppingOrderId(shoppingOrderItemDO.getShoppingOrderId());
		rtn.setGmtCreate(shoppingOrderItemDO.getGmtCreate());
		rtn.setGmtModified(shoppingOrderItemDO.getGmtModified());
		rtn.setOrderStatus(ShoppingOrderStatusEnum.getEnum(shoppingOrderItemDO.getOrderStatus()));
		rtn.setRefundStatus(RefundStatusEnum.getEnum(shoppingOrderItemDO.getRefundStatus()));
		
		return rtn;
	}
	
	/**
	 * ShoppingOrderItemBO List转换
	 * 
	 * @param shoppingOrderItemDO
	 * @return
	 */
	public static List<ShoppingOrderItemBO> convert(List<ShoppingOrderItemDO> shoppingOrderItemDOList) {
		if (shoppingOrderItemDOList == null || shoppingOrderItemDOList.isEmpty()) {
			return null;
		}
		
		List<ShoppingOrderItemBO> shoppingOrderItemBOList = new ArrayList<ShoppingOrderItemBO>();
		for (ShoppingOrderItemDO shoppingOrderItemDO : shoppingOrderItemDOList) {
			shoppingOrderItemBOList.add(convert(shoppingOrderItemDO));
		}
		
		return shoppingOrderItemBOList;
	}
	
	/**
	 * ShoppingOrderItemBO转换
	 * 
	 * @param shoppingOrderItemDO
	 * @return
	 */
	public static ShoppingOrderItemDTO convert(ShoppingOrderItemBO shoppingOrderItemBO) {
		ShoppingOrderItemDTO rtn = null;
		if (shoppingOrderItemBO == null) {
			return rtn;
		}

		rtn = new ShoppingOrderItemDTO();
		rtn.setId(shoppingOrderItemBO.getId());
		rtn.setIsAllowRefund(shoppingOrderItemBO.getIsAllowRefund());
		rtn.setIsEvaluation(shoppingOrderItemBO.getIsEvaluation());
		rtn.setOrderStatus(shoppingOrderItemBO.getOrderStatus());
		rtn.setProductFeatureImage(shoppingOrderItemBO.getProductFeatureImage());
		rtn.setProductId(shoppingOrderItemBO.getProductId());
		rtn.setProductModelId(shoppingOrderItemBO.getProductModelId());
		rtn.setProductModelName(shoppingOrderItemBO.getProductModelName());
		rtn.setProductName(shoppingOrderItemBO.getProductName());
		rtn.setQuantity(shoppingOrderItemBO.getQuantity());
		rtn.setRefundStatus(shoppingOrderItemBO.getRefundStatus());
		rtn.setRegularPrice(shoppingOrderItemBO.getRegularPrice());
		rtn.setSalesPrice(shoppingOrderItemBO.getSalesPrice());
		
		return rtn;
	}
	
	/**
	 * ShoppingOrderItemBO List转换
	 * 
	 * @param shoppingOrderItemDO
	 * @return
	 */
	public static List<ShoppingOrderItemDTO> convertShoppingOrderItemDTOList(List<ShoppingOrderItemBO> shoppingOrderItemBOList) {
		if (shoppingOrderItemBOList == null || shoppingOrderItemBOList.isEmpty()) {
			return null;
		}
		
		List<ShoppingOrderItemDTO> shoppingOrderItemDTOList = new ArrayList<ShoppingOrderItemDTO>();
		for (ShoppingOrderItemBO shoppingOrderItemBO : shoppingOrderItemBOList) {
			shoppingOrderItemDTOList.add(convert(shoppingOrderItemBO));
		}
		
		return shoppingOrderItemDTOList;
	}

}
