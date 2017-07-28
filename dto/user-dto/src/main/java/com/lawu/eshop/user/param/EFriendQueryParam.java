package com.lawu.eshop.user.param;

import com.lawu.eshop.framework.core.page.AbstractPageParam;
import io.swagger.annotations.ApiParam;

public class EFriendQueryParam extends AbstractPageParam{

	@ApiParam (name="queryContent", value = "账号或昵称")
	private String queryContent;

	public String getQueryContent() {
		return queryContent;
	}

	public void setQueryContent(String queryContent) {
		this.queryContent = queryContent;
	}
}
