package com.lawu.eshop.cache.srv.service;

/**
 * @author Leach
 * @date 2017/10/11
 */
public interface LoginTokenService {

    /**
     *
     * 通用参数说明：
     * userType 用户类型：由外部保证不同调用者不同值（会员、商家、代理商。。。）
     * account 登录账号
     * token 生成的token
     * expireSeconds token超时时间
     */

    /**
     * 单客户端登录情况下的token映射
     * 需要同时记录两个映射 account-token，token-account
     *
     * @param userType
     * @param account
     * @param token
     * @param expireSeconds
     */
    void setTokenOneToOne(int userType, String account, String token, Integer expireSeconds);

    /**
     * 多客户端登录情况下的token映射
     * 只需记录token-account
     *
     * @param userType
     * @param account
     * @param token
     * @param expireSeconds
     */
    void setTokenOneToMany(int userType, String account, String token, Integer expireSeconds);

    /**
     * 通过token获取账号
     *
     * @param userType
     * @param token
     * @param flushExpireAfterOperation 是否刷新超时时间
     * @param expireSeconds
     * @param singleTokenWithUser 是否单客户端登录
     * @return
     */
    String getAccount(int userType, String token, Boolean flushExpireAfterOperation, Integer expireSeconds, Boolean singleTokenWithUser);

    /**
     * 通过账号删除token（仅适用于单客户端登录）
     *
     * @param userType
     * @param account
     */
    void delRelationshipByAccount(int userType, String account);

    /**
     * 通过token删除账号信息
     *
     * @param userType
     * @param token
     * @param singleTokenWithUser 是否单客户端登录
     */
    void delRelationshipByToken(int userType, String token, Boolean singleTokenWithUser);

}
