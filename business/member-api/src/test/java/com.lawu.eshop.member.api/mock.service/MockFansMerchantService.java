package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.FansMerchantService;
import com.lawu.eshop.user.constants.FansMerchantChannelEnum;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class MockFansMerchantService implements FansMerchantService {

    @Override
    public Result<Boolean> isFansMerchant(@PathVariable("merchantId") Long merchantId, @RequestParam("memberId") Long memberId) {
        return null;
    }

    @Override
    public List<Long> findMerchant(@RequestParam("memberId") Long memberId) {
        return null;
    }

    @Override
    public Result saveFansMerchant(@PathVariable("merchantId") Long merchantId, @RequestParam("memberId") Long memberId, @RequestParam("channelEnum") FansMerchantChannelEnum channelEnum) {
        return null;
    }

    @Override
    public Result<Integer> countFans(@PathVariable("merchantId") Long merchantId) {
        return null;
    }
}
