package com.lawu.eshop.merchant.api.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lawu.eshop.framework.core.event.AsyncEventHandle;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.interceptor.UserVisitEvent;
import com.lawu.eshop.merchant.api.MerchantApiConfig;
import com.lawu.eshop.merchant.api.service.MerchantService;
import com.lawu.eshop.merchant.api.service.UserFreezeRecordService;
import com.lawu.eshop.merchant.api.service.UserVisitService;
import com.lawu.eshop.user.dto.MobileDTO;
import com.lawu.eshop.user.param.UserFreezeRecordParam;
import com.lawu.eshop.utils.DateUtil;

/**
 * @author zhangyong
 * @date 2017/7/3.
 */
@Component
public class UserVisitEventHandle implements AsyncEventHandle<UserVisitEvent> {

    @Autowired
    private UserVisitService userVisitService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantApiConfig merchantApiConfig;

    @Autowired
    private UserFreezeRecordService userFreezeRecordService;

    @Override
    public void execute(UserVisitEvent event) {
        String nowTimeStr = DateUtil.getIntDate();
        userVisitService.addUserVisitCount(event.getUserNum(), nowTimeStr, event.getUserId(), event.getUserType());

        Long currTime = System.currentTimeMillis();
        //上次访问接口时间
        Result<Long> timeResult = userVisitService.getUserVisitTime(event.getUserId(), event.getUserType());
        if (currTime - timeResult.getModel() < merchantApiConfig.getVisitTimeInterval()) {
            userVisitService.addUserVisitFrequency(event.getUserId(), event.getUserType(), merchantApiConfig.getExpireTime());
        }
        //查询时间周期内访问接口频率
        Result<Integer> frequencyResult = userVisitService.getUserVisitFrequency(event.getUserId(), event.getUserType());
        if (frequencyResult.getModel() >= merchantApiConfig.getVisitFrequencyCount()) {
            userVisitService.delUserVisitFrequency(event.getUserId(), event.getUserType());

            Result<MobileDTO> dtoResult = merchantService.selectMobile(event.getUserId());
            //保存冻结记录
            UserFreezeRecordParam param = new UserFreezeRecordParam();
            param.setUserNum(event.getUserNum());
            param.setAccount(dtoResult.getModel().getMobile());
            param.setUserType(event.getUserType().val);
            param.setCause("访问频率过高(" + merchantApiConfig.getVisitFrequencyCount() + "次/" + merchantApiConfig.getVisitTimeInterval() / 1000 + "秒)，系统冻结");
            userFreezeRecordService.saveUserFreezeRecord(param);
        }
        //保存本次访问接口时间
        userVisitService.addUserVisitTime(event.getUserId(), event.getUserType());
    }
}
