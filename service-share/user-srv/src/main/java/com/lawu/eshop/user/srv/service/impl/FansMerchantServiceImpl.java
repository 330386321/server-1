package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.user.param.InviteFansParam;
import com.lawu.eshop.user.param.ListFansParam;
import com.lawu.eshop.user.param.ListFansRealParam;
import com.lawu.eshop.user.srv.bo.FansMerchantBO;
import com.lawu.eshop.user.srv.converter.FansMerchantConverter;
import com.lawu.eshop.user.srv.domain.FansMerchantDO;
import com.lawu.eshop.user.srv.domain.FansMerchantDOExample;
import com.lawu.eshop.user.srv.domain.extend.FansMerchantDOView;
import com.lawu.eshop.user.srv.mapper.FansMerchantDOMapper;
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

    @Autowired
    private FansMerchantDOMapper fansMerchantDOMapper;

    @Override
    public List<FansMerchantBO> listInviteFans(Long merchantId, String regionPath) {
        InviteFansParam inviteFansParam = new InviteFansParam();
        inviteFansParam.setMerchantId(merchantId);
        inviteFansParam.setRegionPath(regionPath);
        List<FansMerchantDOView> fansMerchantDOViewList = fansMerchantDOMapperExtend.listInviteFans(inviteFansParam);
        return FansMerchantConverter.convertBO(fansMerchantDOViewList);
    }

    @Override
    public Page<FansMerchantBO> listFans(Long merchantId, ListFansParam listFansParam) {
        ListFansRealParam listFansRealParam = new ListFansRealParam();
        listFansRealParam.setMerchantId(merchantId);
        listFansRealParam.setRegionPath(listFansParam.getRegionPath());
        List<FansMerchantDOView> fansMerchantDOViewList = fansMerchantDOMapperExtend.listFans(listFansRealParam);

        Page<FansMerchantBO> page = new Page<>();
        page.setRecords(FansMerchantConverter.convertBO(fansMerchantDOViewList));
        page.setTotalCount(fansMerchantDOMapperExtend.countFans(listFansRealParam));
        page.setCurrentPage(listFansParam.getCurrentPage());
        return page;
    }

    @Override
    public FansMerchantBO getFansMerchant(Long memberId, Long merchantId) {
        FansMerchantDOExample fansMerchantDOExample = new FansMerchantDOExample();
        fansMerchantDOExample.createCriteria().andMemberIdEqualTo(memberId).andMerchantIdEqualTo(merchantId);
        List<FansMerchantDO> fansMerchantDOS = fansMerchantDOMapper.selectByExample(fansMerchantDOExample);
        return fansMerchantDOS.isEmpty() ? null : FansMerchantConverter.convertBO(fansMerchantDOS.get(0));
    }

}
