package com.lawu.eshop.merchant.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.merchant.api.service.MemberProfileService;
import com.lawu.eshop.user.dto.InviteeMechantCountDTO;
import com.lawu.eshop.user.dto.InviteeMemberCountDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author meishuquan
 * @date 2017/7/24.
 */
@Service
public class MockMemberProfileService extends BaseController implements MemberProfileService {
    @Override
    public Result<InviteeMemberCountDTO> getMemberCount(@RequestParam("id") Long id) {
        return successGet();
    }

    @Override
    public Result<InviteeMechantCountDTO> getMerchantCount(@RequestParam("id") Long id) {
        return successGet();
    }
}
