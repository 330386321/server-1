package com.lawu.eshop.mall.srv.service;

import com.lawu.eshop.mall.srv.bo.AppVersionBO;
import com.lawu.eshop.mall.srv.domain.AppVersionDO;

public interface AppVersionService {

	AppVersionBO getVersion(int appType);
	
}
