package com.lawu.eshop.user.srv.rong.service.impl;

import com.lawu.eshop.user.srv.rong.RongCloud;
import com.lawu.eshop.user.srv.rong.constant.RongYunConstant;
import com.lawu.eshop.user.srv.rong.models.CheckOnlineResult;
import com.lawu.eshop.user.srv.rong.models.CodeSuccessResult;
import com.lawu.eshop.user.srv.rong.models.TokenResult;
import com.lawu.eshop.user.srv.rong.service.RongMerchantService;

/**
 * @author zhangyong
 * @date 2017/4/18.
 */
public class RongMerchantServiceImpl implements RongMerchantService {
    RongCloud rongCloud = RongCloud.getInstance(RongYunConstant.M_APP_KEY, RongYunConstant.M_APP_SECRET);

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
}
