package com.lawu.eshop.order.srv.service.impl;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.FollowTransactionRecordService;
import com.lawu.eshop.order.srv.domain.FollowTransactionRecordDO;
import com.lawu.eshop.order.srv.domain.FollowTransactionRecordDOExample;
import com.lawu.eshop.order.srv.mapper.FollowTransactionRecordDOMapper;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 * 
 * @author jiangxinjun
 * @date 2017年7月12日
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
    	String topic = "order-srv";
    	Long transationId = 1L;
    	
    	FollowTransactionRecordDO record = new FollowTransactionRecordDO();
    	record.setTopic(topic);
    	record.setTransationId(transationId);
    	record.setGmtCreate(new Date());
    	followTransactionRecordDOMapper.insert(record);
    	
    	Boolean isExist = followTransactionRecordService.isExist(topic, transationId);
    	Assert.assertEquals(true, isExist);
    }
    
    @Transactional
    @Rollback
    @Test
    public void consumptionSuccessful() {
    	String topic = "order-srv";
    	Long transationId = 1L;
    	try {
            followTransactionRecordService.consumptionSuccessful(topic, transationId);
        } catch (MySQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
        }
    	
    	FollowTransactionRecordDOExample example = new FollowTransactionRecordDOExample();
    	example.createCriteria().andTopicEqualTo(topic).andTransationIdEqualTo(transationId);
    	List<FollowTransactionRecordDO> list = followTransactionRecordDOMapper.selectByExample(example);
    	Assert.assertNotNull(list);
    	Assert.assertEquals(1, list.size());
    }
}
