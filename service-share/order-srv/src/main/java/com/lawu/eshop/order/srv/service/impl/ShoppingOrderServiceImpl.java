package com.lawu.eshop.order.srv.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.ShoppingOrderNoPaymentNotification;
import com.lawu.eshop.mq.dto.order.ShoppingOrderOrdersTradingIncomeNoticeNotification;
import com.lawu.eshop.mq.dto.order.ShoppingOrderRemindShipmentsNotification;
import com.lawu.eshop.mq.dto.order.ShoppingOrderpaymentSuccessfulNotification;
import com.lawu.eshop.mq.dto.order.ShoppingRefundToBeConfirmedForRefundRemindNotification;
import com.lawu.eshop.mq.dto.order.reply.ShoppingOrderCreateOrderReply;
import com.lawu.eshop.mq.dto.property.ShoppingOrderPaymentNotification;
import com.lawu.eshop.mq.message.MessageProducerService;
import com.lawu.eshop.order.constants.CommissionStatusEnum;
import com.lawu.eshop.order.constants.RefundStatusEnum;
import com.lawu.eshop.order.constants.ReportFansRiseRateEnum;
import com.lawu.eshop.order.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.order.constants.ShoppingOrderStatusToMemberEnum;
import com.lawu.eshop.order.constants.ShoppingRefundTypeEnum;
import com.lawu.eshop.order.constants.StatusEnum;
import com.lawu.eshop.order.dto.ReportRiseRateDTO;
import com.lawu.eshop.order.dto.ReportRiseRerouceDTO;
import com.lawu.eshop.order.param.ReportDataParam;
import com.lawu.eshop.order.param.ShoppingOrderLogisticsInformationParam;
import com.lawu.eshop.order.param.ShoppingOrderReportDataParam;
import com.lawu.eshop.order.param.ShoppingOrderRequestRefundParam;
import com.lawu.eshop.order.param.ShoppingOrderSettlementItemParam;
import com.lawu.eshop.order.param.ShoppingOrderSettlementParam;
import com.lawu.eshop.order.param.ShoppingOrderUpdateInfomationParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderQueryForeignToMemberParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderQueryForeignToMerchantParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderQueryForeignToOperatorParam;
import com.lawu.eshop.order.srv.bo.ShoppingOrderBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderExtendBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderIsNoOnGoingOrderBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderMoneyBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderNumberOfOrderStatusBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderNumberOfOrderStatusForMerchantBO;
import com.lawu.eshop.order.srv.constants.ExceptionMessageConstant;
import com.lawu.eshop.order.srv.constants.PropertyNameConstant;
import com.lawu.eshop.order.srv.converter.ReportConvert;
import com.lawu.eshop.order.srv.converter.ShoppingOrderConverter;
import com.lawu.eshop.order.srv.converter.ShoppingOrderExtendConverter;
import com.lawu.eshop.order.srv.converter.ShoppingOrderItemConverter;
import com.lawu.eshop.order.srv.domain.ShoppingCartDOExample;
import com.lawu.eshop.order.srv.domain.ShoppingOrderDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderDOExample;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDOExample;
import com.lawu.eshop.order.srv.domain.ShoppingRefundDetailDO;
import com.lawu.eshop.order.srv.domain.ShoppingRefundDetailDOExample;
import com.lawu.eshop.order.srv.domain.ShoppingRefundProcessDO;
import com.lawu.eshop.order.srv.domain.extend.NotShippedDO;
import com.lawu.eshop.order.srv.domain.extend.ReportFansSaleTransFormDO;
import com.lawu.eshop.order.srv.domain.extend.ReportRiseRateView;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderExtendDO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderExtendDOExample;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderExtendDOExample.Criteria;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderItemExtendDO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderItemExtendDOExample;
import com.lawu.eshop.order.srv.exception.CanNotFillInShippingLogisticsException;
import com.lawu.eshop.order.srv.exception.DataNotExistException;
import com.lawu.eshop.order.srv.exception.IllegalOperationException;
import com.lawu.eshop.order.srv.exception.OrderCreationFailedException;
import com.lawu.eshop.order.srv.exception.OrderNotCanceledException;
import com.lawu.eshop.order.srv.exception.OrderNotDeleteException;
import com.lawu.eshop.order.srv.exception.OrderNotReceivedException;
import com.lawu.eshop.order.srv.exception.OrderNotRefundException;
import com.lawu.eshop.order.srv.exception.TheOrderIsBeingProcessedException;
import com.lawu.eshop.order.srv.mapper.ShoppingCartDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderItemDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingRefundDetailDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingRefundProcessDOMapper;
import com.lawu.eshop.order.srv.mapper.extend.ShoppingOrderExtendDOMapper;
import com.lawu.eshop.order.srv.mapper.extend.ShoppingOrderItemExtendDOMapper;
import com.lawu.eshop.order.srv.service.PropertyService;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;
import com.lawu.eshop.utils.DateUtil;

@Service
public class ShoppingOrderServiceImpl implements ShoppingOrderService {

	private static Logger logger = LoggerFactory.getLogger(ShoppingOrderServiceImpl.class);

	@Autowired
	private ShoppingCartDOMapper shoppingCartDOMapper;

	@Autowired
	private ShoppingOrderExtendDOMapper shoppingOrderDOExtendMapper;

	@Autowired
	private ShoppingOrderDOMapper shoppingOrderDOMapper;

	@Autowired
	private ShoppingOrderItemDOMapper shoppingOrderItemDOMapper;

	@Autowired
	private ShoppingOrderItemExtendDOMapper shoppingOrderItemExtendDOMapper;

	@Autowired
	private ShoppingRefundDetailDOMapper shoppingRefundDetailDOMapper;

	@Autowired
	private ShoppingRefundProcessDOMapper shoppingRefundProcessDOMapper;

	@Autowired
	private PropertyService propertyService;

	@Autowired
	private MessageProducerService messageProducerService;

	@Autowired
	@Qualifier("shoppingOrderTradingSuccessTransactionMainServiceImpl")
	private TransactionMainService<Reply> shoppingOrderTradingSuccessTransactionMainServiceImpl;

	@Autowired
	@Qualifier("shoppingOrderCreateOrderTransactionMainServiceImpl")
	private TransactionMainService<ShoppingOrderCreateOrderReply> shoppingOrderCreateOrderTransactionMainServiceImpl;

	@Autowired
	@Qualifier("shoppingOrderCancelOrderTransactionMainServiceImpl")
	private TransactionMainService<Reply> shoppingOrderCancelOrderTransactionMainServiceImpl;

	@Autowired
	@Qualifier("shoppingOrderAutoCommentTransactionMainServiceImpl")
	private TransactionMainService<Reply> shoppingOrderAutoCommentTransactionMainServiceImpl;

	@Autowired
	@Qualifier("shoppingOrderCreateOrderFansTransactionMainServiceImpl")
	private TransactionMainService<Reply> shoppingOrderCreateOrderFansTransactionMainServiceImpl;

	@Autowired
	@Qualifier("shoppingOrderTradingSuccessIncreaseSalesTransactionMainServiceImpl")
	private TransactionMainService<Reply> shoppingOrderTradingSuccessIncreaseSalesTransactionMainServiceImpl;

	@Autowired
	@Qualifier("shoppingOrderPaymentsToMerchantTransactionMainServiceImpl")
	private TransactionMainService<Reply> shoppingOrderPaymentsToMerchantTransactionMainServiceImpl;

	/**
	 * 
	 * @param params
	 *            多个订单参数
	 * @return 返回保存的订单id
	 */
	@Transactional
	@Override
	public List<Long> save(List<ShoppingOrderSettlementParam> params) {

		List<Long> rtn = new ArrayList<>();

		// 插入订单
		for (ShoppingOrderSettlementParam shoppingOrderSettlementParam : params) {
			ShoppingOrderDO shoppingOrderDO = ShoppingOrderConverter.convert(shoppingOrderSettlementParam);

			shoppingOrderDOMapper.insertSelective(shoppingOrderDO);
			Long id = shoppingOrderDO.getId();

			// 插入订单项
			for (ShoppingOrderSettlementItemParam item : shoppingOrderSettlementParam.getItems()) {
				ShoppingOrderItemDO shoppingOrderItemDO = ShoppingOrderItemConverter.convert(id, item);
				shoppingOrderItemDOMapper.insertSelective(shoppingOrderItemDO);

			}

			// 考虑订单是否保存失败处理
			if (id != null && id > 0) {
				// 把订单id放入list返回
				rtn.add(id);
				// 事务补偿(减掉库存)
				shoppingOrderCreateOrderTransactionMainServiceImpl.sendNotice(id);

				// 判断用户是否是商家粉丝
				if (!shoppingOrderDO.getIsFans()) {
					// 事务补偿(用户成为商家粉丝)
					shoppingOrderCreateOrderFansTransactionMainServiceImpl.sendNotice(id);
				}
			}
		}

		return rtn;
	}

	@Override
	public Page<ShoppingOrderExtendBO> selectPageByMemberId(Long memberId, ShoppingOrderQueryForeignToMemberParam param) {
		ShoppingOrderExtendDOExample shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		shoppingOrderExtendDOExample.setIncludeViewShoppingOrderItem(false);
		shoppingOrderExtendDOExample.setIncludeShoppingOrderItem(true);

		// 组装Criteria
		Criteria baseCriteria = shoppingOrderExtendDOExample.createCriteria();

		// 用户如果删除则不显示
		baseCriteria.andStatusEqualTo(StatusEnum.VALID.getValue());

		if (memberId != null) {
			baseCriteria.andMemberIdEqualTo(memberId);
		}

		if (param.getOrderStatus() != null) {
			baseCriteria.andOrderStatusEqualTo(param.getOrderStatus().getValue());

			// 查找待评价的订单
			if (param.getOrderStatus().equals(ShoppingOrderStatusToMemberEnum.BE_EVALUATED)) {
				baseCriteria.andSOIIsEvaluationEqualTo(false);
			}
		}

		if (!StringUtils.isEmpty(param.getKeyword())) {
			shoppingOrderExtendDOExample.clear();

			Criteria orderNumCriteria = shoppingOrderExtendDOExample.or();
			orderNumCriteria.andOrderNumEqualTo(param.getKeyword());
			orderNumCriteria.getAllCriteria().addAll(baseCriteria.getAllCriteria());

			Criteria paroductCriteria = shoppingOrderExtendDOExample.or();
			paroductCriteria.andSOIProductNameLike("%" + param.getKeyword() + "%");
			paroductCriteria.getAllCriteria().addAll(baseCriteria.getAllCriteria());
		}

		// 过滤重复记录
		shoppingOrderExtendDOExample.setDistinct(true);

		// 查询总记录数
		Long count = shoppingOrderDOExtendMapper.countByExample(shoppingOrderExtendDOExample);

		Page<ShoppingOrderExtendBO> shoppingOrderItemBOPage = new Page<>();
		shoppingOrderItemBOPage.setTotalCount(count.intValue());
		shoppingOrderItemBOPage.setCurrentPage(param.getCurrentPage());

		// 如果总记录为0，不再执行后续操作直接返回
		if (count <= 0 || param.getOffset() >= count) {
			return shoppingOrderItemBOPage;
		}

		// 分页参数
		RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());

		// 如果参数中的keyword有值，查询结果的订单项会缺少，所有先找出所有购物订单id再通过去查找购物订单以及级联的购物订单项
		List<Long> idList = shoppingOrderDOExtendMapper.selectIdByExample(shoppingOrderExtendDOExample, rowBounds);

		shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		shoppingOrderExtendDOExample.setIncludeViewShoppingOrderItem(true);
		shoppingOrderExtendDOExample.setIncludeShoppingOrderItem(true);
		shoppingOrderExtendDOExample.createCriteria().andIdIn(idList);

		// 默认创建时间排序
		shoppingOrderExtendDOExample.setOrderByClause("so.gmt_create desc");

		List<ShoppingOrderExtendDO> shoppingOrderExtendDOList = shoppingOrderDOExtendMapper.selectByExample(shoppingOrderExtendDOExample);

		shoppingOrderItemBOPage.setRecords(ShoppingOrderExtendConverter.convertShoppingOrderExtendBO(shoppingOrderExtendDOList));
		shoppingOrderExtendDOList = null;

		return shoppingOrderItemBOPage;
	}

	@Override
	public Page<ShoppingOrderExtendBO> selectPageByMerchantId(Long merchantId, ShoppingOrderQueryForeignToMerchantParam param) {
		ShoppingOrderExtendDOExample shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();

		// 组装Criteria
		Criteria baseCriteria = shoppingOrderExtendDOExample.createCriteria();

		if (merchantId != null) {
			baseCriteria.andMerchantIdEqualTo(merchantId);
		}

		if (param.getOrderStatus() != null) {
			// 参数的订单状态是一个数组类型，查询多个参数状态
			baseCriteria.andOrderStatusIn(Arrays.asList(param.getOrderStatus().getValue()));
		}

		if (!StringUtils.isEmpty(param.getKeyword())) {
			shoppingOrderExtendDOExample.clear();

			Criteria orderNumCriteria = shoppingOrderExtendDOExample.or();
			orderNumCriteria.andOrderNumEqualTo(param.getKeyword());
			orderNumCriteria.getAllCriteria().addAll(baseCriteria.getAllCriteria());

			Criteria paroductCriteria = shoppingOrderExtendDOExample.or();
			paroductCriteria.andConsigneeNameLike("%" + param.getKeyword() + "%");
			paroductCriteria.getAllCriteria().addAll(baseCriteria.getAllCriteria());
		}

		// 查询总记录数
		Long count = shoppingOrderDOExtendMapper.countByExample(shoppingOrderExtendDOExample);

		Page<ShoppingOrderExtendBO> shoppingOrderItemBOPage = new Page<>();
		shoppingOrderItemBOPage.setTotalCount(count.intValue());
		shoppingOrderItemBOPage.setCurrentPage(param.getCurrentPage());
		// 初始一条空记录
		shoppingOrderItemBOPage.setRecords(new ArrayList<>());

		/*
		 * 如果count为0，或者offset大于count 不再执行后续操作直接返回
		 */
		if (count <= 0 || param.getOffset() >= count) {
			return shoppingOrderItemBOPage;
		}

		// 分页参数
		RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());

		// 默认创建时间排序
		shoppingOrderExtendDOExample.setOrderByClause("so.gmt_create desc");

		// 如果参数中的keyword有值，查询结果的订单项会缺少，所有先找出所有购物订单id再通过去查找购物订单以及级联的购物订单项
		List<Long> idList = shoppingOrderDOExtendMapper.selectIdByExample(shoppingOrderExtendDOExample, rowBounds);

		shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		shoppingOrderExtendDOExample.setIncludeViewShoppingOrderItem(true);
		shoppingOrderExtendDOExample.setIncludeShoppingOrderItem(true);
		shoppingOrderExtendDOExample.createCriteria().andIdIn(idList);

		// 默认创建时间排序
		shoppingOrderExtendDOExample.setOrderByClause("so.gmt_create desc");

		List<ShoppingOrderExtendDO> shoppingOrderExtendDOList = shoppingOrderDOExtendMapper.selectByExample(shoppingOrderExtendDOExample);

		shoppingOrderItemBOPage.setRecords(ShoppingOrderExtendConverter.convertShoppingOrderExtendBO(shoppingOrderExtendDOList));

		return shoppingOrderItemBOPage;
	}

	/**
	 * 查询订单详情
	 * 
	 * @param id
	 *            购物订单id
	 * @param memberId
	 *            会员id
	 * @param merchantId
	 *            商家id
	 * @return
	 * @author jiangxinjun
	 * @date 2017年7月10日
	 */
	@Override
	public ShoppingOrderExtendBO get(Long id, Long memberId, Long merchantId) {
		ShoppingOrderExtendDOExample shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		shoppingOrderExtendDOExample.setIncludeShoppingOrderItem(true);
		shoppingOrderExtendDOExample.setIncludeViewShoppingOrderItem(true);
		ShoppingOrderExtendDO shoppingOrderExtendDO = shoppingOrderDOExtendMapper.selectByPrimaryKey(id);
		// 如果是会员端请求,需要判断订单的数据状态是否是正常
		boolean isNotFind = shoppingOrderExtendDO == null || shoppingOrderExtendDO.getItems() == null || shoppingOrderExtendDO.getItems().isEmpty() || (memberId != null && shoppingOrderExtendDO.getStatus().equals(StatusEnum.INVALID.getValue()));
		if (isNotFind) {
			throw new DataNotExistException(ExceptionMessageConstant.SHOPPING_ORDER_DATA_NOT_EXIST);
		}
		if (memberId != null && !shoppingOrderExtendDO.getMemberId().equals(memberId)) {
			throw new IllegalOperationException(ExceptionMessageConstant.ILLEGAL_OPERATION_SHOPPING_ORDER);
		}
		if (merchantId != null && !shoppingOrderExtendDO.getMerchantId().equals(merchantId)) {
			throw new IllegalOperationException(ExceptionMessageConstant.ILLEGAL_OPERATION_SHOPPING_ORDER);
		}
		return ShoppingOrderExtendConverter.convertShoppingOrderExtendDetailBO(shoppingOrderExtendDO);
	}

	/**
	 * 查询订单详情
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@Override
	public ShoppingOrderExtendBO get(Long id) {
		return get(id, null, null);
	}

	/**
	 * 根据id获取购物订单
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@Override
	public ShoppingOrderBO getShoppingOrder(Long id) throws DataNotExistException, IllegalOperationException {
		return getShoppingOrder(id, null, null);
	}

	/**
	 * 根据id获取购物订单
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@Override
	public ShoppingOrderBO getShoppingOrder(Long id, Long memberId, Long merchantId) throws DataNotExistException, IllegalOperationException {
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(id);
		if (shoppingOrderDO == null || shoppingOrderDO.getStatus().equals(StatusEnum.INVALID)) {
			throw new DataNotExistException(ExceptionMessageConstant.SHOPPING_ORDER_DATA_NOT_EXIST);
		}
		if (memberId != null && !shoppingOrderDO.getMemberId().equals(memberId)) {
			throw new IllegalOperationException(ExceptionMessageConstant.ILLEGAL_OPERATION_SHOPPING_ORDER);
		}
		if (merchantId != null && !shoppingOrderDO.getMerchantId().equals(merchantId)) {
			throw new IllegalOperationException(ExceptionMessageConstant.ILLEGAL_OPERATION_SHOPPING_ORDER);
		}
		return ShoppingOrderConverter.convertShoppingOrderBO(shoppingOrderDO);
	}

	/**
	 * 取消购物订单
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@Transactional
	@Override
	public void cancelOrder(Long memberId, Long id) throws DataNotExistException, IllegalOperationException, OrderNotCanceledException {
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(id);

		if (shoppingOrderDO == null || shoppingOrderDO.getStatus().equals(StatusEnum.INVALID)) {
			throw new DataNotExistException(ExceptionMessageConstant.SHOPPING_ORDER_DATA_NOT_EXIST);
		}

		if (memberId != null && !shoppingOrderDO.getMemberId().equals(memberId)) {
			throw new IllegalOperationException(ExceptionMessageConstant.ILLEGAL_OPERATION_SHOPPING_ORDER);
		}

		// 被取消的订单必须要是待支付的状态
		if (!shoppingOrderDO.getOrderStatus().equals(ShoppingOrderStatusEnum.PENDING_PAYMENT.getValue())) {
			throw new OrderNotCanceledException(ExceptionMessageConstant.ORDER_IS_NOT_PENDING_PAYMENT_STATUS);
		}

		// 更新购物订单的状态
		shoppingOrderDO = new ShoppingOrderDO();
		shoppingOrderDO.setId(id);
		shoppingOrderDO.setGmtModified(new Date());
		// 更新订单状态
		shoppingOrderDO.setOrderStatus(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue());
		// 更新成交时间
		shoppingOrderDO.setGmtTransaction(new Date());
		shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderDO);

		// 更新购物订单项状态
		ShoppingOrderItemDOExample shoppingOrderItemDOExample = new ShoppingOrderItemDOExample();
		com.lawu.eshop.order.srv.domain.ShoppingOrderItemDOExample.Criteria shoppingOrderItemDOExampleCriteria = shoppingOrderItemDOExample.createCriteria();
		shoppingOrderItemDOExampleCriteria.andShoppingOrderIdEqualTo(id);

		ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
		shoppingOrderItemDO.setGmtModified(new Date());
		shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue());

		shoppingOrderItemDOMapper.updateByExampleSelective(shoppingOrderItemDO, shoppingOrderItemDOExample);

		// 事务补偿(释放库存)
		shoppingOrderCancelOrderTransactionMainServiceImpl.sendNotice(id);
	}

	/**
	 * 
	 * @param memberId
	 *            会员id
	 * @param id
	 *            购物订单id
	 * @author jiangxinjun
	 * @date 2017年7月10日
	 */
	@Transactional
	@Override
	public void deleteOrder(Long memberId, Long id) {
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(id);

		if (shoppingOrderDO == null || shoppingOrderDO.getStatus().equals(StatusEnum.INVALID.getValue())) {
			throw new DataNotExistException(ExceptionMessageConstant.SHOPPING_ORDER_DATA_NOT_EXIST);
		}

		if (!shoppingOrderDO.getMemberId().equals(memberId)) {
			throw new IllegalOperationException(ExceptionMessageConstant.ILLEGAL_OPERATION_SHOPPING_ORDER);
		}

		// 订单的当前状态必须已结束状态的订单
		if (!shoppingOrderDO.getOrderStatus().equals(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue()) && !shoppingOrderDO.getOrderStatus().equals(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue())) {
			throw new OrderNotDeleteException(ExceptionMessageConstant.ORDER_IS_NOT_OVER);
		}

		// 如果订单的状态是交易成功，检查订单项的订单状态是否是否是完成状态s
		if (shoppingOrderDO.getOrderStatus().equals(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue()) && !shoppingOrderDO.getIsDone()) {
			throw new OrderNotDeleteException(ExceptionMessageConstant.ORDER_HAS_NOT_BEEN_COMPLETED);
		}

		// 更新购物订单的状态
		shoppingOrderDO = new ShoppingOrderDO();
		shoppingOrderDO.setId(id);
		shoppingOrderDO.setGmtModified(new Date());
		// 更改数据状态为删除
		shoppingOrderDO.setStatus(StatusEnum.INVALID.getValue());
		shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderDO);
	}

	/**
	 * 支付成功之后 修改购物订单以及订单项状态为待发货
	 * 
	 * @param id
	 *            购物订单id
	 */
	@Transactional
	@Override
	public void paymentSuccessful(ShoppingOrderPaymentNotification notification) {
		if (notification == null) {
			return;
		}

		String[] shoppingOrderIds = StringUtils.split(notification.getShoppingOrderIds(), ",");

		for (String shoppingOrderId : shoppingOrderIds) {
			/*
			 * 实现MQ幂等性 只有订单是待支付的状态才允许更新购物订单的状态
			 */
			Long id = Long.valueOf(shoppingOrderId);
			ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(id);

			// 更新购物订单的状态
			shoppingOrderDO.setGmtModified(new Date());
			// 更改订单状态为待发货
			shoppingOrderDO.setOrderStatus(ShoppingOrderStatusEnum.BE_SHIPPED.getValue());
			// 更新付款时间
			shoppingOrderDO.setGmtPayment(new Date());
			// 更新付款方式以及第三方交易号
			shoppingOrderDO.setPaymentMethod(notification.getPaymentMethod());
			shoppingOrderDO.setThirdNumber(notification.getThirdNumber());
			shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderDO);

			// 更新购物订单项状态
			ShoppingOrderItemDOExample shoppingOrderItemDOExample = new ShoppingOrderItemDOExample();
			com.lawu.eshop.order.srv.domain.ShoppingOrderItemDOExample.Criteria shoppingOrderItemDOExampleCriteria = shoppingOrderItemDOExample.createCriteria();
			shoppingOrderItemDOExampleCriteria.andShoppingOrderIdEqualTo(id);

			List<ShoppingOrderItemDO> shoppingOrderItemDOList = shoppingOrderItemDOMapper.selectByExample(shoppingOrderItemDOExample);

			ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
			shoppingOrderItemDO.setGmtModified(new Date());
			shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.BE_SHIPPED.getValue());
			shoppingOrderItemDOMapper.updateByExampleSelective(shoppingOrderItemDO, shoppingOrderItemDOExample);

			/*
			 * 买家支付成功发送 推送消息给商家提醒买家新增了一个订单 发送推送消息提醒买家支付成功
			 */
			// 组装要发送的消息
			ShoppingOrderpaymentSuccessfulNotification shoppingOrderpaymentSuccessfulNotification = new ShoppingOrderpaymentSuccessfulNotification();
			shoppingOrderpaymentSuccessfulNotification.setMerchantNum(shoppingOrderDO.getMerchantNum());
			shoppingOrderpaymentSuccessfulNotification.setMemberNum(shoppingOrderDO.getMemberNum());
			shoppingOrderpaymentSuccessfulNotification.setOrderNum(shoppingOrderDO.getOrderNum());
			StringBuilder stringBuilder = new StringBuilder();
			for (ShoppingOrderItemDO item : shoppingOrderItemDOList) {
				if (stringBuilder.length() > 0) {
					stringBuilder.append(",");
				}
				stringBuilder.append(item.getProductName());
			}
			shoppingOrderpaymentSuccessfulNotification.setProductName(stringBuilder.toString());

			// 发送消MQ息
			messageProducerService.sendMessage(MqConstant.TOPIC_ORDER_SRV, MqConstant.TAG_PAYMENT_SUCCESSFUL_PUSH, shoppingOrderpaymentSuccessfulNotification);
		}
	}

	/**
	 * 确认收货之后 修改购物订单以及订单项状态为交易成功
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@Transactional
	@Override
	public void tradingSuccess(Long id, Long memberId, boolean isAutomaticReceipt) {
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(id);
		if (shoppingOrderDO == null) {
			throw new DataNotExistException(ExceptionMessageConstant.SHOPPING_ORDER_DATA_NOT_EXIST);
		}
		if (memberId != null && !shoppingOrderDO.getMemberId().equals(memberId)) {
			throw new IllegalOperationException(ExceptionMessageConstant.ILLEGAL_OPERATION_SHOPPING_ORDER);
		}
		// 订单的当前状态必须是待收货
		if (!shoppingOrderDO.getOrderStatus().equals(ShoppingOrderStatusEnum.TO_BE_RECEIVED.getValue())) {
			throw new OrderNotReceivedException(ExceptionMessageConstant.THE_ORDER_IS_NOT_IN_RECEIPT);
		}
		// 更新购物订单的状态
		ShoppingOrderDO shoppingOrderUpdateDO = new ShoppingOrderDO();
		shoppingOrderUpdateDO.setId(id);
		shoppingOrderUpdateDO.setGmtModified(new Date());
		shoppingOrderUpdateDO.setIsAutomaticReceipt(isAutomaticReceipt);
		// 如果是自动收货，设置订单的状态为完成
		if (isAutomaticReceipt) {
			shoppingOrderUpdateDO.setIsDone(true);
		}
		// 更改订单状态为交易成功
		shoppingOrderUpdateDO.setOrderStatus(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
		// 更新成交时间
		shoppingOrderUpdateDO.setGmtTransaction(new Date());
		shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderUpdateDO);
		// 更新购物订单项状态
		ShoppingOrderItemDOExample shoppingOrderItemDOExample = new ShoppingOrderItemDOExample();
		ShoppingOrderItemDOExample.Criteria shoppingOrderItemDOExampleCriteria = shoppingOrderItemDOExample.createCriteria();
		shoppingOrderItemDOExampleCriteria.andShoppingOrderIdEqualTo(id);
		List<ShoppingOrderItemDO> shoppingOrderItemDOList = shoppingOrderItemDOMapper.selectByExample(shoppingOrderItemDOExample);
		// 如果用户确认收货，订单当中还有退款的商品，关闭退款申请
		for (ShoppingOrderItemDO item : shoppingOrderItemDOList) {
			// 如果订单项的状态是退款中的状态
			if (item.getOrderStatus().equals(ShoppingOrderStatusEnum.REFUNDING.getValue())) {
				// 清空退款状态
				item.setRefundStatus(null);
				// 撤销退款申请
				ShoppingRefundDetailDOExample shoppingRefundDetailDOExample = new ShoppingRefundDetailDOExample();
				ShoppingRefundDetailDOExample.Criteria shoppingRefundDetailDOExampleCriteria = shoppingRefundDetailDOExample.createCriteria();
				shoppingRefundDetailDOExampleCriteria.andShoppingOrderItemIdEqualTo(item.getId());
				shoppingRefundDetailDOExampleCriteria.andStatusEqualTo(StatusEnum.VALID.getValue());
				ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
				shoppingRefundDetailDO.setStatus(StatusEnum.INVALID.getValue());
				shoppingRefundDetailDOMapper.updateByExampleSelective(shoppingRefundDetailDO, shoppingRefundDetailDOExample);
			}
			// 更改订单项状态为交易成功
			item.setOrderStatus(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
			item.setGmtModified(new Date());
			shoppingOrderItemDOMapper.updateByPrimaryKey(item);
		}
		// 发送MQ消息通知产品模块增加销量
		shoppingOrderTradingSuccessIncreaseSalesTransactionMainServiceImpl.sendNotice(id);
		/*
		 * 发送MQ消息通知资产模块 如果是手动收货保存资金冻结表 如果是自动收货直接付款给商家
		 */
		shoppingOrderTradingSuccessTransactionMainServiceImpl.sendNotice(id);
		if (shoppingOrderDO.getIsDone()) {
			// 提示商家新增一笔订单交易收入
			ordersTradingIncomeNotice(shoppingOrderUpdateDO.getId(), shoppingOrderUpdateDO.getActualAmount(), shoppingOrderUpdateDO.getMerchantNum());
		}
	}

	/**
	 * 确认收货之后 修改购物订单以及订单项状态为交易成功
	 * 
	 * @param id
	 *            购物订单id
	 * @param memberId
	 *            会员id
	 * @return
	 */
	@Transactional
	@Override
	public void tradingSuccess(Long id, Long memberId) {
		tradingSuccess(id, memberId, false);
	}

	/**
	 * 确认收货之后 修改购物订单以及订单项状态为交易成功
	 * 
	 * @param id
	 *            购物订单id
	 * @param memberId
	 *            会员id
	 * @return
	 */
	@Transactional
	@Override
	public void tradingSuccess(Long id, boolean isAutomaticReceipt) {
		tradingSuccess(id, null, isAutomaticReceipt);
	}

	/**
	 * 买家申请退款 修改订单项状态为退款中
	 * 
	 * @param shoppingOrderitemId
	 *            购物订单项id
	 * @param param
	 *            退款参数
	 * @return
	 */
	@Transactional
	@Override
	public void requestRefund(Long shoppingOrderitemId, Long memberId, ShoppingOrderRequestRefundParam param) {
		ShoppingOrderItemDO shoppingOrderItemDO = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingOrderitemId);
		if (shoppingOrderItemDO == null) {
			throw new DataNotExistException(ExceptionMessageConstant.SHOPPING_ORDER_DATA_NOT_EXIST);
		}
		// 检查订单是否已经是退款
		if (shoppingOrderItemDO.getRefundStatus() != null) {
			throw new OrderNotRefundException(ExceptionMessageConstant.IN_THE_ORDER_HAS_BEEN_REFUNDED);

		}
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getShoppingOrderId());
		if (shoppingOrderDO == null) {
			throw new DataNotExistException(ExceptionMessageConstant.SHOPPING_ORDER_DATA_NOT_EXIST);
		}
		if (!shoppingOrderDO.getMemberId().equals(memberId)) {
			throw new IllegalOperationException(ExceptionMessageConstant.ILLEGAL_OPERATION_SHOPPING_ORDER);
		}
		// 只有待发货、待收货、交易成功才能被允许退款
		if (!shoppingOrderDO.getOrderStatus().equals(ShoppingOrderStatusEnum.BE_SHIPPED.getValue()) && !shoppingOrderDO.getOrderStatus().equals(ShoppingOrderStatusEnum.TO_BE_RECEIVED.getValue()) && !shoppingOrderDO.getOrderStatus().equals(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue())) {
			throw new OrderNotRefundException(ExceptionMessageConstant.THE_CURRENT_ORDER_STATUS_DOES_NOT_MATCH);
		}
		// 商家还没有发货，无论商品是否允许支持退货都能允许退款
		if (!shoppingOrderDO.getOrderStatus().equals(ShoppingOrderStatusEnum.BE_SHIPPED.getValue()) && !shoppingOrderItemDO.getIsAllowRefund()) {
			throw new OrderNotRefundException(ExceptionMessageConstant.PRODUCT_DOES_NOT_SUPPORT_REFUNDS);
		}
		// 订单是已完成状态不允许退款
		if (shoppingOrderDO.getIsDone()) {
			throw new OrderNotRefundException(ExceptionMessageConstant.THE_ORDER_EXCEEDS_THE_REFUND_TIME);
		}
		// 更新购物订单项状态
		ShoppingOrderItemDO shoppingOrderItemUpdateDO = new ShoppingOrderItemDO();
		shoppingOrderItemUpdateDO.setId(shoppingOrderItemDO.getId());
		shoppingOrderItemUpdateDO.setGmtModified(new Date());
		shoppingOrderItemUpdateDO.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
		// 根据订单状态是否需要退货
		ShoppingRefundTypeEnum shoppingRefundTypeEnum = null;
		if (shoppingOrderDO.getOrderStatus().equals(ShoppingOrderStatusEnum.BE_SHIPPED.getValue())) {
			shoppingRefundTypeEnum = ShoppingRefundTypeEnum.REFUND;
		} else {
			// 判断当前订单是否需要物流
			if (shoppingOrderDO.getIsNeedsLogistics()) {
				shoppingRefundTypeEnum = ShoppingRefundTypeEnum.RETURN_REFUND;
			} else {
				shoppingRefundTypeEnum = ShoppingRefundTypeEnum.REFUND;
			}
		}
		/*
		 * 如果卖家支持七天无理由退货，跳过商家确认这个阶段 后台判断的类型是否跟用户选择的一致
		 */
		if (shoppingOrderDO.getIsNoReasonReturn() && shoppingRefundTypeEnum.equals(param.getType())) {
			// 订单是否需要物流
			if (ShoppingRefundTypeEnum.REFUND.equals(shoppingRefundTypeEnum)) {
				shoppingOrderItemUpdateDO.setRefundStatus(RefundStatusEnum.TO_BE_REFUNDED.getValue());
			} else if (ShoppingRefundTypeEnum.RETURN_REFUND.equals(shoppingRefundTypeEnum)) {
				shoppingOrderItemUpdateDO.setRefundStatus(RefundStatusEnum.FILL_RETURN_ADDRESS.getValue());
			}
		} else {
			shoppingOrderItemUpdateDO.setRefundStatus(RefundStatusEnum.TO_BE_CONFIRMED.getValue());
		}
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemUpdateDO);
		ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
		// 保存退货详情记录
		shoppingRefundDetailDO.setShoppingOrderItemId(shoppingOrderItemDO.getId());
		// 计算退款金额
		BigDecimal amount = shoppingOrderItemDO.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemDO.getQuantity()));
		shoppingRefundDetailDO.setStatus(StatusEnum.VALID.getValue());
		shoppingRefundDetailDO.setType(param.getType().getValue());
		shoppingRefundDetailDO.setAmount(amount);
		shoppingRefundDetailDO.setReason(param.getReason());
		shoppingRefundDetailDO.setDescription(param.getDescription());
		if (StringUtils.isNotBlank(param.getVoucherPicture())) {
			shoppingRefundDetailDO.setVoucherPicture(param.getVoucherPicture());
		}
		shoppingRefundDetailDO.setGmtCreate(new Date());
		shoppingRefundDetailDO.setGmtModified(new Date());
		shoppingRefundDetailDOMapper.insertSelective(shoppingRefundDetailDO);
		// 把当前的退款状态加入记录到退款流程
		ShoppingRefundProcessDO shoppingRefundProcessDO = new ShoppingRefundProcessDO();
		shoppingRefundProcessDO.setRefundStatus(shoppingOrderItemDO.getRefundStatus());
		shoppingRefundProcessDO.setGmtCreate(new Date());
		shoppingRefundProcessDO.setShoppingRefundDetailId(shoppingRefundDetailDO.getId());
		shoppingRefundProcessDOMapper.insertSelective(shoppingRefundProcessDO);
		// 用户申请退款，提醒商家处理
		ShoppingRefundToBeConfirmedForRefundRemindNotification notification = new ShoppingRefundToBeConfirmedForRefundRemindNotification();
		notification.setShoppingOrderItemId(shoppingOrderItemDO.getId());
		notification.setMemberNum(shoppingOrderDO.getMemberNum());
		notification.setMerchantNum(shoppingOrderDO.getMerchantNum());
		messageProducerService.sendMessage(MqConstant.TOPIC_ORDER_SRV, MqConstant.TAG_TO_BE_CONFIRMED_FOR_REFUND_REMIND, notification);
	}

	/**
	 * 商家填写物流信息
	 * 
	 * @param id
	 *            购物订单id
	 * @param merchantId
	 *            商家id
	 * @param param
	 *            物流信息参数
	 * @throws DataNotExistException
	 * @throws IllegalOperationException
	 * @throws CanNotFillInShippingLogisticsException
	 * @author jiangxinjun
	 * @date 2017年7月11日
	 */
	@Transactional
	@Override
	public void fillLogisticsInformation(Long id, Long merchantId, ShoppingOrderLogisticsInformationParam param) throws DataNotExistException, IllegalOperationException, CanNotFillInShippingLogisticsException {
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(id);
		if (shoppingOrderDO == null) {
			throw new DataNotExistException(ExceptionMessageConstant.SHOPPING_ORDER_DATA_NOT_EXIST);
		}
		if (!shoppingOrderDO.getMerchantId().equals(merchantId)) {
			throw new IllegalOperationException(ExceptionMessageConstant.ILLEGAL_OPERATION_SHOPPING_ORDER);
		}
		// 只有订单状态为待发货才能填写物流信息
		if (!shoppingOrderDO.getOrderStatus().equals(ShoppingOrderStatusEnum.BE_SHIPPED.getValue())) {
			throw new CanNotFillInShippingLogisticsException(ExceptionMessageConstant.ORDERS_NOT_TO_BE_SHIPPING_STATUS);
		}
		// 更新购物订单的状态
		ShoppingOrderDO shoppingOrderUpdateDO = new ShoppingOrderDO();
		shoppingOrderUpdateDO.setId(shoppingOrderDO.getId());
		shoppingOrderUpdateDO.setGmtTransport(new Date());
		shoppingOrderUpdateDO.setGmtModified(new Date());
		// 更改订单状态为待商家确认
		shoppingOrderUpdateDO.setOrderStatus(ShoppingOrderStatusEnum.TO_BE_RECEIVED.getValue());
		if (param.getIsNeedsLogistics()) {
			// 更新购物订单的物流信息
			shoppingOrderUpdateDO.setExpressCompanyId(param.getExpressCompanyId());
			shoppingOrderUpdateDO.setExpressCompanyCode(param.getExpressCompanyCode());
			shoppingOrderUpdateDO.setExpressCompanyName(param.getExpressCompanyName());
			shoppingOrderUpdateDO.setWaybillNum(param.getWaybillNum());
		}
		shoppingOrderUpdateDO.setIsNeedsLogistics(param.getIsNeedsLogistics());
		shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderUpdateDO);
		// 更新购物订单项状态
		ShoppingOrderItemDOExample shoppingOrderItemDOExample = new ShoppingOrderItemDOExample();
		com.lawu.eshop.order.srv.domain.ShoppingOrderItemDOExample.Criteria shoppingOrderItemDOExampleCriteria = shoppingOrderItemDOExample.createCriteria();
		shoppingOrderItemDOExampleCriteria.andShoppingOrderIdEqualTo(id);
		// 只更新待发货状态的订单项，对于退款状态的订单不予处理
		shoppingOrderItemDOExampleCriteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.BE_SHIPPED.getValue());
		ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
		shoppingOrderItemDO.setGmtModified(new Date());
		shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.TO_BE_RECEIVED.getValue());
		shoppingOrderItemDOMapper.updateByExampleSelective(shoppingOrderItemDO, shoppingOrderItemDOExample);
	}

	/**
	 * 第三方支付时获取订单原始总金额，用于调用第三方支付平台
	 * 
	 * @param orderIds
	 * @return
	 * @author Yangqh
	 */
	@Override
	public ShoppingOrderMoneyBO selectOrderMoney(String orderIds) throws TheOrderIsBeingProcessedException, OrderCreationFailedException {
		String[] orderIdsArray = orderIds.split(",");
		BigDecimal total = new BigDecimal(0);
		for (int i = 0; i < orderIdsArray.length; i++) {
			ShoppingOrderDO orderDO = shoppingOrderDOMapper.selectByPrimaryKey(Long.valueOf(orderIdsArray[i]));
			/*
			 * 判断订单的状态是否是待支付 如果不是，说明扣除库存失败，提示买家库存不足
			 */
			if (ShoppingOrderStatusEnum.PENDING.getValue().equals(orderDO.getOrderStatus())) {
				throw new TheOrderIsBeingProcessedException(ExceptionMessageConstant.THE_ORDER_IS_BEING_PROCESSED);
			}
			if (ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue().equals(orderDO.getOrderStatus())) {
				throw new OrderCreationFailedException(ExceptionMessageConstant.THE_ORDER_IS_BEING_PROCESSED);
			}
			total = total.add(orderDO.getOrderTotalPrice());
		}
		ShoppingOrderMoneyBO rtn = new ShoppingOrderMoneyBO();
		rtn.setOrderTotalPrice(total);
		return rtn;
	}

	/**
	 * 根据查询参数分页查询订单列表
	 * 
	 * @param param
	 *            查询参数
	 * @return
	 */
	@Override
	public Page<ShoppingOrderExtendBO> selectPage(ShoppingOrderQueryForeignToOperatorParam param) {
		ShoppingOrderExtendDOExample shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		// 组装Criteria
		Criteria baseCriteria = shoppingOrderExtendDOExample.createCriteria();

		if (param.getOrderStatus() != null) {
			baseCriteria.andOrderStatusEqualTo(param.getOrderStatus().getValue());
		}

		if (!StringUtils.isEmpty(param.getKeyword())) {
			shoppingOrderExtendDOExample.clear();

			Criteria orderNumCriteria = shoppingOrderExtendDOExample.or();
			orderNumCriteria.andOrderNumEqualTo(param.getKeyword());
			orderNumCriteria.getAllCriteria().addAll(baseCriteria.getAllCriteria());

			Criteria paroductCriteria = shoppingOrderExtendDOExample.or();
			paroductCriteria.andConsigneeNameLike("%" + param.getKeyword() + "%");
			paroductCriteria.getAllCriteria().addAll(baseCriteria.getAllCriteria());
		}

		// 查询总记录数
		Long count = shoppingOrderDOExtendMapper.countByExample(shoppingOrderExtendDOExample);

		Page<ShoppingOrderExtendBO> shoppingOrderItemBOPage = new Page<>();
		shoppingOrderItemBOPage.setTotalCount(count.intValue());
		shoppingOrderItemBOPage.setCurrentPage(param.getCurrentPage());

		// 如果总记录为0，不再执行后续操作直接返回
		if (count <= 0 || param.getOffset() >= count) {
			return shoppingOrderItemBOPage;
		}

		if (param.getSortName() != null && param.getSortOrder() != null) {
			shoppingOrderExtendDOExample.setOrderByClause(param.getSortName().getDatabaseField() + " " + param.getSortOrder().name());
		} else {
			// 默认创建时间排序
			shoppingOrderExtendDOExample.setOrderByClause("so.gmt_create desc");
		}

		// 分页参数
		RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());

		// 如果参数中的keyword有值，查询结果的订单项会缺少，所有先找出所有购物订单id再通过去查找购物订单以及级联的购物订单项
		List<ShoppingOrderExtendDO> shoppingOrderExtendDOList = shoppingOrderDOExtendMapper.selectByExampleWithRowbounds(shoppingOrderExtendDOExample, rowBounds);

		List<Long> shoppingOrderIdList = new ArrayList<>();
		for (ShoppingOrderExtendDO item : shoppingOrderExtendDOList) {
			shoppingOrderIdList.add(item.getId());
		}

		shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		shoppingOrderExtendDOExample.setIncludeViewShoppingOrderItem(true);
		shoppingOrderExtendDOExample.setIncludeShoppingOrderItem(true);
		shoppingOrderExtendDOExample.createCriteria().andIdIn(shoppingOrderIdList);

		if (param.getSortName() != null && param.getSortOrder() != null) {
			shoppingOrderExtendDOExample.setOrderByClause(param.getSortName().getDatabaseField() + " " + param.getSortOrder().name());
		} else {
			// 默认创建时间排序
			shoppingOrderExtendDOExample.setOrderByClause("so.gmt_create desc");
		}

		shoppingOrderExtendDOList = shoppingOrderDOExtendMapper.selectByExample(shoppingOrderExtendDOExample);

		shoppingOrderItemBOPage.setRecords(ShoppingOrderExtendConverter.convertShoppingOrderExtendBO(shoppingOrderExtendDOList));

		return shoppingOrderItemBOPage;
	}

	/**
	 * 减少产品库存成功回调 更改订单的状态为待支付状态 删除对应的购物车记录
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 * @author Sunny
	 */
	@Transactional
	@Override
	public void minusInventorySuccess(Long id, ShoppingOrderCreateOrderReply reply) {
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(id);
		
		ShoppingOrderDO shoppingOrderDOUpdate = new ShoppingOrderDO();
		shoppingOrderDOUpdate.setId(shoppingOrderDO.getId());

		// 设置购物订单项为待支付状态
		ShoppingOrderItemDOExample shoppingOrderItemDOExample = new ShoppingOrderItemDOExample();
		ShoppingOrderItemDOExample.Criteria shoppingOrderItemDOExampleCriteria = shoppingOrderItemDOExample.createCriteria();
		shoppingOrderItemDOExampleCriteria.andShoppingOrderIdEqualTo(id);
		ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();

		if (reply.getResult() == null) {
			// 设置订单状态为待支付状态(局部更新)
			shoppingOrderDOUpdate.setOrderStatus(ShoppingOrderStatusEnum.PENDING_PAYMENT.getValue());

			// 设置购物订单项为待支付状态
			shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.PENDING_PAYMENT.getValue());

			/*
			 * 删除购物车记录 如果用户是点击立即购物，是不经过购物车的，不需要删除购物车记录
			 */
			if (!StringUtils.isEmpty(shoppingOrderDO.getShoppingCartIdsStr())) {
				// 拆分购物车id
				String[] shoppingCartIdStrAry = StringUtils.split(shoppingOrderDO.getShoppingCartIdsStr(), ",");
				List<Long> shoppingCartIdList = new ArrayList<>();
				for (String shoppingCartIdStr : shoppingCartIdStrAry) {
					shoppingCartIdList.add(Long.valueOf(shoppingCartIdStr));
				}

				ShoppingCartDOExample shoppingCartDOExample = new ShoppingCartDOExample();
				shoppingCartDOExample.createCriteria().andIdIn(shoppingCartIdList);
				shoppingCartDOMapper.deleteByExample(shoppingCartDOExample);
			}
		} else {
			// 设置订单状态为交易关闭(局部更新)
			shoppingOrderDOUpdate.setRemark(reply.getResult().getDescription());
			shoppingOrderDOUpdate.setOrderStatus(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue());

			// 设置购物订单项为交易关闭状态
			shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue());
		}

		shoppingOrderDOUpdate.setGmtModified(new Date());
		shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderDOUpdate);

		shoppingOrderItemDO.setGmtModified(new Date());
		shoppingOrderItemDOMapper.updateByExampleSelective(shoppingOrderItemDO, shoppingOrderItemDOExample);

	}

	/**
	 * 更新订单信息
	 * 
	 * @param id
	 *            购物订单id
	 * @param param
	 *            查询参数
	 */
	@Transactional
	@Override
	public void updateInformation(Long id, ShoppingOrderUpdateInfomationParam param) {
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(id);
		if (shoppingOrderDO == null || shoppingOrderDO.getStatus().equals(StatusEnum.INVALID)) {
			throw new DataNotExistException(ExceptionMessageConstant.SHOPPING_ORDER_DATA_NOT_EXIST);
		}
		shoppingOrderDO = ShoppingOrderConverter.convert(id, param);
		shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderDO);
	}

	/**
	 * 自动评论超时未评论的订单项
	 */
	@Override
	public void executetAutoComment() {
		ShoppingOrderItemExtendDOExample shoppingOrderItemExtendDOExample = new ShoppingOrderItemExtendDOExample();
		shoppingOrderItemExtendDOExample.setIsIncludeShoppingOrder(true);
		ShoppingOrderItemExtendDOExample.Criteria shoppingOrderItemExtendDOExampleCriteria = shoppingOrderItemExtendDOExample.createCriteria();
		shoppingOrderItemExtendDOExampleCriteria.andIsEvaluationEqualTo(false);
		shoppingOrderItemExtendDOExampleCriteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());

		// 查找配置表获取自动好评时间
		String automaticEvaluation = propertyService.getByName(PropertyNameConstant.AUTOMATIC_EVALUATION);

		// 如果交易时间超过automaticEvaluation的记录
		shoppingOrderItemExtendDOExampleCriteria.andSOGmtTransactionDateAddDayLessThanOrEqualTo(Integer.valueOf(automaticEvaluation), new Date());

		List<ShoppingOrderItemExtendDO> shoppingOrderItemExtendDOList = shoppingOrderItemExtendDOMapper.selectByExample(shoppingOrderItemExtendDOExample);

		for (ShoppingOrderItemExtendDO shoppingOrderItemExtendDO : shoppingOrderItemExtendDOList) {
			// 将事务拆解成单个事务
			commentShoppingOrder(shoppingOrderItemExtendDO.getId());
		}
	}

	/**
	 * 根据商家的id查询商家是否有进行中的订单
	 * 
	 * @param merchantId
	 *            商家的id
	 * @return
	 * @author Sunny
	 */
	@Override
	public ShoppingOrderIsNoOnGoingOrderBO isNoOnGoingOrder(Long merchantId) {
		ShoppingOrderExtendDOExample shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		shoppingOrderExtendDOExample.setIncludeShoppingOrderItem(true);
		ShoppingOrderExtendDOExample.Criteria shoppingOrderExtendDOExampleCriteria = shoppingOrderExtendDOExample.createCriteria();
		shoppingOrderExtendDOExampleCriteria.andMerchantIdEqualTo(merchantId);

		/*
		 * 查找订单项状态不为交易取消和交易成功的数目
		 */
		List<Byte> processingStatus = new ArrayList<>();
		processingStatus.add(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
		processingStatus.add(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue());

		shoppingOrderExtendDOExampleCriteria.andSOIOrderStatusNotIn(processingStatus);

		long count = shoppingOrderDOExtendMapper.countByExample(shoppingOrderExtendDOExample);

		return ShoppingOrderConverter.convert(count);
	}

	/**
	 * 根据购物订单项查询订单以及订单项
	 * 
	 * @param ShoppingOrderItemId
	 *            购物订单项id
	 * @param isAll
	 *            是否查找全部的订单项
	 * @return
	 * @author Sunny
	 */
	@Override
	public ShoppingOrderExtendBO getByShoppingOrderItemId(Long shoppingOrderItemId) {
		ShoppingOrderExtendBO rtn = null;
		ShoppingOrderExtendDOExample shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		shoppingOrderExtendDOExample.setIncludeShoppingOrderItem(true);
		shoppingOrderExtendDOExample.setIncludeViewShoppingOrderItem(true);
		shoppingOrderExtendDOExample.createCriteria().andSOIIdEqualTo(shoppingOrderItemId);
		List<ShoppingOrderExtendDO> shoppingOrderExtendDOList = shoppingOrderDOExtendMapper.selectByExample(shoppingOrderExtendDOExample);
		if (shoppingOrderExtendDOList == null || shoppingOrderExtendDOList.isEmpty()) {
			return rtn;
		}
		rtn = ShoppingOrderExtendConverter.convertShoppingOrderExtendDetailBO(shoppingOrderExtendDOList.get(0));
		return rtn;
	}

	/**
	 * 自动取消未付款的订单
	 * 
	 * @author Sunny
	 */
	@Override
	public void executeAutoCancelOrder() {
		ShoppingOrderExtendDOExample shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		shoppingOrderExtendDOExample.setIncludeViewShoppingOrderItem(false);
		ShoppingOrderExtendDOExample.Criteria criteria = shoppingOrderExtendDOExample.createCriteria();

		String automaticCancelOrderTime = propertyService.getByName(PropertyNameConstant.AUTOMATIC_CANCEL_ORDER);

		String automaticRemindNoPaymentOrderTime = propertyService.getByName(PropertyNameConstant.AUTOMATIC_REMIND_NO_PAYMENT_ORDER);

		criteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.PENDING_PAYMENT.getValue());
		criteria.andGmtCreateDateAddDayLessThanOrEqualTo(Integer.valueOf(automaticRemindNoPaymentOrderTime), new Date());

		// 查找所有超时未付款的订单
		List<ShoppingOrderExtendDO> shoppingOrderDOList = shoppingOrderDOExtendMapper.selectByExample(shoppingOrderExtendDOExample);

		for (ShoppingOrderExtendDO item : shoppingOrderDOList) {
			// 首先判断是否已经发送过提醒消息，如果没有超过，再判断是否超过付款时间
			if (item.getSendTime() <= 0) {
				// 更新发送次数，但是不更新更新时间字段
				int sendTime = item.getSendTime() == null ? 1 : item.getSendTime().intValue() + 1;
				item.setSendTime(sendTime);
				shoppingOrderDOMapper.updateByPrimaryKeySelective(item);

				/*
				 * 买家支付成功发送消息给商家提醒买家新增了一个订单
				 */
				// 组装要发送的消息
				ShoppingOrderNoPaymentNotification shoppingOrderNoPaymentNotification = new ShoppingOrderNoPaymentNotification();
				shoppingOrderNoPaymentNotification.setId(item.getId());
				shoppingOrderNoPaymentNotification.setMemberNum(item.getMemberNum());
				shoppingOrderNoPaymentNotification.setOrderNum(item.getOrderNum());
				// 发送消MQ息
				messageProducerService.sendMessage(MqConstant.TOPIC_ORDER_SRV, MqConstant.TAG_ORDER_NO_PAYMENT_PUSH_TO_MEMBER, shoppingOrderNoPaymentNotification);
				continue;
			}

			boolean isExceeds = DateUtil.isExceeds(item.getGmtCreate(), new Date(), Integer.valueOf(automaticCancelOrderTime), Calendar.DAY_OF_YEAR);
			if (isExceeds) {
				cancelOrder(null, item.getId());
			}
		}
	}

	/**
	 * 自动提醒发货
	 * 
	 * @author Sunny
	 */
	@Override
	public void executeAutoRemindShipments() {
		String automaticRemindShipments = propertyService.getByName(PropertyNameConstant.AUTOMATIC_REMIND_SHIPMENTS);

		// 查找所有超时未发货的订单，提醒卖家发货
		List<NotShippedDO> notShippedDOList = shoppingOrderDOExtendMapper.selectByNotShipped(Integer.valueOf(automaticRemindShipments));

		for (NotShippedDO item : notShippedDOList) {
			// 发送站内信和推送
			ShoppingOrderRemindShipmentsNotification notification = new ShoppingOrderRemindShipmentsNotification();
			notification.setQuantity(item.getCount());
			notification.setMerchantNum(item.getMerchantNum());
			messageProducerService.sendMessage(MqConstant.TOPIC_ORDER_SRV, MqConstant.TAG_REMIND_SHIPMENTS, notification);
		}
	}

	/**
	 * 自动收货
	 * 
	 * @author Sunny
	 */
	@Override
	public void executeAutoReceipt() {
		ShoppingOrderExtendDOExample shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		shoppingOrderExtendDOExample.setIncludeShoppingOrderItem(true);
		shoppingOrderExtendDOExample.setIncludeViewShoppingOrderItem(true);
		ShoppingOrderExtendDOExample.Criteria criteria = shoppingOrderExtendDOExample.createCriteria();

		String automaticReceiptTime = propertyService.getByName(PropertyNameConstant.AUTOMATIC_RECEIPT);

		criteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.TO_BE_RECEIVED.getValue());
		criteria.andGmtTransportAddDayLessThanOrEqualTo(Integer.valueOf(automaticReceiptTime), new Date());

		// 查找所有超时未收货的订单，自动收货
		List<ShoppingOrderExtendDO> shoppingOrderDOList = shoppingOrderDOExtendMapper.selectByExample(shoppingOrderExtendDOExample);

		for (ShoppingOrderExtendDO item : shoppingOrderDOList) {
			boolean isDone = true;
			// 判断订单下的所有订单项是否有正在退款中的
			for (ShoppingOrderItemDO shoppingOrderItemDO : item.getItems()) {
				if (ShoppingOrderStatusEnum.REFUNDING.getValue().equals(shoppingOrderItemDO.getOrderStatus())) {
					isDone = false;
					break;
				}
			}

			if (isDone) {
				tradingSuccess(item.getId(), true);
			}
		}
	}

	/**
	 * 查询各种订单状态的数量
	 * 
	 * @param memberId
	 *            会员id
	 * @return
	 * @author Sunny
	 */
	@Override
	public ShoppingOrderNumberOfOrderStatusBO numberOfOrderStartus(Long memberId) {
		ShoppingOrderNumberOfOrderStatusBO rtn = new ShoppingOrderNumberOfOrderStatusBO();

		// 查询待付款订单的数量
		ShoppingOrderDOExample shoppingOrderDOExample = new ShoppingOrderDOExample();
		ShoppingOrderDOExample.Criteria shoppingOrderDOExampleCriteria = shoppingOrderDOExample.createCriteria();
		shoppingOrderDOExampleCriteria.andMemberIdEqualTo(memberId);
		shoppingOrderDOExampleCriteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.PENDING_PAYMENT.getValue());
		long pendingPaymentCount = shoppingOrderDOMapper.countByExample(shoppingOrderDOExample);

		// 查询待发货数量
		shoppingOrderDOExample = new ShoppingOrderDOExample();
		shoppingOrderDOExampleCriteria = shoppingOrderDOExample.createCriteria();
		shoppingOrderDOExampleCriteria.andMemberIdEqualTo(memberId);
		shoppingOrderDOExampleCriteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.BE_SHIPPED.getValue());
		long beShippedCount = shoppingOrderDOMapper.countByExample(shoppingOrderDOExample);

		// 查询待收货数量
		shoppingOrderDOExample = new ShoppingOrderDOExample();
		shoppingOrderDOExampleCriteria = shoppingOrderDOExample.createCriteria();
		shoppingOrderDOExampleCriteria.andMemberIdEqualTo(memberId);
		shoppingOrderDOExampleCriteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.TO_BE_RECEIVED.getValue());
		long toBeReceivedCount = shoppingOrderDOMapper.countByExample(shoppingOrderDOExample);

		// 查询待评价数量
		ShoppingOrderItemExtendDOExample shoppingOrderItemExtendDOExample = new ShoppingOrderItemExtendDOExample();
		shoppingOrderItemExtendDOExample.setIsIncludeShoppingOrder(true);
		ShoppingOrderItemExtendDOExample.Criteria shoppingOrderItemExtendDOExampleCriteria = shoppingOrderItemExtendDOExample.createCriteria();
		shoppingOrderItemExtendDOExampleCriteria.andSOMemberIdEqualTo(memberId);
		// 订单状态为交易成功并且未评价
		shoppingOrderItemExtendDOExampleCriteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
		shoppingOrderItemExtendDOExampleCriteria.andIsEvaluationEqualTo(false);
		long evaluationCount = shoppingOrderItemExtendDOMapper.countByExample(shoppingOrderItemExtendDOExample);

		// 查询退货中数量
		shoppingOrderItemExtendDOExample = new ShoppingOrderItemExtendDOExample();
		shoppingOrderItemExtendDOExample.setIsIncludeShoppingOrder(true);
		shoppingOrderItemExtendDOExampleCriteria = shoppingOrderItemExtendDOExample.createCriteria();
		shoppingOrderItemExtendDOExampleCriteria.andSOMemberIdEqualTo(memberId);
		shoppingOrderItemExtendDOExampleCriteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.REFUNDING.getValue());
		// 用户可以多次申请退款，查询当中有效的一条记录
		long refundingCount = shoppingOrderItemExtendDOMapper.countByExample(shoppingOrderItemExtendDOExample);

		rtn.setPendingPaymentCount(pendingPaymentCount);
		rtn.setBeShippedCount(beShippedCount);
		rtn.setToBeReceivedCount(toBeReceivedCount);
		rtn.setEvaluationCount(evaluationCount);
		rtn.setRefundingCount(refundingCount);
		return rtn;
	}

	/**
	 * 查询未计算提成订单
	 * 
	 * @return
	 * @author Sunny
	 */
	@Override
	public List<ShoppingOrderBO> commissionShoppingOrder() {
		/*
		 * 查找未计算过提成的订单
		 */
		ShoppingOrderDOExample shoppingOrderDOExample = new ShoppingOrderDOExample();
		ShoppingOrderDOExample.Criteria shoppingOrderDOExampleCriteria = shoppingOrderDOExample.createCriteria();
		// 订单状态是交易成功的
		shoppingOrderDOExampleCriteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
		// 没有计算过提成的
		shoppingOrderDOExampleCriteria.andCommissionStatusEqualTo(CommissionStatusEnum.NOT_COUNTED.getValue());
		// 订单已经完成，钱已经打给商家
		shoppingOrderDOExampleCriteria.andIsDoneEqualTo(true);

		List<ShoppingOrderDO> shoppingOrderDOList = shoppingOrderDOMapper.selectByExample(shoppingOrderDOExample);

		return ShoppingOrderConverter.convert(shoppingOrderDOList);
	}

	/**
	 * 根据订单id更新购物订单的提成状态和提成时间
	 * 
	 * @param ids
	 *            购物订单id集合
	 * @return
	 * @author Sunny
	 */
	@Transactional
	@Override
	public void updateCommissionStatus(List<Long> ids) {
		ShoppingOrderDOExample shoppingOrderDOExample = new ShoppingOrderDOExample();
		shoppingOrderDOExample.createCriteria().andIdIn(ids);
		ShoppingOrderDO shoppingOrderDO = new ShoppingOrderDO();
		// 更新提成状态和提成时间
		shoppingOrderDO.setGmtCommission(new Date());
		shoppingOrderDO.setCommissionStatus(CommissionStatusEnum.CALCULATED.getValue());
		shoppingOrderDOMapper.updateByExampleSelective(shoppingOrderDO, shoppingOrderDOExample);
	}

	@Override
	public ShoppingOrderNumberOfOrderStatusForMerchantBO numberOfOrderStartusByMerchant(Long merchantId) {
		ShoppingOrderNumberOfOrderStatusForMerchantBO rtn = new ShoppingOrderNumberOfOrderStatusForMerchantBO();

		// 查询待收货订单的数量
		ShoppingOrderDOExample shoppingOrderDOExample = new ShoppingOrderDOExample();
		ShoppingOrderDOExample.Criteria shoppingOrderDOExampleCriteria = shoppingOrderDOExample.createCriteria();
		shoppingOrderDOExampleCriteria.andMerchantIdEqualTo(merchantId);
		shoppingOrderDOExampleCriteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.PENDING_PAYMENT.getValue());
		long pendingPaymentCount = shoppingOrderDOMapper.countByExample(shoppingOrderDOExample);

		// 查询待发货数量
		shoppingOrderDOExample = new ShoppingOrderDOExample();
		shoppingOrderDOExampleCriteria = shoppingOrderDOExample.createCriteria();
		shoppingOrderDOExampleCriteria.andMerchantIdEqualTo(merchantId);
		shoppingOrderDOExampleCriteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.BE_SHIPPED.getValue());
		long beShippedCount = shoppingOrderDOMapper.countByExample(shoppingOrderDOExample);

		// 查询待收货数量
		shoppingOrderDOExample = new ShoppingOrderDOExample();
		shoppingOrderDOExampleCriteria = shoppingOrderDOExample.createCriteria();
		shoppingOrderDOExampleCriteria.andMerchantIdEqualTo(merchantId);
		shoppingOrderDOExampleCriteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.TO_BE_RECEIVED.getValue());
		long toBeReceivedCount = shoppingOrderDOMapper.countByExample(shoppingOrderDOExample);

		// 查询退货中数量
		ShoppingOrderItemExtendDOExample shoppingOrderItemExtendDOExample = new ShoppingOrderItemExtendDOExample();
		shoppingOrderItemExtendDOExample.setIsIncludeShoppingOrder(true);
		ShoppingOrderItemExtendDOExample.Criteria shoppingOrderItemExtendDOExampleCriteria = shoppingOrderItemExtendDOExample.createCriteria();
		shoppingOrderItemExtendDOExampleCriteria.andSOMerchantIdEqualTo(merchantId);
		shoppingOrderItemExtendDOExampleCriteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.REFUNDING.getValue());
		long refundingCount = shoppingOrderItemExtendDOMapper.countByExample(shoppingOrderItemExtendDOExample);

		rtn.setPendingPaymentCount(pendingPaymentCount);
		rtn.setBeShippedCount(beShippedCount);
		rtn.setToBeReceivedCount(toBeReceivedCount);
		rtn.setRefundingCount(refundingCount);
		return rtn;
	}

	/**
	 * 统计商家的交易数据
	 * 
	 * @param merchantId
	 *            商家id
	 * @return
	 * @author Sunny
	 */
	@Override
	public ReportRiseRateDTO selectByTransactionData(ReportDataParam param) {
		int x = 0;
		ShoppingOrderReportDataParam shoppingOrderReportDataParam = new ShoppingOrderReportDataParam();
		shoppingOrderReportDataParam.setMerchantId(param.getMerchantId());

		if (ReportFansRiseRateEnum.DAY.getValue().equals(param.getFlag().getValue())) {
			shoppingOrderReportDataParam.setFlag(ReportFansRiseRateEnum.DAY.getValue());
			shoppingOrderReportDataParam.setGmtCreate(DateUtil.getDateFormat(new Date(), "yyyyMM"));
			x = DateUtil.getNowMonthDay();
		} else if (ReportFansRiseRateEnum.MONTH.getValue().equals(param.getFlag().getValue())) {
			shoppingOrderReportDataParam.setFlag(ReportFansRiseRateEnum.MONTH.getValue());
			shoppingOrderReportDataParam.setGmtCreate(DateUtil.getDateFormat(new Date(), "yyyy"));
			x = 12;
		}

		List<ReportRiseRateView> list = shoppingOrderDOExtendMapper.selectByTransactionData(shoppingOrderReportDataParam);
		// 总金额
		BigDecimal total = shoppingOrderDOExtendMapper.selectByTransactionTotalAmount(shoppingOrderReportDataParam);

		ReportRiseRateDTO rtn = ReportConvert.reportBrokeLineShow(list, x);
		rtn.setTotal(total == null ? "0" : total.toString());
		return rtn;
	}

	/**
	 * 粉丝数据-消费转化
	 * 
	 * @param param
	 * @return
	 * @author Sunny
	 */
	@Override
	public List<ReportRiseRerouceDTO> fansSaleTransform(ReportDataParam param) {
		ShoppingOrderReportDataParam shoppingOrderReportDataParam = new ShoppingOrderReportDataParam();
		shoppingOrderReportDataParam.setMerchantId(param.getMerchantId());

		if (ReportFansRiseRateEnum.DAY.getValue().equals(param.getFlag().getValue())) {
			shoppingOrderReportDataParam.setFlag(ReportFansRiseRateEnum.DAY.getValue());
			shoppingOrderReportDataParam.setGmtCreate(DateUtil.getDateFormat(new Date(), "yyyyMM"));
		} else if (ReportFansRiseRateEnum.MONTH.getValue().equals(param.getFlag().getValue())) {
			shoppingOrderReportDataParam.setFlag(ReportFansRiseRateEnum.MONTH.getValue());
			shoppingOrderReportDataParam.setGmtCreate(DateUtil.getDateFormat(new Date(), "yyyy"));
		}

		List<ReportFansSaleTransFormDO> reportFansSaleTransFormDOList = shoppingOrderDOExtendMapper.selectByFansSaleTransForm(shoppingOrderReportDataParam);

		return ShoppingOrderConverter.convertReportRiseRerouceDTOList(reportFansSaleTransFormDOList);
	}

	/**
	 * 订单收货之后 如果超过退款申请时间，直接付款给商家 如果订单中正在退款的商品，跳过不处理 更新订单项不允许退款
	 * 
	 * @author Sunny
	 */
	@Override
	public void executeAutoPaymentsToMerchant() {
		ShoppingOrderExtendDOExample shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		shoppingOrderExtendDOExample.setIncludeShoppingOrderItem(true);
		shoppingOrderExtendDOExample.setIncludeViewShoppingOrderItem(true);
		ShoppingOrderExtendDOExample.Criteria criteria = shoppingOrderExtendDOExample.createCriteria();

		String refundRequestTime = propertyService.getByName(PropertyNameConstant.REFUND_REQUEST_TIME);

		// 查找订单以及订单项状态都为交易成功的订单
		criteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
		criteria.andIsDoneEqualTo(false);
		criteria.andGmtTransactionAddDayLessThanOrEqualTo(Integer.valueOf(refundRequestTime), new Date());

		List<ShoppingOrderExtendDO> shoppingOrderDOList = shoppingOrderDOExtendMapper.selectByExample(shoppingOrderExtendDOExample);

		logger.info("需要释放冻结资金的订单数量:{}", shoppingOrderDOList.size());

		for (ShoppingOrderExtendDO item : shoppingOrderDOList) {
			boolean isDone = true;
			// 判断订单下的所有订单项是否有正在退款中的
			for (ShoppingOrderItemDO shoppingOrderItemDO : item.getItems()) {
				if (ShoppingOrderStatusEnum.REFUNDING.getValue().equals(shoppingOrderItemDO.getOrderStatus())) {
					isDone = false;
					break;
				}
			}

			if (isDone) {
				paymentsToMerchant(item);
			}
		}
	}

	/**************************************************************
	 * PRIVATE METHOD
	 **************************************************************/
	/**
	 * 评论商品订单
	 * 
	 * @author Sunny
	 */
	@Transactional
	public void commentShoppingOrder(Long shoppingOrderItemId) {
		// 更新为已评论
		ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
		shoppingOrderItemDO.setId(shoppingOrderItemId);
		shoppingOrderItemDO.setIsEvaluation(true);
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemDO);

		// 发送MQ消息，通知mall模块添加默认好评记录
		shoppingOrderAutoCommentTransactionMainServiceImpl.sendNotice(shoppingOrderItemId);
	}

	/**
	 * 更改订单状态为完成 释放冻结资金
	 * 
	 * @author Sunny
	 */
	@Transactional
	public void paymentsToMerchant(ShoppingOrderExtendDO shoppingOrderExtendDO) {
		// 更新订单状态为完成
		ShoppingOrderDO update = new ShoppingOrderDO();
		update.setSendTime(0);
		update.setId(shoppingOrderExtendDO.getId());
		update.setIsDone(true);
		update.setGmtModified(new Date());
		shoppingOrderDOMapper.updateByPrimaryKeySelective(update);

		// 发送提醒消息
		ordersTradingIncomeNotice(shoppingOrderExtendDO.getId(), shoppingOrderExtendDO.getActualAmount(), shoppingOrderExtendDO.getMerchantNum());

		// 释放冻结资金
		shoppingOrderPaymentsToMerchantTransactionMainServiceImpl.sendNotice(shoppingOrderExtendDO.getId());
	}

	/**
	 * 提醒商家新增订单交易收入
	 * 
	 * @param shoppingOrderId
	 * @author Sunny
	 * @date 2017年6月8日
	 */
	private void ordersTradingIncomeNotice(Long shoppingOrderId, BigDecimal actualAmount, String merchantNum) {
		ShoppingOrderOrdersTradingIncomeNoticeNotification notification = new ShoppingOrderOrdersTradingIncomeNoticeNotification();
		notification.setShoppingOrderId(shoppingOrderId);
		notification.setActualAmount(actualAmount);
		notification.setMerchantNum(merchantNum);
		messageProducerService.sendMessage(MqConstant.TOPIC_ORDER_SRV, MqConstant.TAG_ORDERS_TRADING_INCOME_NOTICE, notification);
	}
}
