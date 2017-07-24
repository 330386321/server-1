package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.MemberProfileService;
import com.lawu.eshop.user.dto.InviteeMechantCountDTO;
import com.lawu.eshop.user.dto.InviteeMemberCountDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
class MockMemberProfileService implements MemberProfileService {


	@Override
	public Result<InviteeMemberCountDTO> getMemberCount(@RequestParam("id") Long id) {
		return null;
	}

	@Override
	public Result<InviteeMechantCountDTO> getMerchantCount(@RequestParam("id") Long id) {
		return null;
	}
}
