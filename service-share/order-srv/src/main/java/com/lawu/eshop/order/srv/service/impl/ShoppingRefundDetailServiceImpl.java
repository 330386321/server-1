package com.lawu.eshop.order.srv.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.mall.constants.ShoppingOrderItemRefundStatusEnum;
import com.lawu.eshop.mall.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.mall.constants.ShoppingRefundTypeEnum;
import com.lawu.eshop.mall.param.ShoppingRefundDetailLogisticsInformationParam;
import com.lawu.eshop.mall.param.foreign.ShoppingRefundDetailAgreeToApplyForeignParam;
import com.lawu.eshop.mall.param.foreign.ShoppingRefundDetailAgreeToRefundForeignParam;
import com.lawu.eshop.mall.param.foreign.ShoppingRefundDetailRerurnAddressForeignParam;
import com.lawu.eshop.order.srv.bo.ShoppingRefundDetailBO;
import com.lawu.eshop.order.srv.converter.ShoppingRefundDetailConverter;
import com.lawu.eshop.order.srv.domain.ShoppingOrderDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDO;
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
		shoppingRefundDetailDOExample.createCriteria().andShoppingOrderItemIdEqualTo(shoppingOrderItemId);

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
	 * @param shoppingRefundDetailBO
	 *            退款详情
	 * @param param
	 *            退款详情物流信息
	 * @return
	 */
	@Transactional
	@Override
	public Integer fillReturnAddress(ShoppingRefundDetailBO shoppingRefundDetailBO, ShoppingRefundDetailRerurnAddressForeignParam param) {
		// 更新订单项状态为待退款
		ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
		shoppingOrderItemDO.setId(shoppingRefundDetailBO.getShoppingOrderItemId());
		shoppingOrderItemDO.setRefundStatus(ShoppingOrderItemRefundStatusEnum.TO_BE_RETURNED.getValue());
		shoppingOrderItemDO.setGmtModified(new Date());
		Integer result = shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemDO);

		// 更新退款详情的退货地址信息
		ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
		shoppingRefundDetailDO.setId(shoppingRefundDetailBO.getId());

		shoppingRefundDetailDO.setGmtFill(new Date());
		shoppingRefundDetailDO.setConsigneeName(param.getConsigneeName());
		shoppingRefundDetailDO.setConsigneeMobile(param.getConsigneeMobile());
		shoppingRefundDetailDO.setConsigneeAddress(param.getConsigneeAddress());
		shoppingRefundDetailDO.setGmtModified(new Date());

		shoppingRefundDetailDOMapper.updateByPrimaryKeySelective(shoppingRefundDetailDO);

		return result;
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
			ShoppingOrderDO shoppingOrderDO = new ShoppingOrderDO();
			shoppingOrderDO.setId(shoppingOrderItemDO.getShoppingOrderId());
			shoppingOrderDO.setGmtModified(new Date());
			shoppingOrderDO.setOrderStatus(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue());
			shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderDO);

			// TODO 事务补偿退款给用户
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

}
