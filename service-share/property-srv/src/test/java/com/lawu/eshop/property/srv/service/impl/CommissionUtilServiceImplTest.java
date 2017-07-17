package com.lawu.eshop.property.srv.service.impl;

import com.lawu.eshop.property.srv.bo.CommissionUtilBO;
import com.lawu.eshop.property.srv.service.CommissionUtilService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author yangqh
 * @date 2017/7/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class CommissionUtilServiceImplTest {

    @Autowired
    private CommissionUtilService commissionUtilService;

    @Transactional
    @Rollback
    @Test
    public void getClickAdMine(){

        CommissionUtilBO bo = commissionUtilService.getClickAdMine(new BigDecimal(1));
        Assert.assertNotNull(bo);
    }

    @Transactional
    @Rollback
    @Test
    public void getIncomeMoney(){

        CommissionUtilBO bo = commissionUtilService.getIncomeMoney(new BigDecimal(1));
        Assert.assertNotNull(bo);
    }
}
