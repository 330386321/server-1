package com.lawu.eshop.ad.srv.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.ad.srv.domain.FollowTransactionRecordDO;
import com.lawu.eshop.ad.srv.domain.FollowTransactionRecordDOExample;
import com.lawu.eshop.ad.srv.mapper.FollowTransactionRecordDOMapper;
import com.lawu.eshop.synchronization.lock.service.FollowTransactionRecordService;

/**
 * 
 * @author Sunny
 * @date 2017年5月18日
 */
@Service
public class FollowTransactionRecordServiceImpl implements FollowTransactionRecordService {
	
	@Autowired
	private FollowTransactionRecordDOMapper followTransactionRecordDOMapper;

	/**
	 * 判断MQ消息是否被成功消费
	 * 
	 * @param topic MQ消息的topic
	 * @param transationId 事务id
	 * @return
	 * @author Sunny
	 * @date 2017年6月1日
	 */
	@Override
	public boolean isExist(String topic, Long transationId) {
		FollowTransactionRecordDOExample example = new FollowTransactionRecordDOExample();
		example.createCriteria().andTopicEqualTo(topic).andTransationIdEqualTo(transationId);
		followTransactionRecordDOMapper.countByExample(example);
		return followTransactionRecordDOMapper.countByExample(example) > 0 ? true : false;
	}

	/**
	 * 消息被消费成功，保存一条记录
	 * 
	 * @param topic MQ消息的topic
	 * @param transationId 事务id
	 * @author Sunny
	 * @date 2017年6月1日
	 */
	@Transactional
	@Override
	public void consumptionSuccessful(String topic, Long transationId) {
		FollowTransactionRecordDO followTransactionRecordDO = new FollowTransactionRecordDO();
		followTransactionRecordDO.setTransationId(transationId);
		followTransactionRecordDO.setTopic(topic);
		followTransactionRecordDO.setGmtCreate(new Date());
		followTransactionRecordDOMapper.insert(followTransactionRecordDO);
	} 
	
}
