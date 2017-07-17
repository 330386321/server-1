package com.lawu.eshop.property.srv.service.impl;

import com.lawu.eshop.property.constants.PropertyType;
import com.lawu.eshop.property.srv.domain.PropertyDO;
import com.lawu.eshop.property.srv.mapper.PropertyDOMapper;
import com.lawu.eshop.property.srv.service.PropertyService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author yangqh
 * @date 2017/7/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class PropertyServiceImplTest {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PropertyDOMapper propertyDOMapper;


    @Transactional
    @Rollback
    @Test
    public void getValue(){
        String value = propertyService.getValue(PropertyType.CASH_SCALE);
        Assert.assertNotEquals("",value);
    }

    @Transactional
    @Rollback
    @Test
    public void getValues(){
        PropertyDO record = new PropertyDO();
        record.setName(PropertyType.CASH_SCALE);
        record.setValue(PropertyType.CASH_SCALE);
        record.setGmtCreate(new Date());
        propertyDOMapper.insert(record);
        List<String> values = propertyService.getValues(PropertyType.CASH_SCALE);
        Assert.assertNotNull(values);
    }
}
