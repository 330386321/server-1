package com.lawu.eshop.property.srv.service.impl;

import com.lawu.eshop.compensating.transaction.TransactionStatusService;
import com.lawu.eshop.compensating.transaction.bo.TransactionRecordBO;
import com.lawu.eshop.property.srv.domain.TransactionRecordDO;
import com.lawu.eshop.property.srv.mapper.TransactionRecordDOMapper;
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
 * @author yangqh
 * @date 2017/7/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class TransactionStatusServiceImplTest {

    @Autowired
    private TransactionStatusService TransactionStatusService;

    @Autowired
    private TransactionRecordDOMapper transactionRecordDOMapper;

    @Transactional
    @Rollback
    @Test
    public void save(){
        Long id = TransactionStatusService.save(1L,new Byte("1"));
        Assert.assertNotNull(id);

        Long id1 = TransactionStatusService.save(1L,new Byte("1"));
        Assert.assertNotNull(id1);
    }

    @Transactional
    @Rollback
    @Test
    public void success(){
        TransactionRecordDO transactionRecord = new TransactionRecordDO();
        transactionRecord.setIsProcessed(false);
        transactionRecord.setRelateId(1L);
        transactionRecord.setType(new Byte("1"));
        transactionRecord.setGmtModified(new Date());
        transactionRecord.setGmtCreate(new Date());
        transactionRecord.setTimes(0L);
        transactionRecordDOMapper.insertSelective(transactionRecord);

        Long id = TransactionStatusService.success(transactionRecord.getId());
        Assert.assertNotNull(id);
    }

    @Transactional
    @Rollback
    @Test
    public void selectNotDoneList(){
        TransactionRecordDO transactionRecord = new TransactionRecordDO();
        transactionRecord.setIsProcessed(false);
        transactionRecord.setRelateId(1L);
        transactionRecord.setType(new Byte("1"));
        transactionRecord.setGmtModified(new Date());
        transactionRecord.setGmtCreate(new Date());
        transactionRecord.setTimes(0L);
        transactionRecordDOMapper.insertSelective(transactionRecord);

        List<TransactionRecordBO> rtnList = TransactionStatusService.selectNotDoneList(new Byte("1"));
        Assert.assertEquals(1,rtnList.size());

    }

    @Transactional
    @Rollback
    @Test
    public void updateTimes(){
        TransactionRecordDO transactionRecord = new TransactionRecordDO();
        transactionRecord.setIsProcessed(false);
        transactionRecord.setRelateId(1L);
        transactionRecord.setType(new Byte("1"));
        transactionRecord.setGmtModified(new Date());
        transactionRecord.setGmtCreate(new Date());
        transactionRecord.setTimes(0L);
        transactionRecordDOMapper.insertSelective(transactionRecord);

        TransactionStatusService.updateTimes(transactionRecord.getId(),1L);

        TransactionRecordDO tdo = transactionRecordDOMapper.selectByPrimaryKey(transactionRecord.getId());
        Assert.assertEquals(1,tdo.getTimes().intValue());
    }
}
