package com.lawu.eshop.order.srv.converter;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.mall.constants.ShoppingRefundTypeEnum;
import com.lawu.eshop.order.srv.bo.ShoppingRefundDetailBO;
import com.lawu.eshop.order.srv.domain.ShoppingRefundDetailDO;

/**
 * 购物退货详情转换器
 *
 * @author Sunny
 * @date 2017/04/11
 */
public class ShoppingRefundDetailConverter {

	/**
	 * ShoppingRefundDetailBO转换
	 * 
	 * @param shoppingRefundDetailDO
	 * @return
	 */
	public static ShoppingRefundDetailBO convert(ShoppingRefundDetailDO shoppingRefundDetailDO) {
		if (shoppingRefundDetailDO == null) {
			return null;
		}

		ShoppingRefundDetailBO shoppingRefundDetailBO = new ShoppingRefundDetailBO();
		BeanUtils.copyProperties(shoppingRefundDetailDO, shoppingRefundDetailBO, "type");
		
		shoppingRefundDetailBO.setType(ShoppingRefundTypeEnum.getEnum(shoppingRefundDetailDO.getType()));
		
		return shoppingRefundDetailBO;
	}

}