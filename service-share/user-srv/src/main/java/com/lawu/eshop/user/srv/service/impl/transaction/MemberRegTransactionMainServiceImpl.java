package com.lawu.eshop.user.srv.service.impl.transaction;

import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.user.srv.bo.MemberBO;
import com.lawu.eshop.user.srv.bo.RegNotification;
import com.lawu.eshop.user.srv.constants.TransactionConstant;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.user.srv.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Leach
 * @date 2017/3/29
 */
@Service("memberRegTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.REGISTER, topic = MqConstant.MQ_TOPIC_USER_SRV, tags = MqConstant.MQ_TAG_REG)
public class MemberRegTransactionMainServiceImpl extends AbstractTransactionMainService<RegNotification> {

    @Autowired
    private MemberService memberService;

    @Override
    public RegNotification selectNotification(Long memberId) {
        MemberBO memberBO = memberService.getMemberById(memberId);
        if (memberBO == null) {
            return null;
        }
        RegNotification regNotification = new RegNotification();
        regNotification.setUserNum(memberBO.getNum());
        return regNotification;
    }


}
