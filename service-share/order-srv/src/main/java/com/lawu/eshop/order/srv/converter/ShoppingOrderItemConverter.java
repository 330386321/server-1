package com.lawu.eshop.order.srv.converter;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.mall.param.ShoppingOrderSettlementItemParam;
import com.lawu.eshop.order.srv.domain.ShoppingOrderDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDO;
import com.lawu.eshop.utils.RandomUtil;

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
	 * @param param
	 * @return
	 */
	public static ShoppingOrderItemDO convert(Long shoppingOrderId, ShoppingOrderSettlementItemParam param) {
		if (param == null) {
			return null;
		}

		ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
		BeanUtils.copyProperties(param, shoppingOrderItemDO,
				new String[] { "shoppingOrderId", "orderStatus", "gmtCreate", "gmtModified" });
		// 设置订单id
		shoppingOrderItemDO.setShoppingOrderId(shoppingOrderId);
		// 设置为未付款状态
		shoppingOrderItemDO.setOrderStatus((byte) 0x00);
		// 设置为待评价
		shoppingOrderItemDO.setGmtCreate(new Date());
		shoppingOrderItemDO.setGmtModified(new Date());

		return shoppingOrderItemDO;
	}

}
