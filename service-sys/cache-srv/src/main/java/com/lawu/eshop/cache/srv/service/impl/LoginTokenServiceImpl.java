package com.lawu.eshop.cache.srv.service.impl;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.lawu.eshop.cache.srv.constants.KeyConstant;
import com.lawu.eshop.cache.srv.service.LoginTokenService;

/**
 * @author Leach
 * @date 2017/10/11
 */
@Service
public class LoginTokenServiceImpl implements LoginTokenService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void setTokenOneToOne(int userType, String account, String token, Integer expireSeconds) {

        String accountKey = formatAccountKey(userType, account);

        String oldToken = getValue(accountKey);
        if (oldToken != null) {
            String oldTokenKey = formatTokenKey(userType, oldToken);
            stringRedisTemplate.delete(oldTokenKey);
        }
        stringRedisTemplate.opsForValue().set(accountKey, token, expireSeconds, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set(formatTokenKey(userType, token), account, expireSeconds, TimeUnit.SECONDS);

    }

    @Override
    public void setTokenOneToMany(int userType, String account, String token, Integer expireSeconds) {
        stringRedisTemplate.opsForValue().set(formatTokenKey(userType, token), account, expireSeconds, TimeUnit.SECONDS);
    }

    @Override
    public String getAccount(int userType, String token, Boolean flushExpireAfterOperation, Integer expireSeconds, Boolean singleTokenWithUser) {
        String tokenKey = formatTokenKey(userType, token);
        String account = getValue(tokenKey);
        //根据设置，在每次有效操作后刷新过期时间
        if (account != null && flushExpireAfterOperation) {
            if (singleTokenWithUser) {
                stringRedisTemplate.expire(formatAccountKey(userType, account), expireSeconds, TimeUnit.SECONDS);

            }
            stringRedisTemplate.expire(tokenKey, expireSeconds, TimeUnit.SECONDS);

        }
        return account;
    }

    @Override
    public void delRelationshipByAccount(int userType, String account) {
        String accountKey = formatAccountKey(userType, account);
        String token = getValue(accountKey);
        if (token != null) {
            stringRedisTemplate.delete(Arrays.asList(accountKey, formatTokenKey(userType, token)));
        }
    }

    @Override
    public void delRelationshipByToken(int userType, String token, Boolean singleTokenWithUser) {
        String tokenKey = formatTokenKey(userType, token);

        if (singleTokenWithUser) {
            String account = getValue(tokenKey);
            stringRedisTemplate.delete(Arrays.asList(formatAccountKey(userType, account), tokenKey));
        } else {
            stringRedisTemplate.delete(tokenKey);
        }
    }

    /**
     *
     * 通过key获取缓存中的字符串值
     * @param key
     * @return
     */
    private String getValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 拼接账号key
     *
     * @param type
     * @param account
     * @return
     */
    private String formatAccountKey(int type, String account) {
        return KeyConstant.REDIS_ACCOUNT_PREFIX.concat(String.valueOf(type) + "_").concat(account);
    }

    /**
     * 拼接token key
     *
     * @param type
     * @param token
     * @return
     */
    private String formatTokenKey(int type, String token) {
        return KeyConstant.REDIS_TOKEN_PREFIX.concat(String.valueOf(type) + "_").concat(token);
    }

}
