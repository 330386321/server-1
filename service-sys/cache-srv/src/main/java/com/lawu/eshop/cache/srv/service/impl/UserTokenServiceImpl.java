package com.lawu.eshop.cache.srv.service.impl;

import com.lawu.eshop.cache.srv.constants.KeyConstant;
import com.lawu.eshop.cache.srv.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author Leach
 * @date 2017/3/20
 */
@Service
public class UserTokenServiceImpl implements UserTokenService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void setMemberTokenOneToOne(String account, String token, Integer expireSeconds) {

        String accountKey = formatMemberKey(account);

        String oldToken = getValue(accountKey);
        if (oldToken != null) {
            String oldTokenKey = formatMemberToken(oldToken);
            stringRedisTemplate.delete(oldTokenKey);
        }
        stringRedisTemplate.opsForValue().set(formatMemberKey(account), token, expireSeconds);
        stringRedisTemplate.opsForValue().set(formatMemberToken(token), account, expireSeconds);
    }

    @Override
    public void setMemberTokenOneToMany(String account, String token, Integer expireSeconds) {

        stringRedisTemplate.opsForValue().set(formatMemberToken(token), account, expireSeconds);
    }

    @Override
    public String getMemberAccount(String token, Boolean flushExpireAfterOperation, Integer expireSeconds, Boolean singleTokenWithUser) {
        String tokenKey = formatMemberToken(token);
        String account = getValue(tokenKey).toString();
        //根据设置，在每次有效操作后刷新过期时间
        if (account != null && flushExpireAfterOperation) {
            if (singleTokenWithUser) {
                stringRedisTemplate.expire(formatMemberKey(account), expireSeconds, TimeUnit.SECONDS);

            }
            stringRedisTemplate.expire(tokenKey, expireSeconds, TimeUnit.SECONDS);

        }
        return account;
    }

    @Override
    public void delMemberRelationshipByAccount(String account) {
        String accountKey = formatMemberKey(account);
        String token = getValue(accountKey);
        if (token != null) {
            stringRedisTemplate.delete(Arrays.asList(accountKey, formatMemberToken(token)));
        }
    }

    @Override
    public void delMemberRelationshipByToken(String token, Boolean singleTokenWithUser) {
        String tokenKey = formatMemberToken(token);

        if (singleTokenWithUser) {
            String account = getValue(tokenKey);
            stringRedisTemplate.delete(Arrays.asList(formatMemberKey(account), tokenKey));
        } else {
            stringRedisTemplate.delete(tokenKey);
        }
    }

    private String getValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }


    private String formatMemberKey(String account) {
        return KeyConstant.REDIS_KEY_MEMBER_PREFIX.concat(account);
    }

    private String formatMemberToken(String token) {
        return KeyConstant.REDIS_TOKEN_MEMBER_PREFIX.concat(token);
    }


}
