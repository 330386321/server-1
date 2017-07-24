package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.MerchantService;
import com.lawu.eshop.user.dto.MerchantBaseInfoDTO;
import com.lawu.eshop.user.dto.RongYunDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class MockMerchantService implements MerchantService {

    @Override
    public Result<RongYunDTO> getRongYunInfoByNum(@PathVariable("num") String num) {
        return null;
    }

    @Override
    public Result<MerchantBaseInfoDTO> getMerchantById(@PathVariable("merchantId") Long merchantId) {
        return null;
    }
}
