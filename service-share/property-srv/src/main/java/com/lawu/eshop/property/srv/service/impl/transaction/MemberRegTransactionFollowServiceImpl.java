package com.lawu.eshop.property.srv.service.impl.transaction;

import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.property.srv.bo.RegNotification;
import org.springframework.stereotype.Service;

/**
 * @author Leach
 * @date 2017/3/29
 */
@Service
@CompensatingTransactionFollow(topic = "transaction-reg", tags = "property")
public class MemberRegTransactionFollowServiceImpl extends AbstractTransactionFollowService<RegNotification> {
    @Override
    public void execute(RegNotification notification) {

        // 注册用户后，初始化资金账户等
        // ...
    }
}
