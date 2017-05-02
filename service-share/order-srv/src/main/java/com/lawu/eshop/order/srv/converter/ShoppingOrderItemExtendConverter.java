package com.lawu.eshop.order.srv.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.order.constants.RefundStatusEnum;
import com.lawu.eshop.order.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderItemRefundDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderItemRefundForMerchantDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderItemRefundForOperatorDTO;
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
		if (shoppingOrderItemExtendBO.getShoppingOrder() != null) {
			rtn.setConsigneeName(shoppingOrderItemExtendBO.getShoppingOrder().getConsigneeName());
		}
		if (shoppingOrderItemExtendBO.getShoppingRefundDetail() != null) {
			rtn.setShoppingRefundDetailId(shoppingOrderItemExtendBO.getShoppingRefundDetail().getId());
			rtn.setGmtCreate(shoppingOrderItemExtendBO.getShoppingRefundDetail().getGmtCreate());
		}
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
	
	/**
	 * ShoppingOrderItemRefundForOperatorDTO转换
	 * 
	 * @param shoppingOrderItemExtendBO
	 * @return
	 */
	public static ShoppingOrderItemRefundForOperatorDTO convertShoppingOrderItemRefundForOperatorDTO(ShoppingOrderItemExtendBO shoppingOrderItemExtendBO) {
		ShoppingOrderItemRefundForOperatorDTO rtn = null;
		if (shoppingOrderItemExtendBO == null) {
			return rtn;
		}
		rtn = new ShoppingOrderItemRefundForOperatorDTO();
		BeanUtils.copyProperties(shoppingOrderItemExtendBO, rtn);
		if (shoppingOrderItemExtendBO.getShoppingOrder() != null) {
			rtn.setConsigneeName(shoppingOrderItemExtendBO.getShoppingOrder().getConsigneeName());
			rtn.setConsigneeAddress(shoppingOrderItemExtendBO.getShoppingOrder().getConsigneeAddress());
			rtn.setConsigneeMobile(shoppingOrderItemExtendBO.getShoppingOrder().getConsigneeMobile());
			rtn.setOrderNum(shoppingOrderItemExtendBO.getShoppingOrder().getOrderNum());
		}
		if (shoppingOrderItemExtendBO.getShoppingRefundDetail() != null) {
			rtn.setShoppingRefundDetailId(shoppingOrderItemExtendBO.getShoppingRefundDetail().getId());
			rtn.setGmtIntervention(shoppingOrderItemExtendBO.getShoppingRefundDetail().getGmtIntervention());
		}
		return rtn;
	}
	
	/**
	 * ShoppingOrderItemRefundForOperatorDTO List转换
	 * 
	 * @param shoppingOrderItemExtendBOList
	 * @return
	 */
	public static List<ShoppingOrderItemRefundForOperatorDTO> convertShoppingOrderItemRefundForOperatorDTOList(List<ShoppingOrderItemExtendBO> shoppingOrderItemExtendBOList) {
		List<ShoppingOrderItemRefundForOperatorDTO> rtn = new ArrayList<ShoppingOrderItemRefundForOperatorDTO>();
		
		if (shoppingOrderItemExtendBOList == null || shoppingOrderItemExtendBOList.isEmpty()) {
			return rtn;
		}
		
		for (ShoppingOrderItemExtendBO shoppingOrderItemExtendBO : shoppingOrderItemExtendBOList) {
			rtn.add(convertShoppingOrderItemRefundForOperatorDTO(shoppingOrderItemExtendBO));
		}
		
		return rtn;
	}
	
	/**
	 * ShoppingOrderItemRefundForOperatorDTO Page转换
	 * 
	 * @param shoppingOrderItemExtendBOPage
	 * @return
	 */
	public static Page<ShoppingOrderItemRefundForOperatorDTO> convertShoppingOrderItemRefundForOperatorDTOPage(Page<ShoppingOrderItemExtendBO> shoppingOrderItemExtendBOPage) {
		Page<ShoppingOrderItemRefundForOperatorDTO> rtn = new Page<ShoppingOrderItemRefundForOperatorDTO>();
		rtn.setCurrentPage(shoppingOrderItemExtendBOPage.getCurrentPage());
		rtn.setTotalCount(shoppingOrderItemExtendBOPage.getTotalCount());
		rtn.setRecords(convertShoppingOrderItemRefundForOperatorDTOList(shoppingOrderItemExtendBOPage.getRecords()));
		return rtn;
	}
	
	/**
	 * ShoppingOrderItemRefundDTO转换
	 * 
	 * @param shoppingOrderItemExtendBO
	 * @return
	 */
	public static ShoppingOrderItemRefundDTO convertShoppingOrderItemRefundDTO(ShoppingOrderItemExtendBO shoppingOrderItemExtendBO) {
		ShoppingOrderItemRefundDTO rtn = null;
		if (shoppingOrderItemExtendBO == null) {
			return rtn;
		}
		rtn = new ShoppingOrderItemRefundDTO();
		BeanUtils.copyProperties(shoppingOrderItemExtendBO, rtn, "");
		
		if (shoppingOrderItemExtendBO.getShoppingOrder() != null) {
			rtn.setMerchantId(shoppingOrderItemExtendBO.getShoppingOrder().getMerchantId());
			rtn.setMerchantName(shoppingOrderItemExtendBO.getShoppingOrder().getMerchantName());
		}
		if (shoppingOrderItemExtendBO.getShoppingRefundDetail() != null) {
			rtn.setShoppingRefundDetailId(shoppingOrderItemExtendBO.getShoppingRefundDetail().getId());
			rtn.setAmount(shoppingOrderItemExtendBO.getShoppingRefundDetail().getAmount());
			rtn.setType(shoppingOrderItemExtendBO.getShoppingRefundDetail().getType());
		}
		return rtn;
	}
	
	/**
	 * ShoppingOrderItemRefundDTO List转换
	 * 
	 * @param shoppingOrderItemExtendBOList
	 * @return
	 */
	public static List<ShoppingOrderItemRefundDTO> convertShoppingOrderItemRefundDTOList(List<ShoppingOrderItemExtendBO> shoppingOrderItemExtendBOList) {
		List<ShoppingOrderItemRefundDTO> rtn = new ArrayList<ShoppingOrderItemRefundDTO>();
		
		if (shoppingOrderItemExtendBOList == null || shoppingOrderItemExtendBOList.isEmpty()) {
			return rtn;
		}
		
		for (ShoppingOrderItemExtendBO shoppingOrderItemExtendBO : shoppingOrderItemExtendBOList) {
			rtn.add(convertShoppingOrderItemRefundDTO(shoppingOrderItemExtendBO));
		}
		
		return rtn;
	}
	
	/**
	 * ShoppingOrderItemRefundDTO Page转换
	 * 
	 * @param shoppingOrderItemExtendBOPage
	 * @return
	 */
	public static Page<ShoppingOrderItemRefundDTO> convertShoppingOrderItemRefundDTOPage(Page<ShoppingOrderItemExtendBO> shoppingOrderItemExtendBOPage) {
		Page<ShoppingOrderItemRefundDTO> rtn = new Page<ShoppingOrderItemRefundDTO>();
		rtn.setCurrentPage(shoppingOrderItemExtendBOPage.getCurrentPage());
		rtn.setTotalCount(shoppingOrderItemExtendBOPage.getTotalCount());
		rtn.setRecords(convertShoppingOrderItemRefundDTOList(shoppingOrderItemExtendBOPage.getRecords()));
		return rtn;
	}
}
