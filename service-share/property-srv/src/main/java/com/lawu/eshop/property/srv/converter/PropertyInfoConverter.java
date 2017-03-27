package com.lawu.eshop.property.srv.converter;

import com.lawu.eshop.property.srv.bo.PropertyInfoBO;
import com.lawu.eshop.property.srv.domain.PropertyInfoDO;

/**
 * 资产信息转换器
 *
 * @author meishuquan
 * @date 2017/3/27
 */
public class PropertyInfoConverter {

    public static PropertyInfoBO convertBO(PropertyInfoDO propertyInfoDO ) {
        if (propertyInfoDO == null) {
            return null;
        }

        PropertyInfoBO propertyInfoBO=new PropertyInfoBO();
        propertyInfoBO.setPayPassword(propertyInfoDO.getPayPassword());
        return  propertyInfoBO;
    }
}
