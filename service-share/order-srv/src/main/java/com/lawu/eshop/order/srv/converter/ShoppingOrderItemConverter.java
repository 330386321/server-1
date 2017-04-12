package com.lawu.eshop.order.srv.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.mall.constants.ShoppingOrderItemRefundStatusEnum;
import com.lawu.eshop.mall.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderItemDTO;
import com.lawu.eshop.mall.param.ShoppingOrderSettlementItemParam;
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
		// 设置为未付款状态
		shoppingOrderItemDO.setOrderStatus((byte) 0x00);
		// 设置为待评价
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
		if (shoppingOrderItemDO == null) {
			return null;
		}

		ShoppingOrderItemBO shoppingOrderItemBO = new ShoppingOrderItemBO();
		BeanUtils.copyProperties(shoppingOrderItemDO, shoppingOrderItemBO, new String[]{"orderStatus", "refundStatus"});
		
		shoppingOrderItemBO.setOrderStatus(ShoppingOrderStatusEnum.getEnum(shoppingOrderItemDO.getOrderStatus()));
		shoppingOrderItemBO.setRefundStatus(ShoppingOrderItemRefundStatusEnum.getEnum(shoppingOrderItemDO.getRefundStatus()));
		
		return shoppingOrderItemBO;
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
		if (shoppingOrderItemBO == null) {
			return null;
		}

		ShoppingOrderItemDTO shoppingOrderItemDTO = new ShoppingOrderItemDTO();
		BeanUtils.copyProperties(shoppingOrderItemBO, shoppingOrderItemDTO);
		
		return shoppingOrderItemDTO;
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
