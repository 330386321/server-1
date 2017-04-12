package com.lawu.eshop.order.srv.service;

import com.lawu.eshop.mall.param.ShoppingRefundDetailLogisticsInformationParam;
import com.lawu.eshop.order.srv.bo.ShoppingRefundDetailBO;

/**
 * 购物退货详情接口
 *
 * @author Sunny
 * @date 2017/4/11
 */
public interface ShoppingRefundDetailService {

	/**
	 * 根据购物退货详情id 获取购物退货详情
	 * 
	 * @param id
	 *            购物订单项id
	 * @return
	 */
	ShoppingRefundDetailBO get(Long id);

	/**
	 * 买家填写退货的物流信息
	 * 
	 * @param param
	 *            退款详情
	 * @param param
	 *            退款详情物流信息
	 * @return
	 */
	Integer fillLogisticsInformation(ShoppingRefundDetailBO shoppingRefundDetailBO, ShoppingRefundDetailLogisticsInformationParam param);

}
