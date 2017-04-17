package com.lawu.eshop.property.srv.service.impl.transaction;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.user.RegNotification;
import com.lawu.eshop.property.srv.bo.PropertyInfoBO;
import com.lawu.eshop.property.srv.service.PropertyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Leach
 * @date 2017/3/29
 */
@Service
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_USER_SRV, tags = MqConstant.TAG_REG)
public class MemberRegTransactionFollowServiceImpl extends AbstractTransactionFollowService<RegNotification, Reply> {

    @Autowired
    private PropertyInfoService propertyInfoService;

    @Override
    public Reply execute(RegNotification notification) {
        PropertyInfoBO propertyInfoBO = propertyInfoService.getPropertyInfoByUserNum(notification.getUserNum());
        if (propertyInfoBO != null) {
            return new Reply();
        }

        propertyInfoService.savePropertyInfo(notification.getUserNum());
        return new Reply();
    }
}
