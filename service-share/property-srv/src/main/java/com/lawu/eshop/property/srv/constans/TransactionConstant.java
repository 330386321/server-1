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
    
    /**
     * 支付商品订单成功
     */
    public static final byte PAY_SHOPPING_ORDER = 0x04;

    /**
     * 交保证金成功
     */
    public static final byte HANDLE_DESPOISIT_SUCCESS = 0x05;

    /**
     * 交保证金财务审核成功
     */
    public static final byte HANDLE_DESPOISIT_AUDIT_PASS = 0x06;
    /**
     * 退保证金财务审核注销
     */
    public static final byte HANDLE_DESPOISIT_AUDIT_CANCEL = 0x07;
    /**
     * 用户发红包支付成功后修改红包状态
     */
    public static final byte HANDLE_MEMBER_RED_PACKET = 0x08;
}
