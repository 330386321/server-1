package com.lawu.eshop.user.srv.service.impl.transaction;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.user.RegNotification;
import com.lawu.eshop.user.srv.bo.MerchantBO;
import com.lawu.eshop.user.srv.constants.TransactionConstant;
import com.lawu.eshop.user.srv.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Leach
 * @date 2017/3/29
 */
@Service("merchantRegTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.MERCHANT_REGISTER, topic = MqConstant.TOPIC_USER_SRV, tags = MqConstant.TAG_REG)
public class MerchantRegTransactionMainServiceImpl extends AbstractTransactionMainService<RegNotification, Reply> {

    @Autowired
    private MerchantService merchantService;

    @Override
    public RegNotification selectNotification(Long merchantId) {
        MerchantBO merchantBO = merchantService.getMerchantBOById(merchantId);
        if (merchantBO == null) {
            return null;
        }
        RegNotification regNotification = new RegNotification();
        regNotification.setUserNum(merchantBO.getNum());
        return regNotification;
    }


}
