package com.lawu.eshop.mall.srv.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.mall.param.LotteryRecordParam;
import com.lawu.eshop.mall.srv.domain.LotteryRecordDO;
import com.lawu.eshop.mall.srv.domain.LotteryRecordDOExample;
import com.lawu.eshop.mall.srv.mapper.LotteryRecordDOMapper;
import com.lawu.eshop.mall.srv.service.LotteryRecordService;

/**
 * @author meishuquan
 * @date 2017/11/23.
 */
@Service
public class LotteryRecordServiceImpl implements LotteryRecordService {

    @Autowired
    private LotteryRecordDOMapper lotteryRecordDOMapper;

    @Override
    @Transactional
    public void saveLotteryRecord(LotteryRecordParam param) {
        LotteryRecordDO recordDO = new LotteryRecordDO();
        recordDO.setUserId(param.getUserId());
        recordDO.setUserNum(param.getUserNum());
        recordDO.setAccount(param.getAccount());
        recordDO.setLotteryActivityId(param.getLotteryActivityId());
        recordDO.setPrizeName(param.getPrizeName());
        recordDO.setLotteryCount(1);
        recordDO.setGmtCreate(new Date());
        lotteryRecordDOMapper.insertSelective(recordDO);
    }

    @Override
    @Transactional
    public void updateLotteryCountByUserNumAndLotteryActivityId(String userNum, Long lotteryActivityId) {
        LotteryRecordDO recordDO = new LotteryRecordDO();
        recordDO.setLotteryCount(2);
        LotteryRecordDOExample example = new LotteryRecordDOExample();
        example.createCriteria().andUserNumEqualTo(userNum).andLotteryActivityIdEqualTo(lotteryActivityId);
        lotteryRecordDOMapper.updateByExampleSelective(recordDO, example);
    }

}
