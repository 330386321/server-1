package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.user.param.FansInviteContentExtendParam;
import com.lawu.eshop.user.srv.bo.FansInviteContentBO;

public interface FansInviteContentService {

	
	Long saveInviteContentService(FansInviteContentExtendParam inviteContentParam);
	
	Long saveInviteContentExtendService(FansInviteContentExtendParam inviteContentParam);
	
	FansInviteContentBO selectInviteContentById(Long id);

	/**
	 * 处理过期的粉丝邀请(更改过期状态，删除粉丝记录，退还商家积分)
	 *
	 * @return
	 * @author meishuquan
	 */
	void dealOverdueFansInvite();
}
