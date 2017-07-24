package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.MerchantInviterService;
import com.lawu.eshop.user.dto.MerchantInviterDTO;
import com.lawu.eshop.user.param.MerchantInviterParam;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class MockMerchantInviterService implements MerchantInviterService {

    @Override
    public Result<Page<MerchantInviterDTO>> getMerchantByInviter(@RequestParam("userId") Long id, @RequestBody MerchantInviterParam query, @RequestParam("inviterType") Byte inviterType) {
        return null;
    }
}
