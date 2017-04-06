package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.user.param.InviteFansParam;
import com.lawu.eshop.user.param.InviteInviteFansRealParam;
import com.lawu.eshop.user.param.ListFansParam;
import com.lawu.eshop.user.param.ListFansRealParam;
import com.lawu.eshop.user.srv.bo.FansMerchantBO;
import com.lawu.eshop.user.srv.converter.FansMerchantConverter;
import com.lawu.eshop.user.srv.domain.extend.FansMerchantDOView;
import com.lawu.eshop.user.srv.mapper.extend.FansMerchantDOMapperExtend;
import com.lawu.eshop.user.srv.service.FansMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/6.
 */
@Service
public class FansMerchantServiceImpl implements FansMerchantService {

    @Autowired
    private FansMerchantDOMapperExtend fansMerchantDOMapperExtend;

    @Override
    public List<FansMerchantBO> listInviteFans(InviteFansParam inviteFansParam) {
        InviteInviteFansRealParam inviteFansRealParam = new InviteInviteFansRealParam();
        inviteFansRealParam.setSex(inviteFansParam.getUserSexEnum().val);
        inviteFansRealParam.setRegionPath(inviteFansParam.getRegionPath());
        inviteFansRealParam.setStartAge(inviteFansParam.getStartAge());
        inviteFansRealParam.setEndAge(inviteFansParam.getEndAge());
        List<FansMerchantDOView> fansMerchantDOViewList = fansMerchantDOMapperExtend.listInviteFans(inviteFansRealParam);
        return FansMerchantConverter.convertBO(fansMerchantDOViewList);
    }

    @Override
    public List<FansMerchantBO> listFans(Long merchantId, ListFansParam listFansParam) {
        ListFansRealParam listFansRealParam = new ListFansRealParam();
        listFansRealParam.setMerchantId(merchantId);
        listFansRealParam.setSex(listFansParam.getUserSexEnum().val);
        listFansRealParam.setRegionPath(listFansParam.getRegionPath());
        listFansRealParam.setStartAge(listFansParam.getStartAge());
        listFansRealParam.setEndAge(listFansParam.getEndAge());
        List<FansMerchantDOView> fansMerchantDOViewList = fansMerchantDOMapperExtend.listFans(listFansRealParam);
        return FansMerchantConverter.convertBO(fansMerchantDOViewList);
    }

}
