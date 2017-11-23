/**
 * Administrator
 * 2017年11月23日
 */
package com.lawu.eshop.product.srv.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawu.eshop.common.constants.StatusEnum;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.constant.ActivityStatusEnum;
import com.lawu.eshop.product.param.SnapUpActivityPageQueryParam;
import com.lawu.eshop.product.srv.domain.SeckillActivityDO;
import com.lawu.eshop.product.srv.domain.SeckillActivityDOExample;
import com.lawu.eshop.product.srv.mapper.SeckillActivityDOMapper;
import com.lawu.eshop.product.srv.service.SnapUpActivityService;
import com.lawu.eshop.utils.DateUtil;

/**
 * 抢购服务接口实现类
 * 
 * @author jiangxinjun
 * @createDate 2017年11月23日
 * @updateDate 2017年11月23日
 */
public class SnapUpActivityServiceImpl implements SnapUpActivityService {

    @Autowired
    private SeckillActivityDOMapper seckillActivityDOMapper;
    
    @Override
    public Page<?> page(SnapUpActivityPageQueryParam param) {
        return null;
    }

    @Override
    public List<?> list() {
        SeckillActivityDOExample example = new SeckillActivityDOExample();
        SeckillActivityDOExample.Criteria criteria = example.createCriteria();
        // 有效的记录
        criteria.andStatusEqualTo(StatusEnum.VALID.getValue());
        // 获取当前时间日期的格式化
        String date = DateUtil.getDateFormat(new Date());
        // 获取当天的所有抢购活动。
        Date startDatetime = DateUtil.formatDate(date + "00:00:00", DateUtil.DATETIME_DEFAULT_FORMAT);
        Date endDatetime = DateUtil.formatDate(date + "23:59:59", DateUtil.DATETIME_DEFAULT_FORMAT);
        criteria.andStartDateBetween(startDatetime, endDatetime);
        criteria.andActivityStatusNotEqualTo(ActivityStatusEnum.UNPUBLISHED.getValue());
        List<SeckillActivityDO> list = seckillActivityDOMapper.selectByExample(example);
        return null;
    }

    @Override
    public Object information(Long id) {
        return null;
    }
    
}
