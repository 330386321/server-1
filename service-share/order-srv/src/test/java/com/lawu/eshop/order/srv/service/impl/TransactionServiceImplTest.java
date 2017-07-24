package com.lawu.eshop.order.srv.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author jiangxinjun
 * @date 2017年7月24日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class TransactionServiceImplTest {

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @Test
    public void getCount() {
    	Long count = transactionServiceImpl.getCount("type");
    	Assert.assertNotNull(count);
    }
    
    @Test
    public void addCount() {
    	Long beforeCount = transactionServiceImpl.getCount("type");
    	transactionServiceImpl.addCount("type");
    	Long afterCount = transactionServiceImpl.getCount("type");
    	Assert.assertNotNull(afterCount);
    	Assert.assertEquals(beforeCount + 1, afterCount.longValue());
    }
    
}
