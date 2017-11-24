package com.lawu.eshop.mall.srv.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.constants.LotteryActivityStatusEnum;
import com.lawu.eshop.mall.query.LotteryActivityRealQuery;
import com.lawu.eshop.mall.srv.bo.LotteryActivityBO;
import com.lawu.eshop.mall.srv.converter.LotteryActivityConverter;
import com.lawu.eshop.mall.srv.domain.LotteryActivityDO;
import com.lawu.eshop.mall.srv.domain.LotteryActivityDOExample;
import com.lawu.eshop.mall.srv.domain.LotteryRecordDO;
import com.lawu.eshop.mall.srv.domain.LotteryRecordDOExample;
import com.lawu.eshop.mall.srv.mapper.LotteryActivityDOMapper;
import com.lawu.eshop.mall.srv.mapper.LotteryRecordDOMapper;
import com.lawu.eshop.mall.srv.service.LotteryActivityService;

/**
 * @author meishuquan
 * @date 2017/11/23.
 */
@Service
public class LotteryActivityServiceImpl implements LotteryActivityService {

    @Autowired
    private LotteryActivityDOMapper lotteryActivityDOMapper;

    @Autowired
    private LotteryRecordDOMapper lotteryRecordDOMapper;

    @Override
    public Page<LotteryActivityBO> listLotteryActivity(LotteryActivityRealQuery query) {
        LotteryActivityDOExample example = new LotteryActivityDOExample();
        example.createCriteria().andStatusLessThan(LotteryActivityStatusEnum.CANCEL.getVal());
        example.setOrderByClause("status asc,grade asc");

        RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
        List<LotteryActivityDO> activityDOS = lotteryActivityDOMapper.selectByExampleWithRowbounds(example, rowBounds);
        List<LotteryActivityBO> activityBOS = new ArrayList<>();
        if (!activityDOS.isEmpty()) {
            for (LotteryActivityDO activityDO : activityDOS) {
                LotteryActivityBO activityBO = LotteryActivityConverter.converBO(activityDO);

                LotteryRecordDOExample recordDOExample = new LotteryRecordDOExample();
                LotteryRecordDOExample.Criteria criteria = recordDOExample.createCriteria();
                //查询参与人数
                criteria.andLotteryActivityIdEqualTo(activityDO.getId());
                int lotteryNumber = lotteryRecordDOMapper.countByExample(recordDOExample);
                activityBO.setLotteryNumber(lotteryNumber);

                //查询参与次数
                criteria.andUserNumEqualTo(query.getUserNum());
                List<LotteryRecordDO> recordDOS = lotteryRecordDOMapper.selectByExample(recordDOExample);
                if (recordDOS.isEmpty()) {
                    activityBO.setLotteryCount(0);
                } else {
                    activityBO.setLotteryCount(recordDOS.get(0).getLotteryCount());
                }
                activityBOS.add(activityBO);
            }
        }

        Page<LotteryActivityBO> page = new Page<>();
        page.setCurrentPage(query.getCurrentPage());
        page.setTotalCount(lotteryActivityDOMapper.countByExample(example));
        page.setRecords(activityBOS);
        return page;
    }

    @Override
    public LotteryActivityBO getLotteryActivityById(Long id) {
        LotteryActivityDO activityDO = lotteryActivityDOMapper.selectByPrimaryKey(id);
        return LotteryActivityConverter.converBO(activityDO);
    }

}
