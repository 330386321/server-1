package com.lawu.eshop.product.srv.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.dto.ShoppingCartProductModelDTO;
import com.lawu.eshop.product.srv.bo.ShoppingCartProductModelBO;
import com.lawu.eshop.product.srv.domain.ProductDO;
import com.lawu.eshop.product.srv.domain.ProductModelDO;

/**
 * 购物车产品型号转换器
 * 
 * @author Sunny
 * @date 2017/3/30
 */
public class ShoppingCartProductModelConverter {

	/**
	 * BO转换
	 * 
	 * @param productModelDO
	 * @param productDO
	 * @return
	 */
	public static ShoppingCartProductModelBO convert(ProductModelDO productModelDO, ProductDO productDO) {
		if (productModelDO == null) {
			return null;
		}

		ShoppingCartProductModelBO shoppingCartProductModelBO = new ShoppingCartProductModelBO();
		BeanUtils.copyProperties(productModelDO, shoppingCartProductModelBO);

		if (productDO != null) {
			shoppingCartProductModelBO.setProductName(productDO.getName());
			shoppingCartProductModelBO.setFeatureImage(productDO.getFeatureImage());
			if (ProductStatusEnum.PRODUCT_STATUS_UP.val.equals(productDO.getStatus())) {
				if (productModelDO.getStatus()) {
					shoppingCartProductModelBO.setStatus(ProductStatusEnum.PRODUCT_STATUS_UP);
				} else {
					shoppingCartProductModelBO.setStatus(ProductStatusEnum.PRODUCT_STATUS_DEL);
				}
			} else {
				shoppingCartProductModelBO.setStatus(ProductStatusEnum.getEnum(productDO.getStatus()));
			}
			
			shoppingCartProductModelBO.setIsAllowRefund(productDO.getIsAllowRefund());
		}

		return shoppingCartProductModelBO;
	}
	
	/**
	 * BOS转换
	 * 
	 * @param productModelDOS
	 * @param productDOS
	 * @return
	 */
	public static List<ShoppingCartProductModelBO> convert(List<ProductModelDO> productModelDOS, List<ProductDO> productDOS) {
		if (productModelDOS == null || productModelDOS.isEmpty() || productDOS == null || productDOS.isEmpty()) {
			return null;
		}
		
		Map<Long, ProductDO> productDOMap = new HashMap<Long, ProductDO>();
		for (ProductDO productDO : productDOS) {
			if (!productDOMap.containsKey(productDO.getId())) {
				productDOMap.put(productDO.getId(), productDO);
			}
		}
		
		List<ShoppingCartProductModelBO> shoppingCartProductModelBOS = new ArrayList<ShoppingCartProductModelBO>();
		for (ProductModelDO productModelDO : productModelDOS) {
			shoppingCartProductModelBOS.add(convert(productModelDO, productDOMap.get(productModelDO.getProductId())));
		}
		
		return shoppingCartProductModelBOS;
	}

	/**
	 * DTO转换
	 * 
	 * @param shoppingCartProductModelBO
	 * @return
	 */
	public static ShoppingCartProductModelDTO convert(ShoppingCartProductModelBO shoppingCartProductModelBO) {
		if (shoppingCartProductModelBO == null) {
			return null;
		}

		ShoppingCartProductModelDTO shoppingCartProductModelDTO = new ShoppingCartProductModelDTO();
		BeanUtils.copyProperties(shoppingCartProductModelBO, shoppingCartProductModelDTO);

		return shoppingCartProductModelDTO;
	}
	
	/**
	 * DTOS转换
	 * 
	 * @param shoppingCartProductModelBOS
	 * @return
	 */
	public static List<ShoppingCartProductModelDTO> convert(List<ShoppingCartProductModelBO> shoppingCartProductModelBOS) {
		if (shoppingCartProductModelBOS == null || shoppingCartProductModelBOS.isEmpty()) {
			return null;
		}
		
		List<ShoppingCartProductModelDTO> shoppingCartProductModelDTOS = new ArrayList<ShoppingCartProductModelDTO>();
		for (ShoppingCartProductModelBO shoppingCartProductModelBO : shoppingCartProductModelBOS) {
			shoppingCartProductModelDTOS.add(convert(shoppingCartProductModelBO));
		}
		
		return shoppingCartProductModelDTOS;
	}

}
