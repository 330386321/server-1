package com.lawu.eshop.order.srv.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.order.constants.ShoppingOrderItemRefundStatusEnum;
import com.lawu.eshop.order.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.order.constants.ShoppingRefundDetailStatusEnum;
import com.lawu.eshop.order.constants.ShoppingRefundTypeEnum;
import com.lawu.eshop.order.param.ShoppingRefundDetailLogisticsInformationParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundDetailAgreeToApplyForeignParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundDetailAgreeToRefundForeignParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundDetailRerurnAddressForeignParam;
import com.lawu.eshop.order.srv.bo.ShoppingRefundDetailBO;
import com.lawu.eshop.order.srv.converter.ShoppingRefundDetailConverter;
import com.lawu.eshop.order.srv.domain.ShoppingOrderDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDOExample;
import com.lawu.eshop.order.srv.domain.ShoppingRefundDetailDO;
import com.lawu.eshop.order.srv.domain.ShoppingRefundDetailDOExample;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderItemDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingRefundDetailDOMapper;
import com.lawu.eshop.order.srv.service.ShoppingRefundDetailService;

@Service
public class ShoppingRefundDetailServiceImpl implements ShoppingRefundDetailService {

	@Autowired
	private ShoppingRefundDetailDOMapper shoppingRefundDetailDOMapper;

	@Autowired
	private ShoppingOrderItemDOMapper shoppingOrderItemDOMapper;

	@Autowired
	private ShoppingOrderDOMapper shoppingOrderDOMapper;

	@Autowired
	@Qualifier("shoppingOrderAgreeToRefundTransactionMainServiceImpl")
	private TransactionMainService<Reply> shoppingOrderAgreeToRefundTransactionMainServiceImpl;
	
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
	public ShoppingRefundDetailBO getByShoppingOrderitemId(Long shoppingOrderItemId) {
		if (shoppingOrderItemId == null || shoppingOrderItemId <= 0) {
			return null;
		}

		ShoppingRefundDetailDOExample shoppingRefundDetailDOExample = new ShoppingRefundDetailDOExample();
		ShoppingRefundDetailDOExample.Criteria criteria = shoppingRefundDetailDOExample.createCriteria();
		criteria.andShoppingOrderItemIdEqualTo(shoppingOrderItemId);
		// 找到有效记录
		criteria.andStatusEqualTo(ShoppingRefundDetailStatusEnum.VALID.getValue());

		List<ShoppingRefundDetailDO> shoppingRefundDetailDO = shoppingRefundDetailDOMapper.selectByExample(shoppingRefundDetailDOExample);

		if (shoppingRefundDetailDO == null || shoppingRefundDetailDO.isEmpty()) {
			return null;
		}

		return ShoppingRefundDetailConverter.convert(shoppingRefundDetailDO.get(0));
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
		shoppingOrderItemDO.setRefundStatus(ShoppingOrderItemRefundStatusEnum.TO_BE_REFUNDED.getValue());
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
	public int fillReturnAddress(Long id, ShoppingRefundDetailRerurnAddressForeignParam param) {
		
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
		if (!shoppingOrderItemDO.getRefundStatus().equals(ShoppingOrderItemRefundStatusEnum.FILL_RETURN_ADDRESS)) {
			return ResultCode.ORDER_NOT_FILL_RETURN_ADDRESS;
		}
		
		// 商家判断是否需要寄回货物
		if (param.getIsNeedReturn()) {
			// 更新订单项退款状态为待退货
			shoppingOrderItemDO.setRefundStatus(ShoppingOrderItemRefundStatusEnum.TO_BE_RETURNED.getValue());
	
			// 更新退款详情的退货地址信息
			shoppingRefundDetailDO.setGmtFill(new Date());
			shoppingRefundDetailDO.setConsigneeName(param.getConsigneeName());
			shoppingRefundDetailDO.setConsigneeMobile(param.getConsigneeMobile());
			shoppingRefundDetailDO.setConsigneeAddress(param.getConsigneeAddress());
			shoppingRefundDetailDO.setGmtModified(new Date());
	
			shoppingRefundDetailDOMapper.updateByPrimaryKeySelective(shoppingRefundDetailDO);
		} else {
			// 更新订单项退款状态为待退货
			shoppingOrderItemDO.setRefundStatus(ShoppingOrderItemRefundStatusEnum.TO_BE_REFUNDED.getValue());
		}
		
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
				shoppingOrderItemDO.setRefundStatus(ShoppingOrderItemRefundStatusEnum.TO_BE_REFUNDED.getValue());
			} else {
				shoppingOrderItemDO.setRefundStatus(ShoppingOrderItemRefundStatusEnum.FILL_RETURN_ADDRESS.getValue());
			}
		} else {
			shoppingOrderItemDO.setRefundStatus(ShoppingOrderItemRefundStatusEnum.REFUND_FAILED.getValue());
		}

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
	 * @param shoppingRefundDetailBO
	 *            退款详情
	 * @param param
	 *            参数 是否同意退款
	 * @return
	 */
	@Transactional
	@Override
	public Integer agreeToRefund(ShoppingRefundDetailBO shoppingRefundDetailBO, ShoppingRefundDetailAgreeToRefundForeignParam param) {
		// 更新订单项状态为待退款
		ShoppingOrderItemDO shoppingOrderItemDO = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingRefundDetailBO.getShoppingOrderItemId());
		shoppingOrderItemDO.setGmtModified(new Date());
		if (param.getIsAgree()) {
			shoppingOrderItemDO.setRefundStatus(ShoppingOrderItemRefundStatusEnum.REFUND_SUCCESSFULLY.getValue());
			// 设置购物订单项的订单状态为交易关闭
			shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue());

			// 更新购物订单的订单状态
			ShoppingOrderItemDOExample shoppingOrderItemDOExample = new ShoppingOrderItemDOExample();
			shoppingOrderItemDOExample.createCriteria().andShoppingOrderIdNotEqualTo(shoppingOrderItemDO.getShoppingOrderId()).andOrderStatusNotEqualTo(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue());
			
			long count = shoppingOrderItemDOMapper.countByExample(shoppingOrderItemDOExample);
			
			// 如果当前订单下的所有订单项都是交易关闭状态。关闭订单
			if (count <= 0) {
				ShoppingOrderDO shoppingOrderDO = new ShoppingOrderDO();
				shoppingOrderDO.setId(shoppingOrderItemDO.getShoppingOrderId());
				shoppingOrderDO.setGmtModified(new Date());
				shoppingOrderDO.setOrderStatus(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue());
				shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderDO);
			}
		} else {
			shoppingOrderItemDO.setRefundStatus(ShoppingOrderItemRefundStatusEnum.REFUND_FAILED.getValue());
		}

		Integer result = shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemDO);
		
		// 更新退款详情
		ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
		shoppingRefundDetailDO.setId(shoppingRefundDetailBO.getId());

		shoppingRefundDetailDO.setGmtRefund(new Date());
		shoppingRefundDetailDO.setGmtModified(new Date());

		shoppingRefundDetailDOMapper.updateByPrimaryKeySelective(shoppingRefundDetailDO);
		
		if (param.getIsAgree()) {
			shoppingOrderAgreeToRefundTransactionMainServiceImpl.sendNotice(shoppingOrderItemDO.getId());
		}
		
		return result;
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
		shoppingOrderItemDO.setRefundStatus(ShoppingOrderItemRefundStatusEnum.PLATFORM_INTERVENTION.getValue());
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
		if (!shoppingOrderItemDO.getOrderStatus().equals(ShoppingOrderStatusEnum.REFUNDING)) {
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
		shoppingOrderItemDO.setGmtModified(new Date());
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemDO);

		// 更新退款详情
		// 设置订单的状态为无效
		shoppingRefundDetailDO.setStatus(ShoppingRefundDetailStatusEnum.INVALID.getValue());
		shoppingRefundDetailDO.setGmtIntervention(new Date());
		shoppingRefundDetailDO.setGmtModified(new Date());
		shoppingRefundDetailDOMapper.updateByPrimaryKeySelective(shoppingRefundDetailDO);
		
		return ResultCode.SUCCESS;
	}

}
