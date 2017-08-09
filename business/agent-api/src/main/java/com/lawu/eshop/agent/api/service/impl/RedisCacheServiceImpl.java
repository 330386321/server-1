package com.lawu.eshop.agent.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawu.eshop.agent.api.service.AgentUserTokenService;
import com.lawu.eshop.authorization.manager.CacheService;

/**
 * @author Leach
 * @date 2017/3/20
 */
public class RedisCacheServiceImpl implements CacheService {

    @Autowired
    private AgentUserTokenService agentUserTokenService;

    @Override
    public void setTokenOneToOne(String account, String token, Integer expireSeconds) {
        agentUserTokenService.setMemberTokenOneToOne(account, token, expireSeconds);
    }

    @Override
    public void setTokenOneToMany(String account, String token, Integer expireSeconds) {

        agentUserTokenService.setMemberTokenOneToMany(account, token, expireSeconds);
    }

    @Override
    public String getAccount(String token, Boolean flushExpireAfterOperation, Integer expireSeconds, Boolean singleTokenWithUser) {
        return agentUserTokenService.getMemberAccount(token, flushExpireAfterOperation, expireSeconds, singleTokenWithUser);
    }

    @Override
    public void delRelationshipByAccount(String account) {
        agentUserTokenService.delMemberRelationshipByAccount(account);
    }

    @Override
    public void delRelationshipByToken(String token, Boolean singleTokenWithUser) {
        agentUserTokenService.delMemberRelationshipByToken(token, singleTokenWithUser);
    }
}
