package com.lawu.eshop.compensating.transaction;

import java.util.List;

/**
 * 事务状态服务类
 * @author Leach
 * @date 2017/3/29
 */
public interface TransactionStatusService {

    /**
     * 保存事务状态信息
     * @param relateId
     * @param type
     * @return
     */
    Long save(Long relateId, byte type);

    /**
     * 事务处理成功
     *
     * @param transactionId
     * @return
     */
    Long success(Long transactionId);

    /**
     * 查出指定类型的所有未处理事务
     *
     * @param type
     * @return
     */
    List<Long> selectNotDoneList(byte type);

}
