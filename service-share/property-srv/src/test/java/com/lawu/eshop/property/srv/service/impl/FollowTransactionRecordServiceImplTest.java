package com.lawu.eshop.property.srv.service.impl;

import com.lawu.eshop.compensating.transaction.FollowTransactionRecordService;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yangqh
 * @date 2017/7/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class FollowTransactionRecordServiceImplTest {

    @Autowired
    private FollowTransactionRecordService followTransactionRecordService;

    @Transactional
    @Rollback
    @Test
    public void isExist(){
        Boolean bool = followTransactionRecordService.isExist("",0L);
        Assert.assertEquals(false,bool);
    }

    @Transactional
    @Rollback
    @Test
    public void consumptionSuccessful(){
        try {
            followTransactionRecordService.consumptionSuccessful("ad_srv",1L);
        } catch (MySQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
        }
        Boolean bool = followTransactionRecordService.isExist("ad_srv",1L);
        Assert.assertEquals(true,bool);
    }
}
