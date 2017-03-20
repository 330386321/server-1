package com.lawu.eshop.authorization.manager.impl;

import com.lawu.eshop.authorization.manager.CacheService;

/**
 * 使用缓存存储Token
 * @author ScienJus
 * @author Leach
 * @date 2015/10/26.
 */
public class CacheTokenManager extends AbstractTokenManager {

    private CacheService cacheService;

    @Override
    protected void delSingleRelationshipByKey(String account) {
        cacheService.delRelationshipByAccount(account);
    }

    @Override
    public void delRelationshipByToken(String token) {
        cacheService.delRelationshipByToken(token, singleTokenWithUser);
    }

    @Override
    protected void createSingleRelationship(String account, String token) {
        cacheService.setTokenOneToOne(account, token, tokenExpireSeconds);
    }

    @Override
    protected void createMultipleRelationship(String account, String token) {

        cacheService.setTokenOneToMany(account, token, tokenExpireSeconds);
    }

    @Override
    protected String getAccountByToken(String token, boolean flushExpireAfterOperation) {
        return cacheService.getAccount(token, flushExpireAfterOperation, tokenExpireSeconds, singleTokenWithUser);
    }

    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

}
