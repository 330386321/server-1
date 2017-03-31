package com.lawu.eshop.property.srv.converter;

import com.lawu.eshop.property.dto.PropertyPointDTO;
import com.lawu.eshop.property.srv.bo.PropertyPointBO;
import com.lawu.eshop.property.srv.domain.PropertyInfoDO;

/**
 * 资产积分转换器
 *
 * @author Sunny
 * @date 2017/3/31
 */
public class PropertyPointConverter {

	/**
	 * PropertyPointBO转换器
	 * 
	 * @param propertyInfoDO
	 * @return
	 */
    public static PropertyPointBO convert(PropertyInfoDO propertyInfoDO) {
        if (propertyInfoDO == null) {
            return null;
        }

        PropertyPointBO propertyPointBO = new PropertyPointBO();
        propertyPointBO.setPoint(propertyInfoDO.getPoint());
        return  propertyPointBO;
    }
    
    /**
     * PropertyPointDTO转换器
     * 
     * @param propertyPointBO
     * @return
     */
    public static PropertyPointDTO convert(PropertyPointBO propertyPointBO) {
        if (propertyPointBO == null) {
            return null;
        }

        PropertyPointDTO propertyPointDTO = new PropertyPointDTO();
        propertyPointDTO.setPoint(propertyPointBO.getPoint());
        return  propertyPointDTO;
    }
}
