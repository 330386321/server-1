package com.lawu.eshop.order.srv.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.mall.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderExtendQueryDTO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderExtendQueryBO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderExtendDO;

/**
 *
 * 购物订单扩展转换器
 *
 * @author Sunny
 * @date 2017/04/06
 */
public class ShoppingOrderExtendConverter {
	
	/**
	 * ShoppingOrderExtendQueryBO转换
	 * 
	 * @param shoppingOrderExtendDO
	 * @return
	 */
	public static ShoppingOrderExtendQueryBO convert(ShoppingOrderExtendDO shoppingOrderExtendDO) {
		if (shoppingOrderExtendDO == null) {
			return null;
		}

		ShoppingOrderExtendQueryBO shoppingOrderExtendQueryBO = new ShoppingOrderExtendQueryBO();
		BeanUtils.copyProperties(shoppingOrderExtendDO, shoppingOrderExtendQueryBO, new String[]{"items", "orderStatus"});
		
		// 转换为枚举类型
		shoppingOrderExtendQueryBO.setOrderStatus(ShoppingOrderStatusEnum.getEnum(shoppingOrderExtendDO.getOrderStatus()));
		
		shoppingOrderExtendQueryBO.setItems(ShoppingOrderItemConverter.convert(shoppingOrderExtendDO.getItems()));
		
		return shoppingOrderExtendQueryBO;
	}
	
	/**
	 * ShoppingOrderExtendQueryBO List转换
	 * 
	 * @param shoppingOrderExtendDOList
	 * @return
	 */
	public static List<ShoppingOrderExtendQueryBO> convert(List<ShoppingOrderExtendDO> shoppingOrderExtendDOList) {
		if (shoppingOrderExtendDOList == null || shoppingOrderExtendDOList.isEmpty()) {
			return null;
		}
		
		List<ShoppingOrderExtendQueryBO> shoppingOrderExtendQueryBOS = new ArrayList<ShoppingOrderExtendQueryBO>();
		for (ShoppingOrderExtendDO shoppingOrderExtendDO : shoppingOrderExtendDOList) {
			shoppingOrderExtendQueryBOS.add(convert(shoppingOrderExtendDO));
		}
		
		return shoppingOrderExtendQueryBOS;
	}
	
	/**
	 * ShoppingOrderExtendQueryDTO转换
	 * 
	 * @param shoppingOrderDO
	 * @return
	 */
	public static ShoppingOrderExtendQueryDTO convert(ShoppingOrderExtendQueryBO shoppingOrderExtendQueryBO) {
		if (shoppingOrderExtendQueryBO == null) {
			return null;
		}

		ShoppingOrderExtendQueryDTO shoppingOrderExtendQueryDTO = new ShoppingOrderExtendQueryDTO();
		BeanUtils.copyProperties(shoppingOrderExtendQueryBO, shoppingOrderExtendQueryDTO, new String[]{"items"});
		
		shoppingOrderExtendQueryDTO.setItems(ShoppingOrderItemConverter.convertShoppingOrderItemDTOList(shoppingOrderExtendQueryBO.getItems()));
		
		return shoppingOrderExtendQueryDTO;
	}
	
	/**
	 * ShoppingOrderExtendQueryDTO List转换
	 * 
	 * @param shoppingOrderDOS
	 * @return
	 */
	public static List<ShoppingOrderExtendQueryDTO> convertShoppingOrderExtendQueryDTOList(List<ShoppingOrderExtendQueryBO> shoppingOrderExtendQueryBOList) {
		if (shoppingOrderExtendQueryBOList == null || shoppingOrderExtendQueryBOList.isEmpty()) {
			return null;
		}
		
		List<ShoppingOrderExtendQueryDTO> shoppingOrderExtendQueryDTOList = new ArrayList<ShoppingOrderExtendQueryDTO>();
		for (ShoppingOrderExtendQueryBO shoppingOrderExtendQueryBO : shoppingOrderExtendQueryBOList) {
			shoppingOrderExtendQueryDTOList.add(convert(shoppingOrderExtendQueryBO));
		}
		
		return shoppingOrderExtendQueryDTOList;
	}
	
}