package com.lawu.eshop.order.srv.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.order.constants.RefundStatusEnum;
import com.lawu.eshop.order.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderItemRefundForMerchantDTO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemExtendBO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderItemExtendDO;

/**
 *
 * 购物订单扩展转换器
 *
 * @author Sunny
 * @date 2017/04/06
 */
public class ShoppingOrderItemExtendConverter {
	
	/**
	 * ShoppingOrderItemExtendBO转换
	 * 
	 * @param shoppingOrderItemExtendDO
	 * @return
	 */
	public static ShoppingOrderItemExtendBO convert(ShoppingOrderItemExtendDO shoppingOrderItemExtendDO) {
		ShoppingOrderItemExtendBO rtn = null;
		if (shoppingOrderItemExtendDO == null) {
			return rtn;
		}

		rtn = new ShoppingOrderItemExtendBO();
		BeanUtils.copyProperties(shoppingOrderItemExtendDO, rtn, new String[]{"orderStatus", "refundStatus"});
		
		rtn.setOrderStatus(ShoppingOrderStatusEnum.getEnum(shoppingOrderItemExtendDO.getOrderStatus()));
		rtn.setRefundStatus(RefundStatusEnum.getEnum(shoppingOrderItemExtendDO.getRefundStatus()));
		
		rtn.setShoppingRefundDetail(ShoppingRefundDetailConverter.convert(shoppingOrderItemExtendDO.getShoppingRefundDetail()));
		rtn.setShoppingOrder(ShoppingOrderConverter.convertShoppingOrderBO(shoppingOrderItemExtendDO.getShoppingOrder()));
		
		return rtn;
	}
	
	/**
	 * ShoppingOrderItemExtendBO List转换
	 * 
	 * @param shoppingOrderItemExtendDOList
	 * @return
	 */
	public static List<ShoppingOrderItemExtendBO> convert(List<ShoppingOrderItemExtendDO> shoppingOrderItemExtendDOList) {
		List<ShoppingOrderItemExtendBO> rtn = null;
		if (shoppingOrderItemExtendDOList == null || shoppingOrderItemExtendDOList.isEmpty()) {
			return null;
		}
		
		rtn = new ArrayList<ShoppingOrderItemExtendBO>();
		for (ShoppingOrderItemExtendDO shoppingOrderItemExtendDO : shoppingOrderItemExtendDOList) {
			rtn.add(convert(shoppingOrderItemExtendDO));
		}
		
		return rtn;
	}
	
	/**
	 * ShoppingOrderItemRefundForMerchantDTO转换
	 * 
	 * @param shoppingOrderItemExtendBO
	 * @return
	 */
	public static ShoppingOrderItemRefundForMerchantDTO convert(ShoppingOrderItemExtendBO shoppingOrderItemExtendBO) {
		ShoppingOrderItemRefundForMerchantDTO rtn = null;
		if (shoppingOrderItemExtendBO == null) {
			return rtn;
		}
		rtn = new ShoppingOrderItemRefundForMerchantDTO();
		BeanUtils.copyProperties(shoppingOrderItemExtendBO, rtn);
		
		return rtn;
	}
	
	/**
	 * ShoppingOrderItemRefundForMerchantDTO List转换
	 * 
	 * @param shoppingOrderItemExtendBOList
	 * @return
	 */
	public static List<ShoppingOrderItemRefundForMerchantDTO> convertShoppingOrderItemRefundForMerchantDTOList(List<ShoppingOrderItemExtendBO> shoppingOrderItemExtendBOList) {
		List<ShoppingOrderItemRefundForMerchantDTO> rtn = new ArrayList<ShoppingOrderItemRefundForMerchantDTO>();
		
		if (shoppingOrderItemExtendBOList == null || shoppingOrderItemExtendBOList.isEmpty()) {
			return rtn;
		}
		
		for (ShoppingOrderItemExtendBO shoppingOrderItemExtendBO : shoppingOrderItemExtendBOList) {
			rtn.add(convert(shoppingOrderItemExtendBO));
		}
		
		return rtn;
	}
	
	/**
	 * ShoppingOrderItemRefundForMerchantDTO Page转换
	 * 
	 * @param shoppingOrderItemExtendBOPage
	 * @return
	 */
	public static Page<ShoppingOrderItemRefundForMerchantDTO> convertShoppingOrderItemRefundForMerchantDTOPage(Page<ShoppingOrderItemExtendBO> shoppingOrderItemExtendBOPage) {
		Page<ShoppingOrderItemRefundForMerchantDTO> rtn = new Page<ShoppingOrderItemRefundForMerchantDTO>();
		rtn.setCurrentPage(shoppingOrderItemExtendBOPage.getCurrentPage());
		rtn.setTotalCount(shoppingOrderItemExtendBOPage.getTotalCount());
		rtn.setRecords(convertShoppingOrderItemRefundForMerchantDTOList(shoppingOrderItemExtendBOPage.getRecords()));
		return rtn;
	}
	
}
