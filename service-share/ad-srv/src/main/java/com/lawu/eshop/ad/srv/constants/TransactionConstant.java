package com.lawu.eshop.ad.srv.constants;

/**
 * 分布式事务业务类型
 * @author Leach
 * @date 2017/3/29
 */
public class TransactionConstant {
    
    public static final byte AD_ME_CUT_POINT = 0x01;
    
    public static final byte AD_ME_ADD_POINT = 0x02;
    
    public static final byte AD_USER_ADD_POINT = 0x03;
    
    public static final byte RP_ME_CUT_POINT = 0x04;
    
    public static final byte AD_CLICK_POINT = 0x05;
    
    /**
     * 用户红包退还
     */
    public static final byte USER_REDPACKED_MONEY_ADD=0x06;
    /**
     * 用户红包扣除金额
     */
    public static final byte USER_REDPACKED_CUT_MONEY=0x07;
    
    /**
     * 用户领取红包
     */
    public static final byte USER_REDPACKED_GET_MONEY=0x08;
   
}
