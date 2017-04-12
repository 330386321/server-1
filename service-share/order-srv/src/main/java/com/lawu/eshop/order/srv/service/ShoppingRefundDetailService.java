package com.lawu.eshop.order.srv.service;

import com.lawu.eshop.mall.param.ShoppingRefundDetailLogisticsInformationParam;
import com.lawu.eshop.mall.param.foreign.ShoppingRefundDetailAgreeToApplyForeignParam;
import com.lawu.eshop.mall.param.foreign.ShoppingRefundDetailAgreeToRefundForeignParam;
import com.lawu.eshop.mall.param.foreign.ShoppingRefundDetailRerurnAddressForeignParam;
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
	 * 根据购物订单项id 获取购物退货详情
	 * 
	 * @param shoppingOrderitemId
	 *            购物订单项id
	 * @return
	 */
	ShoppingRefundDetailBO getByShoppingOrderitemId(Long shoppingOrderItemId);

	/**
	 * 商家是否同意买家的退货申请
	 * 
	 * @param shoppingRefundDetailBO
	 *            退款详情
	 * @param param
	 *            参数 是否同意申请
	 * @return
	 */
	Integer agreeToApply(ShoppingRefundDetailBO shoppingRefundDetailBO, ShoppingRefundDetailAgreeToApplyForeignParam param);

	/**
	 * 商家填写退货地址信息
	 * 
	 * @param shoppingRefundDetailBO
	 *            退款详情
	 * @param param
	 *            退款详情物流信息
	 * @return
	 */
	Integer fillReturnAddress(ShoppingRefundDetailBO shoppingRefundDetailBO, ShoppingRefundDetailRerurnAddressForeignParam param);

	/**
	 * 买家填写退货的物流信息
	 * 
	 * @param shoppingRefundDetailBO
	 *            退款详情
	 * @param param
	 *            退款详情物流信息
	 * @return
	 */
	Integer fillLogisticsInformation(ShoppingRefundDetailBO shoppingRefundDetailBO, ShoppingRefundDetailLogisticsInformationParam param);

	/**
	 * 商家是否同意退款
	 * 
	 * @param shoppingRefundDetailBO
	 *            退款详情
	 * @param param
	 *            参数 是否同意退款
	 * @return
	 */
	Integer agreeToRefund(ShoppingRefundDetailBO shoppingRefundDetailBO, ShoppingRefundDetailAgreeToRefundForeignParam param);

	/**
	 * 如果商家拒绝买家的退款申请或者拒绝退款 买家可以申请平台介入
	 * 
	 * @param id
	 *            退款详情id
	 * @return
	 */
	Integer platformIntervention(ShoppingRefundDetailBO shoppingRefundDetailBO);
}
