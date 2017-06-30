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
    public UserActiveBO collectionMemberActiveAreaDaily() {
        Date time = DateUtil.getDayBefore(DateUtil.getNowDate());
        UserActiveDOView userActiveDOView = userActiveDOMapperExtend.collectionMemberActiveAreaDaily(time);
        UserActiveBO userActiveBO = UserActiveConverter.coverBO(userActiveDOView);
        return userActiveBO;
    }

    @Override
    public UserActiveBO collectionMerchantActiveAreaDaily() {
        Date time = DateUtil.getDayBefore(DateUtil.getNowDate());
        UserActiveDOView userActiveDOView = userActiveDOMapperExtend.collectionMerchantActiveAreaDaily(time);
        UserActiveBO userActiveBO = UserActiveConverter.coverBO(userActiveDOView);
        return userActiveBO;
    }
}
