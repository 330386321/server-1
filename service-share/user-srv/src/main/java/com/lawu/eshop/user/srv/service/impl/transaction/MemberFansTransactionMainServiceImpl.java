package com.lawu.eshop.user.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.user.MemberFansNotification;
import com.lawu.eshop.user.srv.bo.FansMerchantBO;
import com.lawu.eshop.user.srv.bo.MemberBO;
import com.lawu.eshop.user.srv.bo.MerchantStoreBO;
import com.lawu.eshop.user.srv.constants.TransactionConstant;
import com.lawu.eshop.user.srv.service.FansMerchantService;
import com.lawu.eshop.user.srv.service.MemberService;
import com.lawu.eshop.user.srv.service.MerchantStoreService;

/**
 * @author meishuquan
 * @date 2017/9/21
 */
@Service("memberFansTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.MEMBER_FANS, topic = MqConstant.TOPIC_USER_SRV, tags = MqConstant.TAG_MEMBER_FANS)
public class MemberFansTransactionMainServiceImpl extends AbstractTransactionMainService<MemberFansNotification, Reply> {

    @Autowired
    private MemberService memberService;

    @Autowired
    private FansMerchantService fansMerchantService;

    @Autowired
    private MerchantStoreService merchantStoreService;

    @Override
    public MemberFansNotification selectNotification(Long id) {
        FansMerchantBO fansMerchantBO = fansMerchantService.getFansMerchantById(id);
        MemberBO memberBO = memberService.getMemberById(fansMerchantBO.getMemberId());
        MerchantStoreBO merchantStoreBO = merchantStoreService.selectMerchantStore(fansMerchantBO.getMerchantId());
        MemberFansNotification fansNotification = new MemberFansNotification();
        fansNotification.setUserNum(memberBO.getNum());
        fansNotification.setMerchantStoreName(merchantStoreBO.getName());
        return fansNotification;
    }

}
