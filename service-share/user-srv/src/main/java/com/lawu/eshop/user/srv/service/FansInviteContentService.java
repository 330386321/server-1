package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.user.param.FansInviteContentExtendParam;
import com.lawu.eshop.user.srv.bo.FansInviteContentBO;

public interface FansInviteContentService {

	
	Long saveInviteContentService(FansInviteContentExtendParam inviteContentParam);
	
	
	FansInviteContentBO selectInviteContentById(Long id);
}
