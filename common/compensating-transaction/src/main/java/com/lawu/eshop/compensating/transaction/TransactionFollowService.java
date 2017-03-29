package com.lawu.eshop.compensating.transaction;

/**
 * 补偿性事务从逻辑接口
 *
 * @author Leach
 * @date 2017/3/29
 */
public interface TransactionFollowService<V extends Notification, T>{

    /**
     * 通知其它模块进行补偿性事务处理
     *
     * @param notification
     */
    void receiveNotice(V notification);

    /**
     * 补偿成功回调
     *
     * @param reply
     */
    void sendCallback(T reply);

}
