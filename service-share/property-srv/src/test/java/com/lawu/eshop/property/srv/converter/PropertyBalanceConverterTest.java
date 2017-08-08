package com.lawu.eshop.property.srv.converter;

import com.lawu.eshop.property.dto.PropertyBalanceDTO;
import com.lawu.eshop.property.srv.bo.PropertyBalanceBO;
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
public class PropertyBalanceConverterTest {

    @Test
    public void convert(){
        PropertyInfoDO propertyInfoDO = new PropertyInfoDO();
        propertyInfoDO.setBalance(new BigDecimal(("1")));
        PropertyBalanceBO bo = PropertyBalanceConverter.convert(propertyInfoDO);
        Assert.assertNotNull(bo);
    }

    @Test
    public void convert1(){
        PropertyBalanceBO propertyBalanceBO = new PropertyBalanceBO();
        propertyBalanceBO.setBalance(new BigDecimal(("1")));
        propertyBalanceBO.setFreeze(new BigDecimal(1));
        PropertyBalanceDTO dto = PropertyBalanceConverter.convert(propertyBalanceBO);
        Assert.assertNotNull(dto);
    }

}
