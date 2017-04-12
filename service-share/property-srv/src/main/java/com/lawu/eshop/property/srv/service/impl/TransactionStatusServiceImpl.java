package com.lawu.eshop.property.srv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.TransactionStatusService;
import com.lawu.eshop.property.srv.domain.TransactionRecordDO;
import com.lawu.eshop.property.srv.domain.TransactionRecordDOExample;
import com.lawu.eshop.property.srv.mapper.TransactionRecordDOMapper;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年4月12日 上午9:14:30
 *
 */
@Service
public class TransactionStatusServiceImpl implements TransactionStatusService {

    @Autowired
    private TransactionRecordDOMapper transactionRecordDOMapper;


    @Transactional
    @Override
    public Long save(Long relateId, byte type) {
        TransactionRecordDO transactionRecord = new TransactionRecordDO();
        transactionRecord.setIsProcessed(false);
        transactionRecord.setRelateId(relateId);
        transactionRecord.setType(type);
        transactionRecord.setGmtModified(new Date());
        transactionRecord.setGmtCreate(new Date());
        transactionRecordDOMapper.insert(transactionRecord);
        return transactionRecord.getId();
    }

    @Transactional
    @Override
    public Long success(Long transactionId) {
        TransactionRecordDO transactionRecord = transactionRecordDOMapper.selectByPrimaryKey(transactionId);
        if (transactionRecord == null) {
            return null;
        }
        return transactionRecord.getRelateId();
    }

    @Override
    public List<Long> selectNotDoneList(byte type) {
        TransactionRecordDOExample example = new TransactionRecordDOExample();
        example.createCriteria().andTypeEqualTo(type).andIsProcessedEqualTo(false);
        List<TransactionRecordDO> transactionRecordDOS = transactionRecordDOMapper.selectByExample(example);
        List<Long> notDoneList = new ArrayList<>();
        for (int i = 0; i < transactionRecordDOS.size(); i++) {
            TransactionRecordDO transactionRecordDO = transactionRecordDOS.get(i);
            notDoneList.add(transactionRecordDO.getRelateId());
        }
        return notDoneList;
    }
}