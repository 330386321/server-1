package com.lawu.eshop.user.srv.service.impl.transaction;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.user.MerchantFansNotification;
import com.lawu.eshop.user.srv.bo.MerchantBO;
import com.lawu.eshop.user.srv.constants.TransactionConstant;
import com.lawu.eshop.user.srv.service.FansMerchantService;
import com.lawu.eshop.user.srv.service.MerchantService;

/**
 * @author meishuquan
 * @date 2017/9/21
 */
@Service("merchantFansTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.MERCHANT_FANS, topic = MqConstant.TOPIC_USER_SRV, tags = MqConstant.TAG_MERCHANT_FANS)
public class MerchantFansTransactionMainServiceImpl extends AbstractTransactionMainService<MerchantFansNotification, Reply> {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private FansMerchantService fansMerchantService;

    @Override
    public MerchantFansNotification selectNotification(Long merchantId) {
        MerchantBO merchantBO = merchantService.getMerchantBOById(merchantId);
        if (merchantBO == null) {
            return null;
        }
        int overdueFansCount = fansMerchantService.countOverdueFans(merchantId);
        MerchantFansNotification fansNotification = new MerchantFansNotification();
        fansNotification.setUserNum(merchantBO.getNum());
        fansNotification.setPoint(BigDecimal.valueOf(overdueFansCount));
        return fansNotification;
    }

}
