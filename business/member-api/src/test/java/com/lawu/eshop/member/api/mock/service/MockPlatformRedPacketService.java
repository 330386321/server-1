package com.lawu.eshop.member.api.mock.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.ad.dto.GetPlatformRedPacketDTO;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.PlatformRedPacketService;

@Service
public class MockPlatformRedPacketService extends BaseController implements PlatformRedPacketService {

    @Override
    public Result<GetPlatformRedPacketDTO> getRedPacket(@RequestParam("userNum") String userNum) {
        return null;
    }
}
