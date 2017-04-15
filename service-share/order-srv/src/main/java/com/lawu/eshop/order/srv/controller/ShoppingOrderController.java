package com.lawu.eshop.order.srv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.mall.dto.CommentOrderDTO;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderExpressDTO;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderExtendDetailDTO;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderExtendQueryDTO;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderItemRefundDTO;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderQueryToMerchantDTO;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderQueryToOperatorDTO;
import com.lawu.eshop.mall.param.ShoppingOrderLogisticsInformationParam;
import com.lawu.eshop.mall.param.ShoppingOrderSettlementParam;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderQueryForeignToMemberParam;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderQueryForeignToMerchantParam;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderQueryForeignToOperatorParam;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderRequestRefundForeignParam;
import com.lawu.eshop.mall.param.foreign.ShoppingRefundQueryForeignParam;
import com.lawu.eshop.order.srv.bo.ExpressInquiriesDetailBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderExtendBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemRefundBO;
import com.lawu.eshop.order.srv.converter.ShoppingOrderConverter;
import com.lawu.eshop.order.srv.converter.ShoppingOrderExtendConverter;
import com.lawu.eshop.order.srv.converter.ShoppingOrderItemRefundConverter;
import com.lawu.eshop.order.srv.service.ShoppingOrderItemService;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;
import com.lawu.eshop.order.srv.strategy.ExpressStrategy;

/**
 * 购物订单
 * 
 * @author Sunny
 * @date 2017/04/06
 */
@RestController
@RequestMapping(value = "shoppingOrder/")
public class ShoppingOrderController extends BaseController {

	@Autowired
	private ShoppingOrderService shoppingOrderService;
	
	@Autowired
	private ShoppingOrderItemService shoppingOrderItemService;

	@Autowired
	@Qualifier("kDNiaoExpressStrategy")
	private ExpressStrategy expressStrategy;

	/*********************************************************
	 * 					Member Api
	 * ******************************************************/
	
	/**
	 * 保存订单
	 * 
	 * @param param
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Result<List<Long>> save(@RequestBody List<ShoppingOrderSettlementParam> params) {

		// 参数验证
		if (params == null || params.isEmpty()) {
			successCreated(ResultCode.REQUIRED_PARM_EMPTY);
		}

		List<Long> ids = shoppingOrderService.save(params);

		if (ids == null || params.isEmpty()) {
			return successCreated(ResultCode.SAVE_FAIL);
		}

		return successCreated(ids);
	}
	
	/**
	 * To 用户
	 * 根据查询参数分页查询
	 * 
	 * @param memberId
	 *            会员id
	 * @param params
	 *            查询参数
	 * @return
	 */
	@RequestMapping(value = "selectPageByMemberId/{memberId}", method = RequestMethod.POST)
	public Result<Page<ShoppingOrderExtendQueryDTO>> selectPageByMemberId(@PathVariable("memberId") Long memberId, @RequestBody ShoppingOrderQueryForeignToMemberParam param) {
		Page<ShoppingOrderExtendBO> shoppingOrderExtendQueryBOPage = shoppingOrderService.selectPageByMemberId(memberId, param);

		Page<ShoppingOrderExtendQueryDTO> shoppingOrderExtendQueryDTOPage = new Page<ShoppingOrderExtendQueryDTO>();
		shoppingOrderExtendQueryDTOPage.setCurrentPage(shoppingOrderExtendQueryBOPage.getCurrentPage());
		shoppingOrderExtendQueryDTOPage.setTotalCount(shoppingOrderExtendQueryBOPage.getTotalCount());
		shoppingOrderExtendQueryDTOPage.setRecords(ShoppingOrderExtendConverter.convertShoppingOrderExtendQueryDTOList(shoppingOrderExtendQueryBOPage.getRecords()));

		return successCreated(shoppingOrderExtendQueryDTOPage);
	}
	
	/**
	 * 取消购物订单
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "cancelOrder/{id}", method = RequestMethod.PUT)
	public Result cancelOrder(@PathVariable("id") Long id) {

		if (id == null || id <= 0) {
			return successCreated(ResultCode.ID_EMPTY);
		}
		
		ShoppingOrderBO shoppingOrderBO = shoppingOrderService.getShoppingOrder(id);
		
		if (shoppingOrderBO == null || shoppingOrderBO.getId() == null || shoppingOrderBO.getId() <= 0) {
			return successCreated(ResultCode.RESOURCE_NOT_FOUND);
		}
		
		// 被取消的订单必须要是待支付的状态
		if (!shoppingOrderBO.getOrderStatus().equals(ShoppingOrderStatusEnum.PENDING_PAYMENT.getValue())) {
			return successCreated(ResultCode.ORDER_NOT_CANCELED);
		}
		
		shoppingOrderService.cancelOrder(id);

		return successCreated();
	}
	
	/**
	 * 删除购物订单
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "deleteOrder/{id}", method = RequestMethod.PUT)
	public Result deleteOrder(@PathVariable("id") Long id) {

		if (id == null || id <= 0) {
			return successCreated(ResultCode.ID_EMPTY);
		}
		
		ShoppingOrderBO shoppingOrderBO = shoppingOrderService.getShoppingOrder(id);
		
		if (shoppingOrderBO == null || shoppingOrderBO.getId() == null || shoppingOrderBO.getId() <= 0) {
			return successCreated(ResultCode.RESOURCE_NOT_FOUND);
		}
		
		// 被删除的订单必须要是完成的状态(交易完成|交易关闭)
		if (!shoppingOrderBO.getOrderStatus().equals(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue())
				&& !shoppingOrderBO.getOrderStatus().equals(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue())) {
			return successCreated(ResultCode.ORDER_NOT_DELETE);
		}
		
		shoppingOrderService.deleteOrder(id);

		return successCreated();
	}
	
	/**
	 * 根据id查询订单物流信息
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@RequestMapping(value = "getExpressInfo/{id}", method = RequestMethod.GET)
	public Result<ShoppingOrderExpressDTO> getExpressInfo(@PathVariable("id") Long id) {

		if (id == null || id <= 0) {
			return successGet(ResultCode.ID_EMPTY);
		}

		ShoppingOrderBO shoppingOrderBO = shoppingOrderService.getShoppingOrder(id);

		if (shoppingOrderBO == null) {
			return successGet(ResultCode.RESOURCE_NOT_FOUND);
		}

		ExpressInquiriesDetailBO expressInquiriesDetailBO = expressStrategy.inquiries(shoppingOrderBO.getExpressCompanyCode(), shoppingOrderBO.getWaybillNum());

		return successGet(ShoppingOrderConverter.covert(shoppingOrderBO, expressInquiriesDetailBO));
	}
	
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
	@RequestMapping(value = "selectRefundPageByMemberId/{memberId}", method = RequestMethod.POST)
	public Result<Page<ShoppingOrderItemRefundDTO>> selectRefundPageByMemberId(@PathVariable("memberId") Long memberId, @RequestBody ShoppingRefundQueryForeignParam param) {
		
		Page<ShoppingOrderItemRefundBO> shoppingOrderItemRefundBOPage = shoppingOrderItemService.selectRefundPageByMemberId(memberId, param);
		
		Page<ShoppingOrderItemRefundDTO> shoppingOrderItemRefundDTOPage = new Page<ShoppingOrderItemRefundDTO>();
		shoppingOrderItemRefundDTOPage.setCurrentPage(shoppingOrderItemRefundBOPage.getCurrentPage());
		shoppingOrderItemRefundDTOPage.setTotalCount(shoppingOrderItemRefundBOPage.getTotalCount());
		shoppingOrderItemRefundDTOPage.setRecords(ShoppingOrderItemRefundConverter.convertShoppingOrderItemRefundDTOList(shoppingOrderItemRefundBOPage.getRecords()));
		
		return successCreated(shoppingOrderItemRefundDTOPage);
	}
	
	/**
	 * 买家申请退款
	 * 修改订单状态为待商家确认
	 * 
	 * @param shoppingOrderitemId 购物订单项id
	 * @param param 退款参数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "requestRefund/{shoppingOrderitemId}", method = RequestMethod.PUT)
	public Result requestRefund(@PathVariable("shoppingOrderitemId") Long shoppingOrderitemId, @RequestBody ShoppingOrderRequestRefundForeignParam param) {

		// 修改购物订单以及订单项状态，保存退款详情记录
		int result = shoppingOrderService.requestRefund(shoppingOrderitemId, param);
		
		if (result != ResultCode.SUCCESS) {
			return successCreated(result);
		}
		
		return successCreated();
	}
	
	/**
	 * 确认收货之后
	 * 修改购物订单以及订单项状态为交易成功
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "tradingSuccess/{id}", method = RequestMethod.PUT)
	public Result tradingSuccess(@PathVariable("id") Long id) {
		
		int resultCode = shoppingOrderService.tradingSuccess(id);
		
		if (resultCode != ResultCode.SUCCESS) {
			return successCreated(resultCode);
		}

		return successCreated();
	}
	
	/*********************************************************
	 * 					Merchant Api
	 * ******************************************************/
	
	/**
	 * To商家
	 * 根据查询参数分页查询
	 * 
	 * @param merchantId
	 *            商家id
	 * @param params
	 *            查询参数
	 * @return
	 */
	@RequestMapping(value = "selectPageByMerchantId/{merchantId}", method = RequestMethod.POST)
	public Result<Page<ShoppingOrderQueryToMerchantDTO>> selectPageByMerchantId(@PathVariable("merchantId") Long merchantId, @RequestBody ShoppingOrderQueryForeignToMerchantParam param) {
		Page<ShoppingOrderExtendBO> shoppingOrderExtendQueryBOPage = shoppingOrderService.selectPageByMerchantId(merchantId, param);

		Page<ShoppingOrderQueryToMerchantDTO> shoppingOrderExtendQueryDTOPage = new Page<ShoppingOrderQueryToMerchantDTO>();
		shoppingOrderExtendQueryDTOPage.setCurrentPage(shoppingOrderExtendQueryBOPage.getCurrentPage());
		shoppingOrderExtendQueryDTOPage.setTotalCount(shoppingOrderExtendQueryBOPage.getTotalCount());
		shoppingOrderExtendQueryDTOPage.setRecords(ShoppingOrderExtendConverter.convertShoppingOrderQueryToMerchantDTOList(shoppingOrderExtendQueryBOPage.getRecords()));

		return successCreated(shoppingOrderExtendQueryDTOPage);
	}
	
	
	/**
	 * 商家发货填写物流信息
	 * 并修改购物订单以及购物订单项的状态为待收货
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "fillLogisticsInformation/{id}", method = RequestMethod.PUT)
	public Result fillLogisticsInformation(@PathVariable("id") Long id, ShoppingOrderLogisticsInformationParam param) {

		if (id == null || id <= 0) {
			return successCreated(ResultCode.ID_EMPTY);
		}
		
		ShoppingOrderBO shoppingOrderBO = shoppingOrderService.getShoppingOrder(id);
		
		if (shoppingOrderBO == null || shoppingOrderBO.getId() == null || shoppingOrderBO.getId() <= 0) {
			return successCreated(ResultCode.RESOURCE_NOT_FOUND);
		}
		
		// 只有订单状态为待发货才能填写物流信息
		if (!shoppingOrderBO.getOrderStatus().equals(ShoppingOrderStatusEnum.BE_SHIPPED.getValue())){
			return successCreated(ResultCode.NOT_SHIPPING_STATUS);
		}
		
		shoppingOrderService.fillLogisticsInformation(id, param);

		return successCreated();
	}
	
	/*********************************************************
	 * 					Operator Api
	 * ******************************************************/
	/**
	 * 根据查询参数分页查询
	 * 
	 * @param param
	 *            查询参数
	 * @return
	 */
	@RequestMapping(value = "selectPage", method = RequestMethod.POST)
	public Result<Page<ShoppingOrderQueryToOperatorDTO>> selectPage(@RequestBody ShoppingOrderQueryForeignToOperatorParam param) {
		Page<ShoppingOrderExtendBO> shoppingOrderExtendQueryBOPage = shoppingOrderService.selectPage(param);

		Page<ShoppingOrderQueryToOperatorDTO> shoppingOrderQueryToOperatorDTOPage = new Page<ShoppingOrderQueryToOperatorDTO>();
		shoppingOrderQueryToOperatorDTOPage.setCurrentPage(shoppingOrderExtendQueryBOPage.getCurrentPage());
		shoppingOrderQueryToOperatorDTOPage.setTotalCount(shoppingOrderExtendQueryBOPage.getTotalCount());
		shoppingOrderQueryToOperatorDTOPage.setRecords(ShoppingOrderExtendConverter.convertShoppingOrderQueryToOperatorDTOList(shoppingOrderExtendQueryBOPage.getRecords()));

		return successCreated(shoppingOrderQueryToOperatorDTOPage);
	}
	
	/*********************************************************
	 * 					Common
	 * ******************************************************/
	
	/**
	 * 根据id查询订单详情
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@RequestMapping(value = "get/{id}", method = RequestMethod.GET)
	public Result<ShoppingOrderExtendDetailDTO> get(@PathVariable("id") Long id) {

		if (id == null || id <= 0) {
			return successGet(ResultCode.ID_EMPTY);
		}

		ShoppingOrderExtendBO shoppingOrderExtendDetailBO = shoppingOrderService.get(id);

		if (shoppingOrderExtendDetailBO == null || shoppingOrderExtendDetailBO.getId() == null || shoppingOrderExtendDetailBO.getId() <= 0 || shoppingOrderExtendDetailBO.getItems() == null || shoppingOrderExtendDetailBO.getItems().isEmpty()) {
			return successGet(ResultCode.RESOURCE_NOT_FOUND);
		}

		return successGet(ShoppingOrderExtendConverter.convert(shoppingOrderExtendDetailBO));
	}
	
	/** 第三方支付时获取订单原始总金额，用于调用第三方支付平台
	 * @param orderIds
	 * @return
	 * @author Yangqh
	 */
	@RequestMapping(value = "selectOrderMoney", method = RequestMethod.GET)
	public double selectOrderMoney(@RequestParam String orderIds) {
		double totalMoney = shoppingOrderService.selectOrderMoney(orderIds);
		return totalMoney;
	}
	
	/**
	 * 获取商品评价状态
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "getOrderCommentStatus/{shoppingOrderItemId}", method = RequestMethod.GET)
	public Result<CommentOrderDTO> getOrderCommentStatus(@PathVariable("shoppingOrderItemId") Long shoppingOrderItemId) {
		// 查询订单商品评价状态
		ShoppingOrderItemBO shoppingOrderItemBO = shoppingOrderItemService.get(shoppingOrderItemId);
		CommentOrderDTO commentOrderDTO = ShoppingOrderConverter.coverCommentStatusDTO(shoppingOrderItemBO);
		return successGet(commentOrderDTO);
	}
}
