package com.lawu.eshop.merchant.api.service.impl;

import com.lawu.eshop.authorization.manager.CacheService;
import com.lawu.eshop.merchant.api.service.MerchantTokenService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Leach
 * @date 2017/3/20
 */
public class RedisCacheServiceImpl implements CacheService {

    @Autowired
    private MerchantTokenService merchantTokenService;

    @Override
    public void setTokenOneToOne(String account, String token, Integer expireSeconds) {
        merchantTokenService.setMerchantTokenOneToOne(account, token, expireSeconds);
    }

    @Override
    public void setTokenOneToMany(String account, String token, Integer expireSeconds) {

        merchantTokenService.setMerchantTokenOneToMany(account, token, expireSeconds);
    }

    @Override
    public String getAccount(String token, Boolean flushExpireAfterOperation, Integer expireSeconds, Boolean singleTokenWithUser) {
        return merchantTokenService.getMerchantAccount(token, flushExpireAfterOperation, expireSeconds, singleTokenWithUser);
    }

    @Override
    public void delRelationshipByAccount(String account) {
        merchantTokenService.delMerchantRelationshipByAccount(account);
    }

    @Override
    public void delRelationshipByToken(String token, Boolean singleTokenWithUser) {
        merchantTokenService.delMerchantRelationshipByToken(token, singleTokenWithUser);
    }
}
