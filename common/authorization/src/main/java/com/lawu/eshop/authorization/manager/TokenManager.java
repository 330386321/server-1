package com.lawu.eshop.authorization.manager;

/**
 * 对Token进行管理的接口
 * 引入JWT 2017/03/15
 *
 * @author ScienJus
 * @author Leach
 *
 * @date 2015/7/31.
 */
public interface TokenManager {

    /**
     * 通过key删除关联关系
     * @param account
     */
    void delRelationship(String account);

    /**
     * 通过token删除关联关系
     * @param token
     */
    void delRelationshipByToken(String token);

    /**
     * 创建token
     * @param type
     * @param userNo
     * @param userId
     * @param account
     */
    String createToken(String type, String userNo, Long userId, String account);

    /**
     * 通过token获得对应的key
     * @param token
     * @return
     */
    String getAccount(String token);
}
