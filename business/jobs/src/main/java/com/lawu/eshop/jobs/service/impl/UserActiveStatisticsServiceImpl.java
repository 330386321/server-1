package com.lawu.eshop.jobs.service.impl;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.jobs.service.CollectionUserActiveService;
import com.lawu.eshop.jobs.service.UserActiveService;
import com.lawu.eshop.jobs.service.UserActiveStatisticsService;
import com.lawu.eshop.statistics.dto.UserActiveDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        //查询用户活跃总数并统计
        Result<List<UserActiveDTO>> memberResult = collectionUserActiveService.collectionMemberActiveAreaDaily();
        if(memberResult.getModel() != null && !memberResult.getModel().isEmpty()){
            userActiveService.saveUserActiveAreaDaily(memberResult.getModel());
        }
        //查询商家活跃总数
        Result<List<UserActiveDTO>> merchantResult = collectionUserActiveService.collectionMerchantActiveAreaDaily();
        //更新统计商家总数
        if(merchantResult.getModel() != null && !merchantResult.getModel().isEmpty()){
            userActiveService.saveMerchantActiveAreaDaily(merchantResult.getModel());
        }

    }
}
