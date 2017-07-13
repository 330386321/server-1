package com.lawu.eshop.mall.srv.service.impl;

import com.lawu.eshop.mall.srv.domain.PropertyDO;
import com.lawu.eshop.mall.srv.mapper.PropertyDOMapper;
import com.lawu.eshop.mall.srv.service.PropertyService;
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
 * @author zhangyong
 * @date 2017/7/13.
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
    public void getValue() {
        PropertyDO propertyDO = new PropertyDO();
        propertyDO.setName("name");
        propertyDO.setValue("value");
        propertyDO.setGmtCreate(new Date());
        propertyDO.setRemark("remark");
        propertyDOMapper.insert(propertyDO);

        String value = propertyService.getValue("name");
        Assert.assertEquals("value", value);

    }

    @Transactional
    @Rollback
    @Test
    public void getValues() {
        PropertyDO propertyDO = new PropertyDO();
        propertyDO.setName("name");
        propertyDO.setValue("value");
        propertyDO.setGmtCreate(new Date());
        propertyDO.setRemark("remark");
        propertyDOMapper.insert(propertyDO);

        PropertyDO propertyDO2 = new PropertyDO();
        propertyDO2.setName("name");
        propertyDO2.setValue("value2");
        propertyDO2.setGmtCreate(new Date());
        propertyDO2.setRemark("remark");
        propertyDOMapper.insert(propertyDO2);

        List<String> values = propertyService.getValues("name");
        Assert.assertTrue( values.contains("value"));
        Assert.assertTrue( values.contains("value2"));

    }
}
