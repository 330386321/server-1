package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.compensating.transaction.TransactionStatusService;
import com.lawu.eshop.compensating.transaction.bo.TransactionRecordBO;
import com.lawu.eshop.user.srv.domain.TransactionRecordDO;
import com.lawu.eshop.user.srv.mapper.TransactionRecordDOMapper;
import com.lawu.eshop.utils.DataTransUtil;
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
public class TransactionStatusServiceImplTest {

    @Autowired
    private TransactionStatusService transactionStatusService;

    @Autowired
    private TransactionRecordDOMapper transactionRecordDOMapper;

    @Transactional
    @Rollback
    @Test
    public void save() {
        transactionStatusService.save(300L, DataTransUtil.intToByte(1));
        List<TransactionRecordDO> transactionRecordDOList = transactionRecordDOMapper.selectByExample(null);
        Assert.assertNotNull(transactionRecordDOList);
        Assert.assertEquals(1, transactionRecordDOList.size());

        transactionStatusService.save(300L, DataTransUtil.intToByte(1));
        transactionRecordDOList = transactionRecordDOMapper.selectByExample(null);
        Assert.assertNotNull(transactionRecordDOList);
        Assert.assertEquals(1, transactionRecordDOList.size());
    }

    @Transactional
    @Rollback
    @Test
    public void success() {
        TransactionRecordDO transactionRecord = new TransactionRecordDO();
        transactionRecord.setRelateId(300L);
        transactionRecord.setType(DataTransUtil.intToByte(1));
        transactionRecord.setIsProcessed(false);
        transactionRecord.setTimes(0L);
        transactionRecord.setGmtModified(new Date());
        transactionRecord.setGmtCreate(new Date());
        transactionRecordDOMapper.insertSelective(transactionRecord);

        transactionStatusService.success(transactionRecord.getId());
        TransactionRecordDO recordDO = transactionRecordDOMapper.selectByPrimaryKey(transactionRecord.getId());
        Assert.assertNotNull(recordDO);
        Assert.assertTrue(recordDO.getIsProcessed());
    }

    @Transactional
    @Rollback
    @Test
    public void selectNotDoneList() {
        TransactionRecordDO transactionRecord = new TransactionRecordDO();
        transactionRecord.setRelateId(300L);
        transactionRecord.setType(DataTransUtil.intToByte(1));
        transactionRecord.setIsProcessed(false);
        transactionRecord.setTimes(0L);
        transactionRecord.setGmtModified(new Date());
        transactionRecord.setGmtCreate(new Date());
        transactionRecordDOMapper.insertSelective(transactionRecord);

        List<TransactionRecordBO> recordBOS = transactionStatusService.selectNotDoneList(DataTransUtil.intToByte(1));
        Assert.assertNotNull(recordBOS);
        Assert.assertEquals(1, recordBOS.size());
    }

    @Transactional
    @Rollback
    @Test
    public void updateTimes() {
        TransactionRecordDO transactionRecord = new TransactionRecordDO();
        transactionRecord.setRelateId(300L);
        transactionRecord.setType(DataTransUtil.intToByte(1));
        transactionRecord.setIsProcessed(false);
        transactionRecord.setTimes(0L);
        transactionRecord.setGmtModified(new Date());
        transactionRecord.setGmtCreate(new Date());
        transactionRecordDOMapper.insertSelective(transactionRecord);

        transactionStatusService.updateTimes(transactionRecord.getId(), 0L);
        TransactionRecordDO recordDO = transactionRecordDOMapper.selectByPrimaryKey(transactionRecord.getId());
        Assert.assertNotNull(recordDO);
        Assert.assertEquals(0, recordDO.getTimes().intValue());
    }

}
