package com.lawu.eshop.product.srv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.TransactionStatusService;
import com.lawu.eshop.compensating.transaction.bo.TransactionRecordBO;
import com.lawu.eshop.product.srv.domain.TransactionRecordDO;
import com.lawu.eshop.product.srv.domain.TransactionRecordDOExample;
import com.lawu.eshop.product.srv.mapper.TransactionRecordDOMapper;

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
        transactionRecord.setTimes(0L);
        transactionRecordDOMapper.insertSelective(transactionRecord);
        return transactionRecord.getId();
    }

    @Transactional
    @Override
    public Long success(Long transactionId) {
        TransactionRecordDO transactionRecord = transactionRecordDOMapper.selectByPrimaryKey(transactionId);
        if (transactionRecord == null) {
            return null;
        }
        TransactionRecordDO record = new TransactionRecordDO();
        record.setId(transactionId);
        record.setIsProcessed(true);
        record.setGmtModified(new Date());
        transactionRecordDOMapper.updateByPrimaryKey(record);
        return transactionRecord.getRelateId();
    }

    @Override
    public List<TransactionRecordBO> selectNotDoneList(byte type) {
        TransactionRecordDOExample example = new TransactionRecordDOExample();
        example.createCriteria().andTypeEqualTo(type).andIsProcessedEqualTo(false);
        List<TransactionRecordDO> transactionRecordDOS = transactionRecordDOMapper.selectByExample(example);
        List<TransactionRecordBO> notDoneList = new ArrayList<TransactionRecordBO>();
        for (int i = 0; i < transactionRecordDOS.size(); i++) {
            TransactionRecordDO transactionRecordDO = transactionRecordDOS.get(i);
            
            TransactionRecordBO transactionRecordBO = new TransactionRecordBO();
            transactionRecordBO.setId(transactionRecordDO.getId());
            transactionRecordBO.setRelateId(transactionRecordDO.getRelateId());
            transactionRecordBO.setTimes(transactionRecordDO.getTimes());
            notDoneList.add(transactionRecordBO);
        }
        return notDoneList;
    }
    
    @Transactional
	@Override
	public void updateTimes(Long transactionId, Long times) {
		TransactionRecordDO record = new TransactionRecordDO();
		record.setId(transactionId);
		record.setTimes(times);
		transactionRecordDOMapper.updateByPrimaryKeySelective(record);
	}
}
