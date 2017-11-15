package com.lawu.eshop.order.srv.service;

import java.util.List;

import com.lawu.eshop.order.param.ShoppingRefundDetailLogisticsInformationParam;
import com.lawu.eshop.order.param.ShoppingRefundDetailRerurnAddressParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundDetailAgreeToApplyForeignParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundDetailAgreeToRefundForeignParam;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemExtendBO;
import com.lawu.eshop.order.srv.bo.ShoppingRefundDetailBO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderItemExtendDO;

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
	ShoppingOrderItemExtendBO getByShoppingOrderItemId(Long shoppingOrderItemId);

	/**
	 * 商家是否同意买家的退货申请
	 * 
	 * @param id
	 *            退款详情id
	 * @param merchantId
	 *            商家id
	 * @param param
	 *            参数 是否同意申请
	 * @return
	 * @author jiangxinjun
	 * @date 2017年7月11日
	 */
	@Deprecated
	void agreeToApply(Long id, Long merchantId, ShoppingRefundDetailAgreeToApplyForeignParam param);

	/**
	 * 商家填写退货地址信息
	 * 
	 * @param id
	 *            退款详情id
	 * @param merchantId
	 *            商家id
	 * @param param
	 * 			     退货地址信息
	 * @author jiangxinjun
	 * @date 2017年7月11日
	 */
	void fillReturnAddress(Long id, Long merchantId, ShoppingRefundDetailRerurnAddressParam param);

	/**
	 * 买家填写退货的物流信息
	 * 
	 * @param id
	 *            退款详情id
	 * @param memberId
	 *            会员id
	 * @param param
	 *            退款详情物流信息
	 * @return
	 * @author jiangxinjun
	 * @date 2017年7月10日
	 */
	void fillLogisticsInformation(Long id, Long memberId, ShoppingRefundDetailLogisticsInformationParam param);

	/**
	 * 商家是否同意退款
	 * 
	 * @param id
	 *            退款详情Id
	 * @param merchantId
	 *            商家Id
	 * @param param
	 * 			      参数
	 * @param isAutoRefund
	 *            是否是定时任务自动退款
	 * @author jiangxinjun
	 * @date 2017年7月11日
	 */
	void agreeToRefund(Long id, Long merchantId, ShoppingRefundDetailAgreeToRefundForeignParam param, boolean isAutoRefund);
	
	/**
	 * 商家是否同意退款
	 * 
	 * @param id
	 *            退款详情Id
	 * @param isAutoRefund
	 *            是否是定时任务自动退款
	 * @author jiangxinjun
	 * @date 2017年7月11日
	 */
	void agreeToRefund(Long id);

	/**
	 * 如果商家拒绝买家的退款申请或者拒绝退款 买家可以申请平台介入
	 * 
	 * @param id
	 *            退款详情id
	 * @param memberId
	 *            会员id
	 */
	void platformIntervention(Long id, Long memberId);
	
	/**
	 * 买家撤销退货申请
	 * 
	 * @param id
	 *            退款详情id
	 * @return
	 */
	void revokeRefundRequest(Long id);
	
	/**
	 * 买家撤销退货申请
	 * 
	 * @param id
	 *            退款详情id
	 * @param memberId
	 *            会员id
	 * @return
	 */
	void revokeRefundRequest(Long id, Long memberId);
	
	/**
	 * 退款中-退款失败
	 * 商家拒绝退款
	 * 平台提示买家操作，是否申请平台介入
	 * 否则自动撤销退款申请
	 * 
	 * @author Sunny
	 */
	void executeAutoRefundFailed();
	
	/**
	 * 退款中 - 等待买家退货
	 * 平台提醒买家操作，否则自动撤销退款申请
	 * 
	 * @author Sunny
	 */
	void executeAutoForToBeReturn();
	
	/**
	 * 退款中 - 等待商家退款
	 * 平台提醒商家操作，否则自动退款
	 * 
	 * @author Sunny
	 */
	void executeAutoForToBeRefund();
	
	/**
	 * 提醒商家处理退款相关操作
	 * 
	 * @param shoppingOrderItemDO
	 * @author jiangxinjun
	 * @createDate 2017年11月14日
	 * @updateDate 2017年11月14日
	 */
    void refundRemind(ShoppingOrderItemDO shoppingOrderItemDO);
    
    /**
     * 分页查询符合待商家确认，商家超时未处理、退款类型-退款，满足自动提醒的退款记录
     * 
     * @param offset
     * @param pageSize
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月14日
     * @updateDate 2017年11月14日
     */
    List<ShoppingOrderItemExtendDO> selectAutoRemindToBeConfirmedForRefund(int offset, int pageSize);
    
    /**
     * 分页查询符合待商家确认，商家超时未处理、退款类型-退款，满足自动退款的退款记录
     * 
     * @param offset
     * @param pageSize
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月15日
     * @updateDate 2017年11月15日
     */
    List<ShoppingOrderItemExtendDO> selectAutoRefundToBeConfirmedForRefund(int offset, int pageSize);
    
    /**
     * 分页查询符合待商家确认，商家超时未处理、退款类型-退货退款，满足自动提醒的退款记录
     * 
     * @author jiangxinjun
     * @createDate 2017年11月15日
     * @updateDate 2017年11月15日
     */
    List<ShoppingOrderItemExtendDO> selectAutoRemindToBeConfirmedForReturnRefund(int offset, int pageSize);
    
    /**
     * 分页查询符合待商家确认，商家超时未处理、退款类型-退货退款，满足自动退款的退款记录
     * 
     * @author jiangxinjun
     * @createDate 2017年11月15日
     * @updateDate 2017年11月15日
     */
    List<ShoppingOrderItemExtendDO> selectAutoRefundToBeConfirmedForReturnRefund(int offset, int pageSize);
    
    /**
     * 分页查询符合待填写退货地址，商家超时未处理，满足自动提醒的退款记录
     * 
     * @param offset
     * @param pageSize
     * @author jiangxinjun
     * @return 
     * @createDate 2017年11月15日
     * @updateDate 2017年11月15日
     */
    List<ShoppingOrderItemDO> selectAutoRemindFillReturnAddress(int offset, int pageSize);
    
    /**
     * 分页查询符合待填写退货地址，商家超时未处理，满足自动退款的退款记录
     * 
     * @param offset
     * @param pageSize
     * @author jiangxinjun
     * @return 
     * @createDate 2017年11月15日
     * @updateDate 2017年11月15日
     */
    List<ShoppingOrderItemDO> selectAutoRefundFillReturnAddress(int offset, int pageSize);
    
    /**
     * 待填写退货地址，商家超时未处理，自动退款给买家
     * 
     * @param shoppingOrderItemDO
     * @author jiangxinjun
     * @createDate 2017年11月15日
     * @updateDate 2017年11月15日
     */
    void executeAutoRefundFillReturnAddress(ShoppingOrderItemDO shoppingOrderItemDO);
}
