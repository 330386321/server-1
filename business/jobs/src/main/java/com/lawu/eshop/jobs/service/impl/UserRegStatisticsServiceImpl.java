package com.lawu.eshop.jobs.service.impl;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.jobs.service.CollectionUserRegService;
import com.lawu.eshop.jobs.service.RegionService;
import com.lawu.eshop.jobs.service.UserRegService;
import com.lawu.eshop.jobs.service.UserRegStatisticsService;
import com.lawu.eshop.mall.dto.RegionDTO;
import com.lawu.eshop.statistics.param.UserRegAreaParam;
import com.lawu.eshop.user.param.CollectionUserRegParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/6/29.
 */
@Service
public class UserRegStatisticsServiceImpl implements UserRegStatisticsService {

    @Autowired
    private CollectionUserRegService collectionUserRegService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private UserRegService userRegService;

    @Override
    public void executeCollectionUserRegDaily() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        CollectionUserRegParam param = new CollectionUserRegParam();
        param.setYear(calendar.get(Calendar.YEAR));
        param.setMonth(calendar.get(Calendar.MONTH) + 1);
        param.setDay(calendar.get(Calendar.DATE));
        Result<Integer> memberResult = collectionUserRegService.collectionMemberRegDaily(param);
        Result<Integer> merchantResult = collectionUserRegService.collectionMerchantRegDaily(param);
        userRegService.saveUserRegDaily(memberResult.getModel(), merchantResult.getModel());
    }

    @Override
    public void executeCollectionUserRegMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        CollectionUserRegParam param = new CollectionUserRegParam();
        param.setYear(calendar.get(Calendar.YEAR));
        param.setMonth(calendar.get(Calendar.MONTH) + 1);
        Result<Integer> memberResult = collectionUserRegService.collectionMemberRegMonth(param);
        Result<Integer> merchantResult = collectionUserRegService.collectionMerchantRegMonth(param);
        userRegService.saveUserRegMonth(memberResult.getModel(), merchantResult.getModel());
    }

    @Override
    public void executeCollectionUserRegArea() {
        Result<List<RegionDTO>> regionResult = regionService.getRegionLevelTwo();
        if (regionResult.getModel().isEmpty()) {
            return;
        }
        CollectionUserRegParam param = new CollectionUserRegParam();
        UserRegAreaParam userRegAreaParam = new UserRegAreaParam();
        for (RegionDTO regionDTO : regionResult.getModel()) {
            param.setRegionPath(regionDTO.getPath());
            Result<Integer> memberResult = collectionUserRegService.collectionMemberRegArea(param);
            Result<Integer> merchantCommonResult = collectionUserRegService.collectionMerchantCommonRegArea(param);
            Result<Integer> merchantEntityResult = collectionUserRegService.collectionMerchantEntityRegArea(param);
            userRegAreaParam.setMemberCount(memberResult.getModel());
            userRegAreaParam.setMerchantCommonCount(merchantCommonResult.getModel());
            userRegAreaParam.setMerchantEntityCount(merchantEntityResult.getModel());
            userRegAreaParam.setMerchantCount(merchantCommonResult.getModel() + merchantEntityResult.getModel());
            userRegAreaParam.setCityId(regionDTO.getId());
            if (regionDTO.getId() == 1102) {
                //北京县辖数据统计到北京市辖
                userRegAreaParam.setCityId(1101);
            } else if (regionDTO.getId() == 1202) {
                //天津县辖数据统计到天津市辖
                userRegAreaParam.setCityId(1201);
            } else if (regionDTO.getId() == 3102) {
                //上海县辖数据统计到上海市辖
                userRegAreaParam.setCityId(3101);
            } else if (regionDTO.getId() == 5002) {
                //重庆县辖数据统计到重庆市辖
                userRegAreaParam.setCityId(5001);
            }
            userRegService.updateUserRegArea(userRegAreaParam);
        }
    }

}
