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
import com.lawu.eshop.order.srv.exception.CanNotAgreeToARefundException;
import com.lawu.eshop.order.srv.exception.CanNotAgreeToApplyException;
import com.lawu.eshop.order.srv.exception.CanNotApplyForPlatformInterventionException;
import com.lawu.eshop.order.srv.exception.CanNotCancelApplicationException;
import com.lawu.eshop.order.srv.exception.CanNotFillInTheReturnAddressException;
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
		ShoppingRefundDetailDO shoppingRefundDetailDO = shoppingRefundDetailDOMapper.selectByPrimaryKey(id);
		return ShoppingRefundDetailConverter.convert(shoppingRefundDetailDO);
	}

	/**
	 * 根据购物订单项id 获取购物退货详情
	 * 
	 * @param shoppingOrderItemId
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
		// 只有待退货状态才能填写退货物流
		if (!shoppingOrderItemDO.getRefundStatus().equals(RefundStatusEnum.TO_BE_RETURNED.getValue())) {
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
	 * @param merchantId
	 *            商家id
	 * @param param
	 * 			     退货地址信息
	 * @author jiangxinjun
	 * @date 2017年7月11日
	 */
	@Transactional
	@Override
	public void fillReturnAddress(Long id, Long merchantId, ShoppingRefundDetailRerurnAddressParam param) {
		ShoppingRefundDetailDO shoppingRefundDetailDO = shoppingRefundDetailDOMapper.selectByPrimaryKey(id);
		if (shoppingRefundDetailDO == null || shoppingRefundDetailDO.getStatus().equals(StatusEnum.INVALID.getValue())) {
			throw new DataNotExistException(ExceptionMessageConstant.SHOPPING_ORDER_REFUND_DATA_DOES_NOT_EXIST);
		}
		ShoppingOrderItemDO shoppingOrderItemDO = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingRefundDetailDO.getShoppingOrderItemId());
		if (shoppingOrderItemDO == null) {
			throw new DataNotExistException(ExceptionMessageConstant.SHOPPING_ORDER_ITEM_DATA_DOES_NOT_EXIST);
		}
		// 订单状态必须为退款中
		if (!shoppingOrderItemDO.getOrderStatus().equals(ShoppingOrderStatusEnum.REFUNDING.getValue())) {
			throw new CanNotFillInTheReturnAddressException(ExceptionMessageConstant.ORDER_STATUS_IS_NOT_TO_BE_REFUND_STATUS);
		}
		/*
		 *  1.退款状态必须为填写退货地址
		 *  2.退款类型为退货退款,并且退款状态当前为待确认状态	
		 */
		if (!shoppingOrderItemDO.getRefundStatus().equals(RefundStatusEnum.FILL_RETURN_ADDRESS.getValue())
				&& !(RefundStatusEnum.TO_BE_CONFIRMED.getValue().equals(shoppingOrderItemDO.getRefundStatus()) && ShoppingRefundTypeEnum.RETURN_REFUND.getValue().equals(shoppingRefundDetailDO.getType()))) {
			throw new CanNotFillInTheReturnAddressException(ExceptionMessageConstant.REFUND_STATUS_DOES_NOT_MATCH);
		}
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getShoppingOrderId());
		if (shoppingOrderDO == null) {
			throw new DataNotExistException(ExceptionMessageConstant.SHOPPING_ORDER_ITEM_DATA_DOES_NOT_EXIST);
		}
		if (!shoppingOrderDO.getMerchantId().equals(merchantId)) {
			throw new IllegalOperationException(ExceptionMessageConstant.ILLEGAL_OPERATION_SHOPPING_ORDER);
		}
		
		ShoppingRefundDetailDO shoppingRefundDetailUpdateDO = new ShoppingRefundDetailDO();
		shoppingRefundDetailUpdateDO.setId(shoppingRefundDetailDO.getId());
		// 更新退款详情的退货地址信息
		shoppingRefundDetailUpdateDO.setGmtFill(new Date());
		shoppingRefundDetailUpdateDO.setConsigneeName(param.getConsigneeName());
		shoppingRefundDetailUpdateDO.setConsigneeMobile(param.getConsigneeMobile());
		shoppingRefundDetailUpdateDO.setConsigneeAddress(param.getConsigneeAddress());
		shoppingRefundDetailUpdateDO.setGmtModified(new Date());
		shoppingRefundDetailDOMapper.updateByPrimaryKeySelective(shoppingRefundDetailUpdateDO);
		
		ShoppingOrderItemDO shoppingOrderItemUpdateDO = new ShoppingOrderItemDO();
		shoppingOrderItemUpdateDO.setId(shoppingOrderItemDO.getId());
		// 更新订单项退款状态为待退货
		shoppingOrderItemUpdateDO.setRefundStatus(RefundStatusEnum.TO_BE_RETURNED.getValue());
		// 重置发送提醒的次数
		shoppingOrderItemUpdateDO.setSendTime(0);
		shoppingOrderItemUpdateDO.setGmtModified(new Date());
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemUpdateDO);
		
		// 加入退款流程
		ShoppingRefundProcessDO shoppingRefundProcessDO = new ShoppingRefundProcessDO();
		shoppingRefundProcessDO.setRefundStatus(shoppingOrderItemUpdateDO.getRefundStatus());
		shoppingRefundProcessDO.setShoppingRefundDetailId(shoppingRefundDetailUpdateDO.getId());
		shoppingRefundProcessDO.setGmtCreate(new Date());
		shoppingRefundProcessDOMapper.insertSelective(shoppingRefundProcessDO);
		
		// 商家填写退货地址，提醒买家退货
		ShoppingRefundFillReturnAddressRemindNotification notification = new ShoppingRefundFillReturnAddressRemindNotification();
		notification.setShoppingOrderItemId(shoppingOrderItemUpdateDO.getId());
		notification.setMemberNum(shoppingOrderDO.getMemberNum());
		messageProducerService.sendMessage(MqConstant.TOPIC_ORDER_SRV, MqConstant.TAG_FILL_RETURN_ADDRESS_REMIND, notification);
	}

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
	@Transactional
	@Override
	public void agreeToApply(Long id, Long merchantId, ShoppingRefundDetailAgreeToApplyForeignParam param) {
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
			throw new CanNotAgreeToApplyException(ExceptionMessageConstant.ORDER_STATUS_IS_NOT_TO_BE_REFUND_STATUS);
		}
		// 只有待确认状态才能同意会员的退款申请
		if (!shoppingOrderItemDO.getRefundStatus().equals(RefundStatusEnum.TO_BE_CONFIRMED.getValue())) {
			throw new CanNotAgreeToApplyException(ExceptionMessageConstant.THE_STATUS_OF_THE_REFUND_IS_NOT_PENDING);
		}
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getShoppingOrderId());
		if (shoppingOrderDO == null) {
			throw new DataNotExistException(ExceptionMessageConstant.SHOPPING_ORDER_ITEM_DATA_DOES_NOT_EXIST);
		}
		if (!shoppingOrderDO.getMerchantId().equals(merchantId)) {
			throw new IllegalOperationException(ExceptionMessageConstant.ILLEGAL_OPERATION_SHOPPING_ORDER);
		}
		ShoppingOrderItemDO shoppingOrderItemUpdateDO = new ShoppingOrderItemDO();
		shoppingOrderItemUpdateDO.setId(shoppingOrderItemDO.getId());
		// 更新退款状态
		if (param.getIsAgree()) {
			// 判断是否需要退货
			if (shoppingRefundDetailDO.getType().equals(ShoppingRefundTypeEnum.REFUND.getValue())) {
				shoppingOrderItemUpdateDO.setRefundStatus(RefundStatusEnum.TO_BE_REFUNDED.getValue());
			} else {
				shoppingOrderItemUpdateDO.setRefundStatus(RefundStatusEnum.FILL_RETURN_ADDRESS.getValue());
			}
		} else {
			shoppingOrderItemUpdateDO.setRefundStatus(RefundStatusEnum.REFUND_FAILED.getValue());
		}
		// 重置发送提醒的次数
		shoppingOrderItemUpdateDO.setSendTime(0);
		shoppingOrderItemUpdateDO.setGmtModified(new Date());
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemUpdateDO);
		ShoppingRefundDetailDO shoppingRefundDetailUpdateDO = new ShoppingRefundDetailDO();
		shoppingRefundDetailUpdateDO.setId(shoppingRefundDetailDO.getId());
		// 更新退款详情
		shoppingRefundDetailUpdateDO.setGmtConfirmed(new Date());
		if (!param.getIsAgree()) {
			shoppingRefundDetailUpdateDO.setGmtRefund(new Date());
		}
		shoppingRefundDetailUpdateDO.setRefusalReasons(param.getRefusalReasons());
		shoppingRefundDetailUpdateDO.setIsAgree(param.getIsAgree());
		shoppingRefundDetailUpdateDO.setGmtModified(new Date());
		shoppingRefundDetailDOMapper.updateByPrimaryKeySelective(shoppingRefundDetailUpdateDO);
		// 加入退款流程
		ShoppingRefundProcessDO shoppingRefundProcessDO = new ShoppingRefundProcessDO();
		shoppingRefundProcessDO.setRefundStatus(shoppingOrderItemUpdateDO.getRefundStatus());
		shoppingRefundProcessDO.setShoppingRefundDetailId(shoppingRefundDetailDO.getId());
		shoppingRefundProcessDO.setGmtCreate(new Date());
		shoppingRefundProcessDOMapper.insertSelective(shoppingRefundProcessDO);
		if (!param.getIsAgree()) {
			// 商家拒绝退款，提醒买家
			ShoppingRefundRefuseRefundRemindNotification notification = new ShoppingRefundRefuseRefundRemindNotification();
			notification.setShoppingOrderItemId(shoppingOrderDO.getId());
			notification.setMemberNum(shoppingOrderDO.getMemberNum());
			messageProducerService.sendMessage(MqConstant.TOPIC_ORDER_SRV, MqConstant.TAG_REFUSE_REFUND_REMIND, notification);
		}
	}

	/**
	 * 商家是否同意退款
	 * 
	 * @param id
	 *            退款详情Id
	 * @param merchantId
	 *            商家Id
	 * @param param
	 * 			      参数
	 * @author jiangxinjun
	 * @date 2017年7月11日
	 */
	@Transactional
	@Override
	public void agreeToRefund(Long id, Long merchantId, ShoppingRefundDetailAgreeToRefundForeignParam param) {
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
			throw new CanNotAgreeToARefundException(ExceptionMessageConstant.ORDER_STATUS_IS_NOT_TO_BE_REFUND_STATUS);
		}
		/*
		 *  1.退款状态必须为待退款或者平台介入状态
		 *  2.待确认状态，如果退款类型为退款可以直接退款
		 *  3.如果退款状态为平台介入,运营人员可以操作退款
		 */
		if (!shoppingOrderItemDO.getRefundStatus().equals(RefundStatusEnum.TO_BE_REFUNDED.getValue())
				&& !shoppingOrderItemDO.getRefundStatus().equals(RefundStatusEnum.PLATFORM_INTERVENTION.getValue())
				// 
				&& !(ShoppingRefundTypeEnum.REFUND.getValue().equals(shoppingRefundDetailDO.getType()) && RefundStatusEnum.TO_BE_CONFIRMED.getValue().equals(shoppingOrderItemDO.getRefundStatus())) 
				&& !(!param.getIsAgree() && RefundStatusEnum.TO_BE_CONFIRMED.getValue().equals(shoppingOrderItemDO.getRefundStatus()))) {
			throw new CanNotAgreeToARefundException(ExceptionMessageConstant.REFUND_STATUS_DOES_NOT_MATCH);
		}
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getShoppingOrderId());
		if (shoppingOrderDO == null) {
			throw new DataNotExistException(ExceptionMessageConstant.SHOPPING_ORDER_DATA_NOT_EXIST);
		}
		if (merchantId != null && !shoppingOrderDO.getMerchantId().equals(merchantId)) {
			throw new IllegalOperationException(ExceptionMessageConstant.ILLEGAL_OPERATION_SHOPPING_ORDER);
		}
		ShoppingOrderItemDO shoppingOrderItemUpdateDO = new ShoppingOrderItemDO();
		shoppingOrderItemUpdateDO.setId(shoppingOrderItemDO.getId());
		if (param.getIsAgree()) {
			shoppingOrderItemUpdateDO.setRefundStatus(RefundStatusEnum.REFUND_SUCCESSFULLY.getValue());
			// 设置购物订单项的订单状态为交易关闭
			shoppingOrderItemUpdateDO.setOrderStatus(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue());
		} else {
			shoppingOrderItemUpdateDO.setRefundStatus(RefundStatusEnum.REFUND_FAILED.getValue());
		}
		// 重置发送提醒的次数
		shoppingOrderItemUpdateDO.setSendTime(0);
		shoppingOrderItemUpdateDO.setGmtModified(new Date());
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemUpdateDO);
		// 更新退款详情
		ShoppingRefundDetailDO shoppingRefundDetailUpdateDO = new ShoppingRefundDetailDO();
		shoppingRefundDetailUpdateDO.setId(shoppingRefundDetailDO.getId());
		shoppingRefundDetailUpdateDO.setIsAgree(param.getIsAgree());
		shoppingRefundDetailUpdateDO.setRefusalReasons(param.getRefusalReasons());
		shoppingRefundDetailUpdateDO.setGmtRefund(new Date());
		shoppingRefundDetailUpdateDO.setGmtModified(new Date());
		shoppingRefundDetailUpdateDO.setRefuseImages(param.getRefuseImages());
		shoppingRefundDetailDOMapper.updateByPrimaryKeySelective(shoppingRefundDetailUpdateDO);
		// 加入退款流程
		ShoppingRefundProcessDO shoppingRefundProcessDO = new ShoppingRefundProcessDO();
		shoppingRefundProcessDO.setRefundStatus(shoppingOrderItemUpdateDO.getRefundStatus());
		shoppingRefundProcessDO.setShoppingRefundDetailId(shoppingRefundDetailUpdateDO.getId());
		shoppingRefundProcessDO.setGmtCreate(new Date());
		shoppingRefundProcessDOMapper.insertSelective(shoppingRefundProcessDO);
		if (param.getIsAgree()) {
			// 如果当前订单下的所有订单项都是交易关闭状态。关闭订单
			ShoppingOrderItemDOExample shoppingOrderItemDOExample = new ShoppingOrderItemDOExample();
			shoppingOrderItemDOExample.createCriteria().andShoppingOrderIdEqualTo(shoppingOrderItemDO.getShoppingOrderId()).andOrderStatusNotEqualTo(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue());
			long count = shoppingOrderItemDOMapper.countByExample(shoppingOrderItemDOExample);
			ShoppingOrderExtendDO shoppingOrderExtendDO = new ShoppingOrderExtendDO();
			shoppingOrderExtendDO.setId(shoppingOrderItemDO.getShoppingOrderId());
			shoppingOrderExtendDO.setGmtModified(new Date());
			// 如果购物订单下的所有订单项都是关闭状态关闭购物订单
			if (count <= 0) {
				shoppingOrderExtendDO.setOrderStatus(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue());
			}
			// 更新实际支付给商家的金额
			shoppingOrderExtendDO.setActualAmountSubtraction(shoppingOrderItemDO.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemDO.getQuantity())));
			
			// 发送MQ消息给property模块，退款给用户
			shoppingRefundAgreeToRefundTransactionMainServiceImpl.sendNotice(shoppingOrderItemDO.getId());
			
			// 发送MQ消息给mall模块，删除商品订单评价
			shoppingRefundAgreeToRefundDeleteCommentTransactionMainServiceImpl.sendNotice(shoppingOrderItemDO.getId());
			
			// 发送MQ消息之后再更新购物订单表，因为需要获取订单取消之前的订单状态
			shoppingOrderExtendDOMapper.updateByPrimaryKeySelective(shoppingOrderExtendDO);
			
			// 商家同意退款，提醒买家
			ShoppingRefundToBeRefundRemindNotification notification = new ShoppingRefundToBeRefundRemindNotification();
			notification.setShoppingOrderItemId(shoppingOrderDO.getId());
			notification.setMemberNum(shoppingOrderDO.getMemberNum());
			notification.setRefundAmount(shoppingRefundDetailUpdateDO.getAmount());
			messageProducerService.sendMessage(MqConstant.TOPIC_ORDER_SRV, MqConstant.TAG_TO_BE_REFUND_REMIND, notification);
		} else {
			// 商家拒绝退款，提醒买家
			ShoppingRefundRefuseRefundRemindNotification notification = new ShoppingRefundRefuseRefundRemindNotification();
			notification.setShoppingOrderItemId(shoppingOrderDO.getId());
			notification.setMemberNum(shoppingOrderDO.getMemberNum());
			messageProducerService.sendMessage(MqConstant.TOPIC_ORDER_SRV, MqConstant.TAG_REFUSE_REFUND_REMIND, notification);
		}
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
	public void revokeRefundRequest(Long id) {
		revokeRefundRequest(id, null);
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
	public void revokeRefundRequest(Long id, Long memberId) {
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
			throw new CanNotCancelApplicationException(ExceptionMessageConstant.ORDER_STATUS_IS_NOT_TO_BE_REFUND_STATUS);
		}
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getShoppingOrderId());
		if (shoppingOrderDO == null) {
			throw new DataNotExistException(ExceptionMessageConstant.SHOPPING_ORDER_ITEM_DATA_DOES_NOT_EXIST);
		}
		if (memberId != null && !shoppingOrderDO.getMemberId().equals(memberId)) {
			throw new IllegalOperationException(ExceptionMessageConstant.ILLEGAL_OPERATION_SHOPPING_ORDER);
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
		ShoppingRefundDetailDO shoppingRefundDetailUpdateDO = new ShoppingRefundDetailDO();
		shoppingRefundDetailUpdateDO.setId(shoppingRefundDetailDO.getId());
		shoppingRefundDetailUpdateDO.setStatus(StatusEnum.INVALID.getValue());
		shoppingRefundDetailUpdateDO.setGmtModified(new Date());
		shoppingRefundDetailDOMapper.updateByPrimaryKeySelective(shoppingRefundDetailUpdateDO);
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
				agreeToRefund(shoppingOrderItemExtendDO.getShoppingRefundDetail().getId());
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
				agreeToRefund(shoppingOrderItemExtendDO.getId());
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
				agreeToRefund(shoppingOrderItemExtendDO.getId());
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
					agreeToRefund(shoppingOrderItemExtendDO.getShoppingRefundDetail().getId());
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
					agreeToRefund(shoppingOrderItemExtendDO.getShoppingRefundDetail().getId());
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
	
	/**
	 * 商家是否同意退款
	 * 
	 * @param id
	 *            退款详情Id
	 * @author jiangxinjun
	 * @date 2017年7月11日
	 */
	@Transactional
	public void agreeToRefund(Long id) {
		ShoppingRefundDetailAgreeToRefundForeignParam param = new ShoppingRefundDetailAgreeToRefundForeignParam();
		param.setIsAgree(true);
		agreeToRefund(id, null, param);
	}
}
