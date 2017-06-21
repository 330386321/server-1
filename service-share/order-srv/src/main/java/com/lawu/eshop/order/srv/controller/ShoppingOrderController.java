package com.lawu.eshop.order.srv.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.lawu.eshop.order.dto.CommentOrderDTO;
import com.lawu.eshop.order.dto.ReportRiseRateDTO;
import com.lawu.eshop.order.dto.ReportRiseRerouceDTO;
import com.lawu.eshop.order.dto.ShoppingOrderCommissionDTO;
import com.lawu.eshop.order.dto.ShoppingOrderIsNoOnGoingOrderDTO;
import com.lawu.eshop.order.dto.ShoppingOrderMoneyDTO;
import com.lawu.eshop.order.dto.ShoppingOrderPaymentDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExpressDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExtendDetailDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExtendQueryDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderItemRefundDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderItemRefundForMerchantDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderItemRefundForOperatorDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderNumberOfOrderStatusDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderNumberOfOrderStatusForMerchantForeignDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderQueryToMerchantDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderQueryToOperatorDTO;
import com.lawu.eshop.order.param.ReportDataParam;
import com.lawu.eshop.order.param.ShoppingOrderLogisticsInformationParam;
import com.lawu.eshop.order.param.ShoppingOrderRequestRefundParam;
import com.lawu.eshop.order.param.ShoppingOrderSettlementParam;
import com.lawu.eshop.order.param.ShoppingOrderUpdateInfomationParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderQueryForeignToMemberParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderQueryForeignToMerchantParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderQueryForeignToOperatorParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundQueryForeignParam;
import com.lawu.eshop.order.srv.bo.ExpressInquiriesDetailBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderExtendBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderIsNoOnGoingOrderBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemExtendBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderMoneyBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderNumberOfOrderStatusBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderNumberOfOrderStatusForMerchantBO;
import com.lawu.eshop.order.srv.constants.PropertyNameConstant;
import com.lawu.eshop.order.srv.converter.ShoppingOrderConverter;
import com.lawu.eshop.order.srv.converter.ShoppingOrderExtendConverter;
import com.lawu.eshop.order.srv.converter.ShoppingOrderItemExtendConverter;
import com.lawu.eshop.order.srv.service.PropertyService;
import com.lawu.eshop.order.srv.service.ShoppingOrderItemService;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;
import com.lawu.eshop.order.srv.strategy.ExpressStrategy;
import com.lawu.eshop.utils.DateUtil;

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
	
	@Autowired
	private PropertyService propertyService;

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
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
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

		Page<ShoppingOrderExtendQueryDTO> shoppingOrderExtendQueryDTOPage = ShoppingOrderExtendConverter.convertShoppingOrderExtendQueryDTOPage(shoppingOrderExtendQueryBOPage);
		// 把无用对象置空
		shoppingOrderExtendQueryBOPage = null;
		
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
		// 参数验证
		if (id == null || id <= 0) {
			return successCreated(ResultCode.ID_EMPTY);
		}
		
		int resultCode = shoppingOrderService.cancelOrder(id);
		
		if (resultCode != ResultCode.SUCCESS) {
			return successCreated(resultCode);
		}
		
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
		int resultCode = shoppingOrderService.deleteOrder(id);
		
		if (resultCode != ResultCode.SUCCESS) {
			return successCreated(resultCode);
		}
		
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
		
		// 如果快递公司编码和物流编号为空.不查询物流
		ExpressInquiriesDetailBO expressInquiriesDetailBO = null;
		if (StringUtils.isNotBlank(shoppingOrderBO.getExpressCompanyCode()) && StringUtils.isNotBlank(shoppingOrderBO.getWaybillNum())) {
			expressInquiriesDetailBO = expressStrategy.inquiries(shoppingOrderBO.getExpressCompanyCode(), shoppingOrderBO.getWaybillNum());
		}
		
		return successGet(ShoppingOrderConverter.covert(shoppingOrderBO, expressInquiriesDetailBO));
	}
	
	/**
	 * 根据查询参数分页查询退款记录
	 * 购物订单 购物订单项 退款详情关联查询
	 * To Member 
	 * 
	 * @param memberId
	 *            会员id
	 * @param param
	 *            查询参数
	 * @return
	 */
	@RequestMapping(value = "selectRefundPageByMemberId/{memberId}", method = RequestMethod.POST)
	public Result<Page<ShoppingOrderItemRefundDTO>> selectRefundPageByMemberId(@PathVariable("memberId") Long memberId, @RequestBody ShoppingRefundQueryForeignParam param) {
		
		Page<ShoppingOrderItemExtendBO> page = shoppingOrderItemService.selectRefundPageByMemberId(memberId, param);
		
		return successCreated(ShoppingOrderItemExtendConverter.convertShoppingOrderItemRefundDTOPage(page));
	}
	
	/**
	 * 买家申请退款
	 * 修改订单项订单状态为退款中
	 * 修改订单项退款状态为待商家确认
	 * 
	 * @param shoppingOrderitemId 购物订单项id
	 * @param param 退款参数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "requestRefund/{shoppingOrderitemId}", method = RequestMethod.PUT)
	public Result requestRefund(@PathVariable("shoppingOrderitemId") Long shoppingOrderitemId, @RequestBody ShoppingOrderRequestRefundParam param) {

		// 修改购物订单以及订单项状态，保存退款详情记录
		int result = shoppingOrderService.requestRefund(shoppingOrderitemId, param);
		
		if (result != ResultCode.SUCCESS) {
			return successCreated(result);
		}
		
		return successCreated();
	}
	
	/**
	 * 订单支付
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@RequestMapping(value = "orderPayment/{id}", method = RequestMethod.PUT)
	public Result<ShoppingOrderPaymentDTO> orderPayment(@PathVariable("id") Long id) {
		
		ShoppingOrderBO shoppingOrderBO = shoppingOrderService.getShoppingOrder(id);
		
		if (shoppingOrderBO.getId() == null || shoppingOrderBO.getId() <= 0) {
			return successCreated(ResultCode.RESOURCE_NOT_FOUND);
		}
		
		return successCreated(ShoppingOrderConverter.convert(shoppingOrderBO));
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
		
		int resultCode = shoppingOrderService.tradingSuccess(id, false);
		
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
		Page<ShoppingOrderQueryToMerchantDTO> shoppingOrderExtendQueryDTOPage = ShoppingOrderExtendConverter.convertShoppingOrderQueryToMerchantDTOPage(shoppingOrderExtendQueryBOPage);
		shoppingOrderExtendQueryBOPage = null;
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
	public Result fillLogisticsInformation(@PathVariable("id") Long id, @RequestBody ShoppingOrderLogisticsInformationParam param) {

		int resultCode = shoppingOrderService.fillLogisticsInformation(id, param);
		if (resultCode != ResultCode.SUCCESS) {
			return successCreated(resultCode);
		}
		
		return successCreated();
	}
	
	/**
	 * 根据查询参数分页查询退款记录
	 * 购物订单 购物订单项 退款详情关联查询
	 * To Merchant
	 * 
	 * @param merchantId
	 *            商家id
	 * @param param
	 *            查询参数
	 * @return
	 */
	@RequestMapping(value = "selectRefundPageByMerchantId/{merchantId}", method = RequestMethod.POST)
	public Result<Page<ShoppingOrderItemRefundForMerchantDTO>> selectRefundPageByMerchantId(@PathVariable("merchantId") Long merchantId, @RequestBody ShoppingRefundQueryForeignParam param) {
		
		Page<ShoppingOrderItemExtendBO> page = shoppingOrderItemService.selectRefundPageByMerchantId(merchantId, param);
		
		return successCreated(ShoppingOrderItemExtendConverter.convertShoppingOrderItemRefundForMerchantDTOPage(page));
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

		return successGet(shoppingOrderQueryToOperatorDTOPage);
	}
	
	/**
	 * 更新订单信息
	 * 
	 * @param param
	 *            查询参数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "updateInformation/{id}", method = RequestMethod.PUT)
	public Result updateInformation(@PathVariable Long id , @RequestBody ShoppingOrderUpdateInfomationParam param) {
		
		int result = shoppingOrderService.updateInformation(id, param);
		
		if (result != ResultCode.SUCCESS) {
			return successCreated(result);
		}

		return successCreated();
	}
	
	/**
	 * 查询各种订单状态的数量
	 * To Merchant
	 * 
	 * @param merchantId 商家id
	 * @return
	 * @author Sunny
	 */
	@RequestMapping(value = "numberOfOrderStartusByMerchant/{merchantId}", method = RequestMethod.GET)
	public Result<ShoppingOrderNumberOfOrderStatusForMerchantForeignDTO> numberOfOrderStartusByMerchant(@PathVariable("merchantId") Long merchantId) {
		
		ShoppingOrderNumberOfOrderStatusForMerchantBO shoppingOrderNumberOfOrderStatusForMerchantBO = shoppingOrderService.numberOfOrderStartusByMerchant(merchantId);
		
		return successGet(ShoppingOrderConverter.convert(shoppingOrderNumberOfOrderStatusForMerchantBO));
	}
	
	/*********************************************************
	 * 					Operator
	 * ******************************************************/
	
	/**
	 * 根据查询参数分页查询退款记录
	 * 购物订单 购物订单项 退款详情关联查询
	 * To Operator
	 * 
	 * @param param
	 *            查询参数
	 * @return
	 */
	@RequestMapping(value = "selectRefundPage", method = RequestMethod.POST)
	public Result<Page<ShoppingOrderItemRefundForOperatorDTO>> selectRefundPage(@RequestBody ShoppingRefundQueryForeignParam param) {
		
		Page<ShoppingOrderItemExtendBO> page = shoppingOrderItemService.selectRefundPage(param);
		
		return successCreated(ShoppingOrderItemExtendConverter.convertShoppingOrderItemRefundForOperatorDTOPage(page));
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

		boolean isNotFind = shoppingOrderExtendDetailBO == null || shoppingOrderExtendDetailBO.getId() == null || shoppingOrderExtendDetailBO.getId() <= 0 || shoppingOrderExtendDetailBO.getItems() == null || shoppingOrderExtendDetailBO.getItems().isEmpty();
		if (isNotFind) {
			return successGet(ResultCode.RESOURCE_NOT_FOUND);
		}
		
		// 如果快递公司编码和物流编号为空.不查询物流
		ExpressInquiriesDetailBO expressInquiriesDetailBO = null;
		if (StringUtils.isNotBlank(shoppingOrderExtendDetailBO.getExpressCompanyCode()) && StringUtils.isNotBlank(shoppingOrderExtendDetailBO.getWaybillNum())) {
			expressInquiriesDetailBO = expressStrategy.inquiries(shoppingOrderExtendDetailBO.getExpressCompanyCode(), shoppingOrderExtendDetailBO.getWaybillNum());
		}
		
		ShoppingOrderExtendDetailDTO shoppingOrderExtendDetailDTO = ShoppingOrderExtendConverter.convert(shoppingOrderExtendDetailBO, expressInquiriesDetailBO);
		
		// 倒计时在服务端放入
		Long countdown = null;
		switch (shoppingOrderExtendDetailDTO.getOrderStatus()) {
			case PENDING_PAYMENT:
				// 1.自动取消订单
				String automaticCancelOrder = propertyService.getByName(PropertyNameConstant.AUTOMATIC_CANCEL_ORDER);
				
				Date automaticCancelOrderTo = DateUtil.add(shoppingOrderExtendDetailDTO.getGmtCreate(), Integer.valueOf(automaticCancelOrder), Calendar.DAY_OF_YEAR);
				
				countdown = DateUtil.interval(new Date(), automaticCancelOrderTo, Calendar.MILLISECOND);
				break;
			case TO_BE_RECEIVED:
				// 2.自动确认收货
				String automaticReceipt = propertyService.getByName(PropertyNameConstant.AUTOMATIC_RECEIPT);
				
				Date automaticReceiptTo = DateUtil.add(shoppingOrderExtendDetailDTO.getGmtTransport(), Integer.valueOf(automaticReceipt), Calendar.DAY_OF_YEAR);
				
				countdown = DateUtil.interval(new Date(), automaticReceiptTo, Calendar.MILLISECOND);
				break;
			default:
				break;
		}
		shoppingOrderExtendDetailDTO.setCountdown(countdown);
		
		return successGet(shoppingOrderExtendDetailDTO);
	}
	
	/** 第三方支付时获取订单原始总金额，用于调用第三方支付平台
	 * @param orderIds
	 * @return
	 * @author Yangqh
	 */
	@RequestMapping(value = "selectOrderMoney", method = RequestMethod.GET)
	public Result<ShoppingOrderMoneyDTO> selectOrderMoney(@RequestParam String orderIds) {
		Result<ShoppingOrderMoneyBO> result = shoppingOrderService.selectOrderMoney(orderIds);
		if (!isSuccess(result)) {
			return successGet(result.getRet());
		}
		ShoppingOrderMoneyDTO shoppingOrderMoneyDTO = new ShoppingOrderMoneyDTO();
		shoppingOrderMoneyDTO.setOrderTotalPrice(result.getModel().getOrderTotalPrice());
		
		return successGet(shoppingOrderMoneyDTO);
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
	
	/**
	 * 查询商家是否有正在进行中的订单
	 * 
	 * @param merchantId 商家id
	 * @return
	 * @author Sunny
	 */
	@RequestMapping(value = "isNoOnGoingOrder/{merchantId}", method = RequestMethod.GET)
	public Result<ShoppingOrderIsNoOnGoingOrderDTO> isNoOnGoingOrder(@PathVariable("merchantId") Long merchantId) {
		// 查询订单商品评价状态
		ShoppingOrderIsNoOnGoingOrderBO shoppingOrderIsNoOnGoingOrderBO = shoppingOrderService.isNoOnGoingOrder(merchantId);
		
		return successGet(ShoppingOrderConverter.convert(shoppingOrderIsNoOnGoingOrderBO));
	}
	
	/**
	 * 查询各种订单状态的数量
	 * 
	 * @param memberId 会员id
	 * @return
	 * @author Sunny
	 */
	@RequestMapping(value = "numberOfOrderStartus/{memberId}", method = RequestMethod.GET)
	public Result<ShoppingOrderNumberOfOrderStatusDTO> numberOfOrderStartus(@PathVariable("memberId") Long memberId) {
		
		ShoppingOrderNumberOfOrderStatusBO shoppingOrderNumberOfOrderStatusBO = shoppingOrderService.numberOfOrderStartus(memberId);
		
		return successGet(ShoppingOrderConverter.convert(shoppingOrderNumberOfOrderStatusBO));
	}
	
	/**
	 * 查询已完成但是未计算提成的购物订单
	 * 
	 * @param memberId 会员id
	 * @return
	 * @author Sunny
	 */
	@RequestMapping(value = "commissionShoppingOrder", method = RequestMethod.GET)
	public Result<List<ShoppingOrderCommissionDTO>> commissionShoppingOrder() {
		
		List<ShoppingOrderBO> shoppingOrderBOList = shoppingOrderService.commissionShoppingOrder();
		
		return successGet(ShoppingOrderConverter.convertShoppingOrderCommissionDTOList(shoppingOrderBOList));
	}
	
	/**
	 * 查询已完成但是未计算提成的购物订单
	 * 
	 * @param memberId 会员id
	 * @return
	 * @author Sunny
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "updateCommissionStatus", method = RequestMethod.PUT)
	public Result updateCommissionStatus(@RequestParam("ids") List<Long> ids) {
		
		int resultCode = shoppingOrderService.updateCommissionStatus(ids);
		
		if (resultCode != ResultCode.SUCCESS) {
			return successCreated(resultCode);
		}
		
		return successCreated();
	}
	
	/**
	 * 查询已完成但是未计算提成的购物订单
	 * 
	 * @param memberId 会员id
	 * @return
	 * @author Sunny
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "selectByTransactionData", method = RequestMethod.PUT)
	public Result selectByTransactionData(@RequestBody ReportDataParam param) {
		
		ReportRiseRateDTO reportRiseRateDTO = shoppingOrderService.selectByTransactionData(param);
		
		return successCreated(reportRiseRateDTO);
	}
	
	/**
	 * 查询已完成但是未计算提成的购物订单
	 * 
	 * @param memberId 会员id
	 * @return
	 * @author Sunny
	 */
	@RequestMapping(value = "fansSaleTransform", method = RequestMethod.PUT)
	public Result<List<ReportRiseRerouceDTO>> fansSaleTransform(@RequestBody ReportDataParam param) {
		
		List<ReportRiseRerouceDTO> reportRiseRerouceDTOList = shoppingOrderService.fansSaleTransform(param);
		
		return successCreated(reportRiseRerouceDTOList);
	}
}
