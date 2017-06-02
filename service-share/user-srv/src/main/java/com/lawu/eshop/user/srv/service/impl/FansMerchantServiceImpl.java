package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.user.constants.FansMerchantChannelEnum;
import com.lawu.eshop.user.param.ListFansParam;
import com.lawu.eshop.user.param.ListFansRealParam;
import com.lawu.eshop.user.param.ListInviteFansParam;
import com.lawu.eshop.user.param.ListInviteFansRealParam;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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
    public Page<FansMerchantBO> listInviteFans(Long merchantId, ListInviteFansParam param) {
        ListInviteFansRealParam listInviteFansRealParam = new ListInviteFansRealParam();
        listInviteFansRealParam.setMerchantId(merchantId);
        listInviteFansRealParam.setRegionPath(param.getRegionPath());
        listInviteFansRealParam.setSex(param.getUserSexEnum().val);
        listInviteFansRealParam.setAgeLimit(param.getIsAgeLimit());
        listInviteFansRealParam.setStartAge(param.getStartAge());
        listInviteFansRealParam.setEndAge(param.getEndAge());
        listInviteFansRealParam.setInviteCount(param.getInviteCount());
        listInviteFansRealParam.setCurrentPage(param.getCurrentPage());
        listInviteFansRealParam.setPageSize(param.getPageSize());
        List<FansMerchantDOView> fansMerchantDOViewList = fansMerchantDOMapperExtend.listInviteFans(listInviteFansRealParam);
        Page<FansMerchantBO> page = new Page<>();
        page.setCurrentPage(param.getCurrentPage());
        page.setTotalCount(fansMerchantDOMapperExtend.countInviteFans(listInviteFansRealParam));
        page.setRecords(FansMerchantConverter.convertBO(fansMerchantDOViewList));
        return page;
    }

    @Override
    public Page<FansMerchantBO> listFans(Long merchantId, ListFansParam listFansParam) {
        ListFansRealParam listFansRealParam = new ListFansRealParam();
        listFansRealParam.setMerchantId(merchantId);
        listFansRealParam.setRegionPath(listFansParam.getRegionPath());
        listFansRealParam.setCurrentPage(listFansParam.getCurrentPage());
        listFansRealParam.setPageSize(listFansParam.getPageSize());
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

    @Override
    public List<Long> findMerchant(Long memberId) {
        FansMerchantDOExample fansMerchantDOExample = new FansMerchantDOExample();
        fansMerchantDOExample.createCriteria().andMemberIdEqualTo(memberId);
        List<FansMerchantDO> fansMerchantDOS = fansMerchantDOMapper.selectByExample(fansMerchantDOExample);
        List<Long> merchantIds = new ArrayList<>();
        for (FansMerchantDO fansMerchantDO : fansMerchantDOS) {
            merchantIds.add(fansMerchantDO.getMerchantId());
        }
        return merchantIds;
    }

    @Override
    public Integer findFensCount(Long merchantId) {
        FansMerchantDOExample fansMerchantDOExample = new FansMerchantDOExample();
        fansMerchantDOExample.createCriteria().andMerchantIdEqualTo(merchantId);
        int count = fansMerchantDOMapper.countByExample(fansMerchantDOExample);
        return count;
    }

    @Override
    @Transactional
    public void saveFansMerchant(Long merchantId, Long memberId, FansMerchantChannelEnum channelEnum) {
        FansMerchantDO fansMerchantDO = new FansMerchantDO();
        fansMerchantDO.setMemberId(memberId);
        fansMerchantDO.setMerchantId(merchantId);
        fansMerchantDO.setChannel(channelEnum.getValue());
        fansMerchantDO.setGmtCreate(new Date());
        fansMerchantDOMapper.insertSelective(fansMerchantDO);
    }

}
