package com.lawu.eshop.member.api.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lawu.eshop.authorization.manager.TokenManager;
import com.lawu.eshop.framework.core.event.AsyncEventHandle;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.interceptor.UserVisitEvent;
import com.lawu.eshop.member.api.MemberApiConfig;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.member.api.service.UserFreezeRecordService;
import com.lawu.eshop.member.api.service.UserVisitService;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.param.UserFreezeRecordParam;
import com.lawu.eshop.utils.DateUtil;

/**
 * @author Leach
 * @date 2017/7/2
 */
@Component
public class UserVisitEventHandle implements AsyncEventHandle<UserVisitEvent> {

    @Autowired
    private UserVisitService userVisitService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private MemberApiConfig memberApiConfig;

    @Autowired
    private UserFreezeRecordService userFreezeRecordService;

    @Override
    public void execute(UserVisitEvent event) {
        String nowTimeStr = DateUtil.getIntDate();
        Long currTime = System.currentTimeMillis();
        Result<Long> timeResult = userVisitService.addUserVisitCountAndTime(event.getUserNum(), nowTimeStr, event.getUserId(), event.getUserType(), String.valueOf(currTime));
        if (currTime - timeResult.getModel() < memberApiConfig.getVisitTimeInterval()) {
            userVisitService.addUserVisitFrequency(event.getUserId(), event.getUserType(), memberApiConfig.getExpireTime());
        }

        //查询时间周期内访问接口频率
        Result<Integer> frequencyResult = userVisitService.getUserVisitFrequency(event.getUserId(), event.getUserType());
        if (frequencyResult.getModel() >= memberApiConfig.getVisitFrequencyCount()) {
            userVisitService.delUserVisitFrequency(event.getUserId(), event.getUserType());

            Result<UserDTO> dtoResult = memberService.findMemberInfo(event.getUserId());
            //保存冻结记录
            UserFreezeRecordParam param = new UserFreezeRecordParam();
            param.setUserNum(event.getUserNum());
            param.setAccount(dtoResult.getModel().getAccount());
            param.setUserType(event.getUserType().val);
            param.setCause("访问频率过高(" + memberApiConfig.getVisitFrequencyCount() + "次/" + memberApiConfig.getExpireTime() + "分)，系统冻结");
            userFreezeRecordService.saveUserFreezeRecord(param);

            //冻结账号
            memberService.freezeAccount(event.getUserNum(), true, "访问频率过高，系统冻结");
            //删除个推id
            memberService.delUserGtPush(event.getUserId());
            //退出登录
            tokenManager.delRelationship(dtoResult.getModel().getAccount());
        }
    }
}
