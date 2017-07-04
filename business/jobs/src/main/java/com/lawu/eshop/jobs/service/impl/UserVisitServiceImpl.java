package com.lawu.eshop.jobs.service.impl;

import com.lawu.eshop.framework.core.type.UserType;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.jobs.service.*;
import com.lawu.eshop.mall.dto.RegionDTO;
import com.lawu.eshop.statistics.param.UserVisitRecordParam;
import com.lawu.eshop.user.dto.VisitUserInfoDTO;
import com.lawu.eshop.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * @author zhangyong
 * @date 2017/7/3.
 */
public class UserVisitServiceImpl implements UserVisitService {

    @Autowired
    private MemberService memberService;

    @Autowired
    private UserVisitCacheService userVisitCacheService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private UserVisitRecordService userVisitRecordService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantStoreService merchantStoreService;

    @Override
    public void executeAddUserVisitRecords() {
        //查询会员用户记录总数
        Date yesterday = DateUtil.getDayBefore(new Date());
        String nowTimeStr = DateUtil.getIntDateByTime(yesterday);
        Integer memberCount = memberService.getTotalCount();
        if (memberCount != null && memberCount.intValue() > 0) {
            Integer memberSize = memberCount / 100 + 1;
            for (int i = 1; i <= memberSize; i++) {
                //获取缓存数据--可能为null
                Map<String, String> visitRecords = userVisitCacheService.getVisitRecords(i, nowTimeStr, UserType.MEMBER.val);
                if (!visitRecords.isEmpty()) {
                    for (String key : visitRecords.keySet()) {
                        //map.keySet()返回的是所有key的值
                        String userNum = key.substring(key.lastIndexOf("_") + 1, key.length());
                        String visitCount = visitRecords.get(key);//得到每个key多对用value的值
                        //查询用户的账号和path
                        UserVisitRecordParam userVisitRecordParam = new UserVisitRecordParam();
                        userVisitRecordParam.setUserNum(userNum);
                        userVisitRecordParam.setVisitDate(yesterday);
                        userVisitRecordParam.setUserType(UserType.MEMBER.val);
                        userVisitRecordParam.setVisitCount(Integer.valueOf(visitCount));
                        VisitUserInfoDTO userInfoDTO = memberService.findUserAccountAndRegionPathByNum(userNum);
                        if (userInfoDTO != null && StringUtils.isNotEmpty(userInfoDTO.getAccount())) {
                            userVisitRecordParam.setAccount(userInfoDTO.getAccount());
                        }
                        if (userInfoDTO == null || StringUtils.isEmpty(userInfoDTO.getRegionPath())) {
                            userVisitRecordParam.setCityId(0);
                        } else {
                            //查询 城市名称
                            String path = userInfoDTO.getRegionPath();
                            Integer cityId = Integer.valueOf(path.substring(path.lastIndexOf("/")));
                            userVisitRecordParam.setCityId(cityId);
                            Result<RegionDTO> regionDTO = regionService.getRegion(cityId);
                            if (regionDTO != null && regionDTO.getModel() != null) {
                                userVisitRecordParam.setCityName(regionDTO.getModel().getName());
                            }

                        }
                        userVisitRecordService.addUserVisitRecord(userVisitRecordParam);
                        //新增访问记录
                    }
                }
            }
        }
        //商家
        Integer merchantCount = merchantService.getTotalCount();
        if (merchantCount != null && merchantCount.intValue() > 0) {
            Integer merchantSize = merchantCount / 100 + 1;
            for (int j = 1; j <= merchantSize; j++) {
                Map<String, String> records = userVisitCacheService.getVisitRecords(j, nowTimeStr, UserType.MERCHANT.val);
                if (!records.isEmpty()) {
                    for (String key : records.keySet()) {
                        //map.keySet()返回的是所有key的值
                        String merchantNum = key.substring(key.lastIndexOf("_") + 1, key.length());
                        String visitCount = records.get(key);//得到每个key多对用value的值
                        //查询用户的账号和path
                        UserVisitRecordParam param = new UserVisitRecordParam();
                        param.setUserNum(merchantNum);
                        param.setVisitDate(yesterday);
                        param.setUserType(UserType.MERCHANT.val);
                        param.setVisitCount(Integer.valueOf(visitCount));
                        VisitUserInfoDTO merchantInfo = merchantStoreService.findAccountAndRegionPathByNum(merchantNum);
                        if (merchantInfo != null && StringUtils.isNotEmpty(merchantInfo.getAccount())) {
                            param.setAccount(merchantInfo.getAccount());
                        }
                        if (merchantInfo == null || StringUtils.isEmpty(merchantInfo.getRegionPath())) {
                            param.setCityId(0);
                        } else {
                            //查询 城市名称
                            String regionPath = merchantInfo.getRegionPath();
                            Integer cityId = Integer.valueOf(regionPath.substring(regionPath.lastIndexOf("/")));
                            param.setCityId(cityId);
                            Result<RegionDTO> region = regionService.getRegion(cityId);
                            if (region != null && region.getModel() != null && !region.getModel().getName().isEmpty()) {
                                param.setCityName(region.getModel().getName());
                            }
                        }
                        userVisitRecordService.addUserVisitRecord(param);
                        //新增访问记录
                    }
                }

            }
        }
        //删除缓存
        userVisitCacheService.delVisitRecords(nowTimeStr);
    }
}
