package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.user.param.ListPropertyParam;
import com.lawu.eshop.user.srv.bo.PropertyBO;
import com.lawu.eshop.user.srv.domain.PropertyDO;
import com.lawu.eshop.user.srv.mapper.PropertyDOMapper;
import com.lawu.eshop.user.srv.service.PropertyService;
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
 * @author meishuquan
 * @date 2017/7/12.
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
    public void saveProperty() {
        propertyService.saveProperty("test", "测试", "备注");
        List<PropertyDO> propertyDOList = propertyDOMapper.selectByExample(null);
        Assert.assertNotNull(propertyDOList);
        Assert.assertEquals(1, propertyDOList.size());
    }

    @Transactional
    @Rollback
    @Test
    public void deletePropertyById() {
        PropertyDO propertyDO = new PropertyDO();
        propertyDO.setName("test");
        propertyDO.setValue("测试");
        propertyDO.setRemark("备注");
        propertyDO.setGmtCreate(new Date());
        propertyDOMapper.insertSelective(propertyDO);

        propertyService.deletePropertyById(propertyDO.getId());
        List<PropertyDO> propertyDOList = propertyDOMapper.selectByExample(null);
        Assert.assertNotNull(propertyDOList);
        Assert.assertEquals(0, propertyDOList.size());
    }

    @Transactional
    @Rollback
    @Test
    public void updatePropertyById() {
        PropertyDO propertyDO = new PropertyDO();
        propertyDO.setName("test");
        propertyDO.setValue("测试");
        propertyDO.setRemark("备注");
        propertyDO.setGmtCreate(new Date());
        propertyDOMapper.insertSelective(propertyDO);

        propertyService.updatePropertyById(propertyDO.getId(), "update", null, null);
        List<PropertyDO> propertyDOList = propertyDOMapper.selectByExample(null);
        Assert.assertNotNull(propertyDOList);
        Assert.assertEquals(1, propertyDOList.size());
        Assert.assertEquals("update", propertyDOList.get(0).getName());
    }

    @Transactional
    @Rollback
    @Test
    public void getPropertyById() {
        PropertyDO propertyDO = new PropertyDO();
        propertyDO.setName("test");
        propertyDO.setValue("测试");
        propertyDO.setRemark("备注");
        propertyDO.setGmtCreate(new Date());
        propertyDOMapper.insertSelective(propertyDO);

        PropertyBO propertyBO = propertyService.getPropertyById(propertyDO.getId());
        Assert.assertNotNull(propertyBO);
        Assert.assertEquals("test", propertyBO.getName());
    }

    @Transactional
    @Rollback
    @Test
    public void listProperty() {
        PropertyDO propertyDO = new PropertyDO();
        propertyDO.setName("test");
        propertyDO.setValue("测试");
        propertyDO.setRemark("备注");
        propertyDO.setGmtCreate(new Date());
        propertyDOMapper.insertSelective(propertyDO);

        ListPropertyParam param = new ListPropertyParam();
        Page<PropertyBO> propertyBOPage = propertyService.listProperty(param);
        Assert.assertNotNull(propertyBOPage.getRecords());
        Assert.assertEquals(1, propertyBOPage.getTotalCount().intValue());
    }

}
