package com.lawu.eshop.order.srv.converter;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.order.constants.ShoppingRefundTypeEnum;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExpressDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingRefundDetailDTO;
import com.lawu.eshop.order.srv.bo.ExpressInquiriesDetailBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemExtendBO;
import com.lawu.eshop.order.srv.bo.ShoppingRefundDetailBO;
import com.lawu.eshop.order.srv.domain.ShoppingRefundDetailDO;

/**
 * 购物退货详情转换器
 *
 * @author Sunny
 * @date 2017/04/11
 */
public class ShoppingRefundDetailConverter {

	/**
	 * ShoppingRefundDetailBO转换
	 * 
	 * @param shoppingRefundDetailDO
	 * @return
	 */
	public static ShoppingRefundDetailBO convert(ShoppingRefundDetailDO shoppingRefundDetailDO) {
		if (shoppingRefundDetailDO == null) {
			return null;
		}

		ShoppingRefundDetailBO shoppingRefundDetailBO = new ShoppingRefundDetailBO();
		BeanUtils.copyProperties(shoppingRefundDetailDO, shoppingRefundDetailBO, "type");
		
		shoppingRefundDetailBO.setType(ShoppingRefundTypeEnum.getEnum(shoppingRefundDetailDO.getType()));
		
		return shoppingRefundDetailBO;
	}
	
	/**
	 * ShoppingRefundDetailDTO转换
	 * 
	 * @param shoppingOrderItemExtendBO
	 * @return
	 */
	public static ShoppingRefundDetailDTO convert(ShoppingOrderItemExtendBO shoppingOrderItemExtendBO) {
		ShoppingRefundDetailDTO shoppingRefundDetailDTO = null;
		if (shoppingOrderItemExtendBO == null) {
			return null;
		}

		shoppingRefundDetailDTO = new ShoppingRefundDetailDTO();
		BeanUtils.copyProperties(shoppingOrderItemExtendBO.getShoppingRefundDetail(), shoppingRefundDetailDTO);
		shoppingRefundDetailDTO.setRefundStatus(shoppingOrderItemExtendBO.getRefundStatus());
		
		return shoppingRefundDetailDTO;
	}
	
    /**
     * ShoppingOrderExpressDTO转换
     * 
     * @param shoppingRefundDetailBO
     * @param expressInquiriesDetailBO
     * @return
     */
    public static ShoppingOrderExpressDTO covert(ShoppingRefundDetailBO shoppingRefundDetailBO, ExpressInquiriesDetailBO expressInquiriesDetailBO) {
		if(shoppingRefundDetailBO == null){
			return null;
		}
		
		ShoppingOrderExpressDTO shoppingOrderExpressDTO = new ShoppingOrderExpressDTO();
		BeanUtils.copyProperties(shoppingRefundDetailBO, shoppingOrderExpressDTO, "expressInquiriesDetailDTO");
		
		shoppingOrderExpressDTO.setExpressInquiriesDetailDTO(ExpressInquiriesDetailConverter.convert(expressInquiriesDetailBO));
		
		return shoppingOrderExpressDTO;
    }

}
