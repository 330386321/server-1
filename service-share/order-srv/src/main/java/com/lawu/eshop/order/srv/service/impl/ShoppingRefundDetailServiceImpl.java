package com.lawu.eshop.order.srv.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.order.constants.RefundStatusEnum;
import com.lawu.eshop.order.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.order.constants.ShoppingRefundTypeEnum;
import com.lawu.eshop.order.constants.StatusEnum;
import com.lawu.eshop.order.param.ShoppingRefundDetailLogisticsInformationParam;
import com.lawu.eshop.order.param.ShoppingRefundDetailRerurnAddressParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundDetailAgreeToApplyForeignParam;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemExtendBO;
import com.lawu.eshop.order.srv.bo.ShoppingRefundDetailBO;
import com.lawu.eshop.order.srv.constants.PropertyNameConstant;
import com.lawu.eshop.order.srv.converter.ShoppingOrderItemExtendConverter;
import com.lawu.eshop.order.srv.converter.ShoppingRefundDetailConverter;
import com.lawu.eshop.order.srv.domain.ShoppingOrderDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDOExample;
import com.lawu.eshop.order.srv.domain.ShoppingRefundDetailDO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderExtendDO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderItemExtendDO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderItemExtendDOExample;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderItemDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingRefundDetailDOMapper;
import com.lawu.eshop.order.srv.mapper.extend.ShoppingOrderExtendDOMapper;
import com.lawu.eshop.order.srv.mapper.extend.ShoppingOrderItemExtendDOMapper;
import com.lawu.eshop.order.srv.service.PropertyService;
import com.lawu.eshop.order.srv.service.ShoppingRefundDetailService;
import com.lawu.eshop.utils.DateUtil;

@Service
public class ShoppingRefundDetailServiceImpl implements ShoppingRefundDetailService {

	@Autowired
	private ShoppingRefundDetailDOMapper shoppingRefundDetailDOMapper;

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
	@Qualifier("shoppingOrderAgreeToRefundTransactionMainServiceImpl")
	private TransactionMainService<Reply> shoppingOrderAgreeToRefundTransactionMainServiceImpl;
	
	@Autowired
	@Qualifier("shoppingRefundToBeConfirmedForRefundRemindTransactionMainServiceImpl")
	private TransactionMainService<Reply> shoppingRefundToBeConfirmedForRefundRemindTransactionMainServiceImpl;
	
	@Autowired
	@Qualifier("shoppingRefundToBeConfirmedForReturnRefundRemindTransactionMainServiceImpl")
	private TransactionMainService<Reply> shoppingRefundToBeConfirmedForReturnRefundRemindTransactionMainServiceImpl;
	
	@Autowired
	@Qualifier("shoppingRefundRefundFailedRemindTransactionMainServiceImpl")
	private TransactionMainService<Reply> shoppingRefundRefundFailedRemindTransactionMainServiceImpl;
	
	@Autowired
	@Qualifier("shoppingRefundFillReturnAddressTransactionMainServiceImpl")
	private TransactionMainService<Reply> shoppingRefundFillReturnAddressTransactionMainServiceImpl;
	
	@Autowired
	@Qualifier("shoppingRefundToBeReturnRemindTransactionMainServiceImpl")
	private TransactionMainService<Reply> shoppingRefundToBeReturnRemindTransactionMainServiceImpl;
	
	@Autowired
	@Qualifier("shoppingRefundToBeRefundRemindTransactionMainServiceImpl")
	private TransactionMainService<Reply> shoppingRefundToBeRefundRemindTransactionMainServiceImpl;

	@Autowired
	@Qualifier("shoppingOrderAgreeToRefundDeleteCommentTransactionMainServiceImpl")
	private TransactionMainService<Reply> shoppingOrderAgreeToRefundDeleteCommentTransactionMainServiceImpl;
	
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
	public ShoppingOrderItemExtendBO getByShoppingOrderitemId(Long shoppingOrderItemId) {
		if (shoppingOrderItemId == null || shoppingOrderItemId <= 0) {
			return null;
		}
		
		ShoppingOrderItemExtendDOExample shoppingOrderItemExtendDOExample = new ShoppingOrderItemExtendDOExample();
		shoppingOrderItemExtendDOExample.setIsIncludeShoppingRefundDetail(true);
		ShoppingOrderItemExtendDOExample.Criteria criteria = shoppingOrderItemExtendDOExample.createCriteria();
		criteria.andIdEqualTo(shoppingOrderItemId);
		// 找到有效记录
		criteria.andSRDStatusEqualTo(StatusEnum.VALID.getValue());
		
		List<ShoppingOrderItemExtendDO> list = shoppingOrderItemExtendDOMapper.selectByExample(shoppingOrderItemExtendDOExample);

		return ShoppingOrderItemExtendConverter.convert(list.get(0));
	}

	/**
	 * 买家填写退货的物流信息
	 * 
	 * @param param
	 *            退款详情
	 * @param param
	 *            退款详情物流信息
	 * @return
	 */
	@Transactional
	@Override
	public Integer fillLogisticsInformation(ShoppingRefundDetailBO shoppingRefundDetailBO, ShoppingRefundDetailLogisticsInformationParam param) {
		// 更新订单项状态为待退款
		ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
		shoppingOrderItemDO.setId(shoppingRefundDetailBO.getShoppingOrderItemId());
		shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.TO_BE_REFUNDED.getValue());
		// 重置发送提醒的次数
		shoppingOrderItemDO.setSendTime(0);
		shoppingOrderItemDO.setGmtModified(new Date());
		Integer result = shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemDO);

		// 更新退款详情的物流信息
		ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
		shoppingRefundDetailDO.setId(shoppingRefundDetailBO.getId());

		shoppingRefundDetailDO.setGmtSubmit(new Date());
		shoppingRefundDetailDO.setExpressCompanyId(param.getExpressCompanyId());
		shoppingRefundDetailDO.setExpressCompanyCode(param.getExpressCompanyCode());
		shoppingRefundDetailDO.setExpressCompanyName(param.getExpressCompanyName());
		shoppingRefundDetailDO.setWaybillNum(param.getWaybillNum());
		shoppingRefundDetailDO.setGmtModified(new Date());

		shoppingRefundDetailDOMapper.updateByPrimaryKeySelective(shoppingRefundDetailDO);

		return result;
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
		if (!shoppingOrderItemDO.getOrderStatus().equals(ShoppingOrderStatusEnum.REFUNDING)) {
			return ResultCode.NOT_REFUNDING;
		}

		// 退款状态必须为填写退货地址
		if (!shoppingOrderItemDO.getRefundStatus().equals(RefundStatusEnum.FILL_RETURN_ADDRESS)) {
			return ResultCode.ORDER_NOT_FILL_RETURN_ADDRESS;
		}
		
		// 商家判断是否需要寄回货物
		if (param.getIsNeedReturn()) {
			// 更新订单项退款状态为待退货
			shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.TO_BE_RETURNED.getValue());
	
			// 更新退款详情的退货地址信息
			shoppingRefundDetailDO.setGmtFill(new Date());
			shoppingRefundDetailDO.setConsigneeName(param.getConsigneeName());
			shoppingRefundDetailDO.setConsigneeMobile(param.getConsigneeMobile());
			shoppingRefundDetailDO.setConsigneeAddress(param.getConsigneeAddress());
			shoppingRefundDetailDO.setGmtModified(new Date());
	
			shoppingRefundDetailDOMapper.updateByPrimaryKeySelective(shoppingRefundDetailDO);
		} else {
			// 更新订单项退款状态为待退货
			shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.TO_BE_REFUNDED.getValue());
		}
		
		// 重置发送提醒的次数
		shoppingOrderItemDO.setSendTime(0);
		shoppingOrderItemDO.setGmtModified(new Date());
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemDO);
		
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
	public Integer agreeToApply(ShoppingRefundDetailBO shoppingRefundDetailBO, ShoppingRefundDetailAgreeToApplyForeignParam param) {
		// 更新订单项状态为待退款
		ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
		shoppingOrderItemDO.setId(shoppingRefundDetailBO.getShoppingOrderItemId());

		if (param.getIsAgree()) {
			// 判断是否需要退货
			if (shoppingRefundDetailBO.getType().equals(ShoppingRefundTypeEnum.REFUND)) {
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

		Integer result = shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemDO);

		// 更新退款详情
		ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
		shoppingRefundDetailDO.setId(shoppingRefundDetailBO.getId());

		shoppingRefundDetailDO.setGmtConfirmed(new Date());
		if (!param.getIsAgree()) {
			shoppingRefundDetailDO.setGmtRefund(new Date());
		}
		shoppingRefundDetailDO.setIsAgree(param.getIsAgree());
		shoppingRefundDetailDO.setGmtModified(new Date());
		shoppingRefundDetailDOMapper.updateByPrimaryKeySelective(shoppingRefundDetailDO);

		return result;
	}

	/**
	 * 商家是否同意退款
	 * 
	 * @param id
	 *            退款详情
	 * @param isAgree
	 *            是否同意退款
	 * @return
	 */
	@Transactional
	@Override
	public int agreeToRefund(Long id, boolean isAgree) {
		
		if (id == null || id <= 0) {
			return ResultCode.ID_EMPTY;
		}
		
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

		// 退款状态必须为填写退货地址
		if (!shoppingOrderItemExtendDO.getRefundStatus().equals(RefundStatusEnum.TO_BE_REFUNDED.getValue())
				&& !shoppingOrderItemExtendDO.getRefundStatus().equals(RefundStatusEnum.PLATFORM_INTERVENTION.getValue())) {
			return ResultCode.ORDER_NOT_TO_BE_REFUNDED;
		}
		
		// 更新订单项状态为待退款
		if (isAgree) {
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
		shoppingRefundDetailDO.setGmtRefund(new Date());
		shoppingRefundDetailDO.setGmtModified(new Date());
		shoppingRefundDetailDOMapper.updateByPrimaryKeySelective(shoppingRefundDetailDO);
		
		if (isAgree) {
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
			shoppingOrderAgreeToRefundTransactionMainServiceImpl.sendNotice(shoppingOrderItemExtendDO.getId());
			
			// 发送MQ消息给mall模块，删除商品订单评价
			shoppingOrderAgreeToRefundDeleteCommentTransactionMainServiceImpl.sendNotice(shoppingOrderItemExtendDO.getId());
		}
		
		return ResultCode.SUCCESS;
	}

	/**
	 * 如果商家拒绝买家的退款申请或者拒绝退款 买家可以申请平台介入
	 * 
	 * @param ORDER_NOT_REFUND_FAILED
	 *            退款详情
	 * @param param
	 *            参数 是否需要平台介入
	 * @return
	 */
	@Transactional
	@Override
	public Integer platformIntervention(ShoppingRefundDetailBO shoppingRefundDetailBO) {
		// 更新订单项的退款状态为平台介入
		ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
		shoppingOrderItemDO.setId(shoppingRefundDetailBO.getShoppingOrderItemId());
		shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.PLATFORM_INTERVENTION.getValue());
		// 重置发送提醒的次数
		shoppingOrderItemDO.setSendTime(0);
		shoppingOrderItemDO.setGmtModified(new Date());
		Integer result = shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemDO);

		// 更新退款详情
		ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
		shoppingRefundDetailDO.setId(shoppingRefundDetailBO.getId());
		shoppingRefundDetailDO.setGmtIntervention(new Date());
		shoppingRefundDetailDO.setGmtModified(new Date());
		shoppingRefundDetailDOMapper.updateByPrimaryKeySelective(shoppingRefundDetailDO);

		return result;

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
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemDO);

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
		
		// 查询结果包括退款详情
		example.setIsIncludeShoppingRefundDetail(true);
		
		ShoppingOrderItemExtendDOExample.Criteria criteria = example.createCriteria();
		
		criteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.REFUNDING.getValue());
		criteria.andRefundStatusEqualTo(RefundStatusEnum.TO_BE_CONFIRMED.getValue());
		criteria.andSRDTypeEqualTo(ShoppingRefundTypeEnum.REFUND.getValue());
		
		// 提醒时间
		String remindTime = propertyService.getByName(PropertyNameConstant.TO_BE_CONFIRMED_FOR_REFUND_REMIND_TIME);
		// 超过提醒时间
		criteria.andGmtModifiedAddDayLessThanOrEqualTo(Integer.valueOf(remindTime), new Date());
		
		// 退款时间
		String refundTime = propertyService.getByName(PropertyNameConstant.TO_BE_CONFIRMED_FOR_REFUND_REFUND_TIME);
		
		List<ShoppingOrderItemExtendDO> shoppingOrderItemDOList = shoppingOrderItemExtendDOMapper.selectByExample(example);
		
		boolean isExceeds = false;
		for (ShoppingOrderItemExtendDO shoppingOrderItemExtendDO : shoppingOrderItemDOList) {
			// 发送次数为0，发送站内信和推送
			if (shoppingOrderItemExtendDO.getSendTime() == null || shoppingOrderItemExtendDO.getSendTime() <= 0) {
				toBeConfirmedForRefundRemind(shoppingOrderItemExtendDO);
			}
			
			isExceeds = DateUtil.isExceeds(shoppingOrderItemExtendDO.getGmtModified(), new Date(), Integer.valueOf(refundTime), Calendar.DAY_OF_YEAR);
			
			// 如果商家未处理时间超过退款时间，平台自动退款
			if (isExceeds) {
				agreeToRefund(shoppingOrderItemExtendDO.getId(), true);
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
		
		// 查询结果包括退款详情
		example.setIsIncludeShoppingRefundDetail(true);
		
		ShoppingOrderItemExtendDOExample.Criteria criteria = example.createCriteria();
		
		criteria.andOrderStatusEqualTo(ShoppingOrderStatusEnum.REFUNDING.getValue());
		criteria.andRefundStatusEqualTo(RefundStatusEnum.TO_BE_CONFIRMED.getValue());
		criteria.andSRDTypeEqualTo(ShoppingRefundTypeEnum.RETURN_REFUND.getValue());
		
		// 提醒时间
		String remindTime = propertyService.getByName(PropertyNameConstant.TO_BE_CONFIRMED_FOR_RETURN_REFUND_REMIND_TIME);
		// 超过提醒时间
		criteria.andGmtModifiedAddDayLessThanOrEqualTo(Integer.valueOf(remindTime), new Date());
		
		// 退款时间
		String refundTime = propertyService.getByName(PropertyNameConstant.TO_BE_CONFIRMED_FOR_RETURN_REFUND_REFUND_TIME);
		
		List<ShoppingOrderItemExtendDO> shoppingOrderItemDOList = shoppingOrderItemExtendDOMapper.selectByExample(example);
		
		boolean isExceeds = false;
		for (ShoppingOrderItemExtendDO shoppingOrderItemExtendDO : shoppingOrderItemDOList) {
			// 发送次数为0，发送站内信和推送
			if (shoppingOrderItemExtendDO.getSendTime() == null || shoppingOrderItemExtendDO.getSendTime() <= 0) {
				toBeConfirmedForReturnRefundRemind(shoppingOrderItemExtendDO);
			}
			
			isExceeds = DateUtil.isExceeds(shoppingOrderItemExtendDO.getGmtModified(), new Date(), Integer.valueOf(refundTime), Calendar.DAY_OF_YEAR);
			
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
		criteria.andRefundStatusEqualTo(RefundStatusEnum.REFUND_FAILED.getValue());
		
		// 提醒时间
		String remindTime = propertyService.getByName(PropertyNameConstant.REFUND_FAILED_REMIND_TIME);
		// 超过提醒时间
		criteria.andGmtModifiedAddDayLessThanOrEqualTo(Integer.valueOf(remindTime), new Date());
		
		// 自动撤销撤销时间
		String refundTime = propertyService.getByName(PropertyNameConstant.REFUND_FAILED_REVOKE_TIME);
		
		List<ShoppingOrderItemExtendDO> shoppingOrderItemDOList = shoppingOrderItemExtendDOMapper.selectByExample(example);
		
		boolean isExceeds = false;
		for (ShoppingOrderItemExtendDO shoppingOrderItemExtendDO : shoppingOrderItemDOList) {
			// 发送次数为0，发送站内信和推送
			if (shoppingOrderItemExtendDO.getSendTime() == null || shoppingOrderItemExtendDO.getSendTime() <= 0) {
				refundFailedRemind(shoppingOrderItemExtendDO);
			}
			
			isExceeds = DateUtil.isExceeds(shoppingOrderItemExtendDO.getGmtModified(), new Date(), Integer.valueOf(refundTime), Calendar.DAY_OF_YEAR);
			
			// 买家操作超过处理时间，平台自动撤销退款申请
			if (isExceeds) {
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
		
		// 自动撤销撤销时间
		String refundTime = propertyService.getByName(PropertyNameConstant.FILL_RETURN_ADDRESS_REFUND_TIME);
		
		List<ShoppingOrderItemExtendDO> shoppingOrderItemDOList = shoppingOrderItemExtendDOMapper.selectByExample(example);
		
		boolean isExceeds = false;
		for (ShoppingOrderItemExtendDO shoppingOrderItemExtendDO : shoppingOrderItemDOList) {
			// 发送次数为0，发送站内信和推送
			if (shoppingOrderItemExtendDO.getSendTime() == null || shoppingOrderItemExtendDO.getSendTime() <= 0) {
				fillReturnAddressRemind(shoppingOrderItemExtendDO);
			}
			
			isExceeds = DateUtil.isExceeds(shoppingOrderItemExtendDO.getGmtModified(), new Date(), Integer.valueOf(refundTime), Calendar.DAY_OF_YEAR);
			
			// 买家操作超过处理时间，平台自动撤销退款申请
			if (isExceeds) {
				revokeRefundRequest(shoppingOrderItemExtendDO.getShoppingRefundDetail().getId());
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
		
		boolean isExceeds = false;
		for (ShoppingOrderItemExtendDO shoppingOrderItemExtendDO : shoppingOrderItemDOList) {
			// 发送次数为0，发送站内信和推送
			if (shoppingOrderItemExtendDO.getSendTime() == null || shoppingOrderItemExtendDO.getSendTime() <= 0) {
				toBeReturnRemind(shoppingOrderItemExtendDO);
			}
			
			isExceeds = DateUtil.isExceeds(shoppingOrderItemExtendDO.getGmtModified(), new Date(), Integer.valueOf(refundTime), Calendar.DAY_OF_YEAR);
			
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
		
		// 第一次提醒时间
		String firstRemindTime = propertyService.getByName(PropertyNameConstant.TO_BE_REFUNDED_REMIND_FIRST_TIME);
		// 第二次提醒时间
		String secondRemindTime = propertyService.getByName(PropertyNameConstant.TO_BE_REFUNDED_REMIND_SECOND_TIME);
		// 超过提醒时间
		criteria.andGmtModifiedAddDayLessThanOrEqualTo(Integer.valueOf(firstRemindTime), new Date());
		
		// 退款时间
		String refundTime = propertyService.getByName(PropertyNameConstant.TO_BE_REFUNDED_REFUND_TIME);
		
		List<ShoppingOrderItemExtendDO> shoppingOrderItemDOList = shoppingOrderItemExtendDOMapper.selectByExample(example);
		
		boolean isExceeds = false;
		for (ShoppingOrderItemExtendDO shoppingOrderItemExtendDO : shoppingOrderItemDOList) {
			// 发送次数为0，发送站内信和推送
			if (shoppingOrderItemExtendDO.getSendTime() == null || shoppingOrderItemExtendDO.getSendTime() <= 0) {
				toBeRefundRemind(shoppingOrderItemExtendDO);
			}
			
			isExceeds = DateUtil.isExceeds(shoppingOrderItemExtendDO.getGmtModified(), new Date(), Integer.valueOf(secondRemindTime), Calendar.DAY_OF_YEAR);
			if (isExceeds && shoppingOrderItemExtendDO.getSendTime() == null || shoppingOrderItemExtendDO.getSendTime() <= 1) {
				toBeRefundRemind(shoppingOrderItemExtendDO);
			}
			
			isExceeds = DateUtil.isExceeds(shoppingOrderItemExtendDO.getGmtModified(), new Date(), Integer.valueOf(refundTime), Calendar.DAY_OF_YEAR);
			
			// 买家操作超过处理时间，平台自动撤销退款申请
			if (isExceeds) {
				revokeRefundRequest(shoppingOrderItemExtendDO.getShoppingRefundDetail().getId());
			}
		}
	}
	
	/************************************************
	 * Private Method
	 * **********************************************/
	@Transactional
	private void toBeConfirmedForRefundRemind(ShoppingOrderItemExtendDO shoppingOrderItemExtendDO) {
		
		// 更新发送次数，但是不更新更新时间字段
		ShoppingOrderItemDO ShoppingOrderItemDO = new ShoppingOrderItemDO();
		ShoppingOrderItemDO.setId(shoppingOrderItemExtendDO.getId());
		int sendTime = shoppingOrderItemExtendDO.getSendTime() == null ? 1 : shoppingOrderItemExtendDO.getSendTime().intValue() + 1;
		ShoppingOrderItemDO.setSendTime(sendTime);
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(ShoppingOrderItemDO);
		
		// 发送站内信和推送
		shoppingRefundToBeConfirmedForRefundRemindTransactionMainServiceImpl.sendNotice(shoppingOrderItemExtendDO.getId());
	}
	
	@Transactional
	private void toBeConfirmedForReturnRefundRemind(ShoppingOrderItemExtendDO shoppingOrderItemExtendDO) {
		// 更新发送次数，但是不更新更新时间字段
		ShoppingOrderItemDO ShoppingOrderItemDO = new ShoppingOrderItemDO();
		ShoppingOrderItemDO.setId(shoppingOrderItemExtendDO.getId());
		int sendTime = shoppingOrderItemExtendDO.getSendTime() == null ? 1 : shoppingOrderItemExtendDO.getSendTime().intValue() + 1;
		ShoppingOrderItemDO.setSendTime(sendTime);
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(ShoppingOrderItemDO);
		
		// 发送站内信和推送
		shoppingRefundToBeConfirmedForReturnRefundRemindTransactionMainServiceImpl.sendNotice(shoppingOrderItemExtendDO.getId());
	}
	
	@Transactional
	private void refundFailedRemind(ShoppingOrderItemExtendDO shoppingOrderItemExtendDO) {
		// 更新发送次数，但是不更新更新时间字段
		ShoppingOrderItemDO ShoppingOrderItemDO = new ShoppingOrderItemDO();
		ShoppingOrderItemDO.setId(shoppingOrderItemExtendDO.getId());
		int sendTime = shoppingOrderItemExtendDO.getSendTime() == null ? 1 : shoppingOrderItemExtendDO.getSendTime().intValue() + 1;
		ShoppingOrderItemDO.setSendTime(sendTime);
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(ShoppingOrderItemDO);
		
		// 发送站内信和推送
		shoppingRefundToBeConfirmedForReturnRefundRemindTransactionMainServiceImpl.sendNotice(shoppingOrderItemExtendDO.getId());
	}
	
	@Transactional
	private void fillReturnAddressRemind(ShoppingOrderItemExtendDO shoppingOrderItemExtendDO) {
		// 更新发送次数，但是不更新更新时间字段
		ShoppingOrderItemDO ShoppingOrderItemDO = new ShoppingOrderItemDO();
		ShoppingOrderItemDO.setId(shoppingOrderItemExtendDO.getId());
		int sendTime = shoppingOrderItemExtendDO.getSendTime() == null ? 1 : shoppingOrderItemExtendDO.getSendTime().intValue() + 1;
		ShoppingOrderItemDO.setSendTime(sendTime);
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(ShoppingOrderItemDO);
		
		// 发送站内信和推送
		shoppingRefundFillReturnAddressTransactionMainServiceImpl.sendNotice(shoppingOrderItemExtendDO.getId());
	}
	
	@Transactional
	private void toBeReturnRemind(ShoppingOrderItemExtendDO shoppingOrderItemExtendDO) {
		// 更新发送次数，但是不更新更新时间字段
		ShoppingOrderItemDO ShoppingOrderItemDO = new ShoppingOrderItemDO();
		ShoppingOrderItemDO.setId(shoppingOrderItemExtendDO.getId());
		int sendTime = shoppingOrderItemExtendDO.getSendTime() == null ? 1 : shoppingOrderItemExtendDO.getSendTime().intValue() + 1;
		ShoppingOrderItemDO.setSendTime(sendTime);
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(ShoppingOrderItemDO);
		
		// 发送站内信和推送
		shoppingRefundToBeReturnRemindTransactionMainServiceImpl.sendNotice(shoppingOrderItemExtendDO.getId());
	}
	
	@Transactional
	private void toBeRefundRemind(ShoppingOrderItemExtendDO shoppingOrderItemExtendDO) {
		// 更新发送次数，但是不更新更新时间字段
		ShoppingOrderItemDO ShoppingOrderItemDO = new ShoppingOrderItemDO();
		ShoppingOrderItemDO.setId(shoppingOrderItemExtendDO.getId());
		int sendTime = shoppingOrderItemExtendDO.getSendTime() == null ? 1 : shoppingOrderItemExtendDO.getSendTime().intValue() + 1;
		ShoppingOrderItemDO.setSendTime(sendTime);
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(ShoppingOrderItemDO);
		
		// 发送站内信和推送
		shoppingRefundToBeRefundRemindTransactionMainServiceImpl.sendNotice(shoppingOrderItemExtendDO.getId());
	}
}
