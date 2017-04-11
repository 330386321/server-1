package com.lawu.eshop.order.srv.constants;

/**
 * 分布式事务业务类型
 * @author Leach
 * @date 2017/3/29
 */
public class TransactionConstant {

    /**
     * 注册
     */
    public static final byte SETTLEMENT = 0x01;

    /**
     * 买单
     */
    public static final byte PAYORDER = 0x02;
}
