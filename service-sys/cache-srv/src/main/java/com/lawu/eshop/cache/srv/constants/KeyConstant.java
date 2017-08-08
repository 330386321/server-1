package com.lawu.eshop.cache.srv.constants;

/**
 * 缓存key常量
 * @author Leach
 * @date 2017/3/19
 */
public class KeyConstant {


    /**
     * 会员账号-token
     */
    public static final String REDIS_KEY_MEMBER_PREFIX = "AUTHORIZATION_KEY_MEMBER_";

    /**
     * 会员token-账号
     */
    public static final String REDIS_TOKEN_MEMBER_PREFIX = "AUTHORIZATION_TOKEN_MEMBER_";


    /**
     * 商家账号-token
     */
    public static final String REDIS_KEY_MERCHANT_PREFIX = "AUTHORIZATION_KEY_MERCHANT_";

    /**
     * 商家token-账号
     */
    public static final String REDIS_TOKEN_MERCHANT_PREFIX = "AUTHORIZATION_TOKEN_MERCHANT_";
    
    /**
     * 广告ID
     */
    public static final String REDIS_KEY_AD = "AD_KEY_MEMBER_ID_";
    
    /**
     * 事务定时任务执行次数
     */
    public static final String REDIS_KEY_TRANSACTION_EXECUTION_COUNT_PREFIX = "TRANSACTION_EXECUTION_COUNT_";

    /**
     * 会员接口访问次数
     */
    public static final String REDIS_KEY_USER_VISIT_COUNT = "USER_VISIT_COUNT_";

    /**
     * 会员接口访问次数
     */
    public static final String REDIS_KEY_MERCHANT_VISIT_COUNT = "MERCHANT_VISIT_COUNT_";

    /**
     * 新店推荐
     */
    public static final String REDIS_KEY_MERCHANT_NEW_STORE = "MERCHANT_NEW_STORE_";

    /**
     * 优选美食-人气最高
     */
    public static final String REDIS_KEY_MERCHANT_CONSUME_STORE = "MERCHANT_CONSUME_STORE_";

    /**
     * 优选美食-评价最高
     */
    public static final String REDIS_KEY_MERCHANT_COMMENT_STORE = "MERCHANT_COMMENT_STORE_";

}
