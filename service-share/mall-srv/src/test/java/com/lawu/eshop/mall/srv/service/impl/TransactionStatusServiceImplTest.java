package com.lawu.eshop.mall.srv.service.impl;

import com.lawu.eshop.compensating.transaction.TransactionStatusService;
import com.lawu.eshop.compensating.transaction.bo.TransactionRecordBO;
import com.lawu.eshop.mall.srv.domain.TransactionRecordDO;
import com.lawu.eshop.mall.srv.mapper.TransactionRecordDOMapper;
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
public class TransactionStatusServiceImplTest {
    @Autowired
    private TransactionRecordDOMapper transactionRecordDOMapper;

    @Autowired
    private TransactionStatusService transactionStatusService;

    @Transactional
    @Rollback
    @Test
    public void save() {
        TransactionRecordDO recordDO = new TransactionRecordDO();
        recordDO.setGmtCreate(new Date());
        recordDO.setIsProcessed(true);
        recordDO.setRelateId(1L);
        recordDO.setType((byte) 0x01);
        transactionRecordDOMapper.insert(recordDO);
        transactionStatusService.save(1L, (byte) 0x01);
        List<TransactionRecordDO> recordDOS = transactionRecordDOMapper.selectByExample(null);
        Assert.assertNotNull(recordDOS);
        Assert.assertEquals(1, recordDOS.size());

        transactionStatusService.save(1L, (byte) 0x02);
        List<TransactionRecordDO> list = transactionRecordDOMapper.selectByExample(null);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());


    }

    @Transactional
    @Rollback
    @Test
    public void success() {
        TransactionRecordDO recordDO = new TransactionRecordDO();
        recordDO.setGmtCreate(new Date());
        recordDO.setIsProcessed(false);
        recordDO.setRelateId(1L);
        recordDO.setType((byte) 0x01);
        transactionRecordDOMapper.insert(recordDO);

        Long relateId = transactionStatusService.success(recordDO.getId());

        Assert.assertEquals(1l, relateId.longValue());
    }

    @Transactional
    @Rollback
    @Test
    public void selectNotDoneList() {

        TransactionRecordDO recordDO = new TransactionRecordDO();
        recordDO.setGmtCreate(new Date());
        recordDO.setIsProcessed(false);
        recordDO.setRelateId(1L);
        recordDO.setType((byte) 0x01);
        transactionRecordDOMapper.insert(recordDO);
        List<TransactionRecordBO> list = transactionStatusService.selectNotDoneList((byte) 0x01);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
    }

    @Transactional
    @Rollback
    @Test
    public void updateTimes() {
        TransactionRecordDO recordDO = new TransactionRecordDO();
        recordDO.setGmtCreate(new Date());
        recordDO.setIsProcessed(false);
        recordDO.setRelateId(1L);
        recordDO.setType((byte) 0x01);
        transactionRecordDOMapper.insert(recordDO);

        transactionStatusService.updateTimes(recordDO.getId(),3L);
        List<TransactionRecordDO> transactionRecordDOS = transactionRecordDOMapper.selectByExample(null);
        Assert.assertNotNull(transactionRecordDOS);
        Assert.assertEquals(1,transactionRecordDOS.size());
        Assert.assertEquals(3L,transactionRecordDOS.get(0).getTimes().longValue());
    }
}
