package com.lawu.eshop.property.srv.constans;

/**
 * 
 * <p>
 * Description: 分布式事务业务类型
 * </p>
 * @author Yangqh
 * @date 2017年4月11日 下午7:46:08
 *
 */
public class TransactionConstant {

    /**
     * 订单余额支付
     */
    public static final byte ORDER_BALANCE_PAY = 0x01;
    /**
     * 买余额支付
     */
    public static final byte BILL_BALANCE_PAY = 0x02;

    /**
     * 买单
     */
    public static final byte PAYORDER = 0x03;
}
