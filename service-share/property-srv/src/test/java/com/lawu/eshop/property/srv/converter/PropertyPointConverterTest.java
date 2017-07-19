package com.lawu.eshop.property.srv.converter;

import com.lawu.eshop.property.dto.PropertyPointDTO;
import com.lawu.eshop.property.srv.bo.PropertyPointBO;
import com.lawu.eshop.property.srv.domain.PropertyInfoDO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * @author yangqh
 * @date 2017/7/19
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class PropertyPointConverterTest {

    @Test
    public void convert(){
        PropertyInfoDO propertyInfoDO = new PropertyInfoDO();
        propertyInfoDO.setPoint(new BigDecimal("1"));
        PropertyPointBO bo = PropertyPointConverter.convert(propertyInfoDO);
        Assert.assertNotNull(bo);
    }

    @Test
    public void convert1(){
        PropertyPointBO propertyPointBO = new PropertyPointBO();
        propertyPointBO.setPoint(new BigDecimal("1"));
        PropertyPointDTO dto = PropertyPointConverter.convert(propertyPointBO);
        Assert.assertNotNull(dto);
    }

}
