package com.lawu.eshop.property.srv.converter;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.property.dto.PropertyBalanceDTO;
import com.lawu.eshop.property.srv.bo.PropertyBalanceBO;
import com.lawu.eshop.property.srv.domain.PropertyInfoDO;

/**
 * 资产余额转换器
 *
 * @author Sunny
 * @date 2017/3/29
 */
public class PropertyBalanceConverter {
	
	public static PropertyBalanceBO convert(PropertyInfoDO propertyInfoDO) {
		if (propertyInfoDO == null) {
			return null;
		}

		PropertyBalanceBO propertyBalanceBO = new PropertyBalanceBO();
		BeanUtils.copyProperties(propertyInfoDO, propertyBalanceBO);

		return propertyBalanceBO;
	}
	
	public static PropertyBalanceDTO convert(PropertyBalanceBO propertyBalanceBO) {
		if (propertyBalanceBO == null) {
			return null;
		}

		PropertyBalanceDTO propertyBalanceDTO = new PropertyBalanceDTO();
		BeanUtils.copyProperties(propertyBalanceBO, propertyBalanceDTO);

		return propertyBalanceDTO;
	}
	
	
}
