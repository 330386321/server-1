package com.lawu.eshop.order.srv.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.order.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.order.constants.TransactionPayTypeEnum;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExtendDetailDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExtendQueryDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderQueryToMerchantDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderQueryToOperatorDTO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderExtendBO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderExtendDO;

/**
 *
 * 购物订单扩展转换器
 *
 * @author Sunny
 * @date 2017/04/06
 */
public class ShoppingOrderExtendConverter {
	
	/**
	 * ShoppingOrderExtendDetailBO转换
	 * 
	 * @param shoppingOrderExtendDO
	 * @return
	 */
	public static ShoppingOrderExtendBO convertShoppingOrderExtendDetailBO(ShoppingOrderExtendDO shoppingOrderExtendDO) {
		if (shoppingOrderExtendDO == null) {
			return null;
		}

		ShoppingOrderExtendBO shoppingOrderExtendDetailBO = new ShoppingOrderExtendBO();
		BeanUtils.copyProperties(shoppingOrderExtendDO, shoppingOrderExtendDetailBO, new String[]{"items", "orderStatus"});
		
		// 转换为枚举类型
		shoppingOrderExtendDetailBO.setOrderStatus(ShoppingOrderStatusEnum.getEnum(shoppingOrderExtendDO.getOrderStatus()));
		shoppingOrderExtendDetailBO.setPaymentMethod(TransactionPayTypeEnum.getEnum(shoppingOrderExtendDO.getPaymentMethod()));
		
		if (shoppingOrderExtendDO.getItems() != null) {
			shoppingOrderExtendDetailBO.setItems(ShoppingOrderItemConverter.convert(shoppingOrderExtendDO.getItems()));
		}
		
		return shoppingOrderExtendDetailBO;
	}
	
	public static List<ShoppingOrderExtendBO> convertShoppingOrderExtendBO(List<ShoppingOrderExtendDO> shoppingOrderExtendDOList) {
		if (shoppingOrderExtendDOList == null || shoppingOrderExtendDOList.isEmpty()) {
			return null;
		}
		
		List<ShoppingOrderExtendBO> shoppingOrderExtendBOList = new ArrayList<ShoppingOrderExtendBO>();
		for (ShoppingOrderExtendDO shoppingOrderExtendDO : shoppingOrderExtendDOList) {
			shoppingOrderExtendBOList.add(convertShoppingOrderExtendDetailBO(shoppingOrderExtendDO));
		}
		
		return shoppingOrderExtendBOList;
	}
	
	/**
	 * ShoppingOrderExtendDetailDTO转换
	 * 
	 * @param shoppingOrderExtendBO
	 * @return
	 */
	public static ShoppingOrderExtendDetailDTO convert(ShoppingOrderExtendBO shoppingOrderExtendBO) {
		if (shoppingOrderExtendBO == null) {
			return null;
		}

		ShoppingOrderExtendDetailDTO shoppingOrderExtendDetailDTO = new ShoppingOrderExtendDetailDTO();
		BeanUtils.copyProperties(shoppingOrderExtendBO, shoppingOrderExtendDetailDTO, new String[]{"items"});
		
		if (shoppingOrderExtendBO.getItems() != null) {
			shoppingOrderExtendDetailDTO.setItems(ShoppingOrderItemConverter.convertShoppingOrderItemDTOList(shoppingOrderExtendBO.getItems()));
		}
		
		return shoppingOrderExtendDetailDTO;
	}
	
	/**
	 * ShoppingOrderExtendQueryDTO转换
	 * 
	 * @param shoppingOrderExtendBO
	 * @return
	 */
	public static ShoppingOrderExtendQueryDTO convertShoppingOrderExtendQueryDTO(ShoppingOrderExtendBO shoppingOrderExtendBO) {
		if (shoppingOrderExtendBO == null) {
			return null;
		}

		ShoppingOrderExtendQueryDTO shoppingOrderExtendDetailDTO = new ShoppingOrderExtendQueryDTO();
		BeanUtils.copyProperties(shoppingOrderExtendBO, shoppingOrderExtendDetailDTO, new String[]{"items"});
		
		if (shoppingOrderExtendBO.getItems() != null) {
			shoppingOrderExtendDetailDTO.setItems(ShoppingOrderItemConverter.convertShoppingOrderItemDTOList(shoppingOrderExtendBO.getItems()));
		}
		
		return shoppingOrderExtendDetailDTO;
	}
	
	/**
	 * ShoppingOrderExtendQueryDTO List转换
	 * 
	 * @param shoppingOrderExtendBO
	 * @return
	 */
	public static List<ShoppingOrderExtendQueryDTO> convertShoppingOrderExtendQueryDTOList(List<ShoppingOrderExtendBO> shoppingOrderExtendBOList) {
		if (shoppingOrderExtendBOList == null || shoppingOrderExtendBOList.isEmpty()) {
			return null;
		}
		
		List<ShoppingOrderExtendQueryDTO> shoppingOrderExtendQueryDTOList = new ArrayList<ShoppingOrderExtendQueryDTO>();
		for (ShoppingOrderExtendBO shoppingOrderExtendBO : shoppingOrderExtendBOList) {
			shoppingOrderExtendQueryDTOList.add(convertShoppingOrderExtendQueryDTO(shoppingOrderExtendBO));
		}
		
		return shoppingOrderExtendQueryDTOList;
	}
	
	/**
	 * ShoppingOrderQueryToMerchantDTO转换
	 * 
	 * @param shoppingOrderExtendBO
	 * @return
	 */
	public static ShoppingOrderQueryToMerchantDTO convertShoppingOrderQueryToMerchantDTO(ShoppingOrderExtendBO shoppingOrderExtendBO) {
		if (shoppingOrderExtendBO == null) {
			return null;
		}

		ShoppingOrderQueryToMerchantDTO shoppingOrderQueryToMerchantDTO = new ShoppingOrderQueryToMerchantDTO();
		BeanUtils.copyProperties(shoppingOrderExtendBO, shoppingOrderQueryToMerchantDTO, new String[]{"items"});
		
		if (shoppingOrderExtendBO.getItems() != null && !shoppingOrderExtendBO.getItems().isEmpty()) {
			shoppingOrderQueryToMerchantDTO.setProductFeatureImage(shoppingOrderExtendBO.getItems().get(0).getProductFeatureImage());
		}
		
		return shoppingOrderQueryToMerchantDTO;
	}
	
	/**
	 * ShoppingOrderQueryToMerchantDTO List转换
	 * 
	 * @param shoppingOrderExtendBO
	 * @return
	 */
	public static List<ShoppingOrderQueryToMerchantDTO> convertShoppingOrderQueryToMerchantDTOList(List<ShoppingOrderExtendBO> shoppingOrderExtendBOList) {
		List<ShoppingOrderQueryToMerchantDTO> rtn = new ArrayList<ShoppingOrderQueryToMerchantDTO>();
		if (shoppingOrderExtendBOList == null) {
			return rtn;
		}
		
		for (ShoppingOrderExtendBO shoppingOrderExtendBO : shoppingOrderExtendBOList) {
			rtn.add(convertShoppingOrderQueryToMerchantDTO(shoppingOrderExtendBO));
		}
		
		return rtn;
	}
	
	/**
	 * ShoppingOrderQueryToOperatorDTO转换
	 * 
	 * @param shoppingOrderExtendBO
	 * @return
	 */
	public static ShoppingOrderQueryToOperatorDTO convertShoppingOrderQueryToOperatorDTO(ShoppingOrderExtendBO shoppingOrderExtendBO) {
		if (shoppingOrderExtendBO == null) {
			return null;
		}

		ShoppingOrderQueryToOperatorDTO shoppingOrderQueryToOperatorDTO = new ShoppingOrderQueryToOperatorDTO();
		BeanUtils.copyProperties(shoppingOrderExtendBO, shoppingOrderQueryToOperatorDTO, new String[]{"items"});
		
		if (shoppingOrderExtendBO.getItems() != null && !shoppingOrderExtendBO.getItems().isEmpty()) {
			shoppingOrderQueryToOperatorDTO.setProductFeatureImage(shoppingOrderExtendBO.getItems().get(0).getProductFeatureImage());
		}
		
		return shoppingOrderQueryToOperatorDTO;
	}
	
	/**
	 * ShoppingOrderQueryToOperatorDTO List转换
	 * 
	 * @param shoppingOrderExtendBO
	 * @return
	 */
	public static List<ShoppingOrderQueryToOperatorDTO> convertShoppingOrderQueryToOperatorDTOList(List<ShoppingOrderExtendBO> shoppingOrderExtendBOList) {
		if (shoppingOrderExtendBOList == null || shoppingOrderExtendBOList.isEmpty()) {
			return null;
		}
		
		List<ShoppingOrderQueryToOperatorDTO> shoppingOrderQueryToOperatorDTOList = new ArrayList<ShoppingOrderQueryToOperatorDTO>();
		for (ShoppingOrderExtendBO shoppingOrderExtendBO : shoppingOrderExtendBOList) {
			shoppingOrderQueryToOperatorDTOList.add(convertShoppingOrderQueryToOperatorDTO(shoppingOrderExtendBO));
		}
		
		return shoppingOrderQueryToOperatorDTOList;
	}
	
}
