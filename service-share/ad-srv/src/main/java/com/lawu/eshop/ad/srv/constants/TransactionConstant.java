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
    
    public static final byte AD_CLICK_POINT = 0x04;
    
    //用户红包退还
    public static final byte USER_REDPACKED_CANNEL_REFUND_MONEY=0x05;

    //用户领取红包
    public static final byte USER_REDPACKED_GET_MONEY=0x06;
    
   //用户领取商家红包
    public static final byte USER_SWEET_MONEY=0x07;
    
    //用户领取平台红包
    public static final byte USER_TKAE_PLAT_MONEY=0x08;
    
    
   
}
