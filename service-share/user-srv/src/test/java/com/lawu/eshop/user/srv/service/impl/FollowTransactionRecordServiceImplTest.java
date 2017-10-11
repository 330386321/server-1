package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.compensating.transaction.FollowTransactionRecordService;
import com.lawu.eshop.user.srv.domain.FollowTransactionRecordDO;
import com.lawu.eshop.user.srv.mapper.FollowTransactionRecordDOMapper;
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
 * @author meishuquan
 * @date 2017/7/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class FollowTransactionRecordServiceImplTest {

    @Autowired
    private FollowTransactionRecordService followTransactionRecordService;

    @Autowired
    private FollowTransactionRecordDOMapper followTransactionRecordDOMapper;

    @Transactional
    @Rollback
    @Test
    public void isExist() {
        FollowTransactionRecordDO followTransactionRecordDO = new FollowTransactionRecordDO();
        followTransactionRecordDO.setTransationId(300L);
        followTransactionRecordDO.setTopic("test");
        followTransactionRecordDO.setGmtCreate(new Date());
        followTransactionRecordDOMapper.insertSelective(followTransactionRecordDO);

        boolean result = followTransactionRecordService.isExist("test", 300L);
        Assert.assertTrue(result);
    }

    @Transactional
    @Rollback
    @Test
    public void consumptionSuccessful() {
        try {
            followTransactionRecordService.consumptionSuccessful("test", 300L);
        } catch (MySQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
        }
        List<FollowTransactionRecordDO> followTransactionRecordDOList = followTransactionRecordDOMapper.selectByExample(null);
        Assert.assertNotNull(followTransactionRecordDOList);
        Assert.assertEquals(1, followTransactionRecordDOList.size());
    }

}
