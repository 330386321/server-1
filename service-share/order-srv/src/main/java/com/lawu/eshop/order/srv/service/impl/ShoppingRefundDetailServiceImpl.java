package com.lawu.eshop.order.srv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.mall.constants.ShoppingOrderItemRefundStatusEnum;
import com.lawu.eshop.mall.param.ShoppingRefundDetailLogisticsInformationParam;
import com.lawu.eshop.order.srv.bo.ShoppingRefundDetailBO;
import com.lawu.eshop.order.srv.converter.ShoppingRefundDetailConverter;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDO;
import com.lawu.eshop.order.srv.domain.ShoppingRefundDetailDO;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderItemDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingRefundDetailDOMapper;
import com.lawu.eshop.order.srv.service.ShoppingRefundDetailService;

@Service
public class ShoppingRefundDetailServiceImpl implements ShoppingRefundDetailService {
	
	@Autowired
	private ShoppingRefundDetailDOMapper shoppingRefundDetailDOMapper;
	
	@Autowired
	private ShoppingOrderItemDOMapper shoppingOrderItemDOMapper;
	
	/**
	 * 根据购物退货详情id
	 * 获取购物退货详情
	 * 
	 * @param id 购物订单项id
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
		Integer result = shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemDO);
		
		// 更新退款详情的物流信息
		ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
		shoppingRefundDetailDO.setId(shoppingRefundDetailBO.getId());
		
		shoppingRefundDetailDO.setExpressCompanyId(param.getExpressCompanyId());
		shoppingRefundDetailDO.setExpressCompanyCode(param.getExpressCompanyCode());
		shoppingRefundDetailDO.setExpressCompanyName(param.getExpressCompanyName());
		shoppingRefundDetailDO.setWaybillNum(param.getWaybillNum());
		
		shoppingRefundDetailDOMapper.updateByPrimaryKeySelective(shoppingRefundDetailDO);
		
		return result;
	}
	
}
