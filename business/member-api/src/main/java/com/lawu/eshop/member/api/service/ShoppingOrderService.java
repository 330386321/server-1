package com.lawu.eshop.member.api.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.CommentOrderDTO;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderExpressDTO;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderExtendDetailDTO;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderExtendQueryDTO;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderItemRefundDTO;
import com.lawu.eshop.mall.param.ShoppingOrderSettlementParam;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderQueryForeignToMemberParam;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderRequestRefundForeignParam;
import com.lawu.eshop.mall.param.foreign.ShoppingRefundQueryForeignParam;

/**
 * @author Sunny
 * @date 2017/04/06
 */
@FeignClient(value= "order-srv")
public interface ShoppingOrderService {
	
	/**
	 * 批量保存订单
	 * 
	 * @param params
	 */
	@RequestMapping(value = "shoppingOrder/save", method = RequestMethod.POST)
	Result<List<Long>> save(@RequestBody List<ShoppingOrderSettlementParam> params);

	/**
	 * 查询订单评价状态
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "shoppingOrder/getOrderCommentStatus/{orderId}", method = RequestMethod.GET)
    Result<CommentOrderDTO> getOrderCommentStatus(@PathVariable("orderId") Long orderId);
	
	/**
	 * 根据查询参数分页查询
	 * 
	 * @param memberId 会员id
	 * @param params 查询参数
	 * @return
	 */
	@RequestMapping(value = "shoppingOrder/selectPageByMemberId/{memberId}", method = RequestMethod.POST)
	public Result<Page<ShoppingOrderExtendQueryDTO>> selectPageByMemberId(@PathVariable("memberId") Long memberId, @RequestBody ShoppingOrderQueryForeignToMemberParam param);
	
	/**
	 * 根据id查询订单详情
	 * 
	 * @param id 购物订单id
	 * @return
	 */
	@RequestMapping(value = "shoppingOrder/get/{id}", method = RequestMethod.GET)
	Result<ShoppingOrderExtendDetailDTO> get(@PathVariable("id") Long id);
	
	/**
	 * 根据id查询订单物流信息
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@RequestMapping(value = "shoppingOrder/getExpressInfo/{id}", method = RequestMethod.GET)
	Result<ShoppingOrderExpressDTO> getExpressInfo(@PathVariable("id") Long id);
	
	/**
	 * 取消购物订单
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@RequestMapping(value = "shoppingOrder/cancelOrder/{id}", method = RequestMethod.PUT)
	public Result cancelOrder(@PathVariable("id") Long id);
	
	/**
	 * 删除购物订单
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "shoppingOrder/deleteOrder/{id}", method = RequestMethod.PUT)
	Result deleteOrder(@PathVariable("id") Long id);
	
	/**
	 * 确认收货之后
	 * 修改购物订单以及订单项状态为交易成功
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "shoppingOrder/tradingSuccess/{id}", method = RequestMethod.PUT)
	Result tradingSuccess(@PathVariable("id") Long id);
	
	/**
	 * 买家申请退款
	 * 修改订单状态为待商家确认
	 * 
	 * @param shoppingOrderitemId 购物订单项id
	 * @param param 退款参数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "shoppingOrder/requestRefund/{shoppingOrderitemId}", method = RequestMethod.PUT)
	Result requestRefund(@PathVariable("shoppingOrderitemId") Long shoppingOrderitemId, @RequestBody ShoppingOrderRequestRefundForeignParam param);
	
	/**
	 * 根据查询参数分页查询退款记录
	 * 购物订单 购物订单项 退款详情关联查询
	 * 
	 * @param memberId
	 *            会员id
	 * @param param
	 *            查询参数
	 * @return
	 */
	@RequestMapping(value = "shoppingOrder/selectRefundPageByMemberId/{memberId}", method = RequestMethod.POST)
	Result<Page<ShoppingOrderItemRefundDTO>> selectRefundPageByMemberId(@PathVariable("memberId") Long memberId, @RequestBody ShoppingRefundQueryForeignParam param);
	
	/**
	 * 支付成功之后
	 * 回调修改购物订单以及订单项状态为待发货
	 * 提供给api接口调用，也可以在api内部调用
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "shoppingOrder/paymentSuccessful/{id}", method = RequestMethod.PUT)
	Result paymentSuccessful(@PathVariable("id") Long id);
}
