package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.InviterService;
import com.lawu.eshop.user.dto.InviterDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
class MockInviterService extends BaseController implements InviterService {


    @Override
    public Result<InviterDTO> getInviterByAccount(@PathVariable("account") String account) {
        InviterDTO dto = new InviterDTO();
        dto.setInviterId(1L);
        dto.setUserNum("43434");
        return successCreated(dto);
    }
}
