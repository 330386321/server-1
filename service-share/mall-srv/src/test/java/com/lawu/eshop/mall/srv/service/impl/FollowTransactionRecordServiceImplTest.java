package com.lawu.eshop.mall.srv.service.impl;

import com.lawu.eshop.compensating.transaction.FollowTransactionRecordService;
import com.lawu.eshop.mall.srv.domain.FollowTransactionRecordDO;
import com.lawu.eshop.mall.srv.mapper.FollowTransactionRecordDOMapper;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

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
public class FollowTransactionRecordServiceImplTest {

    @Autowired
    private FollowTransactionRecordDOMapper followTransactionRecordDOMapper;
    @Autowired
    private FollowTransactionRecordService followTransactionRecordService;

    @Transactional
    @Rollback
    @Test
    public void isExist(){
        FollowTransactionRecordDO recordDO = new FollowTransactionRecordDO();
        recordDO.setGmtCreate(new Date());
        recordDO.setTopic("topic");
        recordDO.setTransationId(1L);
        followTransactionRecordDOMapper.insert(recordDO);
        boolean flag = followTransactionRecordService.isExist("topic",1L);
        Assert.assertEquals(true,flag);
    }

    @Transactional
    @Rollback
    @Test
    public void consumptionSuccessful(){
        String topic = "topic";
        try {
            followTransactionRecordService.consumptionSuccessful(topic,1L);
        } catch (MySQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
        }
        List<FollowTransactionRecordDO> list = followTransactionRecordDOMapper.selectByExample(null);
        Assert.assertNotNull(list);
        Assert.assertEquals(1,list.size());
        Assert.assertEquals(topic,list.get(0).getTopic());
    }
}
