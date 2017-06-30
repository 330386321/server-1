package com.lawu.eshop.jobs.service.impl;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.jobs.service.CollectionUserActiveService;
import com.lawu.eshop.jobs.service.UserActiveService;
import com.lawu.eshop.jobs.service.UserActiveStatisticsService;
import com.lawu.eshop.statistics.dto.UserActiveDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangyong
 * @date 2017/6/29.
 */
@Service
public class UserActiveStatisticsServiceImpl implements UserActiveStatisticsService{

    @Autowired
    private CollectionUserActiveService collectionUserActiveService;
    @Autowired
    private UserActiveService userActiveService;
    @Override
    public void executeCollectionUserActiveDaily() {

        Result<Integer> memberResult = collectionUserActiveService.collectionMemberActiveDaily();
        Result<Integer> merchantResult = collectionUserActiveService.collectionMerchantActiveDaily();

        userActiveService.saveUserActiveDaily(memberResult.getModel(), merchantResult.getModel());

    }

    @Override
    public void executeCollectionUserActiveMonth() {
        Result<Integer> memberResult = collectionUserActiveService.collectionMemberActiveMonth();
        Result<Integer> merchantResult = collectionUserActiveService.collectionMerchantActiveMonth();

        userActiveService.saveUserActiveMonth(memberResult.getModel(), merchantResult.getModel());
    }

    @Override
    public void executeCollectionUserActiveAreaDaily() {

        Result<UserActiveDTO> memberResult = collectionUserActiveService.collectionMemberActiveAreaDaily();

        Result<UserActiveDTO> merchantResult = collectionUserActiveService.collectionMerchantActiveAreaDaily();

    }
}
