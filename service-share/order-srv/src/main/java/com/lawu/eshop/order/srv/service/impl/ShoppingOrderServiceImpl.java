package com.lawu.eshop.order.srv.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mq.dto.property.ShoppingOrderPaymentNotification;
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
import com.lawu.eshop.order.param.ShoppingOrderSettlementItemParam;
import com.lawu.eshop.order.param.ShoppingOrderSettlementParam;
import com.lawu.eshop.order.param.ShoppingOrderUpdateInfomationParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderQueryForeignToMemberParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderQueryForeignToMerchantParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderQueryForeignToOperatorParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderRequestRefundForeignParam;
import com.lawu.eshop.order.srv.bo.ShoppingOrderBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderExtendBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderIsNoOnGoingOrderBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderNumberOfOrderStatusBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderNumberOfOrderStatusForMerchantBO;
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
import com.lawu.eshop.order.srv.domain.extend.ReportFansSaleTransFormDO;
import com.lawu.eshop.order.srv.domain.extend.ReportRiseRateView;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderExtendDO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderExtendDOExample;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderExtendDOExample.Criteria;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderItemExtendDOExample;
import com.lawu.eshop.order.srv.mapper.ShoppingCartDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderItemDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingRefundDetailDOMapper;
import com.lawu.eshop.order.srv.mapper.extend.ShoppingOrderExtendDOMapper;
import com.lawu.eshop.order.srv.mapper.extend.ShoppingOrderItemExtendDOMapper;
import com.lawu.eshop.order.srv.service.PropertyService;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;
import com.lawu.eshop.utils.DateUtil;

@Service
public class ShoppingOrderServiceImpl implements ShoppingOrderService {
	
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
	private PropertyService propertyService;
	
	@Autowired
	@Qualifier("shoppingOrderTradingSuccessTransactionMainServiceImpl")
	private TransactionMainService<Reply> shoppingOrderTradingSuccessTransactionMainServiceImpl;
	
	@Autowired
    @Qualifier("shoppingOrderCreateOrderTransactionMainServiceImpl")
    private TransactionMainService<Reply> shoppingOrderCreateOrderTransactionMainServiceImpl;
	
	@Autowired
    @Qualifier("shoppingOrderCancelOrderTransactionMainServiceImpl")
    private TransactionMainService<Reply> shoppingOrderCancelOrderTransactionMainServiceImpl;
	
    @Autowired
    @Qualifier("shoppingOrderAutoCommentTransactionMainServiceImpl")
    private TransactionMainService<Reply> shoppingOrderAutoCommentTransactionMainServiceImpl;
    
    @Autowired
    @Qualifier("shoppingOrderRemindShipmentsTransactionMainServiceImpl")
    private TransactionMainService<Reply> shoppingOrderRemindShipmentsTransactionMainServiceImpl;
    
    @Autowired
    @Qualifier("shoppingOrderCreateOrderFansTransactionMainServiceImpl")
    private TransactionMainService<Reply> shoppingOrderCreateOrderFansTransactionMainServiceImpl;
	
	/**
	 * 
	 * @param params 多个订单参数
	 * @return 返回保存的订单id
	 */
	@Transactional
	@Override
	public List<Long> save(List<ShoppingOrderSettlementParam> params) {
		
		List<Long> rtn = new ArrayList<Long>();
		
		// 插入订单
		for (ShoppingOrderSettlementParam shoppingOrderSettlementParam : params) {
			ShoppingOrderDO shoppingOrderDO =  ShoppingOrderConverter.convert(shoppingOrderSettlementParam);
			
			List<Long> shoppingCartIdList = new ArrayList<Long>();
			for (ShoppingOrderSettlementItemParam item : shoppingOrderSettlementParam.getItems()) {
				shoppingCartIdList.add(item.getShoppingCartId());
			}
			
			// 把购物车id用逗号分隔保存在购物订单表中，用于删除购物车记录
			shoppingOrderDO.setShoppingCartIdsStr(StringUtils.join(shoppingCartIdList, ","));
			
			shoppingOrderDOMapper.insertSelective(shoppingOrderDO);
			Long id = shoppingOrderDO.getId();
			
			//插入订单项
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
				
				// 事务补偿(用户成为商家粉丝)
				shoppingOrderCreateOrderFansTransactionMainServiceImpl.sendNotice(id);
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
		baseCriteria.andStatusEqualTo((byte)0x01);
		
		if (memberId != null) {
			baseCriteria.andMemberIdEqualTo(memberId);
		}
		
		if (param.getOrderStatus() != null) {
			baseCriteria.andOrderStatusEqualTo(param.getOrderStatus().getValue());
			
			// 查找待评价的订单
			if (param.getOrderStatus().equals(ShoppingOrderStatusToMemberEnum.BE_EVALUATED)) {
				baseCriteria.andSOIIsEvaluationEqualTo(false);
			}
		} else {
			//如果查询全部状态的订单,不显示待处理的订单
			baseCriteria.andOrderStatusNotEqualTo(ShoppingOrderStatusEnum.PENDING.getValue());
		}
		
		if (!StringUtils.isEmpty(param.getKeyword())) {
			shoppingOrderExtendDOExample.clear();
			
			Criteria orderNumCriteria = shoppingOrderExtendDOExample.or();
			orderNumCriteria.andOrderNumEqualTo(param.getKeyword());
			orderNumCriteria.getAllCriteria().addAll(baseCriteria.getAllCriteria());
			
			Criteria paroductCriteria = shoppingOrderExtendDOExample.or();
			paroductCriteria.andSOIProductNameLike("%"+ param.getKeyword() + "%");
			paroductCriteria.getAllCriteria().addAll(baseCriteria.getAllCriteria());
		}
		
		// 过滤重复记录
		shoppingOrderExtendDOExample.setDistinct(true);
		
		// 查询总记录数
		Long count = shoppingOrderDOExtendMapper.countByExample(shoppingOrderExtendDOExample);
		
		Page<ShoppingOrderExtendBO> shoppingOrderItemBOPage = new Page<ShoppingOrderExtendBO>();
		shoppingOrderItemBOPage.setTotalCount(count.intValue());
		shoppingOrderItemBOPage.setCurrentPage(param.getCurrentPage());
		
		// 如果总记录为0，不再执行后续操作直接返回
		if (count == null || count <= 0) {
			return shoppingOrderItemBOPage;
		}
		
		// 分页参数
		RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
		
		// 默认创建时间排序
		shoppingOrderExtendDOExample.setOrderByClause("so.gmt_create desc");
		
		// 如果参数中的keyword有值，查询结果的订单项会缺少，所有先找出所有购物订单id再通过去查找购物订单以及级联的购物订单项
		List<ShoppingOrderExtendDO> shoppingOrderExtendDOList = shoppingOrderDOExtendMapper.selectByExampleWithRowbounds(shoppingOrderExtendDOExample, rowBounds);
		
		List<Long> shoppingOrderIdList = new ArrayList<Long>();
		for (ShoppingOrderExtendDO item : shoppingOrderExtendDOList) {
			shoppingOrderIdList.add(item.getId());
		}
		
		shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		shoppingOrderExtendDOExample.setIncludeViewShoppingOrderItem(true);
		shoppingOrderExtendDOExample.setIncludeShoppingOrderItem(true);
		shoppingOrderExtendDOExample.createCriteria().andIdIn(shoppingOrderIdList);
		
		// 默认创建时间排序
		shoppingOrderExtendDOExample.setOrderByClause("so.gmt_create desc");
		
		shoppingOrderExtendDOList = shoppingOrderDOExtendMapper.selectByExampleWithRowbounds(shoppingOrderExtendDOExample, rowBounds);
		
		shoppingOrderItemBOPage.setRecords(ShoppingOrderExtendConverter.convertShoppingOrderExtendBO(shoppingOrderExtendDOList));
		
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
		} else {
			//如果查询全部状态的订单,不显示待处理的订单
			baseCriteria.andOrderStatusNotEqualTo(ShoppingOrderStatusEnum.PENDING.getValue());
		}
		
		if (!StringUtils.isEmpty(param.getKeyword())) {
			shoppingOrderExtendDOExample.clear();
			
			Criteria orderNumCriteria = shoppingOrderExtendDOExample.or();
			orderNumCriteria.andOrderNumEqualTo(param.getKeyword());
			orderNumCriteria.getAllCriteria().addAll(baseCriteria.getAllCriteria());
			
			Criteria paroductCriteria = shoppingOrderExtendDOExample.or();
			paroductCriteria.andConsigneeNameLike("%"+ param.getKeyword() + "%");
			paroductCriteria.getAllCriteria().addAll(baseCriteria.getAllCriteria());
		}
		
		// 查询总记录数
		Long count = shoppingOrderDOExtendMapper.countByExample(shoppingOrderExtendDOExample);
		
		Page<ShoppingOrderExtendBO> shoppingOrderItemBOPage = new Page<ShoppingOrderExtendBO>();
		shoppingOrderItemBOPage.setTotalCount(count.intValue());
		shoppingOrderItemBOPage.setCurrentPage(param.getCurrentPage());
		// 初始一条空记录
		shoppingOrderItemBOPage.setRecords(new ArrayList<ShoppingOrderExtendBO>());
		
		/*
		 *  如果count为0，或者offset大于count
		 *  不再执行后续操作直接返回
		 */
		if (count == null || count <= 0 || param.getOffset() >= count) {
			return shoppingOrderItemBOPage;
		}
		
		// 分页参数
		RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
		
		// 默认创建时间排序
		shoppingOrderExtendDOExample.setOrderByClause("so.gmt_create desc");
		
		// 如果参数中的keyword有值，查询结果的订单项会缺少，所有先找出所有购物订单id再通过去查找购物订单以及级联的购物订单项
		List<ShoppingOrderExtendDO> shoppingOrderExtendDOList = shoppingOrderDOExtendMapper.selectByExampleWithRowbounds(shoppingOrderExtendDOExample, rowBounds);
		
		List<Long> shoppingOrderIdList = new ArrayList<Long>();
		for (ShoppingOrderExtendDO item : shoppingOrderExtendDOList) {
			shoppingOrderIdList.add(item.getId());
		}
		
		shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		shoppingOrderExtendDOExample.setIncludeViewShoppingOrderItem(true);
		shoppingOrderExtendDOExample.setIncludeShoppingOrderItem(true);
		shoppingOrderExtendDOExample.createCriteria().andIdIn(shoppingOrderIdList);
		
		// 默认创建时间排序
		shoppingOrderExtendDOExample.setOrderByClause("so.gmt_create desc");
		
		shoppingOrderExtendDOList = shoppingOrderDOExtendMapper.selectByExampleWithRowbounds(shoppingOrderExtendDOExample, rowBounds);
			
		shoppingOrderItemBOPage.setRecords(ShoppingOrderExtendConverter.convertShoppingOrderExtendBO(shoppingOrderExtendDOList));
		
		return shoppingOrderItemBOPage;
	}
	
	/**
	 * 根据id获取购物订单以及订单项
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@Override
	public ShoppingOrderExtendBO get(Long id) {
		ShoppingOrderExtendDOExample shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		shoppingOrderExtendDOExample.createCriteria().andIdEqualTo(id);
		
		ShoppingOrderExtendDO shoppingOrderExtendDO = shoppingOrderDOExtendMapper.selectByPrimaryKey(id);
		
		if (shoppingOrderExtendDO == null || shoppingOrderExtendDO.getId() == null || shoppingOrderExtendDO.getId() <= 0
				|| shoppingOrderExtendDO.getItems() == null || shoppingOrderExtendDO.getItems().isEmpty()) {
			return null;
		}
		
		return ShoppingOrderExtendConverter.convertShoppingOrderExtendDetailBO(shoppingOrderExtendDO);
	}
	
	/**
	 * 根据id获取购物订单
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@Override
	public ShoppingOrderBO getShoppingOrder(Long id) {
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(id);
		
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
	public int cancelOrder(Long id) {
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(id);
		
		if (shoppingOrderDO == null || shoppingOrderDO.getId() == null || shoppingOrderDO.getId() <= 0) {
			return ResultCode.RESOURCE_NOT_FOUND;
		}
		
		// 被取消的订单必须要是待支付的状态
		if (!shoppingOrderDO.getOrderStatus().equals(ShoppingOrderStatusEnum.PENDING_PAYMENT.getValue())) {
			return ResultCode.ORDER_NOT_CANCELED;
		}
		
		// 更新购物订单的状态
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
		
		return ResultCode.SUCCESS;
	}
	
	/**
	 * 删除购物订单
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@Transactional
	@Override
	public int deleteOrder(Long id) {
		
		// 验证
		if (id == null || id <= 0) {
			return ResultCode.ID_EMPTY;
		}
		
		ShoppingOrderExtendDO shoppingOrderExtendDO = shoppingOrderDOExtendMapper.selectByPrimaryKey(id);
		
		if (shoppingOrderExtendDO == null || shoppingOrderExtendDO.getId() == null || shoppingOrderExtendDO.getId() <= 0) {
			return ResultCode.RESOURCE_NOT_FOUND;
		}
		
		// 当前订单是否已经删除，实现删除操作的幂等性，返回成功状态码
		if (shoppingOrderExtendDO.getStatus().equals(StatusEnum.INVALID.getValue())) {
			return ResultCode.SUCCESS;
		}
		
		// 检查订单项的订单状态是否是否是完成状态
		boolean isDone = true;
		for (ShoppingOrderItemDO item : shoppingOrderExtendDO.getItems()) {
			if (!item.getOrderStatus().equals(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue()) &&
					!item.getOrderStatus().equals(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue())) {
				isDone = false;
				break;
			}
		}
		
		// 订单的当前状态必须已结束状态的订单
		if (!isDone) {
			return ResultCode.ORDER_NOT_COMPLETE_STATUS;
		}
		
		String deleteOrder = propertyService.getByName(PropertyNameConstant.DELETE_ORDER);
		
		// 收货时间是否超过七天
		boolean isExceeds = DateUtil.isExceeds(shoppingOrderExtendDO.getGmtTransaction(), new Date(), Integer.valueOf(deleteOrder), Calendar.DAY_OF_YEAR);
		
		// 确认收货之后七天之内不能删除订单，如果是自动确认收货没有时间显示
		if (shoppingOrderExtendDO.getIsAutomaticReceipt() && shoppingOrderExtendDO.getOrderStatus().equals(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue())
				|| isExceeds) {
			return ResultCode.ORDER_NOT_DELETE;
		}
		
		// 更新购物订单的状态
		ShoppingOrderDO shoppingOrderDO = new ShoppingOrderDO();
		shoppingOrderDO.setId(id);
		shoppingOrderDO.setGmtModified(new Date());
		// 更改数据状态为删除
		shoppingOrderDO.setStatus(StatusEnum.INVALID.getValue());
		shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderDO);

		return ResultCode.SUCCESS;
	}

	/**
	 * 支付成功之后
	 * 修改购物订单以及订单项状态为待发货
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
		
		String [] shoppingOrderIds = StringUtils.split(notification.getShoppingOrderIds(), ",");
		
		for (String shoppingOrderId : shoppingOrderIds) {
			/*
			 * 实现MQ幂等性
			 * 只有订单是待支付的状态才允许更新购物订单的状态
			 */
			Long id = Long.valueOf(shoppingOrderId);
			ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(id);
			if (!ShoppingOrderStatusEnum.PENDING_PAYMENT.getValue().equals(shoppingOrderDO.getOrderStatus())) {
				return;
			}
			
			// 更新购物订单的状态
			shoppingOrderDO.setGmtModified(new Date());
			// 更改订单状态为待发货
			shoppingOrderDO.setOrderStatus(ShoppingOrderStatusEnum.BE_SHIPPED.getValue());
			// 更新付款时间
			shoppingOrderDO.setGmtPayment(new Date());
			shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderDO);
			
			// 更新购物订单项状态
			ShoppingOrderItemDOExample shoppingOrderItemDOExample = new ShoppingOrderItemDOExample();
			com.lawu.eshop.order.srv.domain.ShoppingOrderItemDOExample.Criteria shoppingOrderItemDOExampleCriteria = shoppingOrderItemDOExample.createCriteria();
			shoppingOrderItemDOExampleCriteria.andShoppingOrderIdEqualTo(id);
			
			ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
			shoppingOrderItemDO.setGmtModified(new Date());
			shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.BE_SHIPPED.getValue());
			
			shoppingOrderItemDOMapper.updateByExampleSelective(shoppingOrderItemDO, shoppingOrderItemDOExample);
		}
	}
	
	/**
	 * 确认收货之后
	 * 修改购物订单以及订单项状态为交易成功
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@Transactional
	@Override
	public int tradingSuccess(Long id, boolean isAutomaticReceipt) {
		
		// 验证
		if (id == null || id <= 0) {
			return ResultCode.ID_EMPTY;
		}
		
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(id);
		
		if (shoppingOrderDO == null || shoppingOrderDO.getId() == null || shoppingOrderDO.getId() <= 0) {
			return ResultCode.RESOURCE_NOT_FOUND;
		}
		
		// 订单的当前状态必须是待收货
		if (!shoppingOrderDO.getOrderStatus().equals(ShoppingOrderStatusEnum.TO_BE_RECEIVED.getValue())){
			return ResultCode.ORDER_NOT_RECEIVED;
		}
		
		// 更新购物订单的状态
		shoppingOrderDO.setId(id);
		shoppingOrderDO.setGmtModified(new Date());
		// 不是自动收货
		shoppingOrderDO.setIsAutomaticReceipt(isAutomaticReceipt);
		// 更改订单状态为交易成功
		shoppingOrderDO.setOrderStatus(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
		// 更新成交时间
		shoppingOrderDO.setGmtTransaction(new Date());
		shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderDO);
		
		// 更新购物订单项状态
		ShoppingOrderItemDOExample shoppingOrderItemDOExample = new ShoppingOrderItemDOExample();
		com.lawu.eshop.order.srv.domain.ShoppingOrderItemDOExample.Criteria shoppingOrderItemDOExampleCriteria = shoppingOrderItemDOExample.createCriteria();
		shoppingOrderItemDOExampleCriteria.andShoppingOrderIdEqualTo(id);
		
		ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
		shoppingOrderItemDO.setGmtModified(new Date());
		shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
		
		shoppingOrderItemDOMapper.updateByExampleSelective(shoppingOrderItemDO, shoppingOrderItemDOExample);
		
		// 发送MQ消息通知资产保存资金冻结表
		shoppingOrderTradingSuccessTransactionMainServiceImpl.sendNotice(id);
		
		return ResultCode.SUCCESS;
	}
	
	/**
	 * 买家申请退款 
	 * 修改订单项状态为退款中
	 * 
	 * @param shoppingOrderitemId
	 *            购物订单项id
	 * @param param
	 *            退款参数
	 * @return
	 */
	@Transactional
	@Override
	public int requestRefund(Long shoppingOrderitemId, ShoppingOrderRequestRefundForeignParam param) {
		
		if (shoppingOrderitemId == null || shoppingOrderitemId <= 0) {
			return ResultCode.ID_EMPTY;
		}
		
		ShoppingOrderItemDO shoppingOrderItemDO = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingOrderitemId);
		
		if (shoppingOrderItemDO == null || shoppingOrderItemDO.getId() == null || shoppingOrderItemDO.getId() <= 0) {
			return ResultCode.RESOURCE_NOT_FOUND;
		}
		
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getShoppingOrderId());
		
		if (shoppingOrderDO == null || shoppingOrderDO.getId() == null || shoppingOrderDO.getId() <= 0) {
			return ResultCode.RESOURCE_NOT_FOUND;
		}
		
		// 只有待发货、待收货、交易成功才能被允许退款
		if (!shoppingOrderDO.getOrderStatus().equals(ShoppingOrderStatusEnum.BE_SHIPPED.getValue())
				&& !shoppingOrderDO.getOrderStatus().equals(ShoppingOrderStatusEnum.TO_BE_RECEIVED.getValue())
			    && !shoppingOrderDO.getOrderStatus().equals(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue())){
			return ResultCode.ORDER_NOT_REFUND;
		}
		
		String refundRequestTime = propertyService.getByName(PropertyNameConstant.REFUND_REQUEST_TIME);
		
		// 买家收货(交易成功)七天之内才能被允许退款
		if (shoppingOrderDO.getOrderStatus().equals(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue()) 
				&& DateUtil.isExceeds(shoppingOrderDO.getGmtTransaction(), new Date(), Integer.valueOf(refundRequestTime), Calendar.DAY_OF_YEAR)) {
			return ResultCode.EXCEEDS_RETURN_TIME;
		}
		
		// 如果订单是自动收货，不允许退款
		if (shoppingOrderDO.getIsAutomaticReceipt()) {
			return ResultCode.ORDER_NOT_REFUND;
		}
		
		// 更新购物订单项状态
		shoppingOrderItemDO.setGmtModified(new Date());
		shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
		
		/*
		 *  如果卖家支持七天无理由退货并且已经收到货，跳过商家确认这个阶段
		 */
		if (shoppingOrderDO.getIsNoReasonReturn() && shoppingOrderDO.getOrderStatus().equals(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue())) {
			shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.FILL_RETURN_ADDRESS.getValue());
		} else {
			shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.TO_BE_CONFIRMED.getValue());
		}
		
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemDO);
		
		// 保存退货详情记录
		ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
		shoppingRefundDetailDO.setShoppingOrderItemId(shoppingOrderItemDO.getId());
		// 根据订单状态是否需要退货
		if (shoppingOrderDO.getOrderStatus().equals(ShoppingOrderStatusEnum.BE_SHIPPED.getValue())) {
			shoppingRefundDetailDO.setType(ShoppingRefundTypeEnum.REFUND.getValue());
		} else{
			shoppingRefundDetailDO.setType(ShoppingRefundTypeEnum.RETURN_REFUND.getValue());
		}
		// 计算退款金额
		BigDecimal amount = shoppingOrderItemDO.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemDO.getQuantity()));
		shoppingRefundDetailDO.setAmount(amount);
		shoppingRefundDetailDO.setReason(param.getReason());
		shoppingRefundDetailDO.setGmtCreate(new Date());
		shoppingRefundDetailDO.setGmtModified(new Date());
		
		shoppingRefundDetailDOMapper.insertSelective(shoppingRefundDetailDO);
		
		return ResultCode.SUCCESS;
	}
	
	/**
	 * 商家填写物流信息
	 * 更改购物订单的状态为待收货
	 * 
	 * @param id
	 *            购物订单id
	 * @param param
	 *            物流信息参数
	 * @return
	 */
	@Transactional
	@Override
	public int fillLogisticsInformation(Long id, ShoppingOrderLogisticsInformationParam param) {
		
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(id);
		
		if (shoppingOrderDO == null || shoppingOrderDO.getId() == null || shoppingOrderDO.getId() <= 0) {
			return ResultCode.RESOURCE_NOT_FOUND;
		}
		
		// 只有订单状态为待发货才能填写物流信息
		if (!shoppingOrderDO.getOrderStatus().equals(ShoppingOrderStatusEnum.BE_SHIPPED.getValue())){
			return ResultCode.NOT_SHIPPING_STATUS;
		}
		
		// 更新购物订单的状态
		shoppingOrderDO.setGmtModified(new Date());
		// 更改订单状态为待商家确认
		shoppingOrderDO.setOrderStatus(ShoppingOrderStatusEnum.TO_BE_RECEIVED.getValue());
		
		if (param.getIsNeedsLogistics()) {
			// 更新购物订单的物流信息
			shoppingOrderDO.setExpressCompanyId(param.getExpressCompanyId());
			shoppingOrderDO.setExpressCompanyCode(param.getExpressCompanyCode());
			shoppingOrderDO.setExpressCompanyName(param.getExpressCompanyName());
			shoppingOrderDO.setWaybillNum(param.getWaybillNum());
		}
		shoppingOrderDO.setIsNeedsLogistics(param.getIsNeedsLogistics());
		
		shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderDO);
		
		// 更新购物订单项状态
		ShoppingOrderItemDOExample shoppingOrderItemDOExample = new ShoppingOrderItemDOExample();
		com.lawu.eshop.order.srv.domain.ShoppingOrderItemDOExample.Criteria shoppingOrderItemDOExampleCriteria = shoppingOrderItemDOExample.createCriteria();
		shoppingOrderItemDOExampleCriteria.andShoppingOrderIdEqualTo(id);
		shoppingOrderItemDOExampleCriteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.BE_SHIPPED.getValue());
		
		ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
		shoppingOrderItemDO.setGmtModified(new Date());
		shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.TO_BE_RECEIVED.getValue());
		
		shoppingOrderItemDOMapper.updateByExampleSelective(shoppingOrderItemDO, shoppingOrderItemDOExample);
		
		return ResultCode.SUCCESS;
	}

	
	@Override
	public double selectOrderMoney(String orderIds) {
		String []orderIdsArray = orderIds.split(",");
		BigDecimal total = new BigDecimal("0");
		for(int i = 0 ; i < orderIdsArray.length ; i++){
			ShoppingOrderDO orderDO = shoppingOrderDOMapper.selectByPrimaryKey(Long.valueOf(orderIdsArray[i]));
			BigDecimal price = orderDO.getCommodityTotalPrice();
			total = total.add(price);
		}
		return total.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 根据查询参数分页查询订单列表
	 * 
	 * @param param	查询参数
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
			paroductCriteria.andConsigneeNameLike("%"+ param.getKeyword() + "%");
			paroductCriteria.getAllCriteria().addAll(baseCriteria.getAllCriteria());
		}
		
		// 查询总记录数
		Long count = shoppingOrderDOExtendMapper.countByExample(shoppingOrderExtendDOExample);
		
		Page<ShoppingOrderExtendBO> shoppingOrderItemBOPage = new Page<ShoppingOrderExtendBO>();
		shoppingOrderItemBOPage.setTotalCount(count.intValue());
		shoppingOrderItemBOPage.setCurrentPage(param.getCurrentPage());
		
		// 如果总记录为0，不再执行后续操作直接返回
		if (count == null || count <= 0) {
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
		
		List<Long> shoppingOrderIdList = new ArrayList<Long>();
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
		
		shoppingOrderExtendDOList = shoppingOrderDOExtendMapper.selectByExampleWithRowbounds(shoppingOrderExtendDOExample, rowBounds);
		
		shoppingOrderItemBOPage.setRecords(ShoppingOrderExtendConverter.convertShoppingOrderExtendBO(shoppingOrderExtendDOList));
		
		return shoppingOrderItemBOPage;
	}
	
	/**
	 * 减少产品库存成功回调 更改订单的状态为待支付状态
	 * 删除对应的购物车记录
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 * @author Sunny
	 */
	@Transactional
	@Override
	public void minusInventorySuccess(Long id) {
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(id);
		
		/*
		 * 实现MQ消息的幂等性
		 * 如果订单的状态已经不是待确认的状态不再去更新订单的状态
		 */
		if (!ShoppingOrderStatusEnum.PENDING.getValue().equals(shoppingOrderDO.getOrderStatus())) {
			return;
		}
		
		// 设置订单状态为待支付状态
		shoppingOrderDO.setOrderStatus(ShoppingOrderStatusEnum.PENDING_PAYMENT.getValue());
		shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderDO);
		
		// 设置购物订单项为待支付状态
		ShoppingOrderItemDOExample shoppingOrderItemDOExample = new ShoppingOrderItemDOExample();
		com.lawu.eshop.order.srv.domain.ShoppingOrderItemDOExample.Criteria shoppingOrderItemDOExampleCriteria = shoppingOrderItemDOExample.createCriteria();
		shoppingOrderItemDOExampleCriteria.andShoppingOrderIdEqualTo(id);
		
		ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
		shoppingOrderItemDO.setGmtModified(new Date());
		shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.PENDING_PAYMENT.getValue());
		
		shoppingOrderItemDOMapper.updateByExampleSelective(shoppingOrderItemDO, shoppingOrderItemDOExample);
		
		/*
		 * 删除购物车记录
		 */
		// 拆分购物车id
		String[] shoppingCartIdStrAry = StringUtils.split(shoppingOrderDO.getShoppingCartIdsStr(), ",");
		List<Long> shoppingCartIdList = new ArrayList<Long>();
		for (String shoppingCartIdStr : shoppingCartIdStrAry) {
			shoppingCartIdList.add(Long.valueOf(shoppingCartIdStr));
		}
		
		ShoppingCartDOExample shoppingCartDOExample = new ShoppingCartDOExample();
		shoppingCartDOExample.createCriteria().andIdIn(shoppingCartIdList);
		
		shoppingCartDOMapper.deleteByExample(shoppingCartDOExample);
		
	}
	
	/**
	 * 更新订单信息
	 * @param id
	 * 			     购物订单id
	 * @param param
	 *            查询参数
	 * @return
	 */
	@Transactional
	@Override
	public int updateInformation(Long id, ShoppingOrderUpdateInfomationParam param) {
		
		if (id == null || id <= 0) {
			return ResultCode.ID_EMPTY;
		}
		
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(id);
		
		if (shoppingOrderDO == null || shoppingOrderDO.getId() == null || shoppingOrderDO.getId() <= 0) {
			return ResultCode.RESOURCE_NOT_FOUND;
		}
		
		shoppingOrderDOMapper.updateByPrimaryKey(ShoppingOrderConverter.convert(shoppingOrderDO, param));
		
		return ResultCode.SUCCESS;
	}
	
	/**
	 * 自动评论超时未评论的订单项
	 */
	@Override
	public void executetAutoComment() {
		ShoppingOrderExtendDOExample shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		ShoppingOrderExtendDOExample.Criteria shoppingOrderExtendDOExampleCriteria = shoppingOrderExtendDOExample.createCriteria();
		shoppingOrderExtendDOExampleCriteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
		shoppingOrderExtendDOExampleCriteria.andSOIIsEvaluationEqualTo(false);
		
		// 查找配置表获取自动好评时间
		String automaticEvaluation = propertyService.getByName(PropertyNameConstant.AUTOMATIC_EVALUATION);
		
		// 如果交易时间超过automaticEvaluation的记录
		shoppingOrderExtendDOExampleCriteria.andGmtTransactionDateAddDayLessThanOrEqualTo(Integer.valueOf(automaticEvaluation), new Date());
		
		List<ShoppingOrderExtendDO> shoppingOrderExtendDOList = shoppingOrderDOExtendMapper.selectByExample(shoppingOrderExtendDOExample);
		
		for (ShoppingOrderExtendDO shoppingOrderExtendDO : shoppingOrderExtendDOList) {
			for (ShoppingOrderItemDO item : shoppingOrderExtendDO.getItems()) {
				// 将事务拆解成单个事务
				commentShoppingOrder(item.getId());
			}
		}
	}

	/**
	 * 根据商家的id查询商家是否有进行中的订单
	 * 
	 * @param merchantId 商家的id
	 * @return
	 * @author Sunny
	 */
	@Override
	public ShoppingOrderIsNoOnGoingOrderBO isNoOnGoingOrder(Long merchantId) {
		ShoppingOrderExtendDOExample shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		shoppingOrderExtendDOExample.setIncludeShoppingOrderItem(true);
		ShoppingOrderExtendDOExample.Criteria shoppingOrderExtendDOExampleCriteria = shoppingOrderExtendDOExample.createCriteria();
		shoppingOrderExtendDOExampleCriteria.andMerchantIdEqualTo(merchantId);
		
		List<Byte> processingStatus = new ArrayList<Byte>();
		processingStatus.add(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
		processingStatus.add(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue());
		
		shoppingOrderExtendDOExampleCriteria.andSOIOrderStatusNotIn(processingStatus);
		
		long count = shoppingOrderDOExtendMapper.countByExample(shoppingOrderExtendDOExample);
		
		return ShoppingOrderConverter.convert(count);
	}
	
	/**
	 * 根据购物订单项查询订单以及订单项
	 * 
	 * @param ShoppingOrderItemId 购物订单项id
	 * @param isAll 是否查找全部的订单项 
	 * @return
	 * @author Sunny
	 */
	@Override
	public ShoppingOrderExtendBO getByShoppingOrderItemId(Long shoppingOrderItemId, boolean isAll) {
		ShoppingOrderExtendBO rtn = null;
		
		if (isAll) {
			ShoppingOrderExtendDOExample shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
			shoppingOrderExtendDOExample.setIncludeShoppingOrderItem(true);
			shoppingOrderExtendDOExample.setIncludeViewShoppingOrderItem(true);
			shoppingOrderExtendDOExample.createCriteria().andSOIIdEqualTo(shoppingOrderItemId);
			
			List<ShoppingOrderExtendDO> shoppingOrderExtendDOList =  shoppingOrderDOExtendMapper.selectByExample(shoppingOrderExtendDOExample);
			
			if (shoppingOrderExtendDOList == null || shoppingOrderExtendDOList.isEmpty()) {
				return rtn;
			}
			
			rtn = ShoppingOrderExtendConverter.convertShoppingOrderExtendDetailBO(shoppingOrderExtendDOList.get(0));
		} else {
			ShoppingOrderItemDO shoppingOrderItemDO = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingOrderItemId);
			if (shoppingOrderItemDO == null || shoppingOrderItemDO.getId() == null || shoppingOrderItemDO.getId() <= 0) {
				return rtn;
			}
			
			rtn = get(shoppingOrderItemDO.getShoppingOrderId());
		}
		
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
		
		criteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.PENDING_PAYMENT.getValue());
		criteria.andGmtCreateDateAddDayLessThanOrEqualTo(Integer.valueOf(automaticCancelOrderTime), new Date());
		
		// 查找所有超时未付款的订单
		List<ShoppingOrderExtendDO> shoppingOrderDOList = shoppingOrderDOExtendMapper.selectByExample(shoppingOrderExtendDOExample);
		
		for (ShoppingOrderExtendDO item : shoppingOrderDOList) {
			cancelOrder(item.getId());
		}
	}
	
	/**
	 * 自动提醒发货
	 * 
	 * @author Sunny
	 */
	@Override
	public void executeAutoRemindShipments() {
		ShoppingOrderExtendDOExample shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		shoppingOrderExtendDOExample.setIncludeViewShoppingOrderItem(false);
		ShoppingOrderExtendDOExample.Criteria criteria = shoppingOrderExtendDOExample.createCriteria();
		
		String automaticRemindShipments = propertyService.getByName(PropertyNameConstant.AUTOMATIC_REMIND_SHIPMENTS);
		
		criteria.andSOIOrderStatusEqualTo(ShoppingOrderStatusEnum.BE_SHIPPED.getValue());
		criteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.BE_SHIPPED.getValue());
		criteria.andGmtTransportAddDayLessThanOrEqualTo(Integer.valueOf(automaticRemindShipments), new Date());
		
		// 查找所有超时未发货的订单，提醒卖家发货
		List<ShoppingOrderExtendDO> shoppingOrderDOList = shoppingOrderDOExtendMapper.selectByExample(shoppingOrderExtendDOExample);
		
		for (ShoppingOrderExtendDO item : shoppingOrderDOList) {
			// 发送站内信和推送
			shoppingOrderRemindShipmentsTransactionMainServiceImpl.sendNotice(item.getId());
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
		ShoppingOrderExtendDOExample.Criteria criteria = shoppingOrderExtendDOExample.createCriteria();
		
		String automaticRemindShipments = propertyService.getByName(PropertyNameConstant.AUTOMATIC_RECEIPT);
		
		criteria.andSOIOrderStatusEqualTo(ShoppingOrderStatusEnum.TO_BE_RECEIVED.getValue());
		criteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.TO_BE_RECEIVED.getValue());
		criteria.andGmtTransportAddDayLessThanOrEqualTo(Integer.valueOf(automaticRemindShipments), new Date());
		
		// 查找所有超时未收货的订单，自动收货
		List<ShoppingOrderExtendDO> shoppingOrderDOList = shoppingOrderDOExtendMapper.selectByExample(shoppingOrderExtendDOExample);
		
		for (ShoppingOrderExtendDO item : shoppingOrderDOList) {
			tradingSuccess(item.getId(), true);
		}
	}
	
	/**
	 * 查询各种订单状态的数量
	 * 
	 * @param memberId 会员id
	 * @return
	 * @author Sunny
	 */
	@Override
	public ShoppingOrderNumberOfOrderStatusBO numberOfOrderStartus(Long memberId) {
		ShoppingOrderNumberOfOrderStatusBO rtn = new ShoppingOrderNumberOfOrderStatusBO();
		
		//查询待收货订单的数量
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
		
		//查询待评价数量
		ShoppingOrderItemExtendDOExample shoppingOrderItemExtendDOExample = new ShoppingOrderItemExtendDOExample();
		shoppingOrderItemExtendDOExample.setIsIncludeShoppingOrder(true);
		ShoppingOrderItemExtendDOExample.Criteria shoppingOrderItemExtendDOExampleCriteria =  shoppingOrderItemExtendDOExample.createCriteria();
		shoppingOrderItemExtendDOExampleCriteria.andSOMemberIdEqualTo(memberId);
		shoppingOrderItemExtendDOExampleCriteria.andIsEvaluationEqualTo(false);
		long evaluationCount = shoppingOrderItemExtendDOMapper.countByExample(shoppingOrderItemExtendDOExample);
		
		//查询退货中数量
		shoppingOrderItemExtendDOExample = new ShoppingOrderItemExtendDOExample();
		shoppingOrderItemExtendDOExample.setIsIncludeShoppingOrder(true);
		shoppingOrderItemExtendDOExampleCriteria =  shoppingOrderItemExtendDOExample.createCriteria();
		shoppingOrderItemExtendDOExampleCriteria.andSOMemberIdEqualTo(memberId);
		shoppingOrderItemExtendDOExampleCriteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.REFUNDING.getValue());
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
	public List<ShoppingOrderExtendBO> commissionShoppingOrder() {
		ShoppingOrderExtendDOExample shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		ShoppingOrderExtendDOExample.Criteria criteria = shoppingOrderExtendDOExample.createCriteria();
		
		String refundRequestTime = propertyService.getByName(PropertyNameConstant.REFUND_REQUEST_TIME);
		
		// 查找超过退款时间的购物订单记录
		criteria.andSOIOrderStatusEqualTo(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
		criteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
		criteria.andGmtTransactionAddDayLessThanOrEqualTo(Integer.valueOf(refundRequestTime), new Date());
		
		// 查找自动确认收货的购物订单记录
		shoppingOrderExtendDOExample.or().andIsAutomaticReceiptEqualTo(true);
		
		List<ShoppingOrderExtendDO> shoppingOrderDOList = shoppingOrderDOExtendMapper.selectByExample(shoppingOrderExtendDOExample);
		
		return ShoppingOrderExtendConverter.convertShoppingOrderExtendBO(shoppingOrderDOList);
	}
	
	/**
	 * 根据订单id更新购物订单的提成状态和提成时间
	 * 
	 * @param ids 购物订单id集合
	 * @return
	 * @author Sunny
	 */
	@Transactional
	@Override
	public int updateCommissionStatus(List<Long> ids) {
		ShoppingOrderDOExample shoppingOrderDOExample = new ShoppingOrderDOExample();
		shoppingOrderDOExample.createCriteria().andIdIn(ids);
		
		ShoppingOrderDO shoppingOrderDO = new ShoppingOrderDO();
		// 更新提成状态和提成时间
		shoppingOrderDO.setGmtCommission(new Date());
		shoppingOrderDO.setCommissionStatus(CommissionStatusEnum.CALCULATED.getValue());
		
		shoppingOrderDOMapper.updateByExampleSelective(shoppingOrderDO, shoppingOrderDOExample);
		
		return ResultCode.SUCCESS;
	}
	
	@Override
	public ShoppingOrderNumberOfOrderStatusForMerchantBO numberOfOrderStartusByMerchant(Long merchantId) {
		ShoppingOrderNumberOfOrderStatusForMerchantBO rtn = new ShoppingOrderNumberOfOrderStatusForMerchantBO();
		
		//查询待收货订单的数量
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
		ShoppingOrderItemExtendDOExample.Criteria shoppingOrderItemExtendDOExampleCriteria =  shoppingOrderItemExtendDOExample.createCriteria();
		shoppingOrderItemExtendDOExampleCriteria.andSOMemberIdEqualTo(merchantId);
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
	 * @param merchantId 商家id
	 * @return
	 * @author Sunny
	 */
	@Override
	public ReportRiseRateDTO selectByTransactionData(ReportDataParam param) {
		List<ReportRiseRateView> rtn = new ArrayList<ReportRiseRateView>();
		int x = 0;
		BigDecimal total = new BigDecimal(0);// 总金额
		String refundRequestTime = propertyService.getByName(PropertyNameConstant.REFUND_REQUEST_TIME);
		
		ShoppingOrderReportDataParam shoppingOrderReportDataParam = new ShoppingOrderReportDataParam();
		
		if (ReportFansRiseRateEnum.DAY.getValue().equals(param.getFlag().getValue())) {
			shoppingOrderReportDataParam.setGmtCreate(DateUtil.getDateFormat(new Date(), "yyyyMM"));
			shoppingOrderReportDataParam.setOrderStatus(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
			shoppingOrderReportDataParam.setRefundRequestTime(Integer.valueOf(refundRequestTime));
			x = DateUtil.getNowMonthDay();
		} else if (ReportFansRiseRateEnum.MONTH.getValue().equals(param.getFlag().getValue())) {
			x = 12;
		}
		
		rtn = shoppingOrderDOExtendMapper.selectByTransactionData(shoppingOrderReportDataParam);
		total = shoppingOrderDOExtendMapper.selectByTransactionTotalAmount(shoppingOrderReportDataParam);
		
		ReportRiseRateDTO dto = ReportConvert.reportBrokeLineShow(rtn, x);
		dto.setTotal(total == null ? "0" : total.toString());
		return dto;
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
		String refundRequestTime = propertyService.getByName(PropertyNameConstant.REFUND_REQUEST_TIME);
		
		ShoppingOrderReportDataParam shoppingOrderReportDataParam = new ShoppingOrderReportDataParam();
		
		if (ReportFansRiseRateEnum.DAY.getValue().equals(param.getFlag().getValue())) {
			shoppingOrderReportDataParam.setGmtCreate(DateUtil.getDateFormat(new Date(), "yyyyMM"));
			shoppingOrderReportDataParam.setOrderStatus(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
			shoppingOrderReportDataParam.setRefundRequestTime(Integer.valueOf(refundRequestTime));
		} else if (ReportFansRiseRateEnum.MONTH.getValue().equals(param.getFlag().getValue())) {
		}
		
		List<ReportFansSaleTransFormDO> reportFansSaleTransFormDOList = shoppingOrderDOExtendMapper.selectByFansSaleTransForm(shoppingOrderReportDataParam);
		
		return ReportConvert.convert(reportFansSaleTransFormDOList);
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
	private void commentShoppingOrder(Long shoppingOrderItemId) {
		// 更新为已评论
		ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
		shoppingOrderItemDO.setId(shoppingOrderItemId);
		shoppingOrderItemDO.setIsEvaluation(true);
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemDO);
		
		// 发送MQ消息，通知mall模块添加默认好评记录
		shoppingOrderAutoCommentTransactionMainServiceImpl.sendNotice(shoppingOrderItemId);
	}
	
	/**
	 * 提醒卖家发货
	 * 
	 * @author Sunny
	 */
	@Transactional
	private void remindShipments(Long shoppingOrderItemId) {
		// 发送MQ消息，通知mall模块发送推送和站内信
		shoppingOrderAutoCommentTransactionMainServiceImpl.sendNotice(shoppingOrderItemId);
	}
}
