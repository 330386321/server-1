package com.lawu.eshop.compensating.transaction;

/**
 * @author Leach
 * @date 2017/3/29
 */
public abstract class Notification {
    private Long transactionId;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
}
