package com.lawu.eshop.user.srv.rong.service.impl;

import com.lawu.eshop.user.srv.rong.models.HistoryMessageResult;
import org.springframework.stereotype.Service;

import com.lawu.eshop.user.srv.rong.RongCloud;
import com.lawu.eshop.user.srv.rong.constant.RongYunConstant;
import com.lawu.eshop.user.srv.rong.models.CheckOnlineResult;
import com.lawu.eshop.user.srv.rong.models.CodeSuccessResult;
import com.lawu.eshop.user.srv.rong.models.TokenResult;
import com.lawu.eshop.user.srv.rong.service.RongUserService;

/**
 * @author zhangyong
 * @date 2017/4/14.
 */
@Service
public class RongUserServiceImpl implements RongUserService {
    RongCloud rongCloud = RongCloud.getInstance(RongYunConstant.APP_KEY, RongYunConstant.APP_SECRET);

    @Override
    public TokenResult getRongToken(String userId, String name, String portraitUri){
        // 获取 Token 方法
        TokenResult userGetTokenResult = rongCloud.user.getToken(userId, name, portraitUri);
        return userGetTokenResult;
    }

    @Override
    public CodeSuccessResult refreshUserInfo(String userId, String name, String portraitUri){
        CodeSuccessResult userRefreshResult = rongCloud.user.refresh(userId, name, portraitUri);
        return userRefreshResult;
    }

    @Override
    public CheckOnlineResult checkOnline(String userId) throws Exception {
        CheckOnlineResult userCheckOnlineResult = rongCloud.user.checkOnline(userId);
        return userCheckOnlineResult;
    }

    @Override
    public HistoryMessageResult getHistory(String date) {
        HistoryMessageResult messageResult = rongCloud.message.getHistory(date);
        return  messageResult;
    }
}
