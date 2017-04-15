package com.lawu.eshop.order.srv.converter;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.order.srv.bo.ShoppingOrderItemEvaluationBO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderItemEvaluationDO;

/**
 * 订单评论转换器
 *
 * @author Sunny
 * @date 2017/04/15
 */
public class ShoppingOrderItemEvaluationConverter {

	/**
	 * ShoppingOrderItemEvaluationBO转换
	 *
	 * @param shoppingOrderItemEvaluationDO
	 * @return
	 */
	public static ShoppingOrderItemEvaluationBO convert(ShoppingOrderItemEvaluationDO shoppingOrderItemEvaluationDO) {
		ShoppingOrderItemEvaluationBO rtn = null;
		
		if (shoppingOrderItemEvaluationDO == null) {
			return rtn;
		}
		
		BeanUtils.copyProperties(shoppingOrderItemEvaluationDO, rtn);
		
		return rtn;
	}
	
}
