package com.lawu.eshop.member.api.mock.service;

import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.FansInviteContentService;
import com.lawu.eshop.user.dto.FansInviteContentDTO;
@Service
public class MockFansInviteContentService extends BaseController implements FansInviteContentService {

	@Override
	public Result<FansInviteContentDTO> selectInviteContentById(Long id, Long relateId, Long memberId) {
		FansInviteContentDTO fansInviteContentDTO = new FansInviteContentDTO();
		return successCreated(fansInviteContentDTO);
	}

}
