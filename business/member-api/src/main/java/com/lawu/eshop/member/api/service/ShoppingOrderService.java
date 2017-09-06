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
import com.lawu.eshop.order.dto.CommentOrderDTO;
import com.lawu.eshop.order.dto.ShoppingOrderMoneyDTO;
import com.lawu.eshop.order.dto.ShoppingOrderPaymentDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderDetailDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExpressDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExpressInfoDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExtendDetailDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExtendQueryDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderItemRefundDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderNumberOfOrderStatusDTO;
import com.lawu.eshop.order.param.ShoppingOrderRequestRefundParam;
import com.lawu.eshop.order.param.ShoppingOrderSettlementParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderQueryForeignToMemberParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundQueryForeignParam;

/**
 * @author Sunny
 * @date 2017/04/06
 */
@FeignClient(value = "order-srv", path = "shoppingOrder/")
public interface ShoppingOrderService {

	/**
	 * 批量保存订单
	 * 
	 * @param params
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	Result<List<Long>> save(@RequestBody List<ShoppingOrderSettlementParam> params);

	/**
	 * 查询订单评价状态
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "getOrderCommentStatus/{shoppingOrderItemId}", method = RequestMethod.GET)
	Result<CommentOrderDTO> getOrderCommentStatus(@PathVariable("shoppingOrderItemId") Long shoppingOrderItemId);

	/**
	 * 根据查询参数分页查询
	 * 
	 * @param memberId
	 *            会员id
	 * @param params
	 *            查询参数
	 * @return
	 */
	@RequestMapping(value = "selectPageByMemberId/{memberId}", method = RequestMethod.POST)
	Result<Page<ShoppingOrderExtendQueryDTO>> selectPageByMemberId(@PathVariable("memberId") Long memberId, @RequestBody ShoppingOrderQueryForeignToMemberParam param);

	/**
	 * 根据id查询订单详情
	 * 
	 * @param id
	 *            购物订单id
	 * @param memberId
	 *            会员id
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "get/{id}", method = RequestMethod.GET)
	Result<ShoppingOrderExtendDetailDTO> get(@PathVariable("id") Long id, @RequestParam("memberId") Long memberId);
	
	/**
	 * 根据id查询订单详情
	 * 
	 * @param id
	 *            购物订单id
	 * @param memberId
	 *            会员id
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	Result<ShoppingOrderDetailDTO> detail(@PathVariable("id") Long id, @RequestParam("memberId") Long memberId);

	/**
	 * 根据id查询订单物流信息
	 * 
	 * @param id
	 *            购物订单id
	 * @param memberId
	 *            会员idid
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "getExpressInfo/{id}", method = RequestMethod.GET)
	Result<ShoppingOrderExpressDTO> getExpressInfo(@PathVariable("id") Long id, @RequestParam("memberId") Long memberId);
	
	/**
	 * 根据id查询订单物流信息
	 * 
	 * @param id
	 *            购物订单id
	 * @param memberId
	 *            会员idid
	 * @return
	 */
	@RequestMapping(value = "expressInfo/{id}", method = RequestMethod.GET)
	Result<ShoppingOrderExpressInfoDTO> expressInfo(@PathVariable("id") Long id, @RequestParam("memberId") Long memberId);

	/**
	 * 取消购物订单
	 * 
	 * @param memberId
	 *            会员id
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "cancelOrder/{memberId}/{id}", method = RequestMethod.PUT)
	public Result cancelOrder(@PathVariable("memberId") Long memberId, @PathVariable("id") Long id);

	/**
	 * 
	 * @param memberId
	 *            会员id
	 * @param id
	 *            购物订单id
	 * @author jiangxinjun
	 * @date 2017年7月10日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "deleteOrder/{memberId}/{id}", method = RequestMethod.PUT)
	Result deleteOrder(@PathVariable("memberId") Long memberId, @PathVariable("id") Long id);

	/**
	 * 确认收货之后 修改购物订单以及订单项状态为交易成功
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "tradingSuccess/{id}", method = RequestMethod.PUT)
	Result tradingSuccess(@PathVariable("id") Long id, @RequestParam("memberId") Long memberId);

	/**
	 * 买家申请退款 修改订单状态为待商家确认
	 * 
	 * @param shoppingOrderitemId
	 *            购物订单项id
	 * @param memberId
	 *            会员id(用于鉴权)
	 * @param param
	 *            退款参数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "requestRefund/{shoppingOrderitemId}", method = RequestMethod.PUT)
	Result requestRefund(@PathVariable("shoppingOrderitemId") Long shoppingOrderitemId, @RequestParam("memberId") Long memberId, @RequestBody ShoppingOrderRequestRefundParam param);

	/**
	 * 根据查询参数分页查询退款记录 购物订单 购物订单项 退款详情关联查询
	 * 
	 * @param memberId
	 *            会员id
	 * @param param
	 *            查询参数
	 * @return
	 */
	@RequestMapping(value = "selectRefundPageByMemberId/{memberId}", method = RequestMethod.POST)
	Result<Page<ShoppingOrderItemRefundDTO>> selectRefundPageByMemberId(@PathVariable("memberId") Long memberId, @RequestBody ShoppingRefundQueryForeignParam param);

	/**
	 * 获取商品订单的总金额
	 * 
	 * @param orderIds
	 * @return
	 * @author Sunny
	 */
	@RequestMapping(method = RequestMethod.GET, value = "selectOrderMoney")
	Result<ShoppingOrderMoneyDTO> selectOrderMoney(@RequestParam("orderIds") String orderIds);

	/**
	 * 查询各种订单状态的数量
	 * 
	 * @param memberId
	 *            会员id
	 * @return
	 * @author Sunny
	 */
	@RequestMapping(value = "numberOfOrderStartus/{memberId}", method = RequestMethod.GET)
	Result<ShoppingOrderNumberOfOrderStatusDTO> numberOfOrderStartus(@PathVariable("memberId") Long memberId);

	/**
	 * 订单支付
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@RequestMapping(value = "orderPayment/{id}", method = RequestMethod.GET)
	Result<ShoppingOrderPaymentDTO> orderPayment(@PathVariable("id") Long id, @RequestParam("memberId") Long memberId);
}
