package com.lawu.eshop.order.srv.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.order.constants.ShoppingOrderItemRefundStatusEnum;
import com.lawu.eshop.order.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.order.constants.ShoppingRefundTypeEnum;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderItemRefundDTO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemRefundBO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderItemRefundDO;

/**
 * 购物退货记录转换器
 * 
 * @author Sunny
 * @date 2017/4/11
 */
public class ShoppingOrderItemRefundConverter {

	/**
	 * ShoppingOrderItemRefundBO转换
	 * 
	 * @param shoppingOrderItemRefundDO
	 * @return
	 */
	public static ShoppingOrderItemRefundBO convert(ShoppingOrderItemRefundDO shoppingOrderItemRefundDO) {
		if (shoppingOrderItemRefundDO == null) {
			return null;
		}

		ShoppingOrderItemRefundBO shoppingOrderItemRefundBO = new ShoppingOrderItemRefundBO();
		BeanUtils.copyProperties(shoppingOrderItemRefundDO, shoppingOrderItemRefundBO, new String[] {"orderStatus", "refundStatus", "type"});
		
		shoppingOrderItemRefundBO.setOrderStatus(ShoppingOrderStatusEnum.getEnum(shoppingOrderItemRefundDO.getOrderStatus()));
		shoppingOrderItemRefundBO.setRefundStatus(ShoppingOrderItemRefundStatusEnum.getEnum(shoppingOrderItemRefundDO.getRefundStatus()));
		shoppingOrderItemRefundBO.setType(ShoppingRefundTypeEnum.getEnum(shoppingOrderItemRefundDO.getType()));
		
		return shoppingOrderItemRefundBO;
	}
	
	/**
	 * ShoppingOrderItemRefundBO List转换
	 * 
	 * @param shoppingOrderItemRefundDOList
	 * @return
	 */
	public static List<ShoppingOrderItemRefundBO> convertShoppingOrderItemRefundBOList(List<ShoppingOrderItemRefundDO> shoppingOrderItemRefundDOList) {
		if (shoppingOrderItemRefundDOList == null || shoppingOrderItemRefundDOList.isEmpty()) {
			return null;
		}
		
		List<ShoppingOrderItemRefundBO> shoppingOrderItemRefundBOList = new ArrayList<ShoppingOrderItemRefundBO>();
		for (ShoppingOrderItemRefundDO shoppingOrderItemRefundDO : shoppingOrderItemRefundDOList) {
			shoppingOrderItemRefundBOList.add(convert(shoppingOrderItemRefundDO));
		}
		
		return shoppingOrderItemRefundBOList;
	}
	
	/**
	 * ShoppingOrderItemRefundDTO转换
	 * 
	 * @param shoppingOrderItemRefundBO
	 * @return
	 */
	public static ShoppingOrderItemRefundDTO convert(ShoppingOrderItemRefundBO shoppingOrderItemRefundBO) {
		if (shoppingOrderItemRefundBO == null) {
			return null;
		}

		ShoppingOrderItemRefundDTO shoppingOrderItemRefundDTO = new ShoppingOrderItemRefundDTO();
		BeanUtils.copyProperties(shoppingOrderItemRefundBO, shoppingOrderItemRefundDTO);
		return shoppingOrderItemRefundDTO;
	}
	
	/**
	 * ShoppingOrderItemRefundBO List转换
	 * 
	 * @param shoppingOrderItemRefundBOList
	 * @return
	 */
	public static List<ShoppingOrderItemRefundDTO> convertShoppingOrderItemRefundDTOList(List<ShoppingOrderItemRefundBO> shoppingOrderItemRefundBOList) {
		if (shoppingOrderItemRefundBOList == null || shoppingOrderItemRefundBOList.isEmpty()) {
			return null;
		}
		
		List<ShoppingOrderItemRefundDTO> shoppingOrderItemRefundDTOList = new ArrayList<ShoppingOrderItemRefundDTO>();
		for (ShoppingOrderItemRefundBO shoppingOrderItemRefundBO : shoppingOrderItemRefundBOList) {
			shoppingOrderItemRefundDTOList.add(convert(shoppingOrderItemRefundBO));
		}
		
		return shoppingOrderItemRefundDTOList;
	}
}