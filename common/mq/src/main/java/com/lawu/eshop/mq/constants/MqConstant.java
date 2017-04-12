package com.lawu.eshop.mq.constants;

/**
 * @author Leach
 * @date 2017/4/11
 */
public class MqConstant {


    /**topic定义*/

    /**
     * ad-srv模块MQ生产者topic
     */
    public static final String TOPIC_AD_SRV = "ad_srv";

    /**
     * mall-srv模块MQ生产者topic
     */
    public static final String TOPIC_MALL_SRV = "mall_srv";

    /**
     * order-srv模块MQ生产者topic
     */
    public static final String TOPIC_ORDER_SRV = "order_srv";

    /**
     * product-srv模块MQ生产者topic
     */
    public static final String TOPIC_PRODUCT_SRV = "product_srv";

    /**
     * property-srv模块MQ生产者topic
     */
    public static final String TOPIC_PROPERTY_SRV = "property_srv";

    /**
     * user-srv模块MQ生产者topic
     */
    public static final String TOPIC_USER_SRV = "user_srv";

    /******************************************/
    /*******ad-srv模块tag **********/
    public static final String TAG_AD_ME_CUT_POINT = "adMeCutPoint";
    
    public static final String TAG_AD_ME_ADD_POINT = "adMeAddPoint";
    
    public static final String TAG_AD_USER_ADD_POINT = "adUserAddPoint";


    /******************************************/
    /*******mall-srv模块tag **********/
    /**
     * 评论商品时发布的MQ消息
     */
    public static final String TAG_COMMENT_PRODUCT = "comment_product";
    /**
     * 评论商家时发布MQ消息
     */
    public static final String TAG_COMMENT_MERCHANT = "comment_merchant";


    /******************************************/
    /*******order-srv模块tag **********/

    /**
     * 买单时发布的MQ消息
     */
    public static final String TAG_PAYORDER = "payorder";


    /******************************************/
    /*******product-srv模块tag **********/


    /******************************************/
    /*******property-srv模块tag **********/


    /******************************************/
    /*******user-srv模块tag **********/

    /**
     * 注册时发布的MQ消息tag
     */
    public static final String TAG_REG = "reg";


}

