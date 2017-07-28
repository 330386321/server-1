package com.lawu.eshop.user.param;

import com.lawu.eshop.framework.core.page.AbstractPageParam;
import io.swagger.annotations.ApiParam;

import java.util.List;

public class EFriendQueryDataParam extends EFriendQueryParam{

	private Long userId;

	private String userNum;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

}
