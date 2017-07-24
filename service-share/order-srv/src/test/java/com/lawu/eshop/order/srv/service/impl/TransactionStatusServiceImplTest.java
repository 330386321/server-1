package com.lawu.eshop.order.srv.service.impl;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.TransactionStatusService;
import com.lawu.eshop.compensating.transaction.bo.TransactionRecordBO;
import com.lawu.eshop.order.srv.domain.TransactionRecordDO;
import com.lawu.eshop.order.srv.domain.TransactionRecordDOExample;
import com.lawu.eshop.order.srv.mapper.TransactionRecordDOMapper;

/**
 * 
 * @author jiangxinjun
 * @date 2017年7月24日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-test.xml" })
public class TransactionStatusServiceImplTest {

	@Autowired
	private TransactionStatusService transactionStatusService;

	@Autowired
	private TransactionRecordDOMapper transactionRecordDOMapper;

	@Rollback
	@Transactional
	@Test
	public void getCount() {
		Long relateId = 1L;
		Byte type = (byte) 1;
		// 保存一条relateId不存在的记录
		transactionStatusService.save(1L, type);

		TransactionRecordDOExample example = new TransactionRecordDOExample();
		example.createCriteria().andRelateIdEqualTo(relateId);
		TransactionRecordDO transactionRecordDO = transactionRecordDOMapper.selectByExample(example).get(0);
		Assert.assertNotNull(transactionRecordDO);
		Assert.assertNotNull(transactionRecordDO.getId());
		Assert.assertNotNull(transactionRecordDO.getGmtCreate());
		Assert.assertNotNull(transactionRecordDO.getGmtModified());
		Assert.assertEquals(false, transactionRecordDO.getIsProcessed());
		Assert.assertEquals(type, transactionRecordDO.getType());
		Assert.assertEquals(relateId, transactionRecordDO.getRelateId());
		Assert.assertEquals(0, transactionRecordDO.getTimes().intValue());

		// 更新这条事务记录
		TransactionRecordDO updateTransactionRecordDO = new TransactionRecordDO();
		updateTransactionRecordDO.setId(transactionRecordDO.getId());
		updateTransactionRecordDO.setTimes(1L);
		updateTransactionRecordDO.setIsProcessed(true);
		transactionRecordDOMapper.updateByPrimaryKeySelective(updateTransactionRecordDO);

		// 保存一条relateId存在的记录,会重置status/times/is_processed字段
		transactionStatusService.save(1L, type);

		transactionRecordDO = transactionRecordDOMapper.selectByPrimaryKey(updateTransactionRecordDO.getId());
		Assert.assertNotNull(transactionRecordDO);
		Assert.assertNotNull(transactionRecordDO.getId());
		Assert.assertNotNull(transactionRecordDO.getGmtCreate());
		Assert.assertNotNull(transactionRecordDO.getGmtModified());
		Assert.assertEquals(false, transactionRecordDO.getIsProcessed());
		Assert.assertEquals(type, transactionRecordDO.getType());
		Assert.assertEquals(relateId, transactionRecordDO.getRelateId());
		Assert.assertEquals(0, transactionRecordDO.getTimes().intValue());
	}

	@Rollback
	@Transactional
	@Test
	public void selectNotDoneList() {
		Byte type = (byte) 1;
		TransactionRecordDO expected = new TransactionRecordDO();
		expected.setGmtCreate(new Date());
		expected.setGmtModified(new Date());
		expected.setIsProcessed(false);
		expected.setRelateId(1L);
		expected.setTimes(0L);
		expected.setType(type);
		transactionRecordDOMapper.insert(expected);

		TransactionRecordBO actual = transactionStatusService.selectNotDoneList(type).get(0);
		assertTransactionRecordBO(expected, actual);
	}
	
	@Rollback
	@Transactional
	@Test
	public void success() {
		TransactionRecordDO expected = new TransactionRecordDO();
		expected.setGmtCreate(new Date());
		expected.setGmtModified(new Date());
		expected.setIsProcessed(false);
		expected.setRelateId(1L);
		expected.setTimes(0L);
		expected.setType((byte) 1);
		transactionRecordDOMapper.insert(expected);
		
		transactionStatusService.success(expected.getRelateId());
		
		TransactionRecordDO actual = transactionRecordDOMapper.selectByPrimaryKey(expected.getId());
		Assert.assertNotNull(actual);
		Assert.assertEquals(true, actual.getIsProcessed());
	}
	
	@Rollback
	@Transactional
	@Test
	public void updateTimes() {
		TransactionRecordDO expected = new TransactionRecordDO();
		expected.setGmtCreate(new Date());
		expected.setGmtModified(new Date());
		expected.setIsProcessed(false);
		expected.setRelateId(1L);
		expected.setTimes(0L);
		expected.setType((byte) 1);
		transactionRecordDOMapper.insert(expected);
		
		transactionStatusService.updateTimes(expected.getId(), expected.getTimes() + 1);
		
		TransactionRecordDO actual = transactionRecordDOMapper.selectByPrimaryKey(expected.getId());
		Assert.assertNotNull(actual);
		Assert.assertEquals(expected.getTimes() + 1, actual.getTimes().intValue());
	}

	public static void assertTransactionRecordBO(TransactionRecordDO expected, TransactionRecordBO actual) {
		Assert.assertNotNull(actual);
		Assert.assertNotNull(actual.getId());
		Assert.assertEquals(expected.getRelateId(), actual.getRelateId());
		Assert.assertEquals(expected.getTimes().intValue(), actual.getTimes().intValue());
	}
}
