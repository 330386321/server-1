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
import com.lawu.eshop.order.constants.ShoppingOrderItemRefundStatusEnum;
import com.lawu.eshop.order.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.order.constants.ShoppingOrderStatusToMemberEnum;
import com.lawu.eshop.order.constants.ShoppingRefundTypeEnum;
import com.lawu.eshop.order.param.ShoppingOrderLogisticsInformationParam;
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
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemEvaluationBO;
import com.lawu.eshop.order.srv.constants.OrderConstant;
import com.lawu.eshop.order.srv.constants.PropertyNameConstant;
import com.lawu.eshop.order.srv.converter.ShoppingOrderConverter;
import com.lawu.eshop.order.srv.converter.ShoppingOrderExtendConverter;
import com.lawu.eshop.order.srv.converter.ShoppingOrderItemConverter;
import com.lawu.eshop.order.srv.converter.ShoppingOrderItemEvaluationConverter;
import com.lawu.eshop.order.srv.domain.ShoppingCartDOExample;
import com.lawu.eshop.order.srv.domain.ShoppingOrderDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDOExample;
import com.lawu.eshop.order.srv.domain.ShoppingRefundDetailDO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderExtendDO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderExtendDOExample;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderExtendDOExample.Criteria;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderItemEvaluationDO;
import com.lawu.eshop.order.srv.mapper.ShoppingCartDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderItemDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingRefundDetailDOMapper;
import com.lawu.eshop.order.srv.mapper.extend.ShoppingOrderExtendDOMapper;
import com.lawu.eshop.order.srv.service.PropertyService;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;
import com.lawu.eshop.utils.DateUtil;

@Service
public class ShoppingOrderServiceImpl implements ShoppingOrderService {
	
	@Autowired
	private OrderConstant orderConstant;
	
	@Autowired
	private ShoppingCartDOMapper shoppingCartDOMapper;
	
	@Autowired
	private ShoppingOrderExtendDOMapper shoppingOrderDOExtendMapper;
	
	@Autowired
	private ShoppingOrderDOMapper shoppingOrderDOMapper;
	
	@Autowired
	private ShoppingOrderItemDOMapper shoppingOrderItemDOMapper;
	
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
			}
		}
		
		return rtn;
	}

	@Override
	public Page<ShoppingOrderExtendBO> selectPageByMemberId(Long memberId, ShoppingOrderQueryForeignToMemberParam param) {
		ShoppingOrderExtendDOExample shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		
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
				baseCriteria.andIsEvaluationEqualTo(false);
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
			paroductCriteria.andProductNameLike("%"+ param.getKeyword() + "%");
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
		List<Long> shoppingOrderIdList = shoppingOrderDOExtendMapper.selectShoppingOrderIdByExampleWithRowbounds(shoppingOrderExtendDOExample, rowBounds);
		
		shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		shoppingOrderExtendDOExample.createCriteria().andIdIn(shoppingOrderIdList);
		
		// 默认创建时间排序
		shoppingOrderExtendDOExample.setOrderByClause("so.gmt_create desc");
		
		List<ShoppingOrderExtendDO> shoppingOrderExtendDOList = shoppingOrderDOExtendMapper.selectShoppingOrderAssociationByExampleWithRowbounds(shoppingOrderExtendDOExample, rowBounds);
		
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
		List<Long> shoppingOrderIdList = shoppingOrderDOExtendMapper.selectShoppingOrderIdByExampleWithRowbounds(shoppingOrderExtendDOExample, rowBounds);
		
		shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		shoppingOrderExtendDOExample.createCriteria().andIdIn(shoppingOrderIdList);
		
		// 分页参数
		rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
		
		// 默认创建时间排序
		shoppingOrderExtendDOExample.setOrderByClause("so.gmt_create desc");
		
		List<ShoppingOrderExtendDO> shoppingOrderExtendDOList = shoppingOrderDOExtendMapper.selectShoppingOrderAssociationByExampleWithRowbounds(shoppingOrderExtendDOExample, rowBounds);
			
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
		
		ShoppingOrderExtendDO shoppingOrderExtendDO = shoppingOrderDOExtendMapper.getShoppingOrderAssociationByPrimaryKey(id);
		
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
	public Integer cancelOrder(Long id) {
		// 更新购物订单的状态
		ShoppingOrderDO shoppingOrderDO = new ShoppingOrderDO();
		shoppingOrderDO.setId(id);
		shoppingOrderDO.setGmtModified(new Date());
		// 更新订单状态
		shoppingOrderDO.setOrderStatus(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue());
		// 更新成交时间
		shoppingOrderDO.setGmtTransaction(new Date());
		Integer result = shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderDO);

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
		
		return result;
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
	public Integer deleteOrder(Long id) {
		// 更新购物订单的状态
		ShoppingOrderDO shoppingOrderDO = new ShoppingOrderDO();
		shoppingOrderDO.setId(id);
		shoppingOrderDO.setGmtModified(new Date());
		// 更改数据状态为删除
		shoppingOrderDO.setStatus((byte)0x00);
		Integer result = shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderDO);

		return result;
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
	public int tradingSuccess(Long id) {
		
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
		shoppingOrderDO.setIsAutomaticReceipt(false);
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
		
		// 买家收货(交易成功)七天之内才能被允许退款
		if (shoppingOrderDO.getOrderStatus().equals(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue()) 
				&& DateUtil.isExceeds(shoppingOrderDO.getGmtTransaction(), new Date(), orderConstant.getRefundRequestTime(), Calendar.DAY_OF_YEAR)) {
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
			shoppingOrderItemDO.setRefundStatus(ShoppingOrderItemRefundStatusEnum.FILL_RETURN_ADDRESS.getValue());
		} else {
			shoppingOrderItemDO.setRefundStatus(ShoppingOrderItemRefundStatusEnum.TO_BE_CONFIRMED.getValue());
		}
		
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemDO);
		
		// 保存退货详情记录
		ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
		shoppingRefundDetailDO.setShoppingOrderItemId(shoppingOrderItemDO.getId());
		// 根据订单状态是否需要退货
		if (shoppingOrderDO.getOrderStatus().equals(ShoppingOrderStatusEnum.BE_SHIPPED)) {
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
	public Integer fillLogisticsInformation(Long id, ShoppingOrderLogisticsInformationParam param) {
		
		// 更新购物订单的状态
		ShoppingOrderDO shoppingOrderDO = new ShoppingOrderDO();
		shoppingOrderDO.setId(id);
		shoppingOrderDO.setGmtModified(new Date());
		// 更改订单状态为待商家确认
		shoppingOrderDO.setOrderStatus(ShoppingOrderStatusEnum.TO_BE_RECEIVED.getValue());
		
		// 更新购物订单的物流信息
		shoppingOrderDO.setExpressCompanyId(param.getExpressCompanyId());
		shoppingOrderDO.setExpressCompanyCode(param.getExpressCompanyCode());
		shoppingOrderDO.setExpressCompanyName(param.getExpressCompanyName());
		shoppingOrderDO.setWaybillNum(param.getWaybillNum());
		
		Integer result = shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderDO);
		
		// 更新购物订单项状态
		ShoppingOrderItemDOExample shoppingOrderItemDOExample = new ShoppingOrderItemDOExample();
		com.lawu.eshop.order.srv.domain.ShoppingOrderItemDOExample.Criteria shoppingOrderItemDOExampleCriteria = shoppingOrderItemDOExample.createCriteria();
		shoppingOrderItemDOExampleCriteria.andShoppingOrderIdEqualTo(id);
		
		ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
		shoppingOrderItemDO.setGmtModified(new Date());
		shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.TO_BE_RECEIVED.getValue());
		
		shoppingOrderItemDOMapper.updateByExampleSelective(shoppingOrderItemDO, shoppingOrderItemDOExample);
		
		return result;
	}

	
	@Override
	public double selectOrderMoney(String orderIds) {
		String []orderIdsArray = orderIds.split(",");
		BigDecimal total = new BigDecimal("0");
		for(int i = 0 ; i < orderIdsArray.length ; i++){
			ShoppingOrderDO orderDO = shoppingOrderDOMapper.selectByPrimaryKey(Long.valueOf(orderIdsArray[i]));
			BigDecimal price = orderDO.getCommodityTotalPrice();
			total.add(price);
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
			paroductCriteria.andProductNameLike("%"+ param.getKeyword() + "%");
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
		
		// 如果参数中的keyword有值，查询结果的订单项会缺少，所有先找出所有购物订单id再通过去查找购物订单以及级联的购物订单项
		List<Long> shoppingOrderIdList = shoppingOrderDOExtendMapper.selectShoppingOrderIdByExampleWithRowbounds(shoppingOrderExtendDOExample, rowBounds);
		
		shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		shoppingOrderExtendDOExample.createCriteria().andIdIn(shoppingOrderIdList);
		
		// 默认创建时间排序
		shoppingOrderExtendDOExample.setOrderByClause("so.gmt_create desc");
		
		List<ShoppingOrderExtendDO> shoppingOrderExtendDOList = shoppingOrderDOExtendMapper.selectShoppingOrderAssociationByExampleWithRowbounds(shoppingOrderExtendDOExample, rowBounds);
		
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
	 * 
	 */
	@Transactional
	@Override
	public void executetAutoComment() {
		ShoppingOrderExtendDOExample shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		ShoppingOrderExtendDOExample.Criteria shoppingOrderExtendDOExampleCriteria = shoppingOrderExtendDOExample.createCriteria();
		shoppingOrderExtendDOExampleCriteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
		shoppingOrderExtendDOExampleCriteria.andIsEvaluationEqualTo(false);
		
		// 查找配置表获取自动好评时间
		String automaticEvaluation = propertyService.getByName(PropertyNameConstant.AUTOMATIC_EVALUATION);
		Date identifyTime = DateUtil.add(new Date(), Integer.valueOf(automaticEvaluation), Calendar.DAY_OF_YEAR);
		
		// 如果交易时间大于identifyTime
		shoppingOrderExtendDOExampleCriteria.andGmtTransactionGreaterThanOrEqualTo(identifyTime);
		
		List<ShoppingOrderItemEvaluationDO> shoppingOrderItemEvaluationDOList = shoppingOrderDOExtendMapper.selectShoppingOrderItemEvaluationDOByExample(shoppingOrderExtendDOExample);
		
		ShoppingOrderItemDO shoppingOrderItemDO = null;
		for (ShoppingOrderItemEvaluationDO shoppingOrderItemEvaluationDO : shoppingOrderItemEvaluationDOList) {
			
			// 更新为已评论
			shoppingOrderItemDO = new ShoppingOrderItemDO();
			shoppingOrderItemDO.setId(shoppingOrderItemEvaluationDO.getShoppingOrderItem());
			shoppingOrderItemDO.setIsEvaluation(true);
			shoppingOrderItemDOMapper.updateByPrimaryKey(shoppingOrderItemDO);
			
			// 发送MQ消息，通知mall模块添加默认好评记录
			shoppingOrderAutoCommentTransactionMainServiceImpl.sendNotice(shoppingOrderItemEvaluationDO.getShoppingOrderItem());
		}
	}

	@Override
	public ShoppingOrderItemEvaluationBO getShoppingOrderItemEvaluationBOByShoppingOrderItemId(Long ShoppingOrderItemId) {
		ShoppingOrderExtendDOExample shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		ShoppingOrderExtendDOExample.Criteria shoppingOrderExtendDOExampleCriteria = shoppingOrderExtendDOExample.createCriteria();
		shoppingOrderExtendDOExampleCriteria.andShoppingOrderItemIdEqualTo(ShoppingOrderItemId);
		
		List<ShoppingOrderItemEvaluationDO> shoppingOrderItemEvaluationDOList = shoppingOrderDOExtendMapper.selectShoppingOrderItemEvaluationDOByExample(shoppingOrderExtendDOExample);
		
		if (shoppingOrderItemEvaluationDOList == null || shoppingOrderItemEvaluationDOList.isEmpty()) {
			return null;
		}
		
		return ShoppingOrderItemEvaluationConverter.convert(shoppingOrderItemEvaluationDOList.get(0));
	}

	@Override
	public ShoppingOrderIsNoOnGoingOrderBO isNoOnGoingOrder(Long merchantId) {
		ShoppingOrderExtendDOExample shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		ShoppingOrderExtendDOExample.Criteria shoppingOrderExtendDOExampleCriteria = shoppingOrderExtendDOExample.createCriteria();
		shoppingOrderExtendDOExampleCriteria.andMerchantIdEqualTo(merchantId);
		
		List<Byte> processingStatus = new ArrayList<Byte>();
		processingStatus.add(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
		processingStatus.add(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue());
		
		shoppingOrderExtendDOExampleCriteria.andShoppingOrderItemOrderStatusNotIn(processingStatus);
		
		long count = shoppingOrderDOExtendMapper.countByExample(shoppingOrderExtendDOExample);
		
		return ShoppingOrderConverter.convert(count);
	}
	
	
}
