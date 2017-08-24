package com.lawu.eshop.mall.srv.service.impl;

import com.lawu.eshop.mall.constants.MerchantIndustryTypeEnum;
import com.lawu.eshop.mall.srv.bo.IndustryTypeBO;
import com.lawu.eshop.mall.srv.domain.IndustryTypeDO;
import com.lawu.eshop.mall.srv.mapper.IndustryTypeDOMapper;
import com.lawu.eshop.mall.srv.service.IndustryTypeService;
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
public class IndustryTypeServiceImplTest {

    @Autowired
    private IndustryTypeService industryTypeService;

    @Autowired
    private IndustryTypeDOMapper industryTypeDOMapper;
    @Transactional
    @Rollback
    @Test
    public void listIndustryType(){

        List<IndustryTypeBO> list = industryTypeService.listIndustryType();
        Assert.assertNotNull(list);
        Assert.assertEquals(1,list.size());
    }


    @Transactional
    @Rollback
    @Test
    public void listIndustryTypeByParentId(){

        List<IndustryTypeBO> list = industryTypeService.listIndustryTypeByParentId(new Short("0"));
        Assert.assertNotNull(list);
        Assert.assertEquals(1,list.size());
    }


    @Transactional
    @Rollback
    @Test
    public void getAllIndustryList(){
        List<IndustryTypeBO> list = industryTypeService.getAllIndustryList();
        Assert.assertNotNull(list);
        Assert.assertEquals(1,list.size());
    }

    @Transactional
    @Rollback
    @Test
    public void listIndustryTypeByType(){
        List<IndustryTypeBO> list = industryTypeService.listIndustryTypeByType(MerchantIndustryTypeEnum.COMMON_INDUSTRY);
        Assert.assertNotNull(list);
        Assert.assertEquals(1,list.size());
    }


}
