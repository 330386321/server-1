package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.FansMerchantService;
import com.lawu.eshop.user.constants.FansMerchantChannelEnum;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class MockFansMerchantService extends BaseController implements FansMerchantService {

    @Override
    public Result<Boolean> isFansMerchant(@PathVariable("merchantId") Long merchantId, @RequestParam("memberId") Long memberId) {
        return successCreated(true);
    }

    @Override
    public List<Long> findMerchant(@RequestParam("memberId") Long memberId) {
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        return list;
    }

    @Override
    public Result saveFansMerchant(@PathVariable("merchantId") Long merchantId, @RequestParam("memberId") Long memberId, @RequestParam("channelEnum") FansMerchantChannelEnum channelEnum) {
        return successCreated();
    }

    @Override
    public Result<Integer> countFans(@PathVariable("merchantId") Long merchantId) {
        return null;
    }
}
