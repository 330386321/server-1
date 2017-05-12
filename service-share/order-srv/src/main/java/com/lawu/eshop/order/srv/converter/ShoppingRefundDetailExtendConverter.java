package com.lawu.eshop.order.srv.converter;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.order.constants.ShoppingRefundTypeEnum;
import com.lawu.eshop.order.srv.bo.ShoppingRefundDetailExtendBO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingRefundDetailExtendDO;

/**
 * 购物退货详情转换器
 *
 * @author Sunny
 * @date 2017/04/11
 */
public class ShoppingRefundDetailExtendConverter {

	/**
	 * ShoppingRefundDetailExtendBO转换
	 * 
	 * @param shoppingRefundDetailExtendDO
	 * @return
	 */
	public static ShoppingRefundDetailExtendBO convert(ShoppingRefundDetailExtendDO shoppingRefundDetailExtendDO) {
		if (shoppingRefundDetailExtendDO == null) {
			return null;
		}

		ShoppingRefundDetailExtendBO shoppingRefundDetailBO = new ShoppingRefundDetailExtendBO();
		BeanUtils.copyProperties(shoppingRefundDetailExtendDO, shoppingRefundDetailBO, "type", "shoppingRefundProcessList");
		
		shoppingRefundDetailBO.setType(ShoppingRefundTypeEnum.getEnum(shoppingRefundDetailExtendDO.getType()));
		shoppingRefundDetailBO.setShoppingRefundProcessList(ShoppingRefundProcessConverter.convert(shoppingRefundDetailExtendDO.getShoppingRefundProcessList()));
		
		return shoppingRefundDetailBO;
	}

}
