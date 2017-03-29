package com.lawu.eshop.property.srv.service.impl.transaction;

import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.property.srv.bo.RegNotification;
import com.lawu.eshop.property.srv.domain.PropertyInfoDO;
import com.lawu.eshop.property.srv.domain.PropertyInfoDOExample;
import com.lawu.eshop.property.srv.mapper.PropertyInfoDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Leach
 * @date 2017/3/29
 */
@Service
@CompensatingTransactionFollow(topic = "transaction-reg", tags = "property")
public class MemberRegTransactionFollowServiceImpl extends AbstractTransactionFollowService<RegNotification> {

    @Autowired
    private PropertyInfoDOMapper propertyInfoDOMapper;

    @Override
    public void execute(RegNotification notification) {
        PropertyInfoDOExample propertyInfoDOExample = new PropertyInfoDOExample();
        propertyInfoDOExample.createCriteria().andUserNumEqualTo(notification.getUserNum());
        List<PropertyInfoDO> propertyInfoDOList = propertyInfoDOMapper.selectByExample(propertyInfoDOExample);
        if (!propertyInfoDOList.isEmpty()) {
            return;
        }

        PropertyInfoDO propertyInfoDO = new PropertyInfoDO();
        propertyInfoDO.setUserNum(notification.getUserNum());
        propertyInfoDO.setGmtCreate(new Date());
        propertyInfoDOMapper.insertSelective(propertyInfoDO);
    }
}
