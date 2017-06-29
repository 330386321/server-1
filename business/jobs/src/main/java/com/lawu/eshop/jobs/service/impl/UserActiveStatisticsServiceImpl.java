package com.lawu.eshop.jobs.service.impl;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.jobs.service.CollectionUserActiveService;
import com.lawu.eshop.jobs.service.UserActiveService;
import com.lawu.eshop.jobs.service.UserActiveStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhangyong
 * @date 2017/6/29.
 */
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
}
