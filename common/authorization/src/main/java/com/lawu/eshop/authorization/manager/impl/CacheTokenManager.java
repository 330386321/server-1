package com.lawu.eshop.authorization.manager.impl;

import com.lawu.eshop.authorization.manager.TokenCacheService;
import com.lawu.eshop.authorization.manager.TokenClearType;

/**
 * 使用缓存存储Token
 * @author ScienJus
 * @author Leach
 * @date 2015/10/26.
 */
public class CacheTokenManager extends AbstractTokenManager {

    private TokenCacheService tokenCacheService;

    @Override
    protected void delSingleRelationshipByKey(String account, TokenClearType tokenClearType) {
        tokenCacheService.delRelationshipByAccount(account, tokenExpireSeconds, tokenClearType);
    }

    @Override
    public void delRelationshipByToken(String token) {
        tokenCacheService.delRelationshipByToken(token, singleTokenWithUser, tokenExpireSeconds);
    }

    @Override
    public TokenClearType getTokenClearType(String token) {
        return tokenCacheService.getTokenClearType(token);
    }

    @Override
    protected void createSingleRelationship(String account, String token) {
        tokenCacheService.setTokenOneToOne(account, token, tokenExpireSeconds);
    }

    @Override
    protected void createMultipleRelationship(String account, String token) {

        tokenCacheService.setTokenOneToMany(account, token, tokenExpireSeconds);
    }

    @Override
    protected String getAccountByToken(String token, boolean flushExpireAfterOperation) {
        return tokenCacheService.getAccount(token, flushExpireAfterOperation, tokenExpireSeconds, singleTokenWithUser);
    }

    public void setTokenCacheService(TokenCacheService tokenCacheService) {
        this.tokenCacheService = tokenCacheService;
    }

}
