/**
 * Administrator
 * 2017年11月23日
 */
package com.lawu.eshop.product.srv.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.common.constants.StatusEnum;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.constant.ActivityStatusEnum;
import com.lawu.eshop.product.param.SeckillActivityPageQueryParam;
import com.lawu.eshop.product.srv.bo.SeckillActivityBO;
import com.lawu.eshop.product.srv.converter.SeckillActivityConverter;
import com.lawu.eshop.product.srv.domain.SeckillActivityDO;
import com.lawu.eshop.product.srv.domain.SeckillActivityDOExample;
import com.lawu.eshop.product.srv.mapper.SeckillActivityDOMapper;
import com.lawu.eshop.product.srv.service.SeckillActivityService;
import com.lawu.eshop.utils.DateUtil;

/**
 * 抢购服务接口实现类
 * 
 * @author jiangxinjun
 * @createDate 2017年11月23日
 * @updateDate 2017年11月23日
 */
@Service
public class SeckillActivityServiceImpl implements SeckillActivityService {

    @Autowired
    private SeckillActivityDOMapper seckillActivityDOMapper;
    
    @Override
    public Page<?> page(SeckillActivityPageQueryParam param) {
        return null;
    }

    @Override
    public List<SeckillActivityBO> thatDayList() {
        return thatDaylist(new Date());
    }
    
    @Override
    public List<SeckillActivityBO> recentlyList() {
        SeckillActivityDOExample example = new SeckillActivityDOExample();
        SeckillActivityDOExample.Criteria criteria = example.createCriteria();
        // 查询离今天最近的一个活动
        // 有效的记录
        criteria.andStatusEqualTo(StatusEnum.VALID.getValue());
        // 未开始
        criteria.andActivityStatusEqualTo(ActivityStatusEnum.NOT_STARTED.getValue());
        // 获取当前时间日期的格式化
        String todayDate = DateUtil.getDateFormat(new Date());
        // 获取开始时间大于今天的活动
        criteria.andStartDateGreaterThanOrEqualTo(DateUtil.formatDate(todayDate + " 23:59:59", DateUtil.DATETIME_DEFAULT_FORMAT));
        // 获取第一条记录
        RowBounds rowBounds = new RowBounds(0, 1);
        List<SeckillActivityDO> list = seckillActivityDOMapper.selectByExampleWithRowbounds(example, rowBounds);
        // 最近没有活动直接返回
        if (list == null || list.isEmpty()) {
            return null;
        }
        // 取出最近活动的开始时间
        return thatDaylist(list.get(0).getStartDate());
    }

    /**********************************************************************
     * PRIVATE METHOD
     * **********************************************************************/
    /**
     * 根据传入的日期查询当天的所有活动
     * 
     * @param date
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月23日
     * @updateDate 2017年11月23日
     */
    private List<SeckillActivityBO> thatDaylist(Date day) {
        SeckillActivityDOExample example = new SeckillActivityDOExample();
        SeckillActivityDOExample.Criteria criteria = example.createCriteria();
        // 有效的记录
        criteria.andStatusEqualTo(StatusEnum.VALID.getValue());
        // 获取当前时间日期的格式化
        String date = DateUtil.getDateFormat(day);
        // 获取当天的所有抢购活动。
        Date startDatetime = DateUtil.formatDate(date + " 00:00:00", DateUtil.DATETIME_DEFAULT_FORMAT);
        Date endDatetime = DateUtil.formatDate(date + " 23:59:59", DateUtil.DATETIME_DEFAULT_FORMAT);
        criteria.andStartDateBetween(startDatetime, endDatetime);
        criteria.andActivityStatusNotEqualTo(ActivityStatusEnum.UNPUBLISHED.getValue());
        // 按照开始时间正序
        example.setOrderByClause("start_date asc");
        List<SeckillActivityDO> list = seckillActivityDOMapper.selectByExample(example);
        return SeckillActivityConverter.convertSeckillActivityBOList(list);
    }
}
