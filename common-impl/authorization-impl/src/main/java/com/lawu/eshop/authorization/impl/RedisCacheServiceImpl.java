package com.lawu.eshop.authorization.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawu.eshop.authorization.manager.CacheService;

/**
 * @author Leach
 * @date 2017/10/11
 */
public class RedisCacheServiceImpl implements CacheService {

    @Autowired
    private LoginTokenService loginTokenService;

    /**
     * 用户类型
     */
    private UserType userType;

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public void setTokenOneToOne(String account, String token, Integer expireSeconds) {
        loginTokenService.setTokenOneToOne(userType.getVal(), account, token, expireSeconds);
    }

    @Override
    public void setTokenOneToMany(String account, String token, Integer expireSeconds) {

        loginTokenService.setTokenOneToMany(userType.getVal(), account, token, expireSeconds);
    }

    @Override
    public String getAccount(String token, Boolean flushExpireAfterOperation, Integer expireSeconds, Boolean singleTokenWithUser) {
        return loginTokenService.getAccount(userType.getVal(), token, flushExpireAfterOperation, expireSeconds, singleTokenWithUser);
    }

    @Override
    public void delRelationshipByAccount(String account) {
        loginTokenService.delRelationshipByAccount(userType.getVal(), account);
    }

    @Override
    public void delRelationshipByToken(String token, Boolean singleTokenWithUser) {
        loginTokenService.delRelationshipByToken(userType.getVal(), token, singleTokenWithUser);
    }
}
