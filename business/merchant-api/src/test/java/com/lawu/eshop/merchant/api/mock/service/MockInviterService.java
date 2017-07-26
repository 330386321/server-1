package com.lawu.eshop.merchant.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.merchant.api.service.InviterService;
import com.lawu.eshop.user.dto.InviterDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author meishuquan
 * @date 2017/7/24.
 */
@Service
public class MockInviterService extends BaseController implements InviterService {
    @Override
    public Result<InviterDTO> getInviterByAccount(@PathVariable("account") String account) {
        InviterDTO dto = new InviterDTO();
        dto.setInviterId(100L);
        dto.setUserNum("M0001");
        dto.setInviterName("test");
        return successGet(dto);
    }
}
