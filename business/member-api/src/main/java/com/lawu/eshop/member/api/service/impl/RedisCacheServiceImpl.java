package com.lawu.eshop.member.api.service.impl;

import com.lawu.eshop.authorization.manager.CacheService;
import com.lawu.eshop.member.api.service.MemberTokenService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Leach
 * @date 2017/3/20
 */
public class RedisCacheServiceImpl implements CacheService {

    @Autowired
    private MemberTokenService memberTokenService;

    @Override
    public void setTokenOneToOne(String account, String token, Integer expireSeconds) {
        memberTokenService.setMemberTokenOneToOne(account, token, expireSeconds);
    }

    @Override
    public void setTokenOneToMany(String account, String token, Integer expireSeconds) {

        memberTokenService.setMemberTokenOneToMany(account, token, expireSeconds);
    }

    @Override
    public String getAccount(String token, Boolean flushExpireAfterOperation, Integer expireSeconds, Boolean singleTokenWithUser) {
        return memberTokenService.getMemberAccount(token, flushExpireAfterOperation, expireSeconds, singleTokenWithUser);
    }

    @Override
    public void delRelationshipByAccount(String account) {
        memberTokenService.delMemberRelationshipByAccount(account);
    }

    @Override
    public void delRelationshipByToken(String token, Boolean singleTokenWithUser) {
        memberTokenService.delMemberRelationshipByToken(token, singleTokenWithUser);
    }
}
