package com.lawu.eshop.order.srv.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.ShoppingRefundFillReturnAddressRemindNotification;
import com.lawu.eshop.mq.dto.order.ShoppingRefundRefuseRefundRemindNotification;
import com.lawu.eshop.mq.dto.order.ShoppingRefundToBeConfirmedForRefundRemindNotification;
import com.lawu.eshop.mq.dto.order.ShoppingRefundToBeRefundRemindNotification;
import com.lawu.eshop.mq.dto.order.ShoppingRefundToBeReturnRemindNotification;
import com.lawu.eshop.mq.message.MessageProducerService;
import com.lawu.eshop.order.constants.RefundStatusEnum;
import com.lawu.eshop.order.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.order.constants.ShoppingRefundTypeEnum;
import com.lawu.eshop.order.constants.StatusEnum;
import com.lawu.eshop.order.param.ShoppingRefundDetailLogisticsInformationParam;
import com.lawu.eshop.order.param.ShoppingRefundDetailRerurnAddressParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundDetailAgreeToApplyForeignParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundDetailAgreeToRefundForeignParam;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemExtendBO;
import com.lawu.eshop.order.srv.bo.ShoppingRefundDetailBO;
import com.lawu.eshop.order.srv.constants.ExceptionMessageConstant;
import com.lawu.eshop.order.srv.constants.PropertyNameConstant;
import com.lawu.eshop.order.srv.converter.ShoppingOrderItemExtendConverter;
import com.lawu.eshop.order.srv.converter.ShoppingRefundDetailConverter;
import com.lawu.eshop.order.srv.domain.ShoppingOrderDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDOExample;
import com.lawu.eshop.order.srv.domain.ShoppingRefundDetailDO;
import com.lawu.eshop.order.srv.domain.ShoppingRefundProcessDO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderExtendDO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderItemExtendDO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderItemExtendDOExample;
import com.lawu.eshop.order.srv.exception.CanNotApplyForPlatformInterventionException;
import com.lawu.eshop.order.srv.exception.CanNotFillOutTheReturnLogisticsException;
import com.lawu.eshop.order.srv.exception.DataNotExistException;
import com.lawu.eshop.order.srv.exception.IllegalOperationException;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderItemDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingRefundDetailDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingRefundProcessDOMapper;
import com.lawu.eshop.order.srv.mapper.extend.ShoppingOrderExtendDOMapper;
import com.lawu.eshop.order.srv.mapper.extend.ShoppingOrderItemExtendDOMapper;
import com.lawu.eshop.order.srv.service.PropertyService;
import com.lawu.eshop.order.srv.service.ShoppingRefundDetailService;
import com.lawu.eshop.utils.DateUtil;

@Service
public class ShoppingRefundDetailServiceImpl implements ShoppingRefundDetailService {

	private static Logger logger = LoggerFactory.getLogger(ShoppingRefundDetailServiceImpl.class);
	
	@Autowired
	private ShoppingRefundDetailDOMapper shoppingRefundDetailDOMapper;
	
	@Autowired
	private ShoppingRefundProcessDOMapper shoppingRefundProcessDOMapper;

	@Autowired
	private ShoppingOrderItemDOMapper shoppingOrderItemDOMapper;
	
	@Autowired
	private ShoppingOrderItemExtendDOMapper shoppingOrderItemExtendDOMapper;

	@Autowired
	private ShoppingOrderDOMapper shoppingOrderDOMapper;
	
	@Autowired
	private ShoppingOrderExtendDOMapper shoppingOrderExtendDOMapper;

	@Autowired
	private PropertyService propertyService;
	
    @Autowired
    private MessageProducerService messageProducerService;
	
	@Autowired
	@Qualifier("shoppingRefundAgreeToRefundTransactionMainServiceImpl")
	private TransactionMainService<Reply> shoppingRefundAgreeToRefundTransactionMainServiceImpl;
	
	@Autowired
	@Qualifier("shoppingRefundAgreeToRefundDeleteCommentTransactionMainServiceImpl")
	private TransactionMainService<Reply> shoppingRefundAgreeToRefundDeleteCommentTransactionMainServiceImpl;
	
	/**
	 * 根据购物退货详情id 获取购物退货详情
	 * 
	 * @param id
	 *            退货详情id
	 * @return
	 */
	@Override
	public ShoppingRefundDetailBO get(Long id) {
		if (id == null || id <= 0) {
			return null;
		}

		ShoppingRefundDetailDO shoppingRefundDetailDO = shoppingRefundDetailDOMapper.selectByPrimaryKey(id);

		return ShoppingRefundDetailConverter.convert(shoppingRefundDetailDO);
	}

	/**
	 * 根据购物订单项id 获取购物退货详情
	 * 
	 * @param shoppingOrderitemId
	 *            购物订单项id
	 * @return
	 */
	@Override
	public ShoppingOrderItemExtendBO getByShoppingOrderItemId(Long shoppingOrderItemId) {
		ShoppingOrderItemExtendDOExample shoppingOrderItemExtendDOExample = new ShoppingOrderItemExtendDOExample();
		shoppingOrderItemExtendDOExample.setIsIncludeShoppingOrder(true);
		shoppingOrderItemExtendDOExample.setIsIncludeShoppingRefundDetail(true);
		shoppingOrderItemExtendDOExample.setIsIncludeShoppingRefundProcess(true);
		ShoppingOrderItemExtendDOExample.Criteria criteria = shoppingOrderItemExtendDOExample.createCriteria();
		criteria.andIdEqualTo(shoppingOrderItemId);
		// 找到有效记录
		criteria.andSRDStatusEqualTo(StatusEnum.VALID.getValue());
		shoppingOrderItemExtendDOExample.setOrderByClause("srp.gmt_create desc");
		List<ShoppingOrderItemExtendDO> list = shoppingOrderItemExtendDOMapper.selectByExample(shoppingOrderItemExtendDOExample);
		return (list != null && !list.isEmpty()) ? ShoppingOrderItemExtendConverter.convert(list.get(0)) : null;
	}

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
	@Transactional
	@Override
	public void fillLogisticsInformation(Long id, Long memberId, ShoppingRefundDetailLogisticsInformationParam param) {
		ShoppingRefundDetailDO shoppingRefundDetailDO = shoppingRefundDetailDOMapper.selectByPrimaryKey(id);
		if (shoppingRefundDetailDO == null || shoppingRefundDetailDO.getStatus().equals(StatusEnum.INVALID.getValue())) {
			throw new DataNotExistException(ExceptionMessageConstant.SHOPPING_ORDER_REFUND_DATA_DOES_NOT_EXIST);
		}
		ShoppingOrderItemDO shoppingOrderItemDO  = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingRefundDetailDO.getShoppingOrderItemId());
		if (shoppingOrderItemDO == null) {
			throw new DataNotExistException(ExceptionMessageConstant.SHOPPING_ORDER_ITEM_DATA_DOES_NOT_EXIST);
		}
		// 订单状态必须为退款中
		if (!shoppingOrderItemDO.getOrderStatus().equals(ShoppingOrderStatusEnum.REFUNDING.getValue())) {
			throw new CanNotFillOutTheReturnLogisticsException(ExceptionMessageConstant.ORDER_STATUS_IS_NOT_TO_BE_REFUND_STATUS);
		}
		// 只有退款失败才能申请平台介入
		if (!shoppingOrderItemDO.getRefundStatus().equals(RefundStatusEnum.REFUND_FAILED.getValue())) {
			throw new CanNotFillOutTheReturnLogisticsException(ExceptionMessageConstant.REFUND_STATE_IS_NOT_A_STATE_TO_BE_RETURNED);
		}
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getShoppingOrderId());
		if (shoppingOrderDO == null) {
			throw new DataNotExistException(ExceptionMessageConstant.SHOPPING_ORDER_ITEM_DATA_DOES_NOT_EXIST);
		}
		if (!shoppingOrderDO.getMemberId().equals(memberId)) {
			throw new IllegalOperationException(ExceptionMessageConstant.ILLEGAL_OPERATION_SHOPPING_ORDER);
		}
		// 更新订单项状态为待退款
		ShoppingOrderItemDO shoppingOrderItemUpdateDO = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingRefundDetailDO.getShoppingOrderItemId());
		shoppingOrderItemUpdateDO.setRefundStatus(RefundStatusEnum.TO_BE_REFUNDED.getValue());
		// 重置发送提醒的次数
		shoppingOrderItemUpdateDO.setSendTime(0);
		shoppingOrderItemUpdateDO.setGmtModified(new Date());
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemUpdateDO);
		// 更新退款详情的物流信息
		ShoppingRefundDetailDO shoppingRefundDetailUpdateDO = new ShoppingRefundDetailDO();
		shoppingRefundDetailUpdateDO.setId(shoppingRefundDetailDO.getId());
		shoppingRefundDetailUpdateDO.setGmtSubmit(new Date());
		shoppingRefundDetailUpdateDO.setExpressCompanyId(param.getExpressCompanyId());
		shoppingRefundDetailUpdateDO.setExpressCompanyCode(param.getExpressCompanyCode());
		shoppingRefundDetailUpdateDO.setExpressCompanyName(param.getExpressCompanyName());
		shoppingRefundDetailUpdateDO.setWaybillNum(param.getWaybillNum());
		shoppingRefundDetailUpdateDO.setGmtModified(new Date());
		shoppingRefundDetailDOMapper.updateByPrimaryKeySelective(shoppingRefundDetailUpdateDO);
		// 加入退款流程
		ShoppingRefundProcessDO shoppingRefundProcessDO = new ShoppingRefundProcessDO();
		shoppingRefundProcessDO.setRefundStatus(shoppingOrderItemUpdateDO.getRefundStatus());
		shoppingRefundProcessDO.setShoppingRefundDetailId(shoppingRefundDetailUpdateDO.getId());
		shoppingRefundProcessDO.setGmtCreate(new Date());
		shoppingRefundProcessDOMapper.insertSelective(shoppingRefundProcessDO);
		// 用户填写退货物流，提醒买家退款
		ShoppingRefundToBeReturnRemindNotification notification = new ShoppingRefundToBeReturnRemindNotification();
		notification.setShoppingOrderItemId(shoppingOrderItemUpdateDO.getId());
		notification.setMerchantNum(shoppingOrderDO.getMerchantNum());
		notification.setMemberNum(shoppingOrderDO.getMemberNum());
		notification.setOrderNum(shoppingOrderDO.getOrderNum());
		notification.setWaybillNum(shoppingRefundDetailUpdateDO.getWaybillNum());
		messageProducerService.sendMessage(MqConstant.TOPIC_ORDER_SRV, MqConstant.TAG_TO_BE_RETURN_REMIND, notification);
	}

	/**
	 * 商家填写退货地址信息
	 * 
	 * @param id
	 *            退款详情id
	 * @param param
	 *            退款详情物流信息
	 * @return
	 */
	@Transactional
	@Override
	public int fillReturnAddress(Long id, ShoppingRefundDetailRerurnAddressParam param) {
		
		if (id == null || id <= 0) {
			return ResultCode.ID_EMPTY;
		}
		
		ShoppingRefundDetailDO shoppingRefundDetailDO = shoppingRefundDetailDOMapper.selectByPrimaryKey(id);
		
		if (shoppingRefundDetailDO == null || shoppingRefundDetailDO.getId() == null || shoppingRefundDetailDO.getId() <= 0) {
			return ResultCode.RESOURCE_NOT_FOUND;
		}
		
		ShoppingOrderItemDO shoppingOrderItemDO = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingRefundDetailDO.getShoppingOrderItemId());
		
		if (shoppingOrderItemDO == null || shoppingOrderItemDO.getId() == null || shoppingOrderItemDO.getId() <= 0) {
			return ResultCode.RESOURCE_NOT_FOUND;
		}

		// 订单状态必须为退款中
		if (!shoppingOrderItemDO.getOrderStatus().equals(ShoppingOrderStatusEnum.REFUNDING.getValue())) {
			return ResultCode.NOT_REFUNDING;
		}

		// 退款状态必须为填写退货地址
		if (!shoppingOrderItemDO.getRefundStatus().equals(RefundStatusEnum.FILL_RETURN_ADDRESS.getValue())
				&& !(RefundStatusEnum.TO_BE_CONFIRMED.getValue().equals(shoppingOrderItemDO.getRefundStatus()) && ShoppingRefundTypeEnum.RETURN_REFUND.getValue().equals(shoppingRefundDetailDO.getType()))) {
			return ResultCode.ORDER_NOT_FILL_RETURN_ADDRESS;
		}
		
		// 更新订单项退款状态为待退货
		shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.TO_BE_RETURNED.getValue());

		// 更新退款详情的退货地址信息
		shoppingRefundDetailDO.setGmtFill(new Date());
		shoppingRefundDetailDO.setConsigneeName(param.getConsigneeName());
		shoppingRefundDetailDO.setConsigneeMobile(param.getConsigneeMobile());
		shoppingRefundDetailDO.setConsigneeAddress(param.getConsigneeAddress());
		shoppingRefundDetailDO.setGmtModified(new Date());

		shoppingRefundDetailDOMapper.updateByPrimaryKeySelective(shoppingRefundDetailDO);
		
		// 重置发送提醒的次数
		shoppingOrderItemDO.setSendTime(0);
		shoppingOrderItemDO.setGmtModified(new Date());
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemDO);
		
		// 加入退款流程
		ShoppingRefundProcessDO shoppingRefundProcessDO = new ShoppingRefundProcessDO();
		shoppingRefundProcessDO.setRefundStatus(shoppingOrderItemDO.getRefundStatus());
		shoppingRefundProcessDO.setShoppingRefundDetailId(shoppingRefundDetailDO.getId());
		shoppingRefundProcessDO.setGmtCreate(new Date());
		shoppingRefundProcessDOMapper.insertSelective(shoppingRefundProcessDO);
		
		// 商家填写退货地址，提醒买家退货
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getShoppingOrderId());
		ShoppingRefundFillReturnAddressRemindNotification notification = new ShoppingRefundFillReturnAddressRemindNotification();
		notification.setShoppingOrderItemId(shoppingOrderItemDO.getId());
		notification.setMemberNum(shoppingOrderDO.getMemberNum());
		messageProducerService.sendMessage(MqConstant.TOPIC_ORDER_SRV, MqConstant.TAG_FILL_RETURN_ADDRESS_REMIND, notification);
		
		return ResultCode.SUCCESS;
	}

	/**
	 * 商家是否同意买家的退货申请
	 * 
	 * @param shoppingRefundDetailBO
	 *            退款详情
	 * @param param
	 *            参数 是否同意申请
	 * @return
	 */
	@Transactional
	@Override
	public int agreeToApply(Long id, ShoppingRefundDetailAgreeToApplyForeignParam param) {
		
		ShoppingRefundDetailDO shoppingRefundDetailDO = shoppingRefundDetailDOMapper.selectByPrimaryKey(id);

		if (shoppingRefundDetailDO == null || shoppingRefundDetailDO.getId() == null || shoppingRefundDetailDO.getId() <= 0) {
			return ResultCode.RESOURCE_NOT_FOUND;
		}

		ShoppingOrderItemDO shoppingOrderItemDO = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingRefundDetailDO.getShoppingOrderItemId());
		
		if (shoppingOrderItemDO == null || shoppingOrderItemDO.getId() == null || shoppingOrderItemDO.getId() <= 0) {
			return ResultCode.RESOURCE_NOT_FOUND;
		}

		// 订单状态必须为退款中
		if (!shoppingOrderItemDO.getOrderStatus().equals(ShoppingOrderStatusEnum.REFUNDING.getValue())) {
			return ResultCode.NOT_REFUNDING;
		}

		// 退款状态必须为待商家确认
		if (!shoppingOrderItemDO.getRefundStatus().equals(RefundStatusEnum.TO_BE_CONFIRMED.getValue())) {
			return ResultCode.NOT_AGREE_TO_APPLY;
		}
		
		// 更新退款状态
		if (param.getIsAgree()) {
			// 判断是否需要退货
			if (shoppingRefundDetailDO.getType().equals(ShoppingRefundTypeEnum.REFUND.getValue())) {
				shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.TO_BE_REFUNDED.getValue());
			} else {
				shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.FILL_RETURN_ADDRESS.getValue());
			}
		} else {
			shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.REFUND_FAILED.getValue());
		}

		// 重置发送提醒的次数
		shoppingOrderItemDO.setSendTime(0);
		shoppingOrderItemDO.setGmtModified(new Date());
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemDO);

		// 更新退款详情
		shoppingRefundDetailDO.setGmtConfirmed(new Date());
		if (!param.getIsAgree()) {
			shoppingRefundDetailDO.setGmtRefund(new Date());
		}
		shoppingRefundDetailDO.setRefusalReasons(param.getRefusalReasons());
		shoppingRefundDetailDO.setIsAgree(param.getIsAgree());
		shoppingRefundDetailDO.setGmtModified(new Date());
		shoppingRefundDetailDOMapper.updateByPrimaryKeySelective(shoppingRefundDetailDO);
		
		// 加入退款流程
		ShoppingRefundProcessDO shoppingRefundProcessDO = new ShoppingRefundProcessDO();
		shoppingRefundProcessDO.setRefundStatus(shoppingOrderItemDO.getRefundStatus());
		shoppingRefundProcessDO.setShoppingRefundDetailId(shoppingRefundDetailDO.getId());
		shoppingRefundProcessDO.setGmtCreate(new Date());
		shoppingRefundProcessDOMapper.insertSelective(shoppingRefundProcessDO);
		
		if (!param.getIsAgree()) {
			ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getShoppingOrderId());
			// 商家拒绝退款，提醒买家
			ShoppingRefundRefuseRefundRemindNotification notification = new ShoppingRefundRefuseRefundRemindNotification();
			notification.setShoppingOrderItemId(shoppingOrderDO.getId());
			notification.setMemberNum(shoppingOrderDO.getMemberNum());
			messageProducerService.sendMessage(MqConstant.TOPIC_ORDER_SRV, MqConstant.TAG_REFUSE_REFUND_REMIND, notification);
		}
		return ResultCode.SUCCESS;
	}

	/**
	 * 商家是否同意退款
	 * 
	 * @param id
	 *            退款详情
	 * @param param
	 * @return
	 */
	@Transactional
	@Override
	public int agreeToRefund(Long id, ShoppingRefundDetailAgreeToRefundForeignParam param) {
		
		ShoppingOrderItemExtendDOExample shoppingOrderItemExtendDOExample = new ShoppingOrderItemExtendDOExample();
		ShoppingOrderItemExtendDOExample.Criteria shoppingOrderItemExtendDOExampleCriteria =  shoppingOrderItemExtendDOExample.createCriteria();
		shoppingOrderItemExtendDOExample.setIsIncludeShoppingRefundDetail(true);
		shoppingOrderItemExtendDOExampleCriteria.andSRDIdEqualTo(id);
		
		List<ShoppingOrderItemExtendDO> shoppingOrderItemExtendDOList = shoppingOrderItemExtendDOMapper.selectByExample(shoppingOrderItemExtendDOExample);
		
		ShoppingOrderItemExtendDO shoppingOrderItemExtendDO = (shoppingOrderItemExtendDOList == null || shoppingOrderItemExtendDOList.isEmpty()) ? null : shoppingOrderItemExtendDOList.get(0);

		if (shoppingOrderItemExtendDO == null || shoppingOrderItemExtendDO.getId() == null || shoppingOrderItemExtendDO.getId() <= 0) {
			return ResultCode.RESOURCE_NOT_FOUND;
		}

		ShoppingRefundDetailDO shoppingRefundDetailDO = shoppingOrderItemExtendDO.getShoppingRefundDetail();

		if (shoppingRefundDetailDO == null || shoppingRefundDetailDO.getId() == null || shoppingRefundDetailDO.getId() <= 0) {
			return ResultCode.RESOURCE_NOT_FOUND;
		}

		// 订单状态必须为退款中
		if (!shoppingOrderItemExtendDO.getOrderStatus().equals(ShoppingOrderStatusEnum.REFUNDING.getValue())) {
			return ResultCode.NOT_REFUNDING;
		}

		// 退款状态必须为待退款或者平台介入状态
		if (!shoppingOrderItemExtendDO.getRefundStatus().equals(RefundStatusEnum.TO_BE_REFUNDED.getValue())
				&& !shoppingOrderItemExtendDO.getRefundStatus().equals(RefundStatusEnum.PLATFORM_INTERVENTION.getValue())
				// 待确认状态，如果退款类型为退款可以直接退款
				&& !(ShoppingRefundTypeEnum.REFUND.getValue().equals(shoppingRefundDetailDO.getType()) && RefundStatusEnum.TO_BE_CONFIRMED.getValue().equals(shoppingOrderItemExtendDO.getRefundStatus())) ) {
			return ResultCode.ORDER_NOT_TO_BE_REFUNDED;
		}
		
		if (param.getIsAgree()) {
			shoppingOrderItemExtendDO.setRefundStatus(RefundStatusEnum.REFUND_SUCCESSFULLY.getValue());
			// 设置购物订单项的订单状态为交易关闭
			shoppingOrderItemExtendDO.setOrderStatus(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue());
		} else {
			shoppingOrderItemExtendDO.setRefundStatus(RefundStatusEnum.REFUND_FAILED.getValue());
		}

		// 重置发送提醒的次数
		shoppingOrderItemExtendDO.setSendTime(0);
		shoppingOrderItemExtendDO.setGmtModified(new Date());
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemExtendDO);
		
		// 更新退款详情
		shoppingRefundDetailDO.setIsAgree(param.getIsAgree());
		shoppingRefundDetailDO.setRefusalReasons(param.getRefusalReasons());
		shoppingRefundDetailDO.setGmtRefund(new Date());
		shoppingRefundDetailDO.setGmtModified(new Date());
		shoppingRefundDetailDOMapper.updateByPrimaryKeySelective(shoppingRefundDetailDO);
		
		// 加入退款流程
		ShoppingRefundProcessDO shoppingRefundProcessDO = new ShoppingRefundProcessDO();
		shoppingRefundProcessDO.setRefundStatus(shoppingOrderItemExtendDO.getRefundStatus());
		shoppingRefundProcessDO.setShoppingRefundDetailId(shoppingRefundDetailDO.getId());
		shoppingRefundProcessDO.setGmtCreate(new Date());
		shoppingRefundProcessDOMapper.insertSelective(shoppingRefundProcessDO);
		
		if (param.getIsAgree()) {
			// 如果当前订单下的所有订单项都是交易关闭状态。关闭订单
			ShoppingOrderItemDOExample shoppingOrderItemDOExample = new ShoppingOrderItemDOExample();
			shoppingOrderItemDOExample.createCriteria().andShoppingOrderIdEqualTo(shoppingOrderItemExtendDO.getShoppingOrderId()).andOrderStatusNotEqualTo(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue());
			long count = shoppingOrderItemDOMapper.countByExample(shoppingOrderItemDOExample);
			
			ShoppingOrderExtendDO shoppingOrderExtendDO = new ShoppingOrderExtendDO();
			shoppingOrderExtendDO.setId(shoppingOrderItemExtendDO.getShoppingOrderId());
			shoppingOrderExtendDO.setGmtModified(new Date());
			
			// 如果购物订单下的所有订单项都是关闭状态关闭购物订单
			if (count <= 0) {
				shoppingOrderExtendDO.setOrderStatus(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue());
			}
			
			// 更新实际支付给商家的金额
			shoppingOrderExtendDO.setActualAmountSubtraction(shoppingOrderItemExtendDO.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemExtendDO.getQuantity())));
			shoppingOrderExtendDOMapper.updateByPrimaryKeySelective(shoppingOrderExtendDO);
			
			// 发送MQ消息给property模块，退款给用户
			shoppingRefundAgreeToRefundTransactionMainServiceImpl.sendNotice(shoppingOrderItemExtendDO.getId());
			
			// 发送MQ消息给mall模块，删除商品订单评价
			shoppingRefundAgreeToRefundDeleteCommentTransactionMainServiceImpl.sendNotice(shoppingOrderItemExtendDO.getId());
			
			// 商家同意退款，提醒买家
			ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(shoppingOrderItemExtendDO.getShoppingOrderId());
			ShoppingRefundToBeRefundRemindNotification notification = new ShoppingRefundToBeRefundRemindNotification();
			notification.setShoppingOrderItemId(shoppingOrderDO.getId());
			notification.setMemberNum(shoppingOrderDO.getMemberNum());
			notification.setRefundAmount(shoppingRefundDetailDO.getAmount());
			messageProducerService.sendMessage(MqConstant.TOPIC_ORDER_SRV, MqConstant.TAG_TO_BE_REFUND_REMIND, notification);
		} else {
			// 商家拒绝退款，提醒买家
			ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(shoppingOrderItemExtendDO.getShoppingOrderId());
			ShoppingRefundRefuseRefundRemindNotification notification = new ShoppingRefundRefuseRefundRemindNotification();
			notification.setShoppingOrderItemId(shoppingOrderDO.getId());
			notification.setMemberNum(shoppingOrderDO.getMemberNum());
			messageProducerService.sendMessage(MqConstant.TOPIC_ORDER_SRV, MqConstant.TAG_REFUSE_REFUND_REMIND, notification);
		}
		
		return ResultCode.SUCCESS;
	}

	/**
	 * 如果商家拒绝买家的退款申请或者拒绝退款 买家可以申请平台介入
	 * 
	 * @param id
	 *            退款详情id
	 * @param memberId
	 *            会员id
	 */
	@Transactional
	@Override
	public void platformIntervention(Long id, Long memberId) {
		ShoppingRefundDetailDO shoppingRefundDetailDO = shoppingRefundDetailDOMapper.selectByPrimaryKey(id);
		if (shoppingRefundDetailDO == null || shoppingRefundDetailDO.getStatus().equals(StatusEnum.INVALID.getValue())) {
			throw new DataNotExistException(ExceptionMessageConstant.SHOPPING_ORDER_REFUND_DATA_DOES_NOT_EXIST);
		}
		ShoppingOrderItemDO shoppingOrderItemDO  = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingRefundDetailDO.getShoppingOrderItemId());
		if (shoppingOrderItemDO == null) {
			throw new DataNotExistException(ExceptionMessageConstant.SHOPPING_ORDER_ITEM_DATA_DOES_NOT_EXIST);
		}
		// 订单状态必须为退款中
		if (!shoppingOrderItemDO.getOrderStatus().equals(ShoppingOrderStatusEnum.REFUNDING.getValue())) {
			throw new CanNotApplyForPlatformInterventionException(ExceptionMessageConstant.ORDER_STATUS_IS_NOT_TO_BE_REFUND_STATUS);
		}
		// 只有退款失败才能申请平台介入
		if (!shoppingOrderItemDO.getRefundStatus().equals(RefundStatusEnum.REFUND_FAILED.getValue())) {
			throw new CanNotApplyForPlatformInterventionException(ExceptionMessageConstant.THE_REFUND_STATUS_IS_NOT_A_REFUND_FAILURE);
		}
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getShoppingOrderId());
		if (shoppingOrderDO == null) {
			throw new DataNotExistException(ExceptionMessageConstant.SHOPPING_ORDER_ITEM_DATA_DOES_NOT_EXIST);
		}
		if (!shoppingOrderDO.getMemberId().equals(memberId)) {
			throw new IllegalOperationException(ExceptionMessageConstant.ILLEGAL_OPERATION_SHOPPING_ORDER);
		}
		// 更新订单项的退款状态为平台介入
		ShoppingOrderItemDO shoppingOrderItemUpdateDO = new ShoppingOrderItemDO();
		shoppingOrderItemUpdateDO.setId(shoppingOrderItemDO.getId());
		shoppingOrderItemUpdateDO.setRefundStatus(RefundStatusEnum.PLATFORM_INTERVENTION.getValue());
		// 重置发送提醒的次数
		shoppingOrderItemUpdateDO.setSendTime(0);
		shoppingOrderItemUpdateDO.setGmtModified(new Date());
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemUpdateDO);

		// 更新退款详情
		ShoppingRefundDetailDO shoppingRefundDetailUpdateDO = new ShoppingRefundDetailDO();
		shoppingRefundDetailUpdateDO.setId(shoppingRefundDetailDO.getId());
		shoppingRefundDetailUpdateDO.setGmtIntervention(new Date());
		shoppingRefundDetailUpdateDO.setGmtModified(new Date());
		shoppingRefundDetailDOMapper.updateByPrimaryKeySelective(shoppingRefundDetailUpdateDO);
		
		// 加入退款流程
		ShoppingRefundProcessDO shoppingRefundProcessDO = new ShoppingRefundProcessDO();
		shoppingRefundProcessDO.setRefundStatus(shoppingOrderItemUpdateDO.getRefundStatus());
		shoppingRefundProcessDO.setShoppingRefundDetailId(shoppingRefundDetailUpdateDO.getId());
		shoppingRefundProcessDO.setGmtCreate(new Date());
		shoppingRefundProcessDOMapper.insertSelective(shoppingRefundProcessDO);
	}

	/**
	 * 买家撤销退货申请
	 * 
	 * @param id
	 *            退款详情id
	 * @return
	 */
	@Transactional
	@Override
	public int revokeRefundRequest(Long id) {
		
		if (id == null || id <= 0) {
			return ResultCode.ID_EMPTY;
		}
		
		ShoppingRefundDetailDO shoppingRefundDetailDO = shoppingRefundDetailDOMapper.selectByPrimaryKey(id);
		
		if (shoppingRefundDetailDO == null || shoppingRefundDetailDO.getId() == null || shoppingRefundDetailDO.getId() <= 0) {
			return ResultCode.RESOURCE_NOT_FOUND;
		}
		
		ShoppingOrderItemDO shoppingOrderItemDO = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingRefundDetailDO.getShoppingOrderItemId());
		
		if (shoppingOrderItemDO == null || shoppingOrderItemDO.getId() == null || shoppingOrderItemDO.getId() <= 0) {
			return ResultCode.RESOURCE_NOT_FOUND;
		}
		
		// 订单项状态必须为退款中
		if (!shoppingOrderItemDO.getOrderStatus().equals(ShoppingOrderStatusEnum.REFUNDING.getValue())) {
			return ResultCode.NOT_REFUNDING;
		}
		
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getShoppingOrderId());
		
		if (shoppingOrderDO == null || shoppingOrderDO.getId() == null || shoppingOrderDO.getId() <= 0) {
			return ResultCode.RESOURCE_NOT_FOUND;
		}
		
		// 清空退款状态
		shoppingOrderItemDO.setRefundStatus(null);
		// 还原订单之前状态
		shoppingOrderItemDO.setOrderStatus(shoppingOrderDO.getOrderStatus());
		// 重置发送提醒的次数
		shoppingOrderItemDO.setSendTime(0);
		shoppingOrderItemDO.setGmtModified(new Date());
		shoppingOrderItemDOMapper.updateByPrimaryKey(shoppingOrderItemDO);

		// 更新退款详情
		// 设置订单的状态为无效
		shoppingRefundDetailDO.setStatus(StatusEnum.INVALID.getValue());
		shoppingRefundDetailDO.setGmtIntervention(new Date());
		shoppingRefundDetailDO.setGmtModified(new Date());
		shoppingRefundDetailDOMapper.updateByPrimaryKeySelective(shoppingRefundDetailDO);
		
		return ResultCode.SUCCESS;
	}

	/**
	 * 买家申请退款，商家未操作处理
	 * 退款类型-退款
	 * 平台提醒商家，否则自动退款给买家
	 * 
	 * @author Sunny
	 */
	@Override
	public void executeAutoToBeConfirmedForRefund() {
		ShoppingOrderItemExtendDOExample example = new ShoppingOrderItemExtendDOExample();
		
		// 查询结果包括订单
		example.setIsIncludeShoppingOrder(true);
		// 查询结果包括退款详情
		example.setIsIncludeShoppingRefundDetail(true);
		
		ShoppingOrderItemExtendDOExample.Criteria criteria = example.createCriteria();
		
		criteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.REFUNDING.getValue());
		criteria.andRefundStatusEqualTo(RefundStatusEnum.TO_BE_CONFIRMED.getValue());
		// 找到其中有效的记录
		criteria.andSRDStatusEqualTo(StatusEnum.VALID.getValue());
		criteria.andSRDTypeEqualTo(ShoppingRefundTypeEnum.REFUND.getValue());
		
		// 提醒时间
		String remindTime = propertyService.getByName(PropertyNameConstant.TO_BE_CONFIRMED_FOR_REFUND_REMIND_TIME);
		// 超过提醒时间
		criteria.andGmtModifiedAddDayLessThanOrEqualTo(Integer.valueOf(remindTime), new Date());
		
		// 退款时间
		String refundTime = propertyService.getByName(PropertyNameConstant.TO_BE_CONFIRMED_FOR_REFUND_REFUND_TIME);
		
		List<ShoppingOrderItemExtendDO> shoppingOrderItemDOList = shoppingOrderItemExtendDOMapper.selectByExample(example);
		
		for (ShoppingOrderItemExtendDO shoppingOrderItemExtendDO : shoppingOrderItemDOList) {
			// 发送次数为0，发送站内信和推送
			if (shoppingOrderItemExtendDO.getSendTime() == null || shoppingOrderItemExtendDO.getSendTime() <= 0) {
				toBeConfirmedForRefundRemind(shoppingOrderItemExtendDO);
				continue;
			}
			
			boolean isExceeds = DateUtil.isExceeds(shoppingOrderItemExtendDO.getGmtModified(), new Date(), Integer.valueOf(refundTime), Calendar.DAY_OF_YEAR);
			// 如果商家未处理时间超过退款时间，平台自动退款
			if (isExceeds) {
				agreeToRefund(shoppingOrderItemExtendDO.getShoppingRefundDetail().getId(), true);
			}
		}
	}
	
	/**
	 * 买家申请退款，商家未操作处理
	 * 退款类型-退货退款
	 * 平台提醒商家，否则自动退款给买家
	 * 
	 * @author Sunny
	 */
	@Override
	public void executeAutoToBeConfirmedForReturnRefund() {
		ShoppingOrderItemExtendDOExample example = new ShoppingOrderItemExtendDOExample();
		
		// 查询结果包括订单
		example.setIsIncludeShoppingOrder(true);
		// 查询结果包括退款详情
		example.setIsIncludeShoppingRefundDetail(true);
		
		ShoppingOrderItemExtendDOExample.Criteria criteria = example.createCriteria();
		
		criteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.REFUNDING.getValue());
		criteria.andRefundStatusEqualTo(RefundStatusEnum.TO_BE_CONFIRMED.getValue());
		// 找到其中有效的记录
		criteria.andSRDStatusEqualTo(StatusEnum.VALID.getValue());
		criteria.andSRDTypeEqualTo(ShoppingRefundTypeEnum.RETURN_REFUND.getValue());
		
		// 提醒时间
		String remindTime = propertyService.getByName(PropertyNameConstant.TO_BE_CONFIRMED_FOR_RETURN_REFUND_REMIND_TIME);
		// 超过提醒时间
		criteria.andGmtModifiedAddDayLessThanOrEqualTo(Integer.valueOf(remindTime), new Date());
		
		// 退款时间
		String refundTime = propertyService.getByName(PropertyNameConstant.TO_BE_CONFIRMED_FOR_RETURN_REFUND_REFUND_TIME);
		
		List<ShoppingOrderItemExtendDO> shoppingOrderItemDOList = shoppingOrderItemExtendDOMapper.selectByExample(example);
		
		for (ShoppingOrderItemExtendDO shoppingOrderItemExtendDO : shoppingOrderItemDOList) {
			// 发送次数为0，发送站内信和推送
			if (shoppingOrderItemExtendDO.getSendTime() == null || shoppingOrderItemExtendDO.getSendTime() <= 0) {
				toBeConfirmedForRefundRemind(shoppingOrderItemExtendDO);
				continue;
			}
			
			boolean isExceeds = DateUtil.isExceeds(shoppingOrderItemExtendDO.getGmtModified(), new Date(), Integer.valueOf(refundTime), Calendar.DAY_OF_YEAR);
			// 如果商家未处理时间超过退款时间，平台自动退款
			if (isExceeds) {
				agreeToRefund(shoppingOrderItemExtendDO.getId(), true);
			}
		}
	}
	
	/**
	 * 退款中-退款失败
	 * 商家拒绝退款
	 * 平台提示买家操作是否申请平台介入
	 * 否则自动撤销退款申请
	 * 
	 * @author Sunny
	 */
	@Override
	public void executeAutoRefundFailed() {
		ShoppingOrderItemExtendDOExample example = new ShoppingOrderItemExtendDOExample();
		
		// 查询结果包括退款详情
		example.setIsIncludeShoppingRefundDetail(true);
		
		ShoppingOrderItemExtendDOExample.Criteria criteria = example.createCriteria();
		
		criteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.REFUNDING.getValue());
		// 找到其中有效的记录
		criteria.andSRDStatusEqualTo(StatusEnum.VALID.getValue());
		criteria.andRefundStatusEqualTo(RefundStatusEnum.REFUND_FAILED.getValue());
		
		// 提醒时间
		String remindTime = propertyService.getByName(PropertyNameConstant.REFUND_FAILED_REMIND_TIME);
		logger.info("提醒买家操作时间:{}", remindTime);
		// 超过提醒时间
		criteria.andGmtModifiedAddDayLessThanOrEqualTo(Integer.valueOf(remindTime), new Date());
		
		// 自动撤销撤销时间
		String refundTime = propertyService.getByName(PropertyNameConstant.REFUND_FAILED_REVOKE_TIME);
		logger.info("自动撤销退款申请时间:{}", refundTime);
		
		List<ShoppingOrderItemExtendDO> shoppingOrderItemDOList = shoppingOrderItemExtendDOMapper.selectByExample(example);
		
		logger.info("退款失败数量:{}", shoppingOrderItemDOList.size());
		
		for (ShoppingOrderItemExtendDO shoppingOrderItemExtendDO : shoppingOrderItemDOList) {
			// 发送次数为0，发送站内信和推送
			if (shoppingOrderItemExtendDO.getSendTime() == null || shoppingOrderItemExtendDO.getSendTime() <= 0) {
				refundFailedRemind(shoppingOrderItemExtendDO);
			}
			
			boolean isExceeds = DateUtil.isExceeds(shoppingOrderItemExtendDO.getGmtModified(), new Date(), Integer.valueOf(refundTime), Calendar.DAY_OF_YEAR);
			// 买家操作超过处理时间，平台自动撤销退款申请
			if (isExceeds && shoppingOrderItemExtendDO.getShoppingRefundDetail() != null) {
				revokeRefundRequest(shoppingOrderItemExtendDO.getShoppingRefundDetail().getId());
			}
		}
	}
	
	/**
	 * 退款中-商家填写退货地址
	 * 平台提醒商家操作，否则自动退款
	 * 
	 * @author Sunny
	 */
	@Override
	public void executeAutoForFillReturnAddress() {
		ShoppingOrderItemExtendDOExample example = new ShoppingOrderItemExtendDOExample();
		
		// 查询结果包括退款详情
		example.setIsIncludeShoppingRefundDetail(true);
		
		ShoppingOrderItemExtendDOExample.Criteria criteria = example.createCriteria();
		
		criteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.REFUNDING.getValue());
		criteria.andRefundStatusEqualTo(RefundStatusEnum.FILL_RETURN_ADDRESS.getValue());
		
		// 提醒时间
		String remindTime = propertyService.getByName(PropertyNameConstant.FILL_RETURN_ADDRESS_REMIND_TIME);
		// 超过提醒时间
		criteria.andGmtModifiedAddDayLessThanOrEqualTo(Integer.valueOf(remindTime), new Date());
		
		// 自动退款时间
		String refundTime = propertyService.getByName(PropertyNameConstant.FILL_RETURN_ADDRESS_REFUND_TIME);
		
		List<ShoppingOrderItemExtendDO> shoppingOrderItemDOList = shoppingOrderItemExtendDOMapper.selectByExample(example);
		
		for (ShoppingOrderItemExtendDO shoppingOrderItemExtendDO : shoppingOrderItemDOList) {
			// 发送次数为0，发送站内信和推送
			if (shoppingOrderItemExtendDO.getSendTime() == null || shoppingOrderItemExtendDO.getSendTime() <= 0) {
				toBeConfirmedForRefundRemind(shoppingOrderItemExtendDO);
			}
			
			boolean isExceeds = DateUtil.isExceeds(shoppingOrderItemExtendDO.getGmtModified(), new Date(), Integer.valueOf(refundTime), Calendar.DAY_OF_YEAR);
			
			// 如果商家未处理时间超过退款时间，平台自动退款
			if (isExceeds) {
				agreeToRefund(shoppingOrderItemExtendDO.getId(), true);
			}
		}
	}
	
	@Override
	public void executeAutoForToBeReturn() {
		ShoppingOrderItemExtendDOExample example = new ShoppingOrderItemExtendDOExample();
		
		// 查询结果包括退款详情
		example.setIsIncludeShoppingRefundDetail(true);
		
		ShoppingOrderItemExtendDOExample.Criteria criteria = example.createCriteria();
		
		criteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.REFUNDING.getValue());
		criteria.andRefundStatusEqualTo(RefundStatusEnum.TO_BE_RETURNED.getValue());
		
		// 提醒时间
		String remindTime = propertyService.getByName(PropertyNameConstant.TO_BE_RETURNED_REMIND_TIME);
		// 超过提醒时间
		criteria.andGmtModifiedAddDayLessThanOrEqualTo(Integer.valueOf(remindTime), new Date());
		
		// 自动撤销撤销时间
		String refundTime = propertyService.getByName(PropertyNameConstant.TO_BE_RETURNED_REVOKE_TIME);
		
		List<ShoppingOrderItemExtendDO> shoppingOrderItemDOList = shoppingOrderItemExtendDOMapper.selectByExample(example);
		
		for (ShoppingOrderItemExtendDO shoppingOrderItemExtendDO : shoppingOrderItemDOList) {
			// 发送次数为0，发送站内信和推送
			if (shoppingOrderItemExtendDO.getSendTime() == null || shoppingOrderItemExtendDO.getSendTime() <= 0) {
				toBeReturnRemind(shoppingOrderItemExtendDO);
			}
			
			boolean isExceeds = DateUtil.isExceeds(shoppingOrderItemExtendDO.getGmtModified(), new Date(), Integer.valueOf(refundTime), Calendar.DAY_OF_YEAR);
			
			// 买家操作超过处理时间，平台自动撤销退款申请
			if (isExceeds) {
				revokeRefundRequest(shoppingOrderItemExtendDO.getShoppingRefundDetail().getId());
			}
		}
	}
	
	@Override
	public void executeAutoForToBeRefund() {
		ShoppingOrderItemExtendDOExample example = new ShoppingOrderItemExtendDOExample();
		
		// 查询结果包括退款详情
		example.setIsIncludeShoppingRefundDetail(true);
		
		ShoppingOrderItemExtendDOExample.Criteria criteria = example.createCriteria();
		
		criteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.REFUNDING.getValue());
		criteria.andRefundStatusEqualTo(RefundStatusEnum.TO_BE_REFUNDED.getValue());
		
		/*
		 * 有物流 
		 */
		// 第一次提醒时间
		String firstRemindTime = propertyService.getByName(PropertyNameConstant.TO_BE_REFUNDED_REMIND_FIRST_TIME);
		// 第二次提醒时间
		String secondRemindTime = propertyService.getByName(PropertyNameConstant.TO_BE_REFUNDED_REMIND_SECOND_TIME);
		// 退款时间
		String refundTime = propertyService.getByName(PropertyNameConstant.TO_BE_REFUNDED_REFUND_TIME);
		
		/* 
		 * 无物流
		 */
		// 提醒时间
		String remindTime = propertyService.getByName(PropertyNameConstant.TO_BE_CONFIRMED_FOR_REFUND_REMIND_TIME);
		// 退款时间
		String confirmedRefundTime = propertyService.getByName(PropertyNameConstant.TO_BE_CONFIRMED_FOR_REFUND_REFUND_TIME);
		
		// 超过提醒时间
		List<ShoppingOrderItemExtendDO> shoppingOrderItemDOList = shoppingOrderItemExtendDOMapper.selectByExample(example);
		for (ShoppingOrderItemExtendDO shoppingOrderItemExtendDO : shoppingOrderItemDOList) {
			// 如果退款类型为退货退款
			if (ShoppingRefundTypeEnum.RETURN_REFUND.getValue().equals(shoppingOrderItemExtendDO.getShoppingRefundDetail().getType())) {
				// 发送次数为0，发送站内信和推送
				boolean isExceeds = DateUtil.isExceeds(shoppingOrderItemExtendDO.getGmtModified(), new Date(), Integer.valueOf(firstRemindTime), Calendar.DAY_OF_YEAR);
				if (!isExceeds) {
					continue;
				}
				if (shoppingOrderItemExtendDO.getSendTime() == null || shoppingOrderItemExtendDO.getSendTime() <= 0) {
					toBeRefundRemind(shoppingOrderItemExtendDO);
					continue;
				}
				
				isExceeds = DateUtil.isExceeds(shoppingOrderItemExtendDO.getGmtModified(), new Date(), Integer.valueOf(secondRemindTime), Calendar.DAY_OF_YEAR);
				if (!isExceeds) {
					continue;
				}
				if (shoppingOrderItemExtendDO.getSendTime() == null || shoppingOrderItemExtendDO.getSendTime() <= 1) {
					toBeRefundRemind(shoppingOrderItemExtendDO);
					continue;
				}
				
				isExceeds = DateUtil.isExceeds(shoppingOrderItemExtendDO.getGmtModified(), new Date(), Integer.valueOf(refundTime), Calendar.DAY_OF_YEAR);
				// 如果商家未处理时间超过退款时间，平台自动退款
				if (isExceeds) {
					agreeToRefund(shoppingOrderItemExtendDO.getShoppingRefundDetail().getId(), true);
				}
			} else if (ShoppingRefundTypeEnum.REFUND.getValue().equals(shoppingOrderItemExtendDO.getShoppingRefundDetail().getType())) {
				// 如果退款类型为退款
				// 发送次数为0，发送站内信和推送
				boolean isExceeds = DateUtil.isExceeds(shoppingOrderItemExtendDO.getGmtModified(), new Date(), Integer.valueOf(remindTime), Calendar.DAY_OF_YEAR);
				if (!isExceeds) {
					continue;
				}
				if (shoppingOrderItemExtendDO.getSendTime() == null || shoppingOrderItemExtendDO.getSendTime() <= 0) {
					toBeConfirmedForRefundRemind(shoppingOrderItemExtendDO);
					continue;
				}
				
				isExceeds = DateUtil.isExceeds(shoppingOrderItemExtendDO.getGmtModified(), new Date(), Integer.valueOf(confirmedRefundTime), Calendar.DAY_OF_YEAR);
				// 如果商家未处理时间超过退款时间，平台自动退款
				if (isExceeds) {
					agreeToRefund(shoppingOrderItemExtendDO.getShoppingRefundDetail().getId(), true);
				}
			}
		}
	}
	
	/************************************************
	 * Private Method
	 * **********************************************/
	@Transactional
	public void toBeConfirmedForRefundRemind(ShoppingOrderItemExtendDO shoppingOrderItemExtendDO) {
		
		// 更新发送次数，但是不更新更新时间字段
		ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
		shoppingOrderItemDO.setId(shoppingOrderItemExtendDO.getId());
		int sendTime = shoppingOrderItemExtendDO.getSendTime() == null ? 1 : shoppingOrderItemExtendDO.getSendTime().intValue() + 1;
		shoppingOrderItemDO.setSendTime(sendTime);
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemDO);
		
		// 用户申请退款，提醒商家处理
		ShoppingRefundToBeConfirmedForRefundRemindNotification notification = new ShoppingRefundToBeConfirmedForRefundRemindNotification();
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(shoppingOrderItemExtendDO.getShoppingOrderId());
		notification.setShoppingOrderItemId(shoppingOrderItemExtendDO.getId());
		notification.setMemberNum(shoppingOrderDO.getMemberNum());
		notification.setMerchantNum(shoppingOrderDO.getMerchantNum());
		messageProducerService.sendMessage(MqConstant.TOPIC_ORDER_SRV, MqConstant.TAG_TO_BE_CONFIRMED_FOR_REFUND_REMIND, notification);
		
	}
	
	@Transactional
	public void refundFailedRemind(ShoppingOrderItemExtendDO shoppingOrderItemExtendDO) {
		// 更新发送次数，但是不更新更新时间字段
		ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
		shoppingOrderItemDO.setId(shoppingOrderItemExtendDO.getId());
		int sendTime = shoppingOrderItemExtendDO.getSendTime() == null ? 1 : shoppingOrderItemExtendDO.getSendTime().intValue() + 1;
		shoppingOrderItemDO.setSendTime(sendTime);
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemDO);
		
		// 商家拒绝退款，提醒买家
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(shoppingOrderItemExtendDO.getShoppingOrderId());
		ShoppingRefundRefuseRefundRemindNotification notification = new ShoppingRefundRefuseRefundRemindNotification();
		notification.setShoppingOrderItemId(shoppingOrderDO.getId());
		notification.setMemberNum(shoppingOrderDO.getMemberNum());
		messageProducerService.sendMessage(MqConstant.TOPIC_ORDER_SRV, MqConstant.TAG_REFUSE_REFUND_REMIND, notification);
	}
	
	@Transactional
	public void toBeReturnRemind(ShoppingOrderItemExtendDO shoppingOrderItemExtendDO) {
		// 更新发送次数，但是不更新更新时间字段
		ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
		shoppingOrderItemDO.setId(shoppingOrderItemExtendDO.getId());
		int sendTime = shoppingOrderItemExtendDO.getSendTime() == null ? 1 : shoppingOrderItemExtendDO.getSendTime().intValue() + 1;
		shoppingOrderItemDO.setSendTime(sendTime);
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemDO);
		
		// 商家填写退货地址，提醒买家退货
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(shoppingOrderItemExtendDO.getShoppingOrderId());
		ShoppingRefundFillReturnAddressRemindNotification notification = new ShoppingRefundFillReturnAddressRemindNotification();
		notification.setShoppingOrderItemId(shoppingOrderItemExtendDO.getId());
		notification.setMemberNum(shoppingOrderDO.getMemberNum());
		messageProducerService.sendMessage(MqConstant.TOPIC_ORDER_SRV, MqConstant.TAG_FILL_RETURN_ADDRESS_REMIND, notification);
	}
	
	@Transactional
	public void toBeRefundRemind(ShoppingOrderItemExtendDO shoppingOrderItemExtendDO) {
		// 更新发送次数，但是不更新更新时间字段
		ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
		shoppingOrderItemDO.setId(shoppingOrderItemExtendDO.getId());
		int sendTime = shoppingOrderItemExtendDO.getSendTime() == null ? 1 : shoppingOrderItemExtendDO.getSendTime().intValue() + 1;
		shoppingOrderItemDO.setSendTime(sendTime);
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemDO);
		
		// 用户填写退货物流，提醒买家退款
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(shoppingOrderItemExtendDO.getShoppingOrderId());
		ShoppingRefundToBeReturnRemindNotification notification = new ShoppingRefundToBeReturnRemindNotification();
		notification.setShoppingOrderItemId(shoppingOrderItemExtendDO.getId());
		notification.setMerchantNum(shoppingOrderDO.getMerchantNum());
		notification.setMemberNum(shoppingOrderDO.getMemberNum());
		notification.setOrderNum(shoppingOrderDO.getOrderNum());
		notification.setWaybillNum(shoppingOrderItemExtendDO.getShoppingRefundDetail().getWaybillNum());
		messageProducerService.sendMessage(MqConstant.TOPIC_ORDER_SRV, MqConstant.TAG_TO_BE_RETURN_REMIND, notification);
	}
	
	@Transactional
	public int agreeToRefund(Long id, boolean isAgree) {
		ShoppingRefundDetailAgreeToRefundForeignParam param = new ShoppingRefundDetailAgreeToRefundForeignParam();
		param.setIsAgree(isAgree);
		return agreeToRefund(id, param);
	}
}
