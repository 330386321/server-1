package com.lawu.eshop.authorization.manager;

/**
 * @author Leach
 * @date 2017/3/19
 */
public interface CacheService {

    void setTokenOneToOne(String account, String token, Integer expireSeconds);

    void setTokenOneToMany(String account, String token, Integer expireSeconds);

    String getAccount(String token, Boolean flushExpireAfterOperation, Integer expireSeconds, Boolean singleTokenWithUser);

    void delRelationshipByAccount(String account);

    void delRelationshipByToken(String token, Boolean singleTokenWithUser);

}
