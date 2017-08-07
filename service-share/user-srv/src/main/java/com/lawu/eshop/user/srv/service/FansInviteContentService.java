package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.user.param.FansInviteContentParam;
import com.lawu.eshop.user.srv.bo.FansInviteContentBO;

public interface FansInviteContentService {

	
	Long saveInviteContentService(FansInviteContentParam inviteContentParam);
	
	FansInviteContentBO selectInviteContentById(Long id);
}
