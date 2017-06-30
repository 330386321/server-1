package com.lawu.eshop.statistics.srv.service.impl;

import com.lawu.eshop.statistics.srv.bo.UserActiveBO;
import com.lawu.eshop.statistics.srv.converter.UserActiveConverter;
import com.lawu.eshop.statistics.srv.domain.extend.UserActiveDOView;
import com.lawu.eshop.statistics.srv.mapper.extend.UserActiveDOMapperExtend;
import com.lawu.eshop.statistics.srv.service.UserActiveService;
import com.lawu.eshop.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/6/29.
 */
@Service
public class UserActiveServiceImpl implements UserActiveService {

    @Autowired
    private UserActiveDOMapperExtend userActiveDOMapperExtend;
    @Override
    public Integer collectionMemberActiveDaily() {
        Date time = DateUtil.getDayBefore(DateUtil.getNowDate());
        Integer count = userActiveDOMapperExtend.collectionMemberActiveDaily(time);
        return count;
    }

    @Override
    public Integer collectionMerchantActiveDaily() {
        Date time = DateUtil.getDayBefore(DateUtil.getNowDate());
        Integer count = userActiveDOMapperExtend.collectionMerchantActiveDaily(time);
        return count;
    }

    @Override
    public Integer collectionMemberActiveMonth() {
        Integer count = userActiveDOMapperExtend.collectionMemberActiveMonth(DateUtil.getNowDate());
        return count;
    }

    @Override
    public Integer collectionMerchantActiveMonth() {
        Integer count = userActiveDOMapperExtend.collectionMerchantActiveMonth(DateUtil.getNowDate());
        return count;
    }

    @Override
    public List<UserActiveBO> collectionMemberActiveAreaDaily() {
        Date time = DateUtil.getDayBefore(DateUtil.getNowDate());
        List<UserActiveDOView> userActiveDOViews = userActiveDOMapperExtend.collectionMemberActiveAreaDaily(time);
        List<UserActiveBO> userActiveBOS = UserActiveConverter.coverBOS(userActiveDOViews);
        return userActiveBOS;
    }

    @Override
    public List<UserActiveBO> collectionMerchantActiveAreaDaily() {
        Date time = DateUtil.getDayBefore(DateUtil.getNowDate());
        List<UserActiveDOView>  userActiveDOViews = userActiveDOMapperExtend.collectionMerchantActiveAreaDaily(time);
        List<UserActiveBO> userActiveBOS = UserActiveConverter.coverBOS(userActiveDOViews);
        return userActiveBOS;
    }
}
